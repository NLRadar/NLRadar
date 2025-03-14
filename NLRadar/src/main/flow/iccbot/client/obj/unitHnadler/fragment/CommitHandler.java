package iccbot.client.obj.unitHnadler.fragment;

import iccbot.analyze.model.analyzeModel.ObjectSummaryModel;
import iccbot.analyze.model.sootAnalysisModel.Context;
import iccbot.client.obj.model.fragment.FragmentSummaryModel;
import iccbot.client.obj.unitHnadler.UnitHandler;

public class CommitHandler extends UnitHandler {
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
        this.singleFrag.getSendFragment2Start().add(unit);
    }

}
