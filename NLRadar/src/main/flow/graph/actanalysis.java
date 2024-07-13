package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import dataStructure.FunctionTaintInfo;
import dataStructure.GlobalValue;
import dataflow.LocalVariableAnalysis;
import function.InfoPrint;
import soot.Body;
import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.Constant;
import soot.jimple.FieldRef;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.jimple.VirtualInvokeExpr;

class TraceValue{
    private FunctionTaintInfo functionTaintInfo;
    private java.util.List<Integer> reverseLocalsint = new ArrayList<Integer>();

    public TraceValue(FunctionTaintInfo functionTaintInfo, java.util.List<Integer> reverseLocal){
        this.functionTaintInfo = functionTaintInfo;
        this.reverseLocalsint = reverseLocal;
    }

    public TraceValue(FunctionTaintInfo functionTaintInfo){
        this.functionTaintInfo = functionTaintInfo;
    }

    public FunctionTaintInfo getFunctionTaintInfo(){
        return functionTaintInfo;
    }
    
    public java.util.List<Integer> getReverseLocals(){
        return reverseLocalsint;
    }
}

public class actanalysis {

    private static InfoPrint infoPrint = new InfoPrint();
    private static HashSet<FunctionTaintInfo> visitedmethod = new HashSet<FunctionTaintInfo>();
    private static HashMap<FunctionTaintInfo, HashSet<FunctionTaintInfo>> methodMap = new HashMap<FunctionTaintInfo, HashSet<FunctionTaintInfo>>();
    private static HashMap<FunctionTaintInfo,HashSet<FunctionTaintInfo>> reversemethodMap = new HashMap<FunctionTaintInfo,HashSet<FunctionTaintInfo>>();
    private static java.util.List<FunctionTaintInfo> sinkInfos = new ArrayList<FunctionTaintInfo>();

    private static String findingname = null;
    private static String  behaviorfilepath = null;
    private static String cgname = null;
    private static String comparefilepath = null;

        //print call graph tree
    private static void PrintCallTree(Set<FunctionTaintInfo> calledMethods, int level){
        if(calledMethods == null){
            return;
        }
        for(FunctionTaintInfo methodinfo: calledMethods){
            SootMethod method = methodinfo.getMethod();
            if(visitedmethod.contains(methodinfo)){
                //System.out.println(method.getSignature());
                continue;
            }
            if(GlobalValue.SinkMethods.contains(method.getSignature())||method.getSignature().contains("android.app.PendingIntent: void send")){
                System.out.println("Found one sink: " + method.getSignature());
                sinkInfos.add(methodinfo);
                infoPrint.writeToFile(cgname,"Found one sink: " + method.getSignature()+"\n");
            }
            if(GlobalValue.logMethods.contains(method.getSignature())){
                System.out.println("Found one log: " + method.getSignature());
                infoPrint.writeToFile(cgname,"Found one log: " + method.getSignature()+"\n");
                infoPrint.writeToFile(findingname,"Found one log: " + method.getSignature()+"\n");
            }
            for(int i = 0; i < level; i++){
                infoPrint.writeToFile(cgname,"--");
                System.out.print("  ");
            }
            infoPrint.writeToFile(cgname,method.getSignature()+"\n");
            System.out.println(method.getSignature());
            if(methodMap.get(methodinfo) == null){
                continue;
            }
            else{
                visitedmethod.add(methodinfo);
                PrintCallTree(methodMap.get(methodinfo), level + 1);
            }
        }
    }

    public static Set<Local> getTraceLocals(TraceValue traceValue){
        Set<Local> ret = new HashSet<Local>();
        FunctionTaintInfo functionTaintInfo = traceValue.getFunctionTaintInfo();
        Stmt stmt = functionTaintInfo.getStmt();
        java.util.List<Integer> reverseLocalsint = traceValue.getReverseLocals();
        for(int i=0;i<reverseLocalsint.size();i++){
            int index = reverseLocalsint.get(i);
            System.out.println(stmt);
            System.out.println("index: "+index);
            Value value = stmt.getInvokeExpr().getArg(index);
            if(value instanceof Local){
                Local local = (Local)value;
                ret.add(local);
            }
        }
        return ret;
    }

