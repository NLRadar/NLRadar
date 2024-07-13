package iccbot.client.obj.target.ctg;

import iccbot.Global;
import iccbot.MyConfig;
import iccbot.SummaryLevel;
import iccbot.analyze.utils.ConstantUtils;
import iccbot.analyze.utils.output.FileUtils;
import iccbot.client.BaseClient;
import iccbot.client.cg.CallGraphClient;
import iccbot.client.cg.DynamicReceiverCGAnalyzer;
import iccbot.client.manifest.ManifestClient;
import iccbot.client.obj.ObjectAnalyzer;
import iccbot.client.obj.target.fragment.FragmentClient;
import iccbot.client.soot.IROutputClient;
import iccbot.client.statistic.model.StatisticResult;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import soot.SootMethod;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Analyzer Class
 *
 * @author hanada
 * @version 2.0
 */
@Slf4j
public class ICCSpecClient extends BaseClient {
    /**
     * analyze CTG for single app
     */
    @Override
    protected void clientAnalyze() {
        result = new StatisticResult();

        if (!MyConfig.getInstance().isManifestAnalyzeFinish()) {
            new ManifestClient().start();
            MyConfig.getInstance().setManifestAnalyzeFinish(true);
        }
        if (MyConfig.getInstance().isWriteSootOutput()) {
            new IROutputClient().start();
        }
        if (!MyConfig.getInstance().isCallGraphAnalyzeFinish()) {
            new CallGraphClient().start();
            MyConfig.getInstance().setCallGraphAnalyzeFinish(true);
        }
        if (!MyConfig.getInstance().isStaticValueAnalyzeFinish()) {
            if (MyConfig.getInstance().getMySwitch().isStaticFieldSwitch()) {
                StaticValueAnalyzer staticValueAnalyzer = new StaticValueAnalyzer();
                staticValueAnalyzer.start();
                MyConfig.getInstance().setStaticValueAnalyzeFinish(true);
            }
        }
        if (MyConfig.getInstance().getMySwitch().isDynamicBCSwitch()) {
            DynamicReceiverCGAnalyzer dynamicIntentFilterAnalyzer = new DynamicReceiverCGAnalyzer();
            dynamicIntentFilterAnalyzer.start();
        }

        if (MyConfig.getInstance().getMySwitch().isFragmentSwitch()) {
            if (!MyConfig.getInstance().isFragmentAnalyzeFinish()) {
                new FragmentClient().start();
                MyConfig.getInstance().setFragmentAnalyzeFinish(true);
            }
        }
        log.info("Analyzing ICC sending...");
        //rev attributes
        setMySwitch2();
        for (List<SootMethod> topoQueue : Global.v().getAppModel().getTopoMethodQueueSet()) {
            ObjectAnalyzer analyzer = new CTGAnalyzer(topoQueue, result);
            analyzer.start();
        }
        log.info("Successfully analyze with ICCSpecClient");

        //send attributes
        setMySwitch1();
        for (List<SootMethod> topoQueue : Global.v().getAppModel().getTopoMethodQueueSet()) {
            ObjectAnalyzer analyzer = new CTGAnalyzer(topoQueue, result);
            analyzer.start();
        }
        log.info("ICC sending analyze finished, analyzing ICC receiving...");

    }

    protected void setMySwitch1() {
        MyConfig.getInstance().getMySwitch().setSetAttributeStrategy(true);
        MyConfig.getInstance().getMySwitch().setGetAttributeStrategy(false);
        MyConfig.getInstance().getMySwitch().setSummaryStrategy(SummaryLevel.object);
    }

    protected void setMySwitch2() {
        MyConfig.getInstance().getMySwitch().setSetAttributeStrategy(false);
        MyConfig.getInstance().getMySwitch().setGetAttributeStrategy(true);
        MyConfig.getInstance().getMySwitch().setSummaryStrategy(SummaryLevel.path);

    }

    @Override
    public void clientOutput() throws IOException, DocumentException {
        outputCTGInfo();
    }

    private void outputCTGInfo() throws IOException, DocumentException {
        String summary_app_dir = MyConfig.getInstance().getResultFolder() + Global.v().getAppModel().getAppName()
                + File.separator;
        CTGClientOutput outer = new CTGClientOutput(this.result);

        String ictgFolder = summary_app_dir + ConstantUtils.ICTGSPEC;
        FileUtils.createFolder(ictgFolder);
        outer.writeComponentModelJson(ictgFolder, ConstantUtils.COMPONENTMODELJSON);
        outer.writePathSummaryModel(ictgFolder, ConstantUtils.SINGLEPATH_ENTRY, true);
        outer.writeIntentSummaryModel(ictgFolder, ConstantUtils.SINGLEOBJECT_ENTRY, true);
    }


    protected void setMySwitch() {
        // TODO Auto-generated method stub

    }
}