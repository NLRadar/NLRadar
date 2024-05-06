package dataStructure;

import soot.SootMethod;
import soot.Type;
import soot.Local;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import soot.jimple.Stmt;

public class FunctionTaintInfo {
    private SootMethod method;
    private Set<Local> taintedLocals = new HashSet<Local>();
    private List<Local> reversetaitnedLocals = new ArrayList<Local>();
    private Stmt stmt;
    Boolean isConcrete = true;
    private Boolean isfieldreflect = false;

    public FunctionTaintInfo(SootMethod method, Set<Local> taint) {
        this.method = method;
        this.taintedLocals = taint;
    }

    public FunctionTaintInfo(SootMethod method, Set<Local> taint, Stmt stmt) {
        this.method = method;
        this.taintedLocals = taint;
        this.stmt = stmt;
    }

    public FunctionTaintInfo(SootMethod method, Set<Local> taint, List<Local> reversetaint, Stmt stmt) {
        this.method = method;
        this.taintedLocals = taint;
        this.reversetaitnedLocals = reversetaint;
        this.stmt = stmt;
    }

    public FunctionTaintInfo(SootMethod method, Set<Local> taint, List<Local> reversetaint, Stmt stmt, Boolean isfieldreflect) {
        this.method = method;
        this.taintedLocals = taint;
        this.reversetaitnedLocals = reversetaint;
        this.stmt = stmt;
        this.isfieldreflect = isfieldreflect;
    }

    public FunctionTaintInfo(SootMethod method, int mode){
        this.method = method;
        if(mode == 1){
            if(method.isConcrete()){
                int parameterCount = method.getParameterCount();
                for(int i = 0; i < parameterCount; i++) {
                    this.taintedLocals.add(method.getActiveBody().getParameterLocal(i));
                    this.reversetaitnedLocals.add(method.getActiveBody().getParameterLocal(i));
                }
            }
        }
    }

    public FunctionTaintInfo(SootMethod method) {
        this.method = method;
    }

    public FunctionTaintInfo(SootMethod method, Stmt stmt) {
        this.method = method;
        this.stmt = stmt;
    }

    public SootMethod getMethod() {
        return method;
    }

    public Set<Local> getTaintedLocals() {
        return taintedLocals;
    }

    public List<Local> getReverseTaintedLocals() {
        return reversetaitnedLocals;
    }

    public void setIsConcrete(Boolean isConcrete) {
        this.isConcrete = isConcrete;
    }

    public Boolean getIsFieldReflect() {
        return isfieldreflect;
    }

    public void setIsFieldReflect(Boolean isfieldreflect) {
        this.isfieldreflect = isfieldreflect;
    }

    public Boolean getIsConcrete() {
        return isConcrete;
    }

    public Stmt getStmt() {
        return stmt;
    }

    public void addTaintedLocal(Local local) {
        this.taintedLocals.add(local);
        this.reversetaitnedLocals.add(local);
        return;
    }

}