    public static java.util.List<String> findGetNotifValue(Body body,Local base){
        java.util.List<String> ret = new ArrayList<String>();
        Set<Local> locals = new HashSet<Local>();
        locals.add(base);
        Set<Unit> units = LocalVariableAnalysis.findallUses(body, locals);
        for(Unit unit :units){
            Stmt stmt = (Stmt)unit;
            if(stmt instanceof InvokeStmt){
                InvokeStmt invokeStmt = (InvokeStmt)stmt;
                InvokeExpr invokeExpr = invokeStmt.getInvokeExpr();
                if(invokeExpr instanceof VirtualInvokeExpr){
                    VirtualInvokeExpr virtualInvokeExpr = (VirtualInvokeExpr)invokeExpr;
                    if(virtualInvokeExpr.getMethod().getSignature().equals("<android.os.Bundle: java.lang.String getString(java.lang.String)>")){
                        Value value = virtualInvokeExpr.getArg(0);
                        if(value instanceof Constant){
                            Constant constant = (Constant)value;
                            ret.add(constant.toString());
                        }else{
                            ret.add("Local:"+value.toString());
                        }
                    }
                }
            }
        }

        return ret;
    }


    public static void traceback(FunctionTaintInfo functionTaintInfo){
        SootMethod sootMethod = functionTaintInfo.getMethod();
        List<String> valueList = new ArrayList<String>();
        List<String> behaviorList = new ArrayList<String>();
        List<String> behaviormethodlist = new ArrayList<String>();
        List<String> messageList = new ArrayList<String>();
        System.out.println("traceback: "+sootMethod.getSignature());
        if(GlobalValue.createNotificationMethods.contains(sootMethod.getSignature())){
            System.out.println("createNotification: "+sootMethod.getSignature());
        }
        Queue<TraceValue> queue = new LinkedList<TraceValue>();
        Stmt startstmt = functionTaintInfo.getStmt();
        System.out.println("start: "+startstmt);
        List<Integer> startIntegers = new ArrayList<Integer>();
        for(int i=0;i<startstmt.getInvokeExpr().getArgCount();i++){
            Value value = startstmt.getInvokeExpr().getArg(i);
            if(value instanceof Local){
                Local local = (Local)value;
                if(functionTaintInfo.getReverseTaintedLocals().contains(local)){
                    startIntegers.add(i);
                }
            }
        }
        TraceValue startvalue = new TraceValue(functionTaintInfo, startIntegers);
        queue.add(startvalue);
        while(!queue.isEmpty()){
            TraceValue tracevalue = queue.poll();
            FunctionTaintInfo from = tracevalue.getFunctionTaintInfo();
            System.out.println("from: "+from.getMethod().getSignature());
            System.out.println("tree: "+from.getMethod().getSignature()+"\n");

            if(reversemethodMap.containsKey(from)){
                HashSet<FunctionTaintInfo> functionTaintInfos = reversemethodMap.get(from);
                for(FunctionTaintInfo toinfo : functionTaintInfos){
                    SootMethod sootMethod2 = toinfo.getMethod();
                    if(sootMethod2.isConcrete()){
                        if(sootMethod2.getName().equals("onNotificationPosted")){
                            System.out.println("onNotificationPosted");
                            System.out.println("from: "+from.getMethod().getSignature());
                        
                            Stmt stmt = from.getStmt();
                            System.out.println("stmt: "+stmt);
                        }
                        System.out.println("to: "+sootMethod2.getSignature());
                        Body body = sootMethod2.retrieveActiveBody();
                        List<Local> traceLocallist = from.getReverseTaintedLocals();
                        
                        Set<Local> traceLocals = new HashSet<Local>();
                        for(Local local : traceLocallist){
                            traceLocals.add(local);
                            System.out.println("local: "+local);
                        }
                        Set<Local> taintedLocals = LocalVariableAnalysis.findallLocal(body, traceLocals);
                        Set<Unit> taintedUnits = LocalVariableAnalysis.findallUses(body, taintedLocals);
                        
                        //analysis

                        for(Unit unit : taintedUnits){
                            Stmt stmt = (Stmt)unit;
                            System.out.println(stmt);
                            if(stmt.containsInvokeExpr()){
                                InvokeExpr invokeExpr = (InvokeExpr)stmt.getInvokeExpr();
                                System.out.println("invoke: "+invokeExpr.getMethod().getSignature());
                                behaviormethodlist.add(invokeExpr.getMethod().getSignature());
                                if(GlobalValue.BehaviorMethods.contains(invokeExpr.getMethod().getSignature())){
                                    System.out.println("behavior: "+invokeExpr.getMethod().getSignature());
                                    System.out.println(stmt);
                                    List<String> valuetemp = new ArrayList<String>();
                                    for(Value value : invokeExpr.getArgs()){
                                        if(value instanceof Local){
                                            Local local = (Local)value;
                                            List<Unit> units =  LocalVariableAnalysis.findDefs(body, local);
                                            for(Unit unit1 : units){
                                                Stmt stmt1 = (Stmt)unit1;
                                                if(stmt1 instanceof AssignStmt){
                                                    AssignStmt assignStmt = (AssignStmt)stmt1;
                                                    Value rightValue = assignStmt.getRightOp();
                                                    Value leftValue = assignStmt.getLeftOp();
                                                    //如果是常量
                                                    if(rightValue instanceof Constant){
                                                        System.out.println("value: "+rightValue);
                                                        valueList.add(rightValue.toString());
                                                        valuetemp.add(rightValue.toString());
                                                    }
                                                    if(rightValue instanceof FieldRef){
                                                        try{
                                                            String valueString = LocalVariableAnalysis.getVariableName(rightValue);
                                                            System.out.println("value: "+valueString);
                                                            valueList.add(valueString);
                                                            valuetemp.add(valueString);
                                                        } catch (Exception e) {
                                                            System.out.println("error: "+e.toString());
                                                        }
                                                        
                                                    }
                                                    if(leftValue instanceof FieldRef){
                                                        try{
                                                            String valueString = LocalVariableAnalysis.getVariableName(leftValue);
                                                            System.out.println("value: "+valueString);
                                                            valueList.add(valueString);
                                                            valuetemp.add(valueString);
                                                        } catch (Exception e) {
                                                            System.out.println("error: "+e.toString());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if(value instanceof Constant){
                                    
                                            valueList.add(value.toString());
                                            valuetemp.add(value.toString());
                                        }
                                        else if(value instanceof FieldRef){
                                            try{
                                                String valueString = LocalVariableAnalysis.getVariableName(value);
                                                valueList.add(valueString);
                                                valuetemp.add(valueString);
                                            } catch (Exception e) {
                                                System.out.println("error: "+e.toString());
                                            }
                                        }
                                    }
                                    String outputString = "method"+invokeExpr.getMethod().getSignature()+"value : ";
                                    for(String string : valuetemp){
                                        outputString = outputString + string + " ";
                                    }
                                    behaviorList.add(outputString);
                                }
                                if(GlobalValue.StatusBarNotificationMethods.contains(invokeExpr.getMethod().getSignature())){
                                    System.out.println("message"+invokeExpr.getMethod().getSignature());
                                    messageList.add(invokeExpr.getMethod().getSignature());
                                    if(invokeExpr.getMethod().getSignature().equals("<android.service.notification.StatusBarNotification: android.app.Notification getNotification()>")){
                                        if(invokeExpr instanceof VirtualInvokeExpr){
                                            VirtualInvokeExpr virtualInvokeExpr = (VirtualInvokeExpr)invokeExpr;
                                            Local local = (Local)virtualInvokeExpr.getBase();
                                            List<String> strings = findGetNotifValue(body, local);
                                            for(String string : strings){
                                                System.out.println("value: "+string);
                                                messageList.add("<android.service.notification.StatusBarNotification: android.app.Notification getNotification()> "+string);
                                            }
                                        }
                                    }
                                }
                            }
                            for(String string: GlobalValue.StatusBarNotificationMethods){
                                if(stmt.toString().contains(string)){
                                    System.out.println("message: "+string);
                                    messageList.add(string.toString());
                                }
                            }
                        }

                        //queue add
                        List<Integer> reversetoint = new ArrayList<Integer>();
                        for(int i=0;i<body.getParameterLocals().size();i++){
                            if(taintedLocals.contains(body.getParameterLocals().get(i))){
                                reversetoint.add(i);
                            }
                        }
                        TraceValue tracevalue1 = new TraceValue(toinfo, reversetoint);
                        queue.add(tracevalue1);
                    }
                    else{
                        System.out.println("to: "+sootMethod2.getSignature());
                        //queue add
                        List<Integer> reversetoint = new ArrayList<Integer>();
                        for(int i=0;i<sootMethod2.getParameterCount();i++){
                            reversetoint.add(i);
                        }
                        TraceValue tracevalue1 = new TraceValue(toinfo, reversetoint);
                        queue.add(tracevalue1);
                    }
                }
            }
        }


        if(GlobalValue.createNotificationMethods.contains(sootMethod.getSignature())){
            Boolean flag = false;
            for(String string : behaviormethodlist){
                if(GlobalValue.modifyMethods.contains(string)){
                    System.out.println("fake notification");
                    flag = true;
                }
            }
        }

        
    }

    public static void parse(HashMap<FunctionTaintInfo, HashSet<FunctionTaintInfo>> methodMap1, String apkname){
        methodMap = methodMap1;
        behaviorfilepath = "Output/behavior/"+apkname+".txt";
        findingname = "Output/finding/"+apkname+".txt";
        cgname = "Output/cg/"+apkname+".txt";
        comparefilepath = "Output/compare/"+apkname+".txt";
        visitedmethod.clear();
        //reverse
        for(FunctionTaintInfo functionTaintInfo : methodMap.keySet()){
            HashSet<FunctionTaintInfo> functionTaintInfos = methodMap.get(functionTaintInfo);
            for(FunctionTaintInfo functionTaintInfo1 : functionTaintInfos){
                if(reversemethodMap.containsKey(functionTaintInfo1)){
                    HashSet<FunctionTaintInfo> functionTaintInfos1 = reversemethodMap.get(functionTaintInfo1);
                    functionTaintInfos1.add(functionTaintInfo);
                    reversemethodMap.put(functionTaintInfo1, functionTaintInfos1);
                }
                else{
                    HashSet<FunctionTaintInfo> functionTaintInfos1 = new HashSet<FunctionTaintInfo>();
                    functionTaintInfos1.add(functionTaintInfo);
                    reversemethodMap.put(functionTaintInfo1, functionTaintInfos1);
                }
            }
        }

        for(FunctionTaintInfo key: methodMap.keySet()){
            if(visitedmethod.contains(key)){
                continue;
            }
            SootMethod keyMethod = key.getMethod();
            System.out.println("key: "+keyMethod.getSignature());
            infoPrint.writeToFile(cgname,"key: "+keyMethod.getSignature()+"\n");
            visitedmethod.add(key);
            PrintCallTree(methodMap.get(key), 1);
        }

        for(FunctionTaintInfo sinkFunctionTaintInfo : sinkInfos){
            System.out.println("sink: "+sinkFunctionTaintInfo.getMethod().getSignature());
            infoPrint.writeToFile(findingname,"sink: "+sinkFunctionTaintInfo.getMethod().getSignature()+"\n");
            traceback(sinkFunctionTaintInfo);
        }

        
        
        for(FunctionTaintInfo functionTaintInfo : methodMap.keySet()){
            HashSet<FunctionTaintInfo> functionTaintInfos = methodMap.get(functionTaintInfo);
            SootMethod srcMethod = functionTaintInfo.getMethod();
            if(!srcMethod.isConcrete()){
                continue;
            }
            Body body = srcMethod.retrieveActiveBody();
            for(FunctionTaintInfo functionTaintInfo1 : functionTaintInfos){
                if(GlobalValue.BehaviorMethods.contains(functionTaintInfo1.getMethod().getSignature())){
                    System.out.println("function: "+functionTaintInfo1.getMethod().getSignature());
                    infoPrint.writeToFile(behaviorfilepath, "function: "+functionTaintInfo1.getMethod().getSignature()+"\n");
                
                    Stmt stmt = functionTaintInfo1.getStmt();
                    InvokeExpr invokeExpr = (InvokeExpr)stmt.getInvokeExpr();
                    for(Value value : invokeExpr.getArgs()){
                        if(value instanceof Local){
                            Local local = (Local)value;
                            List<Unit> units =  LocalVariableAnalysis.findDefs(body, local);
                            for(Unit unit1 : units){
                                Stmt stmt1 = (Stmt)unit1;
                                if(stmt1 instanceof AssignStmt){
                                    AssignStmt assignStmt = (AssignStmt)stmt1;
                                    Value rightValue = assignStmt.getRightOp();
                                    Value leftValue = assignStmt.getLeftOp();
                                    if(rightValue instanceof Constant){
                                        System.out.println("value: "+rightValue);
                                        infoPrint.writeToFile(behaviorfilepath, "value: "+value+"\n");
                                    }
                                    if(rightValue instanceof FieldRef){
                                        try{
                                            String valueString = LocalVariableAnalysis.getVariableName(rightValue);
                                            System.out.println("value: "+valueString);
                                            infoPrint.writeToFile(behaviorfilepath, "value: "+value+"\n");
                                        } catch (Exception e) {
                                            System.out.println("error: "+e.toString());
                                        }
                                        
                                    }
                                    if(leftValue instanceof FieldRef){
                                        try{
                                            String valueString = LocalVariableAnalysis.getVariableName(leftValue);
                                            System.out.println("value: "+valueString);
                                            infoPrint.writeToFile(behaviorfilepath, "value: "+value+"\n");
                                        } catch (Exception e) {
                                            System.out.println("error: "+e.toString());
                                        }
                                    }
                                }
                            }
                        }
                        else if(value instanceof Constant){
                            System.out.println("value: "+value);
                            infoPrint.writeToFile(behaviorfilepath, "value: "+value+"\n");
                        }
                        else if(value instanceof FieldRef){
                            try{
                                String valueString = LocalVariableAnalysis.getVariableName(value);
                                System.out.println("value: "+valueString);
                                infoPrint.writeToFile(behaviorfilepath, "value: "+value+"\n");
                            } catch (Exception e) {
                                System.out.println("error: "+e.toString());
                            }
                        }
                    }
                }
                if(GlobalValue.StatusBarNotificationMethods.contains(functionTaintInfo1.getMethod().getSignature())){
                    System.out.println("function: "+functionTaintInfo1.getMethod().getSignature());
                    infoPrint.writeToFile(behaviorfilepath, "function: "+functionTaintInfo1.getMethod().getSignature()+"\n");
                    infoPrint.writeToFile(comparefilepath, "message: "+functionTaintInfo1.getMethod().getSignature()+"\n");
                }
                if(GlobalValue.compareMethods.contains(functionTaintInfo1.getMethod().getSignature())){
                    System.out.println("function: "+functionTaintInfo1.getMethod().getSignature());
                    infoPrint.writeToFile(comparefilepath, "function: "+functionTaintInfo1.getMethod().getSignature()+"\n");
                    Stmt stmt = functionTaintInfo1.getStmt();
                    List<ValueBox> usesBoxs = stmt.getUseBoxes();
                    for(ValueBox valueBox : usesBoxs){
                        Value value = valueBox.getValue();
                        if(value instanceof Local){
                            Local local = (Local)value;
                            List<Unit> units =  LocalVariableAnalysis.findDefs(body, local);
                            for(Unit unit1 : units){
                                Stmt stmt1 = (Stmt)unit1;
                                if(stmt1 instanceof AssignStmt){
                                    AssignStmt assignStmt = (AssignStmt)stmt1;
                                    Value rightValue = assignStmt.getRightOp();
                                    Value leftValue = assignStmt.getLeftOp();
                                    if(rightValue instanceof Constant){
                                        System.out.println("value: "+rightValue);
                                        infoPrint.writeToFile(comparefilepath, "value: "+value+"\n");
                                    }
                                    if(rightValue instanceof FieldRef){
                                        try{
                                            String valueString = LocalVariableAnalysis.getVariableName(rightValue);
                                            System.out.println("value: "+valueString);
                                            infoPrint.writeToFile(comparefilepath, "value: "+value+"\n");
                                        } catch (Exception e) {
                                            System.out.println("error: "+e.toString());
                                        }
                                        
                                    }
                                    if(leftValue instanceof FieldRef){
                                        try{
                                            String valueString = LocalVariableAnalysis.getVariableName(leftValue);
                                            System.out.println("value: "+valueString);
                                            infoPrint.writeToFile(comparefilepath, "value: "+value+"\n");
                                        } catch (Exception e) {
                                            System.out.println("error: "+e.toString());
                                        }
                                    }
                                }
                                for(String string : GlobalValue.StatusBarNotificationMethods){
                                    if(stmt1.toString().contains(string)){
                                        System.out.println("message: "+stmt1);
                                        infoPrint.writeToFile(comparefilepath, "base: "+stmt1+"\n");
                                    }
                                }

                            }
                        }
                        else if(value instanceof Constant){
                            System.out.println("value: "+value);
                            infoPrint.writeToFile(comparefilepath, "value: "+value+"\n");
                        }
                        else if(value instanceof FieldRef){
                            try{
                                String valueString = LocalVariableAnalysis.getVariableName(value);
                                System.out.println("value: "+valueString);
                                infoPrint.writeToFile(comparefilepath, "value: "+value+"\n");
                            } catch (Exception e) {
                                System.out.println("error: "+e.toString());
                            }
                        }
                    }
                    InvokeExpr invokeExpr = (InvokeExpr)stmt.getInvokeExpr();
                }
            }
        }
    }
}
