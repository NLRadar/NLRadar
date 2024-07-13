package iccbot.client.obj.unitHnadler;

import iccbot.analyze.model.analyzeModel.ObjectSummaryModel;
import soot.SootMethod;
import soot.Unit;

public class CreateHandler extends UnitHandler {


    @Override
    public void handleSingleObject(ObjectSummaryModel singleObject) {
        singleObject.getCreateList().add(unit);
    }

}
