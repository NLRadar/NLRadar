package dataflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import soot.Body;
import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.FieldRef;
import soot.jimple.InvokeExpr;
import soot.jimple.NewExpr;
import soot.jimple.NullConstant;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.jimple.internal.JInstanceFieldRef;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.SimpleLocalDefs;
import soot.toolkits.scalar.SimpleLocalUses;
import soot.toolkits.scalar.UnitValueBoxPair;

public class LocalVariableAnalysis {
    public static List<Unit> findDefs(Body body, Stmt stmt, Local local) {	
        Unit unit = (Unit) stmt;
        UnitGraph cfg = new BriefUnitGraph(body);
        SimpleLocalDefs defsResolver = new SimpleLocalDefs(cfg);
        List<Unit> defs = defsResolver.getDefsOfAt(local, unit);

        return defs;
    }

    public static List<Unit> findDefs(Body body, Local local) {	
        UnitGraph cfg = new BriefUnitGraph(body);
        SimpleLocalDefs defsResolver = new SimpleLocalDefs(cfg);
        List<Unit>defs = new ArrayList<Unit>(defsResolver.getDefsOf(local));
        
        Set<Unit> defSet = new HashSet<Unit>(defs);
        defs.clear();
        defs.addAll(defSet);

        return defs;
    }

    public static List<Unit> findUses(Body body, Stmt stmt, Local local) {	
        Unit unit = (Unit) stmt;
        UnitGraph cfg = new BriefUnitGraph(body);
        SimpleLocalDefs defsResolver = new SimpleLocalDefs(cfg);
        SimpleLocalUses usesResolver = new SimpleLocalUses(cfg, defsResolver);

        List<Unit> uses = new ArrayList<Unit>();
        List<Unit> defs = defsResolver.getDefsOfAt(local, unit);
        for (Unit defUnit : defs) {
            List<UnitValueBoxPair> pairs = usesResolver.getUsesOf(defUnit);
            for (UnitValueBoxPair pair : pairs) {
                uses.add(pair.unit);
            }
        }

        return uses;
    }

    public static Set<Unit> findUses(Body body, Local local) {	
        UnitGraph cfg = new BriefUnitGraph(body);
        SimpleLocalDefs defsResolver = new SimpleLocalDefs(cfg);
        SimpleLocalUses usesResolver = new SimpleLocalUses(cfg, defsResolver);

        Set<Unit> uses = new HashSet<Unit>();
        List<Unit> defs = defsResolver.getDefsOf(local);
        for (Unit defUnit : defs) {
            List<UnitValueBoxPair> pairs = usesResolver.getUsesOf(defUnit);
            for (UnitValueBoxPair pair : pairs) {
                uses.add(pair.unit);
            }
        }

        return uses;
    }

