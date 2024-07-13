package iccbot.client.obj.unitHnadler;

import iccbot.analyze.model.analyzeModel.ObjectSummaryModel;
import soot.SootMethod;
import soot.Unit;

public class ReceiveFromParaHandler extends UnitHandler {

    @Override
    public void handleSingleObject(ObjectSummaryModel singleObject) {
        singleObject.getReceiveFromParaList().add(unit);
    }

}
