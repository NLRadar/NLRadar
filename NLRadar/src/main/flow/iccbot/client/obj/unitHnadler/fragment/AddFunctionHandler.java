package iccbot.client.obj.unitHnadler.fragment;

import iccbot.analyze.model.analyzeModel.ObjectSummaryModel;
import iccbot.analyze.model.sootAnalysisModel.Context;
import iccbot.analyze.model.sootAnalysisModel.Counter;
import iccbot.analyze.utils.ValueObtainer;
import iccbot.client.obj.model.fragment.FragmentSummaryModel;
import iccbot.client.obj.unitHnadler.UnitHandler;
import soot.*;

public class AddFunctionHandler extends UnitHandler {
    Context context;
    FragmentSummaryModel singleFrag;

    @Override
    public void handleSingleObject(ObjectSummaryModel singleObject) {
        this.handleSingleObject(new Context(), singleObject);
    }

    @Override
    public void handleSingleObject(Context context, ObjectSummaryModel singleObject) {
        this.context = context;
        this.singleFrag = (FragmentSummaryModel) singleObject;
        this.singleFrag.getDataHandleList().add(unit);
        addAnalyze();
    }

    @Override
    public void handleSingleObject(Context oldContextwithRealValue, ObjectSummaryModel singleObject, Unit targetUnit) {
        this.oldContextwithRealValue = oldContextwithRealValue;
        this.singleFrag = (FragmentSummaryModel) singleObject;
        this.singleFrag.getDataHandleList().add(unit);
        this.targetUnit = targetUnit;
        addAnalyze();
    }

    private void addAnalyze() {
        int id = 1;
        Value inputVar = getInputVar(id, unit);
        if (inputVar == null)
            return;
        Context objContextInner = new Context();
        if (oldContextwithRealValue != null) {
            objContextInner = constructContextObj(id + 1, unit);
        }
        ValueObtainer vo = new ValueObtainer(methodSig, "", objContextInner, new Counter());
        for (String res : vo.getValueOfVar(inputVar, unit, 0).getValues()) {
            if (res.contains("new ")) {
                transformFragmentMethod(methodUnderAnalyze.getDeclaringClass(), unit, res.replace("new ", ""),
                        methodUnderAnalyze);
            }
        }

    }

    private void transformFragmentMethod(SootClass sourceCls, Unit u, String res, SootMethod sm) {
        for (SootClass sc : Scene.v().getApplicationClasses()) {
            if (!sc.getName().equals(res))
                continue;
            singleFrag.getAddList().add(sc.getName());
            singleFrag.addSetDestinationList(sc.getName());
        }
        return;

    }

}
