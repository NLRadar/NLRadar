package iccbot.client.obj.unitHnadler.ictg;

import iccbot.analyze.model.analyzeModel.ObjectSummaryModel;
import iccbot.client.obj.model.ctg.IntentSummaryModel;
import iccbot.client.obj.unitHnadler.UnitHandler;
import soot.SootMethod;
import soot.Unit;

public class ReceiveFromOutHandler extends UnitHandler {


    @Override
    public void handleSingleObject(ObjectSummaryModel singleObject) {
        ((IntentSummaryModel) singleObject).getReceiveFromOutList().add(unit);
    }

}