    public static Set<Local> findallLocal(Body body, Set<Local> locals){
        Set<Unit> usesreturn = new HashSet<Unit>();
        Set<Local> taintLocals = new HashSet<Local>();
        Queue<Local> queue = new LinkedList<Local>();
        UnitGraph cfg = new BriefUnitGraph(body);
        SimpleLocalDefs defsResolver = new SimpleLocalDefs(cfg);
        SimpleLocalUses usesResolver = new SimpleLocalUses(cfg, defsResolver);
        
        for(Local taintedLocal : locals){
            taintLocals.add(taintedLocal);
            queue.add(taintedLocal);
            System.out.println("taintedLocal: "+taintedLocal);
        }

        while(!queue.isEmpty()){
            Local local = queue.poll();
            List<Unit> uses = LocalVariableAnalysis.findDefs(body, local);
            Set<Unit> uses2 = LocalVariableAnalysis.findUses(body, local);
            uses.addAll(uses2);
            for(Unit use : uses){
                Stmt stmt = (Stmt) use;
                if(stmt instanceof AssignStmt){
                    AssignStmt assignStmt = (AssignStmt) stmt;
                    Value rightValue = assignStmt.getRightOp();
                    if(rightValue instanceof Local){
                        Local leftLocal = (Local) rightValue;
                        if(!taintLocals.contains(leftLocal)){
                            taintLocals.add(leftLocal);
                            queue.add(leftLocal);
                        }
                    }
                    if(rightValue instanceof FieldRef){
                        FieldRef fieldRef = (FieldRef) rightValue;
                        if(fieldRef instanceof JInstanceFieldRef){
                            JInstanceFieldRef jInstanceFieldRef = (JInstanceFieldRef) fieldRef;
                                Local base = (Local) jInstanceFieldRef.getBase();
                                System.out.println(base);
                                if(!taintLocals.contains(base)){
                                    taintLocals.add(base);
                                    queue.add(base);
                                }
                        }
                    }
                }
                if (stmt.containsInvokeExpr()){
                    InvokeExpr invokeExpr = stmt.getInvokeExpr();
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
                usesreturn.add(stmt);
            }
        }

        return taintLocals;
    }

    public static Set<Unit> findallUses(Body body, Set<Local> locals){
        Set<Unit> usesreturn = new HashSet<Unit>();
        UnitGraph cfg = new BriefUnitGraph(body);
        SimpleLocalDefs defsResolver = new SimpleLocalDefs(cfg);
        SimpleLocalUses usesResolver = new SimpleLocalUses(cfg, defsResolver);
        Set<Local> solved = new HashSet<Local>();
        Queue<Local> queue = new LinkedList<Local>();
        for(Local local: locals){
            queue.add(local);
        }

        while(!queue.isEmpty()){
            Local local = queue.poll();
            if(solved.contains(local)){
                continue;
            }
            solved.add(local);
            Set<Unit> uses = LocalVariableAnalysis.findUses(body, local);
            for(Unit use : uses){
                Stmt stmt = (Stmt) use;
                List<ValueBox> usesBoxs = stmt.getUseBoxes();
                for(ValueBox valueBox : usesBoxs){
                    Value value = valueBox.getValue();
                    if(value instanceof Local){
                        Local localValue = (Local) value;
                        queue.add(localValue);
                    }
                }
                if(stmt instanceof AssignStmt){
                    AssignStmt assignStmt = (AssignStmt) stmt;
                    Value lefValue = assignStmt.getLeftOp();
                    if(lefValue instanceof Local){
                        Local leftLocal = (Local) lefValue;
                        queue.add(leftLocal);
                    }
                }
                usesreturn.add(stmt);
            }
        }

        return usesreturn;
    }

    public static String getVariableName(Value value) {
        FieldRef fieldRef = (FieldRef) value;
        fj.data.List<SootMethod> methods = fj.data.List.iterableList(fieldRef.getField().getDeclaringClass().getMethods());
        fj.data.List<SootMethod> initMethods = null;
        if(fieldRef.getField().isStatic()) 
            initMethods = methods.filter(method -> method.getName().equals("<clinit>"));
        else 
            initMethods = methods.filter(method -> method.getName().equals("<init>"));
        for (SootMethod initMethod : initMethods) {
            if (initMethod != null && initMethod.isConcrete()) {
                // Collects the defined string variable
                Body body = initMethod.retrieveActiveBody();
                for (Unit unit : body.getUnits()) {
                    Stmt stmt = (Stmt) unit;
                    if (stmt instanceof AssignStmt){
                        AssignStmt assignStmt = (AssignStmt) stmt;
                        if (assignStmt.getLeftOp() instanceof FieldRef){
                            FieldRef initFieldRef = (FieldRef) assignStmt.getLeftOp();
                            String variableName = fieldRef.getField().getDeclaringClass().getName()+"."+fieldRef.getField().getName();
                            String initvariableName = initFieldRef.getField().getDeclaringClass().getName()+"."+initFieldRef.getField().getName();
                            if (variableName.equals(initvariableName)) {
                                Value leftValue = assignStmt.getLeftOp();
                                Value rightValue = assignStmt.getRightOp();
                                // System.out.println("leftValue: "+leftValue.getType().toString());

                                // StringConstant
                                if (rightValue instanceof StringConstant || rightValue.getType().toString()=="java.lang.CharSequence") {
                                    return rightValue.toString();
                                }
                                // String[] 
                                else if (leftValue.getType().toString().equals("java.lang.String[]") && rightValue instanceof Local) {
                                    String retuString = "";
                                    Local rightValueLocal = (Local) rightValue;
                                    java.util.List<Unit> uses = LocalVariableAnalysis.findUses(body, stmt, rightValueLocal);
                                        for (Unit useUnit : uses) {
                                            if (!(useUnit instanceof Stmt))
                                                continue;
                                            Stmt useStmt = (Stmt) useUnit;
                                            // System.out.println("useStmt: "+useStmt.toString());
                                            if (useStmt instanceof AssignStmt){
                                                AssignStmt useAssignStmt = (AssignStmt) useStmt;
                                                Value useRightValue = useAssignStmt.getRightOp();
                                                if (useRightValue instanceof StringConstant || useRightValue.getType().toString()=="java.lang.CharSequence") {
                                                    retuString+=useRightValue.toString()+", ";
                                                }
                                            }
                                        }
                                    return retuString;
                                }
                                // List
                                else if (leftValue.getType().toString().equals("java.util.List") && !(rightValue instanceof NullConstant)){
                                    String retuString = "";
                                    Local rightValueLocal = (Local) rightValue;
                                    Local rightValueLocal2 = null;
                                    Local rightValueLocal3 = null;
                                    Stmt defStmt = null;
                                    java.util.List<Unit> defs = LocalVariableAnalysis.findDefs(body, stmt, rightValueLocal);
                                    Unit defUnit = defs.get(0);
                                    
                                    if (!(defUnit instanceof Stmt))
                                        continue;
                                    defStmt = (Stmt) defUnit;
                                    // System.out.println("defStmt: "+defStmt.toString());
                                    
                                    if (defStmt instanceof AssignStmt){  
                                        AssignStmt AssignStmtInList = (AssignStmt)defStmt;
                                        // System.out.println("AssignStmtInList: "+AssignStmtInList.getRightOp().toString());
                                        if (AssignStmtInList.getRightOp() instanceof NewExpr && AssignStmtInList.getRightOp().toString().equals("new java.util.ArrayList")) {
                                            java.util.List<Unit> uses = LocalVariableAnalysis.findUses(body, stmt, rightValueLocal);
                                            Unit useUnit = uses.get(0);
                                            Stmt useStmt = (Stmt) useUnit;
                                            // System.out.println("useStmt: "+useStmt.toString()+Info_Print.getStmtType(useStmt));
                                            if (useStmt.containsInvokeExpr()) {
                                                InvokeExpr invokeExpr = useStmt.getInvokeExpr();
                                                if (invokeExpr instanceof SpecialInvokeExpr){
                                                    // System.out.println("useStmt: "+useStmt.toString());
                                                    Value rightValueTemp = invokeExpr.getArg(0);
                                                    if(rightValueTemp instanceof Local){
                                                        rightValueLocal2 = (Local)rightValueTemp;
                                                        // System.out.println("rightValueLocal2: "+rightValueLocal2.toString());
                                                        java.util.List<Unit> defsInList = LocalVariableAnalysis.findDefs(body, useStmt, rightValueLocal2);
                                                        for (Unit defUnitInList : defsInList) {
                                                            if (!(defUnitInList instanceof Stmt))
                                                                continue;
                                                            defStmt = (Stmt) defUnitInList;
                                                            // System.out.println("defStmt: "+defStmt.toString());
                                                            if (defStmt.containsInvokeExpr() && !defStmt.getInvokeExpr().getArgs().isEmpty() && defStmt.getInvokeExpr().getArgs().get(0) instanceof Local){
                                                                rightValueLocal3 = (Local) defStmt.getInvokeExpr().getArgs().get(0);
                                                                // System.out.println("rightValueLocal3: "+rightValueLocal3.toString());
                                                            }
                                                        }
                                                        java.util.List<Unit> uses2 = LocalVariableAnalysis.findUses(body, defStmt, rightValueLocal3);
                                                        for (Unit useUnit2 : uses2) {
                                                            if (!(useUnit2 instanceof Stmt))
                                                                continue;
                                                            Stmt useStmt2 = (Stmt) useUnit2;
                                                            if (useStmt2 instanceof AssignStmt){
                                                                // System.out.println("useStmt3: "+useStmt.toString());
                                                                AssignStmt useAssignStmt = (AssignStmt) useStmt2;
                                                                Value useRightValue = useAssignStmt.getRightOp();
                                                                // System.out.println("useRightValue: "+useRightValue.toString().toLowerCase());
                                                                if (useRightValue instanceof StringConstant || useRightValue.getType().toString()=="java.lang.CharSequence") {
                                                                    retuString+=useRightValue.toString()+", ";
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if (defStmt.containsInvokeExpr() && !defStmt.getInvokeExpr().getArgs().isEmpty() && defStmt.getInvokeExpr().getArgs().get(0) instanceof Local){
                                        rightValueLocal2 = (Local) defStmt.getInvokeExpr().getArgs().get(0);
                                        java.util.List<Unit> uses = LocalVariableAnalysis.findUses(body, defStmt, rightValueLocal2);
                                        for (Unit useUnit : uses) {
                                            if (!(useUnit instanceof Stmt))
                                                continue;
                                            Stmt useStmt = (Stmt) useUnit;
                                            // System.out.println("useStmt: "+useStmt.toString());
                                            if (useStmt instanceof AssignStmt){
                                                AssignStmt useAssignStmt = (AssignStmt) useStmt;
                                                Value useRightValue = useAssignStmt.getRightOp();
                                                if (useRightValue instanceof StringConstant || useRightValue.getType().toString()=="java.lang.CharSequence") {
                                                    retuString+=useRightValue.toString()+", ";
                                                }
                                            }
                                        }
                                    }
                                    return retuString;
                                }
                                // ArrayList
                                else if (leftValue.getType().toString().equals("java.util.ArrayList") && !(rightValue instanceof NullConstant)){
                                    String retuString = "";
                                    Local rightValueLocal = (Local) rightValue;
                                    Local rightValueLocal2 = null;
                                    Local rightValueLocal3 = null;
                                    Stmt defStmt = null;
                                    java.util.List<Unit> uses = LocalVariableAnalysis.findUses(body, stmt, rightValueLocal);
                                    Unit useUnit = uses.get(0);
                                    Stmt useStmt = (Stmt) useUnit;
                                    // System.out.println("useStmt: "+useStmt.toString()+Info_Print.getStmtType(useStmt));
                                    if (useStmt.containsInvokeExpr()) {
                                        InvokeExpr invokeExpr = useStmt.getInvokeExpr();
                                        if (invokeExpr instanceof SpecialInvokeExpr){
                                            // System.out.println("useStmt: "+useStmt.toString());
                                            Value rightValueTemp = invokeExpr.getArg(0);
                                            if(rightValueTemp instanceof Local){
                                                rightValueLocal2 = (Local)rightValueTemp;
                                                // System.out.println("rightValueLocal2: "+rightValueLocal2.toString());
                                                java.util.List<Unit> defs = LocalVariableAnalysis.findDefs(body, useStmt, rightValueLocal2);
                                                for (Unit defUnit : defs) {
                                                    if (!(defUnit instanceof Stmt))
                                                        continue;
                                                    defStmt = (Stmt) defUnit;
                                                    // System.out.println("defStmt: "+defStmt.toString());
                                                    if (defStmt.containsInvokeExpr() && !defStmt.getInvokeExpr().getArgs().isEmpty() && defStmt.getInvokeExpr().getArgs().get(0) instanceof Local){
                                                        rightValueLocal3 = (Local) defStmt.getInvokeExpr().getArgs().get(0);
                                                        // System.out.println("rightValueLocal3: "+rightValueLocal3.toString());
                                                    }
                                                }
                                                java.util.List<Unit> uses2 = LocalVariableAnalysis.findUses(body, defStmt, rightValueLocal3);
                                                for (Unit useUnit2 : uses2) {
                                                    if (!(useUnit2 instanceof Stmt))
                                                        continue;
                                                    Stmt useStmt2 = (Stmt) useUnit2;
                                                    if (useStmt2 instanceof AssignStmt){
                                                        // System.out.println("useStmt3: "+useStmt.toString());
                                                        AssignStmt useAssignStmt = (AssignStmt) useStmt2;
                                                        Value useRightValue = useAssignStmt.getRightOp();
                                                        if (useRightValue instanceof StringConstant || useRightValue.getType().toString()=="java.lang.CharSequence") {
                                                            retuString+=useRightValue.toString()+", ";
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    return retuString;
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }
}
