package iccbot.client.obj.unitHnadler.ictg;

import iccbot.analyze.model.analyzeModel.ObjectSummaryModel;
import iccbot.analyze.utils.ConstantUtils;
import iccbot.analyze.utils.SootUtils;
import iccbot.client.obj.model.ctg.IntentSummaryModel;
import iccbot.client.obj.model.ctg.SendOrReceiveICCInfo;
import iccbot.client.obj.unitHnadler.UnitHandler;
import soot.SootMethod;
import soot.Unit;

public class SendIntent2ServiceHandler extends UnitHandler {

    IntentSummaryModel intentSummary;
    SootMethod sootMethod;
    Unit unit;
    public SendIntent2ServiceHandler(SootMethod sootMethod, Unit unit) {
        super();
        this.sootMethod = sootMethod;
        this.unit = unit;
    }


    @Override
    public void handleSingleObject(ObjectSummaryModel singleObject) {
        intentSummary = ((IntentSummaryModel) singleObject);
        intentSummary.getSendIntent2ICCList().add(unit);
        intentSummary.setTargetType(ConstantUtils.SERVICE);
        SendOrReceiveICCInfo setTriple = new SendOrReceiveICCInfo(unit, sootMethod.getSignature(), SootUtils.getIdForUnit(unit, sootMethod));
        intentSummary.setSendTriple(setTriple);
    }

}
