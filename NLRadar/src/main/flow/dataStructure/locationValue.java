package dataStructure;
import soot.SootMethod;
import soot.Body;
import soot.jimple.Stmt;

public class locationValue {
    public SootMethod method;
    public Body body;
    public Stmt stmt;

    public locationValue(SootMethod method, Body body, Stmt stmt) {
        this.method = method;
        this.body = body;
        this.stmt = stmt;
    }

    public SootMethod getMethod() {
        return method;
    }

    public Body getBody() {
        return body;
    }

    public Stmt getStmt() {
        return stmt;
    }

    public String getMethodClass(){
        return method.getDeclaringClass().getName();
    }
}
