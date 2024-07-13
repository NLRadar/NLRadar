package iccbot.client.obj.target.fragment;

import iccbot.Global;
import iccbot.MyConfig;
import iccbot.analyze.utils.ConstantUtils;
import iccbot.analyze.utils.GraphUtils;
import iccbot.analyze.utils.output.FileUtils;
import iccbot.client.BaseClient;
import iccbot.client.cg.CallGraphClient;
import iccbot.client.manifest.ManifestClient;
import iccbot.client.obj.ObjectAnalyzer;
import iccbot.client.obj.target.ctg.StaticValueAnalyzer;
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
public class FragmentClient extends BaseClient {

    /**
     * analyze logic for single app
     *
     * @return
     */
    @Override
    protected void clientAnalyze() {
        result = new StatisticResult();
        if (!MyConfig.getInstance().isManifestAnalyzeFinish()) {
            new ManifestClient().start();
        }
        if (!MyConfig.getInstance().isCallGraphAnalyzeFinish()) {
            new CallGraphClient().start();
        }
        if (!MyConfig.getInstance().isStaticValueAnalyzeFinish()) {
            if (MyConfig.getInstance().getMySwitch().isStaticFieldSwitch()) {
                StaticValueAnalyzer staticValueAnalyzer = new StaticValueAnalyzer();
                staticValueAnalyzer.start();
            }
        }
        for (List<SootMethod> topoQueue : Global.v().getAppModel().getTopoMethodQueueSet()) {
            ObjectAnalyzer analyzer = new FragmentAnalyzer(topoQueue, result);
            analyzer.start();
        }
        log.info("Successfully analyze with FragmentClient");
        MyConfig.getInstance().setFragmentAnalyzeFinish(true);
    }

    @Override
    public void clientOutput() throws IOException, DocumentException {
        String summary_app_dir = MyConfig.getInstance().getResultFolder() + Global.v().getAppModel().getAppName()
                + File.separator;
        FileUtils.createFolder(summary_app_dir + ConstantUtils.FRAGFOLDETR);

        FragmentClientOutput outer = new FragmentClientOutput(this.result);
        /** Method **/
        outer.writeMethodSummaryModel(summary_app_dir + ConstantUtils.FRAGFOLDETR, ConstantUtils.SINGLEMETHOD_ENTRY, true);
//		outer.writeMethodSummaryModel(summary_app_dir + ConstantUtils.FRAGFOLDETR, ConstantUtils.SINGLEMETHOD_ALL, false);

        /** Path **/
        outer.writePathSummaryModel(summary_app_dir + ConstantUtils.FRAGFOLDETR, ConstantUtils.SINGLEPATH_ENTRY, true);
//		outer.writePathSummaryModel(summary_app_dir + ConstantUtils.FRAGFOLDETR, ConstantUtils.SINGLEPATH_ALL, false);

        /** Intent **/
        outer.writeSingleFragModel(summary_app_dir + ConstantUtils.FRAGFOLDETR, ConstantUtils.SINGLEOBJECT_ENTRY, true);
        outer.writeSingleFragModel(summary_app_dir + ConstantUtils.FRAGFOLDETR, ConstantUtils.SINGLEOBJECT_ALL, false);

        outer.writeATGModel(summary_app_dir + ConstantUtils.FRAGFOLDETR, ConstantUtils.ICTGMERGE + ".xml", Global.v()
                .getFragmentModel().getAtgModel());

        String dotname = Global.v().getAppModel().getAppName() + "_" + ConstantUtils.ICTGFRAG;
        outer.writeDotFile(summary_app_dir + ConstantUtils.FRAGFOLDETR, dotname, Global.v().getFragmentModel()
                .getAtgModel(), true);
        GraphUtils.generateDotFile(summary_app_dir + ConstantUtils.FRAGFOLDETR + dotname, "pdf");
    }

}