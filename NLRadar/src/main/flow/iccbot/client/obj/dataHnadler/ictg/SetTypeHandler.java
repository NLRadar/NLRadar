package iccbot.client.obj.dataHnadler.ictg;

import iccbot.client.obj.dataHnadler.DataHandler;
import iccbot.client.obj.model.ctg.IntentSummaryModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SetTypeHandler extends DataHandler {

    @Override
    public void handleData(IntentSummaryModel intentSummary, String key, Set<String> dataSet) {
        Set<String> newDataSet = new HashSet<String>();
        for (String data : dataSet)
            newDataSet.add(data.replace("\"", ""));
        intentSummary.addSetTypeValueList(new ArrayList<>(newDataSet));
        return;
    }

}
