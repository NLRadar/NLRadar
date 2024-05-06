import dataStructure.GlobalValue;
import static utils.util.*;
import function.InfoPrint;
import function.broadcastconnect;
import utils.LoggerUtil;
import static graph.cganalysis.constructflow;

import soot.Scene;
import soot.SootMethod;
import soot.Transform;
import soot.PackManager;
import soot.options.Options;

import init.Flowdroidinit;
import soot.jimple.infoflow.results.InfoflowResults;
import soot.jimple.infoflow.taintWrappers.ITaintPropagationWrapper;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.methodSummary.data.provider.LazySummaryProvider;
import soot.jimple.infoflow.methodSummary.taintWrappers.SummaryTaintWrapper;
import soot.jimple.infoflow.taintWrappers.ITaintPropagationWrapper;
import soot.jimple.infoflow.android.InfoflowAndroidConfiguration;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        // broadcastconnect broadcastconnect = new broadcastconnect();
        // PackManager.v().getPack("jtp").add(new Transform("jtp.broadcastconnect", broadcastconnect));
        try{
            PackManager.v().runPacks();
        }catch (Exception e) {
            mylogger.logError(outPath+" "+e.toString());
            e.printStackTrace();
        }

        // String repackagedPath = "apkrepackaged"+"/"+apkname;
        // Path path = Paths.get(repackagedPath);
        // if(Files.exists(path)){
        //     try{
        //         Files.delete(path);
        //         System.out.println("delete success");
        //     }catch (Exception e) {
        //         mylogger.logError(outPath+" "+e.toString());
        //         e.printStackTrace();
        //     }
        // }

        // Options.v().set_output_dir("apkrepackaged");
        // Options.v().set_output_format(Options.output_format_dex);
        // PackManager.v().writeOutput();

    }

    
    public static void analysis(String apkname){
        GlobalValue globalValue = new GlobalValue();
        String apkPath = globalValue.APK_REPACKAGE_PATH;
        String jarPath = globalValue.JAR_Path;
        String outPath = globalValue.SOOT_OUTPUT_PATH;
        String apk = "testapk/"+"/"+apkname;
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

    public static void beginCgAnalysis(String fatherpath , String apkname){
        String apkPath = GlobalValue.APK_PATH;
        String jarPath = GlobalValue.JAR_Path;
        String outPath = GlobalValue.OUT_Path;
        String apk = fatherpath+apkname;
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
            // time limited
            future.get(10, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            infoPrint.writeToFile("Output/error/"+apkname,"runtimeerror"+"\n");
            infoPrint.writeToFile("Output/error/"+apkname,e.toString()+"\n");
            future.cancel(true);
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            infoPrint.writeToFile("Output/error/"+apkname,e.toString()+"\n");
        } finally {
            infoPrint.writeToFile("Output/error/"+apkname,"runtimeerror"+"\n");
            executor.shutdown();
        }
        System.out.println("cganalysis done");
        return;
    }

    public static void main(String[] args) {
        String testapk = args[0];
        String androidjar = args[1];
        GlobalValue.JAR_Path = androidjar;
        File testfile = new File(testapk);
        String filename = testfile.getName();
        String fatherpath = testfile.getParent()+"/";
        File destFile = new File(testapk+"_done");

        try{
            Files.move(testfile.toPath(), destFile.toPath());
        } catch (FileAlreadyExistsException e) {
            System.out.println("target file already exists");
        } catch (IOException e) {
            System.out.println("I/O error：" + e.getMessage());
        }

        try{
            beginCgAnalysis(fatherpath,filename+"_done");
        } catch (Exception e) {
            infoPrint.writeToFile(GlobalValue.error_Path+filename,e.toString()+"\n");
            e.printStackTrace();
        }
    }
    
}