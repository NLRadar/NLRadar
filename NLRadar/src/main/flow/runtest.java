import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import dataStructure.GlobalValue;
import function.InfoPrint;
import static graph.cganalysis.constructflow;
import init.Flowdroidinit;
import soot.PackManager;
import soot.Scene;
import soot.Transform;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.methodSummary.data.provider.LazySummaryProvider;
import soot.jimple.infoflow.methodSummary.taintWrappers.SummaryTaintWrapper;
import soot.jimple.infoflow.results.InfoflowResults;
import soot.jimple.infoflow.taintWrappers.ITaintPropagationWrapper;
import utils.LoggerUtil;
import utils.ReadICCBot;
import static utils.util.getapk;

public class runtest {
    private static LoggerUtil mylogger = new LoggerUtil();
    private static InfoPrint infoPrint = new InfoPrint();

    public static void repackage(String apkname){
        GlobalValue globalValue = new GlobalValue();
        String apkPath = globalValue.APK_PATH;
        String jarPath = globalValue.JAR_Path;
        String outPath = globalValue.OUT_Path;

        String apk = apkPath+"/"+apkname;
        SetupApplication app = Flowdroidinit.init(apk,jarPath,apkname);
        app.getConfig().setMergeDexFiles(true);
        app.constructCallgraph();
        GlobalValue.callgraph = Scene.v().getCallGraph();
        GlobalValue.sootClass = Scene.v().getClasses();
        
        infoPrint.cgprint();
        PackManager.v().getPack("wjtp").add(new Transform("wjtp.MethodFeatureTransformer", infoPrint));
        try{
            PackManager.v().runPacks();
        }catch (Exception e) {
            mylogger.logError(outPath+" "+e.toString());
            e.printStackTrace();
        }

    }

    
    public static void analysis(String apkname){
        GlobalValue globalValue = new GlobalValue();
        String apkPath = globalValue.APK_REPACKAGE_PATH;
        String jarPath = globalValue.JAR_Path;
        String outPath = globalValue.SOOT_OUTPUT_PATH;
        String apk = apkPath+"/"+apkname;
        SetupApplication app = Flowdroidinit.init(apk,jarPath,apkname);
        try{
            ITaintPropagationWrapper taintWrapper = new SummaryTaintWrapper(new LazySummaryProvider("summariesManual"));
            app.setTaintWrapper(taintWrapper);
            InfoflowResults res = app.runInfoflow();
            Writer writer = new FileWriter(outPath+File.separator+apkname.substring(0,apkname.length()-4)+".txt");
            res.printResults(writer);
            writer.close();

        }catch (Exception e) {
            mylogger.logError(outPath+" "+e.toString());
            e.printStackTrace();
        }
        
    }

    public static void beginCgAnalysis(String apkname){
        String apkPath = GlobalValue.APK_PATH;
        String jarPath = GlobalValue.JAR_Path;
        String outPath = GlobalValue.OUT_Path;
        String apk = apkPath+"/"+apkname;
        SetupApplication app = Flowdroidinit.init(apk,jarPath,apkname);
        app.getConfig().setMergeDexFiles(true);
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            app.constructCallgraph();
            GlobalValue.callgraph = Scene.v().getCallGraph();
            GlobalValue.sootClass = Scene.v().getClasses();
            constructflow(apkname);
        });
        try {
            future.get(15 , TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            infoPrint.writeToFile("Output/error/"+apkname,"runtimeerror"+"\n");
            infoPrint.writeToFile("Output/error/"+apkname,e.toString()+"\n");
            future.cancel(true);
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            infoPrint.writeToFile("Output/error/"+apkname,"runtimeerror"+"\n");
            infoPrint.writeToFile("Output/error/"+apkname,e.toString()+"\n");
            // System.exit(1);
        } finally {
            infoPrint.writeToFile("Output/error/"+apkname,"runtimeerror"+"\n");
            executor.shutdown();
            // System.exit(1);
        }
        System.out.println("cganalysis done");
        return;
    }

    public static void main(String[] args) {
        Vector<String> app_name_vec = new Vector<String>();
        app_name_vec = getapk(GlobalValue.APK_PATH);
        ReadICCBot readICCBot = new ReadICCBot();
        for(int i=0;i<app_name_vec.size();i++){
            String apk = GlobalValue.APK_PATH+"/"+app_name_vec.get(i);
            File srcFile = new File(apk);
            File destFile = new File(apk+"_done");
            try{
                Files.move(srcFile.toPath(), destFile.toPath());
            } catch (FileAlreadyExistsException e) {
                System.out.println("Target file already exists: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
            // repackage(app_name_vec.get(i)+"_done");
            // analysis(app_name_vec.get(i));
            try{
                beginCgAnalysis(app_name_vec.get(i)+"_done");
            } catch (Exception e) {
                infoPrint.writeToFile("Output/error/"+app_name_vec.get(i),e.toString()+"\n");
                e.printStackTrace();
            }
            
        }
        System.exit(0);
        return;
    }
    
}