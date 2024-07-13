package graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

import dataStructure.FunctionTaintInfo;
import dataStructure.GlobalValue;
import dataStructure.MyEdge;
import dataStructure.locationValue;
import dataflow.FunctionTransferValue;
import dataflow.LocalVariableAnalysis;
import function.InfoPrint;
import polyglot.types.reflect.Constant;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.FieldRef;
import soot.jimple.InstanceFieldRef;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.ParameterRef;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.jimple.VirtualInvokeExpr;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.util.Chain;
import utils.ReadICCBot;


class sharedpreferenceinfo {
    String method;
    Set<Local> taintedLocals = new HashSet<Local>();

    public sharedpreferenceinfo(String method,Stmt stmt){
        this.method = method;
        if(stmt.containsInvokeExpr()){
            InvokeExpr invokeExpr = stmt.getInvokeExpr();
            if(invokeExpr instanceof VirtualInvokeExpr){
                VirtualInvokeExpr virtualInvokeExpr = (VirtualInvokeExpr) invokeExpr;
                Local local = (Local) virtualInvokeExpr.getBase();
                taintedLocals.add(local);
            }
            else if(invokeExpr.getArgCount() > 0){
                for(Value value: invokeExpr.getArgs()){
                    if(value instanceof Local){
                        Local local = (Local) value;
                        taintedLocals.add(local);
                    }
                }
            }
        }
        if(stmt instanceof AssignStmt){
            AssignStmt assignStmt = (AssignStmt) stmt;
            Value leftValue = assignStmt.getLeftOp();
            if(leftValue instanceof Local){
                taintedLocals.add((Local) leftValue);
            }
        }
    }

    public String getmethod(){
        return method;
    }

    public Set<Local> gettaintedLocals(){
        return taintedLocals;
    }
}


public class cganalysis {

    private static List<SootMethod> sourmethodfound = new ArrayList<SootMethod>();
    
    private static List<locationValue> registerReceiverStmtList = new ArrayList<>();
    
    private static List<locationValue> sharedpreferenceStmtList = new ArrayList<>();
    private static List<locationValue> threadClassList = new ArrayList<>();
    private static List<locationValue> AsyncTaskList = new ArrayList<>();
    private static List<locationValue> threadRunList = new ArrayList<>();
    private static List<locationValue> databaseList = new ArrayList<>();
    private static List<locationValue> fileopList = new ArrayList<>();
    private static HashMap<FunctionTaintInfo, HashSet<FunctionTaintInfo>> methodMap = new HashMap<>();
    
    private static HashSet<FunctionTaintInfo> visitedmethod = new HashSet<FunctionTaintInfo>(); 
    private static InfoPrint infoPrint = new InfoPrint();
    private static String cgname = null;
    private static String findingname = null;
    private static String jimplename = null;
    private static String apkname = null;
    private static String errorname = null;
    private static String ICCbotname = null;
    private static ReadICCBot readICCBot = new ReadICCBot();

    public static boolean isMethodOverridden(SootClass sootClass, SootClass superClass, SootMethod methodToCheck) {
        while (superClass != null) {
            for (SootMethod superMethod : superClass.getMethods()) {
                if (superMethod.getSubSignature().equals(methodToCheck.getSubSignature())) {
                    return true;
                }
            }
            superClass = superClass.getSuperclass();
        }
        return false;
    }


