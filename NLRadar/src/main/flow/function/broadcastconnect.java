package function;

import java.util.Arrays;
import java.util.Map;

import dataStructure.GlobalValue;
import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.NewExpr;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.util.Chain;
import utils.LoggerUtil;

public class broadcastconnect extends BodyTransformer{
    private LoggerUtil mylogger = new LoggerUtil();

    @Override
    protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
        SootMethod method = body.getMethod();
        ExceptionalUnitGraph graph = new ExceptionalUnitGraph(body);
        String action = null;
        String receiverclass = null;
        Stmt sendbroadcaStmt = null;
        
        for(Unit unit : graph){
            Stmt stmt = (Stmt) unit;
            

            if (stmt.containsInvokeExpr() && stmt.getInvokeExpr().getMethod().getName().equals("sendBroadcast")){
                sendbroadcaStmt = stmt;

                Value intentValue = stmt.getInvokeExpr().getArg(0);


                if(intentValue instanceof Local){
                    action = findIntentAction((Local) intentValue,graph);
                }
                if(action != null){
                    receiverclass = findRecevierRegister(action);
                }
                
            }
        }
        if(receiverclass != null){

            addOnReceiveCall(sendbroadcaStmt, receiverclass,body);
        }
    }

    //找到intent的action
    private String findIntentAction(Local intentLocal,ExceptionalUnitGraph graph){
        String action = "";
        for(Unit unit : graph){
            Stmt stmt = (Stmt) unit;

            if(stmt instanceof AssignStmt){
                AssignStmt assignStmt = (AssignStmt) stmt;
                if(assignStmt.getLeftOp().equals(intentLocal)){
                    if(assignStmt.getRightOp() instanceof NewExpr){
                        NewExpr newExpr = (NewExpr) assignStmt.getRightOp();
                        if(newExpr.getBaseType().toString().equals("android.content.Intent")){
                            action = "android.content.Intent";
                        }
                        
                    }else if(assignStmt.getRightOp() instanceof Local){
                        Local local = (Local) assignStmt.getRightOp();
                        action = findIntentAction(local,graph);
                    }
                }
            }else if(stmt instanceof InvokeStmt){
                InvokeStmt invokeStmt = (InvokeStmt) stmt;
                if(invokeStmt.getInvokeExpr().getMethod().getName().equals("setAction")){

                    if(invokeStmt.getInvokeExpr().getArg(0) instanceof StringConstant){
                        StringConstant stringConstant = (StringConstant) invokeStmt.getInvokeExpr().getArg(0);
                        action = stringConstant.value;

                    }else if(invokeStmt.getInvokeExpr().getArg(0) instanceof Local){
                        Local local = (Local) invokeStmt.getInvokeExpr().getArg(0);
                        action = findIntentAction(local,graph);
                    }
                }
            }
        }
        return action;
    }


    private String findRecevierRegister(String action){

        CallGraph callGraph = GlobalValue.callgraph;
        Chain<SootClass> sootClasses = GlobalValue.sootClass;

        for(SootClass sootClass : sootClasses){
            for(SootMethod sootMethod : sootClass.getMethods()){
                try{
                    Body body = sootMethod.retrieveActiveBody();
                    for(Unit unit : body.getUnits()){
                        Stmt stmt = (Stmt) unit;

                        if(stmt.containsInvokeExpr() && stmt.getInvokeExpr().getMethod().getName().equals("registerReceiver")){

                            Value receiverValue = stmt.getInvokeExpr().getArg(0);
                            Value filterValue = stmt.getInvokeExpr().getArg(1);

                            if(filterValue instanceof Local){
                                String registeredAction = findIntentFilterAction((Local) filterValue,body);
                                System.out.println("registeredAction: "+registeredAction);
                                if (action.equals(registeredAction)) {

                                    String receiverClassName = receiverValue.getType().toString();

                                    return receiverClassName;
                                }
                            }
                        }
                    }
                }catch (Exception e) {
                    // mylogger.logError(e.toString());
                    // System.out.println("error");
                }
            }
        }
        return null;
    }

   
    private String findIntentFilterAction(Local filterLocal,Body body){

        Local filterRef = (Local) filterLocal;

        for(Unit unit:body.getUnits()){
            Stmt stmt = (Stmt) unit;
            if (stmt.containsInvokeExpr()) {
                InvokeExpr invokeExpr = stmt.getInvokeExpr();

                if (invokeExpr.getMethod().getName().equals("addAction")) {
                    Value actionArg = invokeExpr.getArg(0);
                    if (actionArg instanceof StringConstant) {
                        return ((StringConstant) actionArg).value;
                    }
                }
            }
        }


        return null;
    }


    private void addOnReceiveCall(Stmt sendbroadcaststmt, String receiverClassName,Body body){

        CallGraph callGraph = GlobalValue.callgraph;
        Chain<SootClass> sootClasses = GlobalValue.sootClass;
        Chain<Unit> units = body.getUnits();


        String receiverClass = receiverClassName;

        SootMethod onReceiveMethod = null;
        for(SootClass sootClass : sootClasses){
            if(sootClass.getName().equals(receiverClass)){
                for(SootMethod sootMethod : sootClass.getMethods()){
                    if(sootMethod.getName().equals("onReceive")){
                        onReceiveMethod = sootMethod;
                    }
                }
            }
        }


        Value intentValue = sendbroadcaststmt.getInvokeExpr().getArg(0);
        System.out.println("intentValue: "+intentValue);

        Local receiverObject = Jimple.v().newLocal("receiverObject", RefType.v(receiverClassName));
        AssignStmt createReceiverStmt = Jimple.v().newAssignStmt(receiverObject,
                Jimple.v().newNewExpr(RefType.v(receiverClassName)));


        units.insertAfter(createReceiverStmt,sendbroadcaststmt);



        Local intentParam = Jimple.v().newLocal("intentParam", RefType.v("android.content.Intent"));

        AssignStmt assignIntentStmt = Jimple.v().newAssignStmt(intentParam, intentValue);

        units.insertAfter(assignIntentStmt,createReceiverStmt);

        Local contextLocal = Jimple.v().newLocal("contextLocal", RefType.v("android.content.Context"));

        AssignStmt assignContextStmt = Jimple.v().newAssignStmt(contextLocal,
                Jimple.v().newVirtualInvokeExpr(receiverObject, Scene.v().getMethod("<" + "android.content.Context" + ": android.content.Context getApplicationContext()>").makeRef()));
        units.insertAfter(assignContextStmt,assignIntentStmt);
        

        Stmt onReceiveCallStmt = Jimple.v().newInvokeStmt(
                Jimple.v().newVirtualInvokeExpr(receiverObject, Scene.v().getMethod("<" + receiverClassName + ": void onReceive(android.content.Context,android.content.Intent)>").makeRef(),
                        Arrays.asList(new Local[]{contextLocal,intentParam})));

        units.insertAfter(onReceiveCallStmt,assignContextStmt);
        
    }
    

}
