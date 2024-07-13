package iccbot.client.obj.dataHnadler.ictg;

import iccbot.analyze.utils.StringUtils;
import iccbot.client.obj.dataHnadler.DataHandler;
import iccbot.client.obj.model.component.Flag;
import iccbot.client.obj.model.ctg.IntentSummaryModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SetFlagHandler extends DataHandler {

    @Override
    public void handleData(IntentSummaryModel intentSummary, String key, Set<String> dataSet) {
        Set<String> newDataSet = new HashSet<String>();
        for (String data : dataSet) {
            if (StringUtils.isInteger(data)) {
                Flag flag = new Flag();
                try {
                    newDataSet.add(flag.getFlag(Integer.parseInt(data)));
                } catch (Exception NumberFormatException) {
                    newDataSet.add(data);
                }
            } else {
                newDataSet.add(data);
            }
        }
        intentSummary.addSetFlagsList(new ArrayList<>(newDataSet));
    }
}
