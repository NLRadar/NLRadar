package function;

import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.Body;
import soot.Local;
import soot.PackManager;
import soot.SootMethodRef;
import soot.Unit;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.VirtualInvokeExpr;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.util.Chain;

import java.util.Map;
import java.util.Iterator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import utils.LoggerUtil;


public class InfoPrint extends SceneTransformer{
    //change your dir
    private LoggerUtil mylogger = new LoggerUtil();
    String cgfilepath = "/Output/cg.txt";
    String jimplefilepath = "/Output/jimple.txt";
    String broadcastrecord = "/Output/broadcastrecord.txt";

    public static void writeToFile(String filepath,String content){
        try{
            File file = new File(filepath);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,true);
            fw.append(content);
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void internalTransform(String phaseName, Map<String, String> options) {
        for(SootClass sootClass : Scene.v().getClasses()){
            for(SootMethod sootMethod : sootClass.getMethods()){
                Boolean flag = false;
                try{
                    if(sootMethod.isConcrete()){
                        Body body = sootMethod.retrieveActiveBody();

                        writeToFile(jimplefilepath, "Method: " + sootMethod.getSignature()+"\n");
                        Chain<Unit> units = body.getUnits();
                        for(Iterator<Unit> iter = units.snapshotIterator();iter.hasNext();){
                            Unit unit = iter.next();
                            if(unit instanceof Stmt){
                                Stmt stmt = (Stmt) unit;
                                //System.out.println(stmt);
                                writeToFile(jimplefilepath, stmt.toString()+"\n");
                            }
                        }
                    }
                }catch (Exception e) {
                    mylogger.logError(e.toString());
                    e.printStackTrace();
                    System.out.println("error");
                }
            }
            writeToFile(jimplefilepath, "\n");
        }
    }

    public void cgprint(){
        int count =0;
        
        for(SootMethod method : Scene.v().getEntryPoints()){
            System.out.println("Method: "+method.getSignature()+"\n");
            writeToFile(cgfilepath, "Method: "+method.getSignature());
            Iterator<Edge> edgeIterator = Scene.v().getCallGraph().edgesOutOf(method);
            if (edgeIterator == null){
                System.out.println("edgeIterator is null");
                return;
            }
            while(edgeIterator.hasNext()){
                Edge edge = edgeIterator.next();
                System.out.println("Call from "+edge.getSrc().method().getSignature()+" to "+edge.getTgt().method().getSignature());
                writeToFile(cgfilepath, "Call from "+edge.getSrc().method().getSignature()+" to "+edge.getTgt().method().getSignature()+"\n");
            }
        }

        for(Edge edge : Scene.v().getCallGraph()){
            count++;
            try{
                System.out.println("Call from "+edge.getSrc().method().getSignature()+" to "+edge.getTgt().method().getSignature());
                writeToFile(cgfilepath, "Call from "+edge.getSrc().method().getSignature()+" to "+edge.getTgt().method().getSignature()+"\n");
            }catch (Exception e) {
                System.out.println("edge.getSrc().method() is null");
                e.printStackTrace();
            }
            
        }
        
        for(Iterator<SootClass> iter = Scene.v().getClasses().snapshotIterator(); iter.hasNext();){
            SootClass sootclass = iter.next();
            List<SootMethod> sootMethods = sootclass.getMethods();
            for(int i = 0; i < sootMethods.size(); i++){
                SootMethod sootmethod = sootMethods.get(i);
                String methodname = sootmethod.getSignature();
                Iterator<Edge> edgeIterator = Scene.v().getCallGraph().edgesInto(sootmethod);
                if (edgeIterator == null){
                    System.out.println("edgeIterator is null");
                    return;
                }
                while(edgeIterator.hasNext()){
                    Edge edge = edgeIterator.next();
                    System.out.println("engeinto: Call from "+edge.getSrc().method().getSignature()+" to "+edge.getTgt().method().getSignature());
                    writeToFile(cgfilepath, "engeinto: Call from "+edge.getSrc().method().getSignature()+" to "+edge.getTgt().method().getSignature()+"\n");
                    writeToFile(cgfilepath, "from method" + methodname+"\n");
                }
            }
        }
        System.out.println("cgcount: "+count);
    }
}