    public static void constructflow(String apkname1){
        apkname = apkname1;
        cgname = "Output/cg/"+apkname1+".txt";
        findingname = "Output/finding/"+apkname1+".txt";
        jimplename = "Output/jimple/"+apkname1+".txt";
        errorname = "Output/error/"+apkname1+".txt";
        ICCbotname = GlobalValue.ICCBOT_OUTPUT_PATH + apkname1.substring(0,apkname1.length()-9);
        List<Element> elements = readICCBot.readIntentSummaryModel(ICCbotname,"CTGResult\\ICCBotResult.xml");

        try{
            for(Iterator<SootClass> iter = Scene.v().getClasses().snapshotIterator(); iter.hasNext();){
                SootClass sootclass = iter.next();
                List<SootMethod> sootMethods = sootclass.getMethods();
                for(int i = 0; i < sootMethods.size(); i++){
                    SootMethod sootmethod = sootMethods.get(i);
                    if(sootmethod.isConcrete()){
                        if(GlobalValue.SourMethods.contains(sootmethod.getSignature())||GlobalValue.SourMethods.contains(sootmethod.getName())){
                            SootClass superClass = sootclass.getSuperclass();
                            boolean isOverridden = isMethodOverridden(sootclass, superClass, sootmethod);
                            System.out.println("SourMethod: "+sootmethod.getSignature());
                            
                            if( isOverridden == true){
                                sourmethodfound.add(sootmethod);
                            }
                        }
                        Body body = sootmethod.retrieveActiveBody();
                        if(body == null){
                            continue;
                        }
                        for(Unit unit: body.getUnits()){
                            if (unit instanceof AssignStmt){
                                if (((AssignStmt) unit).getRightOp() instanceof InvokeExpr){
                                    if(GlobalValue.SourMethods.contains(((InvokeExpr) ((AssignStmt) unit).getRightOp()).getMethodRef().getSignature())||GlobalValue.SourMethods.contains(((InvokeExpr) ((AssignStmt) unit).getRightOp()).getMethodRef().getName())){
                                        System.out.println("Found one source: " + unit.toString() + " in " + sootmethod.getSignature());
                                        
                                        if(!sourmethodfound.contains(sootmethod)){
                                            sourmethodfound.add(sootmethod);
                                        }
                                    }

                                }
                            }
                            Stmt stmt = (Stmt) unit;
                            
                            if(stmt.containsInvokeExpr()){
                                InvokeExpr invokeExpr = stmt.getInvokeExpr();
                                SootMethod method = invokeExpr.getMethod();
                                if(invokeExpr.getMethod().getName().equals("registerReceiver")){
                                    locationValue locationvalue = new locationValue(sootmethod,body,stmt);
                                    registerReceiverStmtList.add(locationvalue);
                                }
                            }
                            
                            
                            if(stmt.containsInvokeExpr() && (stmt.getInvokeExpr().getMethod().getName().equals("getSharedPreferences")
                            ||stmt.getInvokeExpr().getMethod().equals("<android.preference.PreferenceManager: android.content.SharedPreferences getDefaultSharedPreferences(android.content.Context)>")
                            ||stmt.getInvokeExpr().getMethod().equals("<android.content.SharedPreferences: java.lang.String getString(java.lang.String,java.lang.String)>")
                            ||(stmt.getInvokeExpr().getMethod().toString().contains("get")&&stmt.getInvokeExpr().getMethod().toString().contains("SharedPreferences")))){
                                locationValue locationvalue = new locationValue(sootmethod,body,stmt);
                                sharedpreferenceStmtList.add(locationvalue);
                            }
    
                            if(stmt.containsInvokeExpr()&&stmt.getInvokeExpr().getMethod().getSignature().equals("<java.lang.Thread: void <init>()>")){
                                System.out.println("Found one thread: " + unit.toString() + " in " + sootmethod.getSignature());
                                locationValue locationvalue = new locationValue(sootmethod,body,stmt);
                                System.out.println(sootmethod.getDeclaringClass().getName());
                                threadClassList.add(locationvalue);
                            }

                            if(stmt.containsInvokeExpr()&&stmt.getInvokeExpr().getMethod().getSignature().equals("<android.os.AsyncTask: void <init>()>")){
                                System.out.println("Found one thread: " + unit.toString() + " in " + sootmethod.getSignature());
                                locationValue locationvalue = new locationValue(sootmethod,body,stmt);
                                System.out.println(sootmethod.getDeclaringClass().getName());
                                AsyncTaskList.add(locationvalue);
                            }

                            if(stmt.containsInvokeExpr()&&stmt.getInvokeExpr().getMethod().getSignature().equals("<android.database.sqlite.SQLiteDatabase: android.database.Cursor query(java.lang.String,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String,java.lang.String,java.lang.String)>")){
                                System.out.println("Found one database: " + unit.toString() + " in " + sootmethod.getSignature());
                                locationValue locationvalue = new locationValue(sootmethod,body,stmt);
                                databaseList.add(locationvalue);
                            }


                            //read
                            if(stmt.containsInvokeExpr()&&(stmt.getInvokeExpr().getMethod().getSignature().equals("<java.io.BufferedReader: java.lang.String readLine()>")||
                            stmt.getInvokeExpr().getMethod().getSignature().equals("<java.io.BufferedReader: int read()>") || 
                            stmt.getInvokeExpr().getMethod().getSignature().equals("<java.io.FileInputStream: int read()>")||
                            stmt.getInvokeExpr().getMethod().getSignature().equals("<java.io.FileInputStream: int read(byte[])>") || 
                            stmt.getInvokeExpr().getMethod().getSignature().equals("<java.io.FileInputStream: int read(byte[],int,int)>"))){
                                System.out.println("Found one fileop: " + unit.toString() + " in " + sootmethod.getSignature());
                                locationValue locationvalue = new locationValue(sootmethod,body,stmt);
                                fileopList.add(locationvalue);
                            }

                            //cancelNotification
                            if(stmt.containsInvokeExpr()&&GlobalValue.CancelNotificationMethods.contains(stmt.getInvokeExpr().getMethod().getSignature().toString())){
                                System.out.println("Found one cancelNotification: " + unit.toString() + " in " + sootmethod.getSignature());
                                infoPrint.writeToFile(findingname,"cancelNotification in : "+sootmethod.getSignature()+"\n");
                            }
                        }
                        //runnable
                        if(sootmethod.getName().equals("run")){
                            System.out.println("Found one runnable thread: " + sootmethod.getSignature());
                            locationValue locationvalue = new locationValue(sootmethod,body,null);
                            threadRunList.add(locationvalue);
                        }
                    }
                }
            }
        }catch(Exception e){
            System.out.println("constructflow error");
            e.printStackTrace();
            infoPrint.writeToFile(errorname,"constructflow error\n");
            infoPrint.writeToFile(errorname,e.toString()+"\n");
        }
        System.out.println("sourmethodfound size: "+sourmethodfound.size());
        if(sourmethodfound.size()==0){
            System.out.println("No sourmethod found");
            infoPrint.writeToFile(jimplename,"No sourmethod found\n");
            return;
        }
        for(SootMethod sootmethod: sourmethodfound){
            System.out.println("trace: "+sootmethod.getSignature());
            infoPrint.writeToFile(cgname,"trace: "+sootmethod.getSignature()+"\n");
            printsourmethodjimple(sootmethod);
            trace(sootmethod,elements);
        }
    }

    public static void printsourmethodjimple(SootMethod sootmethod){
        Body body = sootmethod.retrieveActiveBody();
        if(body == null){
            return;
        }
        for(Unit unit: body.getUnits()){
            System.out.println(unit);
            infoPrint.writeToFile(jimplename,unit+"\n");
        }
    }

