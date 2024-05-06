package init;

import soot.G;
import soot.Scene;
import soot.options.Options;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sootinit {
    private static Logger logger = Logger.getLogger("cc");
    public static void init(String args,String androidJar) {
        G.reset();
        Options.v().set_process_multiple_dex(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_prepend_classpath(true);
        Options.v().set_output_format(Options.output_format_jimple);
        Options.v().set_android_jars(androidJar);
        if(args.endsWith("apk"))
            Options.v().set_src_prec(Options.src_prec_apk);
        else if(args.endsWith("jar"))
            Options.v().set_src_prec(Options.src_prec_apk_class_jimple);
        else if(args.endsWith("dex"))
            Options.v().set_src_prec(Options.src_prec_apk_class_jimple);

        Options.v().set_process_dir(Collections.singletonList(args));
        Options.v().set_force_overwrite(true);
        Options.v().set_whole_program(true);
 
        try{
            Scene.v().loadNecessaryClasses();
        }catch (Exception e) {
            logger.log(Level.INFO, args);
            e.printStackTrace();
        }
        
    }
}
