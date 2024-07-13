package dataflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import dataStructure.FunctionTaintInfo;
import dataStructure.MyEdge;
import soot.Body;
import soot.Local;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootFieldRef;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.ArrayRef;
import soot.jimple.AssignStmt;
import soot.jimple.FieldRef;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.NewExpr;
import soot.jimple.Stmt;
import soot.jimple.VirtualInvokeExpr;
import soot.jimple.internal.JInstanceFieldRef;
import soot.jimple.internal.JReturnStmt;
import soot.jimple.internal.JVirtualInvokeExpr;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class FunctionTransferValue {

    public static List<MyEdge> findedgesinto(FunctionTaintInfo functionTaintInfo){
        Boolean TraceBackFlag = false;
        SootMethod srcMethod = functionTaintInfo.getMethod();
        Set<Local> srcLocals = functionTaintInfo.getTaintedLocals();
        List<MyEdge> myEdges = new ArrayList<MyEdge>();
        Set<Local> taintLocals = new HashSet<Local>();
        Queue<Local> queue = new LinkedList<Local>();
        Set<Unit> taintUnits = new HashSet<Unit>();
        Iterator<Edge> edgeIterator = null;
        Local Returnlocal = null;
        if(srcMethod.isConcrete()){
            Body body = srcMethod.retrieveActiveBody();

            if(functionTaintInfo.getIsConcrete() == false){
                System.out.println("functionTaintInfo is not concrete");
                return myEdges;
            }

            if(srcLocals == null){
                System.out.println("srcLocals is null");
                return myEdges;
            }

            for(Local srcLocal : srcLocals){
                taintLocals.add(srcLocal);
            }

            for(Local taintedLocal : taintLocals){
                queue.add(taintedLocal);
            }

            while(!queue.isEmpty()){
                Local local = queue.poll();
                Set<Unit> uses = LocalVariableAnalysis.findUses(body, local);
                for(Unit use : uses){
                    Stmt stmt = (Stmt) use;
                    System.out.println("use: "+stmt);
                    if(stmt instanceof AssignStmt){
                        AssignStmt assignStmt = (AssignStmt) stmt;
                        Value leftValue = assignStmt.getLeftOp();
                        if(leftValue instanceof Local){
                            Local leftLocal = (Local) leftValue;
                            if(!taintLocals.contains(leftLocal)){
                                taintLocals.add(leftLocal);
                                queue.add(leftLocal);
                            }
                        }
                        if(leftValue instanceof ArrayRef){
                            Local base = (Local) ((ArrayRef) leftValue).getBase();
                            if(!taintLocals.contains(base)){
                                taintLocals.add(base);
                                queue.add(base);
                            }
                        }
                        if(leftValue instanceof FieldRef){
                            FieldRef fieldRef = (FieldRef) leftValue;
                            if(fieldRef instanceof JInstanceFieldRef){
                                JInstanceFieldRef jInstanceFieldRef = (JInstanceFieldRef) fieldRef;
                                Local base = (Local) jInstanceFieldRef.getBase();

                                if(!taintLocals.contains(base)){
                                    taintLocals.add(base);
                                    queue.add(base);
                                }
                            }
                        }
                    }
                    if (stmt.containsInvokeExpr()){
                        InvokeExpr invokeExpr = stmt.getInvokeExpr();
                        if(stmt.toString().contains("android.content.Intent")){
                            if(invokeExpr instanceof VirtualInvokeExpr){
                                VirtualInvokeExpr virtualInvokeExpr = (VirtualInvokeExpr) invokeExpr;
                                Local base = (Local) virtualInvokeExpr.getBase();
                                if(!taintLocals.contains(base)){
                                    taintLocals.add(base);
                                    queue.add(base);
                                }
                            }
                        }
                        else{
                            List<ValueBox> usesBoxs = stmt.getUseBoxes();
                            for(ValueBox valueBox : usesBoxs){
                                Value value = valueBox.getValue();
                                if(value instanceof Local){
                                    Local localValue = (Local) value;
                                    if(!taintLocals.contains(localValue)){
                                        taintLocals.add(localValue);
                                        queue.add(localValue);
                                    }
                                }
                            }
                        }
                    }
                    taintUnits.add(stmt);
                }
            }

            for(Unit unit : body.getUnits()){
                Stmt stmt = (Stmt) unit;
                System.out.println("stmt: "+stmt);
                System.out.println(stmt.getClass().toString());
                if(stmt instanceof JReturnStmt){
                    System.out.println("stmt: "+stmt);
                  
                    JReturnStmt returnStmt = (JReturnStmt) stmt;
                    //Value ReturnValue = returnStmt.getOp();
                    Value ReturnValue = returnStmt.getOp();
                    if(ReturnValue instanceof Local){
                        Local returnLocal = (Local) ReturnValue;
                        if(taintLocals.contains(returnLocal)){
                            Returnlocal = returnLocal;
                            TraceBackFlag = true;
                            break;
                        }
                    }
                }
            }

            if(TraceBackFlag){
         
                CallGraph callGraph = Scene.v().getCallGraph();
                edgeIterator = callGraph.edgesInto(srcMethod);
                if (edgeIterator == null){
                    System.out.println("edgeIterator is null");
                    return null;
                }
                System.out.println("srcmethod: "+srcMethod);
                while(edgeIterator.hasNext()){
                    Edge edge = edgeIterator.next();
                    if(edge != null && edge.getSrc() != null){
                        SootMethod dgtMethod = edge.getSrc().method();
                        System.out.println("dgtMethod: "+dgtMethod);
                        if(dgtMethod.isConcrete()){
                            Set <Local> dgtTaintedLocals = new HashSet<Local>();
                            List <Local> dgtReverseTaintedLocals = new ArrayList<Local>();
                            Unit dgtUnit = edge.srcUnit();
                            Stmt dgtStmt = (Stmt) dgtUnit;
                            if(dgtStmt instanceof AssignStmt){
                                AssignStmt assignStmt = (AssignStmt) dgtStmt;
                                Value leftValue = assignStmt.getLeftOp();
                                if(leftValue instanceof Local){
                                    Local leftLocal = (Local) leftValue;
                                    dgtTaintedLocals.add(leftLocal);
                                    dgtReverseTaintedLocals.add(leftLocal);
                                    FunctionTaintInfo dgtFunctionTaintInfo = new FunctionTaintInfo(dgtMethod, dgtTaintedLocals,dgtReverseTaintedLocals, dgtStmt);
                                    MyEdge myEdge = new MyEdge(dgtFunctionTaintInfo,functionTaintInfo,dgtStmt);
                                    myEdges.add(myEdge);
                                }
                            }
                        }
                    }
                }
            }
        }
        return myEdges;
    }
    

    public static List<MyEdge> findedgesoutof(FunctionTaintInfo functionTaintInfo){
        SootMethod srcMethod = functionTaintInfo.getMethod();
        Set<Local> srcLocals = functionTaintInfo.getTaintedLocals();
        List<MyEdge> myEdges = new ArrayList<MyEdge>();
        Set<Local> taintLocals = new HashSet<Local>();
        Set<Local> taintLocalstemp = new HashSet<Local>();
        Queue<Local> queue = new LinkedList<Local>();
        Set<Unit> taintUnits = new HashSet<Unit>();
        Set<JInstanceFieldRef> fieldRefs = new HashSet<JInstanceFieldRef>();
        Boolean isFieldRelect = functionTaintInfo.getIsFieldReflect();

        if(srcMethod.isConcrete()){
            Body body = srcMethod.retrieveActiveBody();

            if(functionTaintInfo.getIsConcrete() == false){
                System.out.println("functionTaintInfo is not concrete");
                return myEdges;
            }

            if(srcLocals == null){
                System.out.println("srcLocals is null");
                return myEdges;
            }
            
            for(Local srcLocal : srcLocals){
                System.out.println("srcLocal: "+srcLocal);
                taintLocals.add(srcLocal);
                taintLocalstemp.add(srcLocal);
            }
            RefType contextType = Scene.v().getRefType("android.content.Context");

            for(Unit unit : body.getUnits()){
                Stmt stmt = (Stmt) unit;
                System.out.println("stmt: "+stmt);
                List<ValueBox> valueboxs = stmt.getUseAndDefBoxes();
                Boolean taintedflag = false;
                for(ValueBox valuebox : valueboxs){
                    System.out.println("valuebox: "+valuebox);
                    Value value = valuebox.getValue();
                    if(value instanceof Local){
                        Local local = (Local) value;
                        System.out.println("local: "+local);
                        if(taintLocalstemp.contains(local)){
                            taintedflag = true;
                        }
                    }
                }
                if(taintedflag){
                    taintUnits.add(stmt);
                    if(stmt instanceof AssignStmt){
                        AssignStmt assignStmt = (AssignStmt) stmt;
                        Value leftValue = assignStmt.getLeftOp();
                        if(leftValue instanceof Local){
                            Local leftLocal = (Local) leftValue;
                            if(!taintLocalstemp.contains(leftLocal)){
                                taintLocals.add(leftLocal);
                                taintLocalstemp.add(leftLocal);
                            }
                        }
                        if(leftValue instanceof ArrayRef){
                            Local base = (Local) ((ArrayRef) leftValue).getBase();
                            if(!taintLocalstemp.contains(base)){
                                taintLocals.add(base);
                                taintLocalstemp.add(base);
                            }
                        }
                        if(leftValue instanceof FieldRef){
                            FieldRef fieldRef = (FieldRef) leftValue;
                            if(fieldRef instanceof JInstanceFieldRef){
                                JInstanceFieldRef jInstanceFieldRef = (JInstanceFieldRef) fieldRef;
                                Local base = (Local) jInstanceFieldRef.getBase();
                                System.out.println(base);
                                if(!taintLocalstemp.contains(base)){
                                    taintLocals.add(base);
                                    taintLocalstemp.add(base);
                                }
                            }
                        }
                    }
                    if (stmt.containsInvokeExpr()){
                        InvokeExpr invokeExpr = stmt.getInvokeExpr();
                        List<ValueBox> usesBoxs = stmt.getUseBoxes();
                        for(ValueBox valueBox : usesBoxs){
                            Value value = valueBox.getValue();
                            System.out.println(value);
                            if(value instanceof Local){
                                Local localValue = (Local) value;
                                if(localValue.getType() instanceof RefType){
                                    RefType type = (RefType) localValue.getType();
                                    if(type.equals(contextType)){
                                        continue;
                                    }
                                }
                                if(!taintLocalstemp.contains(localValue)){
                                    taintLocals.add(localValue);
                                    taintLocalstemp.add(localValue);
                                }
                            }
                        }
                    }
                    if (stmt instanceof AssignStmt){
                        Value rightValue = ((AssignStmt) stmt).getRightOp();
                        if (rightValue instanceof NewExpr){
                            NewExpr newExpr = (NewExpr) rightValue;
                            Value leftValue = ((AssignStmt) stmt).getLeftOp();
                            if(leftValue instanceof Local){
                                Local leftLocal = (Local) leftValue;
                                if(taintLocalstemp.contains(leftLocal)){
                                    taintLocalstemp.remove(leftLocal);
                                }
                            }
                        }
                    }
                }
            }

            
            Iterator<Local> iterator = taintLocals.iterator();
            while (iterator.hasNext()) {
                Local local = iterator.next();
                if (local.getType() instanceof RefType) {
                    RefType type = (RefType) local.getType();
                    if (type.equals(contextType)) {
                        System.out.println("Found a Context variable: " + local.getName() + " in method " + body.getMethod());
                        iterator.remove();
                    }
                }
            }

            for(Unit taintUnit : taintUnits){
                System.out.println("taintUnit: "+taintUnit);
                Stmt stmt = (Stmt) taintUnit;
                if(stmt.containsInvokeExpr()){
                    SootMethod dgtMethod = stmt.getInvokeExpr().getMethod();
                    if(dgtMethod.isConcrete()){
                        Set <Local> dgtTaintedLocals = new HashSet<Local>();
                        List <Local> dgtReverseTaintedLocals = new ArrayList<Local>();
                        List<Value> arguments = stmt.getInvokeExpr().getArgs();
                        for(int i=0;i<arguments.size();i++){
                            Value argument = arguments.get(i);
                            if(argument instanceof Local){
                                Local argumentLocal = (Local) argument;
                                if(taintLocals.contains(argumentLocal)){
                                    Body dgtbody = dgtMethod.getActiveBody();
                                    dgtTaintedLocals.add(dgtbody.getParameterLocal(i));
                                    dgtReverseTaintedLocals.add(argumentLocal);
                                    System.out.println("dgtbody.getParameterLocal(i): "+dgtbody.getParameterLocal(i));
                                    System.out.println("argumentLocal: "+argumentLocal);
                                }
                            }
                        }
                        FunctionTaintInfo dgtFunctionTaintInfo = new FunctionTaintInfo(dgtMethod, dgtTaintedLocals,dgtReverseTaintedLocals, stmt);
                        MyEdge myEdge = new MyEdge(functionTaintInfo, dgtFunctionTaintInfo, stmt);
                        myEdges.add(myEdge);
                    }
                    else{
                        Set <Local> dgtTaintedLocals = new HashSet<Local>();
                        List <Local> dgtReverseTaintedLocals = new ArrayList<Local>();
                        if(stmt instanceof InvokeStmt){
                            InvokeStmt invokeStmt = (InvokeStmt) stmt;
                            List<Value> arguments = invokeStmt.getInvokeExpr().getArgs();
                            for(int i=0;i<arguments.size();i++){
                                Value argument = arguments.get(i);
                                if(argument instanceof Local){
                                    Local argumentLocal = (Local) argument;
                                    if(taintLocals.contains(argumentLocal)){
                                        dgtTaintedLocals.add(argumentLocal);
                                        dgtReverseTaintedLocals.add(argumentLocal);
                                    }
                                }
                            }
                            if(stmt.toString().contains("<android.app.PendingIntent: void send")){
                                InvokeExpr invokeExpr = invokeStmt.getInvokeExpr();
                                if(invokeExpr instanceof JVirtualInvokeExpr){
                                    JVirtualInvokeExpr jVirtualInvokeExpr = (JVirtualInvokeExpr) invokeExpr;
                                    Local base = (Local) jVirtualInvokeExpr.getBase();
                                    if(taintLocals.contains(base)){
                                        dgtTaintedLocals.add(base);
                                        dgtReverseTaintedLocals.add(base);
                                    }
                                }
                            }
                            if(stmt.toString().contains("android.content.SharedPreferences$Editor")){
                                if(dgtTaintedLocals.isEmpty()){
                                    continue;
                                }
                            }
                        }
                        FunctionTaintInfo dgtFunctionTaintInfo = new FunctionTaintInfo(dgtMethod,dgtTaintedLocals,dgtReverseTaintedLocals,stmt);
                        dgtFunctionTaintInfo.setIsConcrete(false);
                        MyEdge myEdge = new MyEdge(functionTaintInfo, dgtFunctionTaintInfo, stmt);
                        myEdges.add(myEdge);
                    }
                    
                }
                if (stmt instanceof AssignStmt && isFieldRelect==false){
                    AssignStmt assignStmt = (AssignStmt) stmt;
                    System.out.println(stmt);
                    Value rightValuetmp = assignStmt.getRightOp();
                    Value leftValuetmp = assignStmt.getLeftOp();
                    Value rightValue;
                    Value leftValue;
                    if(rightValuetmp instanceof JInstanceFieldRef){
                        rightValue = rightValuetmp;
                        leftValue = leftValuetmp;
                    }
                    else{
                        rightValue = leftValuetmp;
                        leftValue = rightValuetmp;
                    }
                    if(taintLocals.contains(leftValue)&&rightValue instanceof JInstanceFieldRef){
                        JInstanceFieldRef jInstanceFieldRef = (JInstanceFieldRef) rightValue;
                        Local base = (Local) jInstanceFieldRef.getBase();
                        if(base.toString()=="r0"&&fieldRefs.contains(jInstanceFieldRef)==false){
                            SootFieldRef fieldRef = jInstanceFieldRef.getFieldRef();
                            System.out.println("fieldRef: "+fieldRef);
                            String name = fieldRef.name();
                            System.out.println("name: "+name);
                            String fieldType = fieldRef.type().toString();
                            System.out.println("fieldType: "+fieldType);
                            SootClass sootclass = srcMethod.getDeclaringClass();

                            List<SootMethod> sootMethods = sootclass.getMethods();
                            for(int i = 0; i < sootMethods.size(); i++){
                                SootMethod sootmethod = sootMethods.get(i);
                                if(sootmethod.getSignature().toString().equals(srcMethod.getSignature().toString())){
                                    continue;
                                }
                                if(sootmethod.isConcrete()){
                                    Set <Local> dgtTaintedLocals = new HashSet<Local>();
                                    Body sootBody = sootmethod.getActiveBody();
                                    Stmt recordstmt = null;
                                    for(Unit sootUnit : sootBody.getUnits()){
                                        Stmt sootStmt = (Stmt) sootUnit;
                                        if(sootStmt instanceof AssignStmt){
                                            Value sootRightValue = ((AssignStmt) sootStmt).getRightOp();
                                            Value sootLeftValue = ((AssignStmt) sootStmt).getLeftOp();
                                            if(sootRightValue instanceof JInstanceFieldRef){
                                                JInstanceFieldRef sootJInstanceFieldRef = (JInstanceFieldRef) sootRightValue;
                                                if(sootJInstanceFieldRef.toString().equals(jInstanceFieldRef.toString())&&sootLeftValue instanceof Local){
                                                    dgtTaintedLocals.add((Local) sootLeftValue);
                                                }
                                                recordstmt = sootStmt;
                                            }
                                        }
                                    }
                                    if(dgtTaintedLocals.size()>0){
                                        List<Local> dgtReverseTaintedLocals = new ArrayList<Local>();
                                        dgtReverseTaintedLocals.add((Local)leftValue);
                                        FunctionTaintInfo dgtFunctionTaintInfo = new FunctionTaintInfo(sootmethod,dgtTaintedLocals,dgtReverseTaintedLocals,recordstmt,true);
                                        MyEdge myEdge = new MyEdge(functionTaintInfo, dgtFunctionTaintInfo, stmt);
                                        myEdges.add(myEdge);
                                    }
                                }
                            }
                        }
                        fieldRefs.add(jInstanceFieldRef);
                    }

                }
            }

        }
        System.out.println("myEdges.size(): "+myEdges.size());

        return myEdges;
    }
}