    public static void trace(SootMethod sootmethod,List<Element> elements){
        Queue<MyEdge> queue = new LinkedList<MyEdge>();
        HashSet<String> handled = new HashSet<String>();
        HashSet<String> backtracehandled = new HashSet<String>();
        methodMap.clear();
        visitedmethod.clear();
        FunctionTaintInfo srcFunctionTaintInfo;
        if(sootmethod.getName().equals("onNotificationPosted")||sootmethod.getName().equals("onNotificationRemoved")){
            srcFunctionTaintInfo = new FunctionTaintInfo(sootmethod,1);
        }
        else{
            srcFunctionTaintInfo = new FunctionTaintInfo(sootmethod);
        }
        
        System.out.println(sootmethod.getName());
        if(sootmethod.isConcrete()){
            Body body = sootmethod.retrieveActiveBody();
            for(Unit unit: body.getUnits()){
                Stmt stmt = (Stmt) unit;
                if(stmt instanceof AssignStmt){
                    AssignStmt assignStmt = (AssignStmt) stmt;
                    Value leftValue = assignStmt.getLeftOp();
                    if(assignStmt.getRightOp() instanceof InvokeExpr){
                        InvokeExpr invokeExpr = (InvokeExpr) assignStmt.getRightOp();
                        if(invokeExpr.getMethod().getSignature().equals("<android.service.notification.NotificationListenerService: android.service.notification.StatusBarNotification[] getActiveNotifications()>")){
                            Local leftLocal = (Local) leftValue;
                            srcFunctionTaintInfo.addTaintedLocal(leftLocal);
                        }
                    }
                }
            }
        }
        List<MyEdge> myEdges = new ArrayList<MyEdge>();
        myEdges = FunctionTransferValue.findedgesoutof(srcFunctionTaintInfo);
        if(myEdges == null){
            System.out.println("myEdges is null");
            return;
        }

        for(MyEdge myEdge : myEdges){
            queue.add(myEdge);
        }

        while (!queue.isEmpty()) {
            MyEdge curEdge = queue.poll();
            FunctionTaintInfo tgtMethodInfo = curEdge.getDgtFunctionTaintInfo();
            FunctionTaintInfo srcMethodInfo = curEdge.getSrcFunctionTaintInfo();
            SootMethod tgtMethod = tgtMethodInfo.getMethod();
            SootMethod srcMethod = srcMethodInfo.getMethod();
            Stmt tgtstmt = (Stmt) curEdge.getSrcUnit();
            System.out.println("srcMethod: "+srcMethod.getSignature());
            System.out.println("tgtMethod: "+tgtMethod.getSignature());
            String curedgeString = srcMethod.getSignature()+"->"+tgtMethod.getSignature()+"from"+curEdge.getSrcUnit();

            if(handled.contains(curedgeString)){
                continue;
            }
            handled.add(curedgeString);
            if(tgtMethod.isConcrete()){
                Body body = tgtMethod.retrieveActiveBody();
                for(Unit unit: body.getUnits()){
                    Stmt stmt = (Stmt) unit;
                    for (String storageString : GlobalValue.externalstorage){
                        if(stmt.toString().contains(storageString)){
                            System.out.println("externalstorage: "+stmt);
                            infoPrint.writeToFile(findingname,"externalstorage: "+stmt+"\n");
                        }
                    }
                }
            }

            List<MyEdge> tgtEdges = FunctionTransferValue.findedgesoutof(tgtMethodInfo);

            if(!backtracehandled.contains(srcMethod.getSignature())&&GlobalValue.tracebackmethods.contains(tgtMethod.getSignature())){
                backtracehandled.add(srcMethod.getSignature());
                List<MyEdge> tgtEdges2 = FunctionTransferValue.findedgesinto(srcMethodInfo);
                if(tgtEdges2 != null && tgtEdges2.size() > 0){
                    for(MyEdge myEdge: tgtEdges2){
                        FunctionTaintInfo srcMethodInfo2 = myEdge.getSrcFunctionTaintInfo();
                        List<MyEdge> TraceBackedge = FunctionTransferValue.findedgesoutof(srcMethodInfo2);
                        if(TraceBackedge != null && TraceBackedge.size() > 0){
                            for(MyEdge myEdge2: TraceBackedge){
                                queue.add(myEdge2);
                            }
                        }
                    }
                }
            }
            
            if(!methodMap.containsKey(srcMethodInfo)){
                methodMap.put(srcMethodInfo, new HashSet<>());
            }
            methodMap.get(srcMethodInfo).add(tgtMethodInfo);

            //handle ICCBot
            if(GlobalValue.ICCmethods.contains(tgtMethod.getName())){
                System.out.println(tgtstmt.toString());
                for(Element element: elements){
                    String dstcompoent = readICCBot.findICCdst(element,tgtstmt.toString());
                    String dstcompoentmethod = readICCBot.finddstmethod(element, dstcompoent);
                    System.out.println(dstcompoent);
                    System.out.println(dstcompoentmethod);
                    SootMethod iccmethod = Scene.v().getMethod(dstcompoentmethod);
                    Set <Local> icctaintedLocals = new HashSet<Local>();
                    List <Local> iccreversetaintedLocals = new ArrayList<Local>();
                    if(iccmethod.isConcrete()){
                        if(tgtstmt instanceof InvokeExpr){
                            InvokeExpr invokeExpr = (InvokeExpr) tgtstmt;
                            for(Value value: invokeExpr.getArgs()){
                                if(value instanceof Local){
                                    iccreversetaintedLocals.add((Local) value);
                                }
                            }
                        }
                        List<String> taintunits = readICCBot.findNodes(element,dstcompoent);
                        Body iccbody = iccmethod.retrieveActiveBody();
                        for(Unit unit: iccbody.getUnits()){
                            Stmt stmt = (Stmt) unit;
                            if(taintunits.contains(stmt.toString())){
                                if(stmt instanceof AssignStmt){
                                    AssignStmt assignStmt = (AssignStmt) stmt;
                                    Value leftValue = assignStmt.getLeftOp();
                                    if(leftValue instanceof Local){
                                        icctaintedLocals.add((Local) leftValue);
                                    }
                                }
                            }
                        }
                        FunctionTaintInfo icctaintinfo = new FunctionTaintInfo(iccmethod,icctaintedLocals,iccreversetaintedLocals,tgtstmt);
                        MyEdge newedge = new MyEdge(tgtMethodInfo,icctaintinfo,tgtstmt);
                        queue.add(newedge);
                    }
                }
            }

            if(tgtMethod.getSignature().equals("<android.content.ContextWrapper: void sendBroadcast(android.content.Intent)>")
            ||tgtMethod.getSignature().equals("<android.content.ContextWrapper: void sendBroadcast(android.content.Intent,java.lang.String)>")
            ||tgtMethod.getSignature().equals("<android.content.Context: void sendBroadcast(android.content.Intent)>")
            ||tgtMethod.getSignature().equals("<android.content.Context: void sendBroadcast(android.content.Intent,java.lang.String)>")
            ||tgtMethod.getSignature().equals("<android.app.Activity: void sendBroadcast(android.content.Intent)>")
            ||tgtMethod.getSignature().equals("<android.app.Activity: void sendBroadcast(android.content.Intent,java.lang.String)>")){
                List<String> actions = new ArrayList<String>();
                List<String> receiverclass = null;
                Unit unit = curEdge.getSrcUnit();
                Stmt stmt = (Stmt) unit;
                System.out.println("sendbroadcast: "+stmt);
                if(stmt.containsInvokeExpr()){
                    Value intentValue = stmt.getInvokeExpr().getArg(0);
                    if(intentValue instanceof Local){
                        actions = findIntentAction((Local) intentValue,srcMethod.getActiveBody());
                        
                    }
                    if(actions != null){
                        for(String action: actions){
                            
                            receiverclass = findRecevierRegister(action);
                            
                            if(receiverclass != null){
                                for(String receiverClassName: receiverclass){
                                    if(receiverClassName != null){
                                        SootMethod onreceivemethod = Scene.v().getMethod("<" + receiverClassName + ": void onReceive(android.content.Context,android.content.Intent)>");
                                        Set <Local> onreceivetaintedLocals = new HashSet<Local>();
                                        List <Local> onreceivereversetaintedLocals = new ArrayList<Local>();
                                        if(onreceivemethod.isConcrete()){
                                            onreceivetaintedLocals.add(onreceivemethod.retrieveActiveBody().getParameterLocal(1));
                                            onreceivereversetaintedLocals.add((Local) intentValue);
                                            FunctionTaintInfo onreceivetaintinfo = new FunctionTaintInfo(onreceivemethod,onreceivetaintedLocals,onreceivereversetaintedLocals,stmt);
                                            MyEdge newedge = new MyEdge(tgtMethodInfo,onreceivetaintinfo,stmt);
                                            queue.add(newedge);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            else if(tgtMethod.getSignature().equals("<android.content.ContextWrapper: android.content.SharedPreferences getSharedPreferences(java.lang.String,int)>")){
                if(srcMethod.isConcrete()){
                    Body body = srcMethod.retrieveActiveBody();
                    for(Unit unit: body.getUnits()){
                        if(unit instanceof JInvokeStmt){
                            JInvokeStmt jInvokeStmt = (JInvokeStmt) unit;
                            if(jInvokeStmt.getInvokeExpr().getMethod().getSignature().equals("<android.content.SharedPreferences$Editor: android.content.SharedPreferences$Editor putString(java.lang.String,java.lang.String)>")){
                                Stmt stmt = (Stmt) unit;
                                if(stmt.containsInvokeExpr()){
                                    List<sharedpreferenceinfo> sharelList = new ArrayList<sharedpreferenceinfo>();
                                    Value key = stmt.getInvokeExpr().getArg(0);
                                    Value value = stmt.getInvokeExpr().getArg(1);
                                    if(value instanceof Local){
                                        sharelList = findsharedpreference(key);
                                        for(sharedpreferenceinfo tgtmethodinfo: sharelList){
                                            SootMethod newtgtmethod = Scene.v().getMethod(tgtmethodinfo.getmethod());
                                            Set<Local> taintedLocals = tgtmethodinfo.gettaintedLocals();
                                            List<Local> reversetaintedLocals = new ArrayList<Local>();
                                            reversetaintedLocals.add((Local) value);
                                            FunctionTaintInfo newtgtmethodinfo = new FunctionTaintInfo(newtgtmethod,taintedLocals,reversetaintedLocals,stmt);
                                            MyEdge newedge = new MyEdge(tgtMethodInfo,newtgtmethodinfo,stmt);
                                            queue.add(newedge);
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                }
            }

            
            else if (tgtMethod.getName().equals("<init>")){
                Boolean flag = false;
                System.out.println("tgtMethod: "+tgtMethod.getSignature());
                for(locationValue locationvalue : threadClassList){
                    if(locationvalue.getMethodClass().equals(tgtMethod.getDeclaringClass().getName())){
                        flag = true;
                        String threadClass = tgtMethod.getDeclaringClass().getName();
                        try{
                            SootMethod runMethod = Scene.v().getMethod("<" + threadClass + ": void run()>");
                            //find taint local 
                            Set<Local> taintedLocals = new HashSet<Local>();
                            List<Local> reversetaintedLocals = new ArrayList<Local>();
                            Unit unit = curEdge.getSrcUnit();
                            Stmt stmt = (Stmt) unit;
                            if(stmt.containsInvokeExpr()){
                                InvokeExpr invokeExpr = stmt.getInvokeExpr();
                                if(invokeExpr.getArgCount()>0){
                                    for(Value value: invokeExpr.getArgs()){
                                        if(value instanceof Local){
                                            Local local = (Local) value;
                                            reversetaintedLocals.add(local);
                                        }
                                    }
                                }
                            }
                            SootMethod threadinitMethod = locationvalue.getMethod();
                            List<String> threadruntainted = new ArrayList<String>();
                            if(threadinitMethod.isConcrete()){
                                Body threadinitbody = threadinitMethod.retrieveActiveBody();
                                Set<Local> parameterLocals = new HashSet<Local>();
                                for(Local local: threadinitbody.getParameterLocals()){
                                    parameterLocals.add(local);
                                }
                                Set<Unit> uses = LocalVariableAnalysis.findallUses(threadinitbody,parameterLocals);
                                for(Unit useunit : uses){
                                    Stmt useStmt = (Stmt) useunit;
                                    if(useStmt instanceof AssignStmt){
                                        AssignStmt assignStmt = (AssignStmt) useStmt;
                                        Value leftValue = assignStmt.getLeftOp();
                                        threadruntainted.add(leftValue.toString());
                                    }
                                }

                                if(runMethod.isConcrete()){
                                    Body runbody = runMethod.retrieveActiveBody();
                                    for(Unit unit2 : runbody.getUnits()){
                                        Stmt stmt2 = (Stmt) unit2;
                                        if(stmt2 instanceof AssignStmt){
                                            AssignStmt assignStmt = (AssignStmt) stmt2;
                                            Value rightValue = assignStmt.getRightOp();
                                            if(threadruntainted.contains(rightValue.toString())){
                                                Value lefValue = assignStmt.getLeftOp();
                                                if(lefValue instanceof Local){
                                                    Local local = (Local) lefValue;
                                                    taintedLocals.add(local);
                                                }
                                            }
                                        }
                                    }
                                    FunctionTaintInfo newtgtmethodinfo = new FunctionTaintInfo(runMethod,taintedLocals,reversetaintedLocals,stmt);
                                    MyEdge newedge = new MyEdge(tgtMethodInfo,newtgtmethodinfo,stmt);
                                    queue.add(newedge);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("find thread error");
                        }
                    }
                }
                if(!flag){
                    try{
                        SootClass runClass = tgtMethod.getDeclaringClass();
                        for(SootMethod sootmethod2: runClass.getMethods()){
                            if(sootmethod2.getName().equals("run")){
                                Set<Local> taintedLocals = new HashSet<Local>();
                                List<Local> reversetaintedLocals = new ArrayList<Local>();
                                Unit unit = curEdge.getSrcUnit();
                                Stmt stmt = (Stmt) unit;
                                List<String> threadruntainted = new ArrayList<String>();

                                
                                for(Local local: tgtMethodInfo.getReverseTaintedLocals()){
                                    reversetaintedLocals.add(local);
                                }
                                if(tgtMethod.isConcrete()){//init
                                    Body threadinitbody = tgtMethod.retrieveActiveBody();
                                
                                    Set<Local> parameterLocals = new HashSet<Local>();
                                    for(Local local: tgtMethodInfo.getTaintedLocals()){
                                        parameterLocals.add(local);
                                    }
                                    Set<Unit> uses = LocalVariableAnalysis.findallUses(threadinitbody,parameterLocals);
                                    for(Unit useunit : uses){
                                        Stmt useStmt = (Stmt) useunit;
                                        if(useStmt instanceof AssignStmt){
                                            AssignStmt assignStmt = (AssignStmt) useStmt;
                                            Value leftValue = assignStmt.getLeftOp();
                                            threadruntainted.add(leftValue.toString());
                                        }
                                    }
                                    if(sootmethod2.isConcrete()){
                                        Body runbody = sootmethod2.retrieveActiveBody();
                                        for(Unit unit2 : runbody.getUnits()){
                                            Stmt stmt2 = (Stmt) unit2;
                                            if(stmt2 instanceof AssignStmt){
                                                AssignStmt assignStmt = (AssignStmt) stmt2;
                                                Value rightValue = assignStmt.getRightOp();
                                                if(threadruntainted.contains(rightValue.toString())){
                                                    Value lefValue = assignStmt.getLeftOp();
                                                    if(lefValue instanceof Local){
                                                        Local local = (Local) lefValue;
                                                        taintedLocals.add(local);
                                                    }
                                                }
                                            }
                                        }
                                        FunctionTaintInfo newtgtmethodinfo = new FunctionTaintInfo(sootmethod2,taintedLocals,reversetaintedLocals,stmt);
                                        MyEdge newedge = new MyEdge(tgtMethodInfo,newtgtmethodinfo,stmt);
                                        queue.add(newedge);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("find thread error");
                    }
                }
            }

            else if (tgtMethod.getName().equals("execute")||tgtMethod.getName().equals("executeOnExecutor")){
                Boolean flag = false;
                if(tgtMethod.getName().equals("execute")){
                    flag=true;
                }
                Unit unit = curEdge.getSrcUnit();
                Stmt stmt = (Stmt) unit;
                System.out.println("invoke: "+stmt);
                String baseClassName = null;
                if(stmt.containsInvokeExpr()){
                    InvokeExpr invokeExpr = stmt.getInvokeExpr();
                    if(invokeExpr instanceof VirtualInvokeExpr){
                        VirtualInvokeExpr virtualInvokeExpr = (VirtualInvokeExpr) invokeExpr;
                        Value baseClass = virtualInvokeExpr.getBase();
                        baseClassName = baseClass.getType().toString();
                        System.out.println("baseClassName: "+baseClassName);
                    }
                    for(locationValue locationvalue : AsyncTaskList){
                        if(locationvalue.getMethodClass().equals(baseClassName)){
                            try{
                                SootMethod runMethod = Scene.v().getMethod("<" + baseClassName + ": java.lang.Object doInBackground(java.lang.Object[])>");
                                
                                if(stmt.containsInvokeExpr()){
                                    Value value;
                                    if(flag){
                                        value = invokeExpr.getArg(0);
                                    }
                                    else{
                                        value = invokeExpr.getArg(1);
                                    }
                                    Set <Local> doInBackgroundLocals = new HashSet<Local>();
                                    List <Local> doInBackgroundsetaintedLocals = new ArrayList<Local>();
                                    if(runMethod.isConcrete()){
                                        doInBackgroundLocals.add(runMethod.retrieveActiveBody().getParameterLocal(0));
                                        doInBackgroundsetaintedLocals.add((Local) value);
                                        FunctionTaintInfo onreceivetaintinfo = new FunctionTaintInfo(runMethod,doInBackgroundLocals,doInBackgroundsetaintedLocals,stmt);
                                        MyEdge newedge = new MyEdge(tgtMethodInfo,onreceivetaintinfo,stmt);
                                        queue.add(newedge);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("find AsyncTask error");
                            }
                        }
                    }
                }
                
            }

            //runnable
            else if(tgtMethod.getSignature().toString().contains("execute(java.lang.Runnable)")||
            tgtMethod.getSignature().toString().contains("postDelayed(java.lang.Runnable")||
            tgtMethod.getSignature().toString().contains("post(java.lang.Runnable)")||
            tgtMethod.getSignature().toString().contains("<init>(java.lang.Runnable)")){
                Unit unit = curEdge.getSrcUnit();
                Stmt stmt = (Stmt) unit;
                InvokeExpr invokeExpr = stmt.getInvokeExpr();
                Value runnableValue = invokeExpr.getArg(0);
                if(runnableValue instanceof ParameterRef){
                    int index = ((ParameterRef) runnableValue).getIndex();
                    
                }
            }

            //database
            else if (tgtMethod.getSignature().equals("<android.database.sqlite.SQLiteDatabase: long insert(java.lang.String,java.lang.String,android.content.ContentValues)>")||tgtMethod.getSignature().equals("<android.database.sqlite.SQLiteDatabase: int update(java.lang.String,android.content.ContentValues,java.lang.String,java.lang.String[])>")){
                Unit unit = curEdge.getSrcUnit();
                Stmt stmt = (Stmt) unit;
                System.out.println("database: "+stmt);
                Boolean databaseflag = true;
                if(stmt.containsInvokeExpr()){
                    InvokeExpr invokeExpr = stmt.getInvokeExpr();
                    Value tableName = invokeExpr.getArg(0);
                    System.out.println(tableName);
                    String tableNameString = null;
                    if (tableName instanceof FieldRef){
                        tableNameString = LocalVariableAnalysis.getVariableName(tableName);
                    }
                    else if(tableName instanceof Constant || tableName instanceof StringConstant){
                        tableNameString = tableName.toString();
                    }
                    if(tableNameString == null){
                        databaseflag = false;
                    }
                    System.out.println("tableName: "+tableNameString);
                    if(databaseflag){
                        for(locationValue locationvalue : databaseList){
                            Stmt stmt2 = locationvalue.getStmt();
                            if(stmt2.containsInvokeExpr()){
                                InvokeExpr invokeExpr2 = stmt2.getInvokeExpr();
                                Value tableName2 = invokeExpr2.getArg(0);
                                String tableNameString2 = null;
                                if (tableName2 instanceof FieldRef){
                                    tableNameString2 = LocalVariableAnalysis.getVariableName(tableName2);
                                }
                                else if(tableName2 instanceof Constant || tableName2 instanceof StringConstant){
                                    tableNameString2 = tableName2.toString();
                                }
                                if(tableNameString2 == null){
                                    continue;
                                }
                                System.out.println("tableName2: "+tableNameString2);
                                if(tableNameString.equals(tableNameString2)){
                                    
                                    SootMethod tgtMethod2 = locationvalue.getMethod();
                                    Set<Local> taintedLocals = new HashSet<Local>();
                                    List<Local> reversetaintedLocals = new ArrayList<Local>();
                                    if(stmt2 instanceof AssignStmt){
                                        AssignStmt assignStmt = (AssignStmt) stmt2;
                                        Value leftValue = assignStmt.getLeftOp();
                                        if(leftValue instanceof Local){
                                            Local local = (Local) leftValue;
                                            taintedLocals.add(local);
                                        }
                                    }
                                    
                                    for(Local local: tgtMethodInfo.getTaintedLocals()){
                                        reversetaintedLocals.add(local);
                                    }
                                    FunctionTaintInfo newtgtmethodinfo = new FunctionTaintInfo(tgtMethod2,taintedLocals,reversetaintedLocals,stmt2);
                                    MyEdge newedge = new MyEdge(tgtMethodInfo,newtgtmethodinfo,stmt);
                                    queue.add(newedge);
                                }
                            }
                        }
                    }
                }
            }

            else if (tgtMethod.getSignature().equals("<java.io.FileWriter: void write(java.lang.String)>") || 
             tgtMethod.getSignature().equals("<java.io.BufferedWriter: void write(java.lang.String)>") ||
             tgtMethod.getSignature().equals("<java.io.PrintWriter: void println(java.lang.String)>") ||
             tgtMethod.getSignature().equals("<java.io.Writer: void write(java.lang.String)>")){
                
                System.out.println(tgtstmt);
                Body body = srcMethod.retrieveActiveBody();
                for(Unit unit : body.getUnits()){
                    Stmt stmt = (Stmt) unit;
                    if(stmt.containsInvokeExpr()){
                        InvokeExpr invokeExpr = stmt.getInvokeExpr();
                        if(invokeExpr instanceof SpecialInvokeExpr){
                            SpecialInvokeExpr specialInvokeExpr = (SpecialInvokeExpr) invokeExpr;
                            if(specialInvokeExpr.getMethod().getSignature().equals("<java.io.FileWriter: void <init>(java.lang.String)>")){
                                Value filearg = specialInvokeExpr.getArg(0);
                                String fileString = null;
                                if (filearg instanceof FieldRef){
                                    fileString = LocalVariableAnalysis.getVariableName(filearg);
                                }
                                else if(filearg instanceof Constant || filearg instanceof StringConstant){
                                    fileString = filearg.toString();
                                }
                                if(fileString == null){
                                    continue;
                                }
                                System.out.println("fileString: "+fileString);
                                List<locationValue> filelocaltion = findfile(fileString);
                                for(locationValue locationvalue : filelocaltion){
                                    SootMethod tgtMethod2 = locationvalue.getMethod();
                                    Stmt stmt2 = locationvalue.getStmt();
                                    Set<Local> taintedLocals = new HashSet<Local>();
                                    List<Local> reversetaintedLocals = new ArrayList<Local>();
                                    if(stmt2 instanceof AssignStmt){
                                        AssignStmt assignStmt = (AssignStmt) stmt2;
                                        Value leftValue = assignStmt.getLeftOp();
                                        if(leftValue instanceof Local){
                                            Local local = (Local) leftValue;
                                            taintedLocals.add(local);
                                        }
                                    }
                                    if(stmt2.containsInvokeExpr()){
                                        InvokeExpr invokeExpr2 = stmt2.getInvokeExpr();
                                        if(invokeExpr2.getArgCount()>0){
                                            Local local = (Local) invokeExpr2.getArg(0);
                                            taintedLocals.add(local);
                                        }
                                    }
                                    
                                    for(Local local: tgtMethodInfo.getTaintedLocals()){
                                        reversetaintedLocals.add(local);
                                    }
                                    FunctionTaintInfo newtgtmethodinfo = new FunctionTaintInfo(tgtMethod2,taintedLocals,reversetaintedLocals,stmt2);
                                    MyEdge newedge = new MyEdge(tgtMethodInfo,newtgtmethodinfo,tgtstmt);
                                    queue.add(newedge);
                                }
                            }
                        }
                    }
                }
            }

            for(MyEdge tgtEdge : tgtEdges){
                queue.add(tgtEdge);
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
        actanalysis.parse(methodMap,apkname);
    }

    private static List<locationValue> findfile(String filefrom){
        List<locationValue> filelocation = new ArrayList<locationValue>();
        for(locationValue locationvalue : fileopList){
            SootMethod method = locationvalue.getMethod();
            Body body = method.retrieveActiveBody();
            for(Unit unit : body.getUnits()){
                Stmt stmt = (Stmt) unit;
                if(stmt.containsInvokeExpr()){
                    InvokeExpr invokeExpr = stmt.getInvokeExpr();
                    if(invokeExpr instanceof SpecialInvokeExpr){
                        SpecialInvokeExpr specialInvokeExpr = (SpecialInvokeExpr) invokeExpr;
                        if(specialInvokeExpr.getMethod().getSignature().equals("<java.io.FileReader: void <init>(java.lang.String)>")){
                            Value filearg = specialInvokeExpr.getArg(0);
                            String fileString = null;
                            if (filearg instanceof FieldRef){
                                fileString = LocalVariableAnalysis.getVariableName(filearg);
                            }
                            else if(filearg instanceof Constant || filearg instanceof StringConstant){
                                fileString = filearg.toString();
                            }
                            if(fileString == null){
                                continue;
                            }
                            if(fileString.equals(filefrom)){
                                System.out.println("Found one file: " + filefrom + " in " + method.getSignature());
                                infoPrint.writeToFile(findingname,"Found one file: " + filefrom + " in " + method.getSignature()+"\n");
                                filelocation.add(locationvalue);
                            }
                        }
                    }
                }
            }
        }
        return filelocation;
    }

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
            if(GlobalValue.SinkMethods.contains(method.getSignature())||GlobalValue.SinkMethods.contains(method.getName())){
                System.out.println("Found one sink: " + method.getSignature());
                infoPrint.writeToFile(findingname,"Found one sink: " + method.getSignature()+"\n");
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


    //ICC-broadcast
    private static List<String> findIntentAction(Local intentLocal,Body body){
        List<String> action = new ArrayList<String>();
        Set<Local> taintedLocals = new HashSet<Local>();
        Set<Local> inputLocals = new HashSet<Local>();
        inputLocals.add(intentLocal);
        taintedLocals = LocalVariableAnalysis.findallLocal(body,inputLocals);
        
        for(Unit unit : body.getUnits()){
            System.out.println(unit);
            Stmt stmt = (Stmt) unit;
            Boolean flag = false;
            if(stmt instanceof InvokeStmt){
                InvokeStmt invokeStmt = (InvokeStmt) stmt;
                List<ValueBox> useBoxes = invokeStmt.getUseBoxes();
                for(ValueBox valueBox : useBoxes){
                    Value value = valueBox.getValue();
                    if(taintedLocals.contains(value)){
                        flag=true;
                    }
                }
                if(flag){
                    if(invokeStmt.getInvokeExpr().getMethod().getName().equals("setAction")){
                        
                        if(invokeStmt.getInvokeExpr().getArg(0) instanceof StringConstant){
                            StringConstant stringConstant = (StringConstant) invokeStmt.getInvokeExpr().getArg(0);
                            action.add(stringConstant.value);
                        
                        }else if(invokeStmt.getInvokeExpr().getArg(0) instanceof Local){
                            Local local = (Local) invokeStmt.getInvokeExpr().getArg(0);
                            Value value = local;
                            try{
                                action.add(LocalVariableAnalysis.getVariableName(value));
                            }catch (Exception e) {
                                System.out.println("findIntentAction error");
                            }
                        }
                    }
                    if(invokeStmt.getInvokeExpr().getMethod().getSignature().equals("<android.content.Intent: void <init>(java.lang.String)>")){
                        if(invokeStmt.getInvokeExpr().getArg(0) instanceof StringConstant){
                            StringConstant stringConstant = (StringConstant) invokeStmt.getInvokeExpr().getArg(0);
                            action.add(stringConstant.value);
                            System.out.println(stringConstant.value);
                        }
                        else if(invokeStmt.getInvokeExpr().getArg(0) instanceof Local){
                            Local local = (Local) invokeStmt.getInvokeExpr().getArg(0);
                            Value value = local;
                            try{
                                action.add(LocalVariableAnalysis.getVariableName(value));
                            }catch (Exception e) {
                                System.out.println("findIntentAction error");
                            }
                            
                        }
                    }
                }
            }
        }
        return action;
    }

    private static boolean isNewInstance(String jimpleStatement) {
        Pattern pattern = Pattern.compile("\\$[a-zA-Z0-9]+ = new ([a-zA-Z0-9\\.$]+)");
        Matcher matcher = pattern.matcher(jimpleStatement);

        return matcher.find();
    }

    private static String extractClassName(Unit unit){
        String unitString = unit.toString();
        Pattern pattern = Pattern.compile("\\$[a-zA-Z0-9]+ = new ([a-zA-Z0-9\\.$]+)");
        Matcher matcher = pattern.matcher(unitString);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static List<String> findRecevierRegister(String action){
        CallGraph callGraph = GlobalValue.callgraph;
        Chain<SootClass> sootClasses = GlobalValue.sootClass;
        List<String> receiverclass = new ArrayList<String>();
        

        for(locationValue locationValue : registerReceiverStmtList){
            Body body = locationValue.body;
            Stmt stmt = locationValue.stmt;
            SootMethod method = locationValue.method;
            
            if(stmt.containsInvokeExpr() && stmt.getInvokeExpr().getMethod().getName().equals("registerReceiver")){
                
                Value receiverValue = stmt.getInvokeExpr().getArg(0);
                Value filterValue = stmt.getInvokeExpr().getArg(1);
                
                if(filterValue instanceof Local){
                    List<String> registeredActions = findIntentFilterAction((Local) filterValue,body,stmt);
                    for(String registeredAction : registeredActions){
                        
                        if (action.equals(registeredAction)) {
                            
                            List<Unit> defs = LocalVariableAnalysis.findDefs(body,stmt,(Local)receiverValue);
                            for(Unit unit: defs){
                                System.out.println(unit);
                                if(isNewInstance(unit.toString())){
                                    String receiverClassName = extractClassName(unit);
                                    System.out.println("receiverClassName: "+receiverClassName);
                                    receiverclass.add(receiverClassName);
                                }
                                else{
                                    Stmt defstmt = (Stmt) unit;
                                    if(defstmt instanceof AssignStmt){
                                        AssignStmt assignStmt = (AssignStmt) defstmt;
                                        Value rightValue = assignStmt.getRightOp();
                                        if(rightValue instanceof InstanceFieldRef){
                                            SootClass sootClass = ((InstanceFieldRef) rightValue).getField().getDeclaringClass();
                                            for(SootMethod sootMethod : sootClass.getMethods()){
                                                if(sootMethod.isConcrete()){
                                                    Body body1 = sootMethod.retrieveActiveBody();
                                                    for(Unit unit1: body1.getUnits()){
                                                        if(unit1 instanceof AssignStmt){
                                                            Stmt stmt1 = (Stmt) unit1;
                                                            AssignStmt assignStmt1 = (AssignStmt) unit1;
                                                            Value leftValue = assignStmt1.getLeftOp();
                                                            if(leftValue.toString().equals(rightValue.toString())){
                                                                Value rightValue1 = assignStmt1.getRightOp();
                                                                if(rightValue1 instanceof Local){
                                                                    Local local = (Local) rightValue1;
                                                                    List<Unit> defs1 = LocalVariableAnalysis.findDefs(body1,stmt1,local);
                                                                    for(Unit unit2: defs1){
                                                                        if(isNewInstance(unit2.toString())){
                                                                            System.out.print(unit2);
                                                                            String receiverClassName = extractClassName(unit2);
                                                                            System.out.println("receiverClassName: "+receiverClassName);
                                                                            receiverclass.add(receiverClassName);
                                                                        }
                                                                    }
                                                                }
                                                                System.out.println(unit1);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            System.out.println(sootClass.getName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return receiverclass;
    }

    
    private static List<String> findIntentFilterAction(Local filterLocal,Body body,Stmt defstmt){
        
        Local filterRef = (Local) filterLocal;
        List<String> actionList = new ArrayList<String>();
        List<Unit> defs = LocalVariableAnalysis.findUses(body,defstmt,filterRef);

        for(Unit unit:defs){
            Stmt stmt = (Stmt) unit;
            if (stmt.containsInvokeExpr()) {
                InvokeExpr invokeExpr = stmt.getInvokeExpr();
                
                if (invokeExpr.getMethod().getName().equals("addAction")) {
                    Value actionArg = invokeExpr.getArg(0);
                    if (actionArg instanceof StringConstant) {
                        
                        actionList.add(((StringConstant) actionArg).value);
                    }
                    else if(actionArg instanceof Local){
                        Value value = actionArg;
                        try{
                            String action = LocalVariableAnalysis.getVariableName(value);
                            actionList.add(action);
                        }catch (Exception e) {
                            System.out.println("getVariableName error");
                        }
                        
                    }
                }
                else if(invokeExpr.getMethod().getSignature().equals("<android.content.IntentFilter: void <init>(java.lang.String)>")&&invokeExpr instanceof SpecialInvokeExpr){
                    Value actionArg = invokeExpr.getArg(0);
                    if (actionArg instanceof StringConstant) {
                        
                        actionList.add(((StringConstant) actionArg).value);
                    }
                    else if(actionArg instanceof Local){
                        Value value = actionArg;
                        try{
                            String action = LocalVariableAnalysis.getVariableName(value);
                            actionList.add(action);
                        }catch (Exception e) {
                            System.out.println("getVariableName error");
                        }
                        
                    }
                }
            }
        }

        return actionList;
    }

    private static List<sharedpreferenceinfo> findsharedpreference(Value key){
        List<sharedpreferenceinfo> sharelList = new ArrayList<sharedpreferenceinfo>();
        for(locationValue locationValue: sharedpreferenceStmtList){
            Body body = locationValue.body;
            Stmt stmt = locationValue.stmt;
            SootMethod method = locationValue.method;
            if(stmt.containsInvokeExpr() && stmt.getInvokeExpr().getArgCount() > 0){
                Value keyvalue = stmt.getInvokeExpr().getArg(0);
                if(keyvalue.equals(key)){
                    System.out.println("findsharedpreference: "+stmt);
                    String tgtmethod = method.getSignature();
                    sharedpreferenceinfo sharedpreferenceinfo = new sharedpreferenceinfo(tgtmethod,stmt);
                    sharelList.add(sharedpreferenceinfo);
                }
            }
        }
        return sharelList;
    }
    
}
