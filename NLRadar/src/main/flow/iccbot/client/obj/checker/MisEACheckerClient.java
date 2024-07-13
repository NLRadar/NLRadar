package iccbot.client.obj.checker;

import iccbot.MyConfig;
import iccbot.client.BaseClient;
import iccbot.client.manifest.ManifestClient;
import iccbot.client.soot.IROutputClient;
import iccbot.client.statistic.model.StatisticResult;
import org.dom4j.DocumentException;

import java.io.IOException;

public class MisEACheckerClient extends BaseClient {

    @Override
    protected void clientAnalyze() {
        result = new StatisticResult();

        if (!MyConfig.getInstance().isManifestAnalyzeFinish()) {
            new ManifestClient().start();
        }
        if (MyConfig.getInstance().isWriteSootOutput()) {
            new IROutputClient().start();
        }
        MisEAAnalysis misEaAnalyzer = new MisEAAnalysis();
        misEaAnalyzer.start();

        System.out.println("Successfully analyze with MisEACheckerClient.");
    }

    @Override
    public void clientOutput() throws IOException, DocumentException {

    }

}
