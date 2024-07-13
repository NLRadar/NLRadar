package init;


import dataStructure.GlobalValue;
import soot.G;
import soot.Scene;
import soot.jimple.infoflow.android.InfoflowAndroidConfiguration;
import soot.jimple.infoflow.android.SetupApplication;
import soot.options.Options;

public class Flowdroidinit {
    public static SetupApplication init(String apk,String jarPath,String apkname){
        G.reset();
        InfoflowAndroidConfiguration config = new InfoflowAndroidConfiguration();
        config.getAnalysisFileConfig().setAndroidPlatformDir(jarPath);//android jar
        config.getAnalysisFileConfig().setTargetAPKFile(apk);
        config.getAnalysisFileConfig().setSourceSinkFile("SourcesAndSinksNotification.txt");
        config.setLogSourcesAndSinks(true);
        config.setDataFlowTimeout(300);
        config.getCallbackConfig().setCallbackAnalysisTimeout(300);
        config.getPathConfiguration().setPathReconstructionTimeout(300);
        config.getAnalysisFileConfig().setOutputFile(GlobalValue.SOOT_OUTPUT_PATH+"/"+apkname.substring(0,apkname.length()-4)+".xml");
        config.getCallbackConfig().setEnableCallbacks(true);
        
        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_none);
		Options.v().set_android_jars(jarPath);
		Options.v().set_process_multiple_dex(true);
		Options.v().set_android_api_version(24);
		Options.v().set_output_format(Options.output_format_none);
		Options.v().set_force_overwrite(true);
		Options.v().set_allow_phantom_refs(true);
		Options.v().set_whole_program(true);
		Options.v().ignore_resolution_errors();
        Options.v().set_keep_line_number(true);
        Options.v().set_keep_offset(true);

        Scene.v().addBasicClass("android.app.Service,HIERARCHY");
        

        SetupApplication app = new SetupApplication(config);

        try{
            Scene.v().loadNecessaryClasses();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return app;
    }
}
