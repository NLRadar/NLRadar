package iccbot.client.related.gator;

import iccbot.Global;
import iccbot.MyConfig;
import iccbot.analyze.model.analyzeModel.AppModel;
import iccbot.analyze.utils.ConstantUtils;
import iccbot.analyze.utils.GraphUtils;
import iccbot.analyze.utils.output.FileUtils;
import iccbot.client.BaseClient;
import iccbot.client.manifest.ManifestClient;
import iccbot.client.obj.model.atg.ATGModel;
import iccbot.client.related.gator.model.GatorModel;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;

/**
 * Analyzer Class
 *
 * @author hanada
 * @version 2.0
 */
@Slf4j
public class GatorATGResultEvaluateClient extends BaseClient {

    /**
     * analyze logic for single app
     *
     * @return
     */
    @Override
    protected void clientAnalyze() {
        if (!MyConfig.getInstance().isManifestAnalyzeFinish()) {
            new ManifestClient().start();
        }
        ATGModel model = Global.v().getGatorModel().getGatorAtgModel();
        model.setATGFilePath(ConstantUtils.GATORFOLDETR + Global.v().getAppModel().getAppName() + "_wtg.txt");
        ATGReader reader = new ATGReader(model);
        if (reader.obtainATGfromFile()) {
            reader.constructModelForGator();
        }
        log.info("Successfully analyze with GatorGraphClient");
    }

    @Override
    public void clientOutput() throws IOException, DocumentException {
        AppModel appModel = Global.v().getAppModel();
        String summary_app_dir = MyConfig.getInstance().getResultFolder() + Global.v().getAppModel().getAppName()
                + File.separator;
        FileUtils.createFolder(summary_app_dir + ConstantUtils.GATORFOLDETR);

        FileUtils.copyFile(ConstantUtils.GATORFOLDETR + appModel.getAppName() + "_wtg.txt", summary_app_dir
                + ConstantUtils.GATORFOLDETR + appModel.getAppName() + "_wtg.txt");
        GatorModel model = Global.v().getGatorModel();
        String dotname = Global.v().getAppModel().getAppName() + "_" + ConstantUtils.ATGDOT_GATOR;
        GatorClientOutput.writeDotFileofGator(summary_app_dir + ConstantUtils.GATORFOLDETR, dotname, model.getGatorAtgModel());
        GraphUtils.generateDotFile(summary_app_dir + ConstantUtils.GATORFOLDETR + dotname, "pdf");

//		
//		FileUtils.copyFile(ConstantUtils.GATORFOLDETR + appModel.getAppName() + "_wtg.dot", summary_app_dir
//				+ ConstantUtils.GATORFOLDETR + appModel.getAppName() + "_wtg.dot");
//		FileUtils.copyFile(ConstantUtils.GATORFOLDETR + appModel.getAppName() + "_wtg.pdf", summary_app_dir
//				+ ConstantUtils.GATORFOLDETR + appModel.getAppName() + "_wtg.pdf");
    }

}