package iccbot.client.obj.model.ctg;

import com.alibaba.fastjson.annotation.JSONField;
import iccbot.analyze.model.analyzeModel.ObjectSummaryModel;
import iccbot.analyze.model.analyzeModel.PathSummaryModel;
import iccbot.analyze.utils.output.PrintUtils;
import iccbot.client.obj.model.component.BundleType;
import iccbot.client.obj.model.component.ExtraData;
import soot.Unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IntentSummaryModel extends ObjectSummaryModel implements Serializable, Cloneable {

    // unit
    @JSONField(serialize = false)
    private List<Unit> receiveFromOutList;
    @JSONField(serialize = false)
    private List<Unit> sendIntent2ICCList;
    // value
    @JSONField(name = "action_receive")
    private List<String> getActionCandidateList;
    @JSONField(name = "categories_receive")
    private List<String> getCategoryCandidateList;
    @JSONField(name = "data_receive")
    private List<String> getDataCandidateList;
    @JSONField(name = "type_receive")
    private List<String> getTypeCandidateList;
    @JSONField(serialize = false)
    private BundleType getExtrasCandidateList;
    @JSONField(name = "action_send")
    private List<String> setActionValueList;
    @JSONField(name = "categories_send")
    private List<String> setCategoryValueList;
    @JSONField(name = "data_send")
    private List<String> setDataValueList;
    @JSONField(name = "type_send")
    private List<String> setTypeValueList;
    @JSONField(serialize = false)
    private BundleType setExtrasValueList;
    @JSONField(serialize = false)
    private List<String> setDestinationList;
    @JSONField(serialize = false)
    private List<String> setFlagsList;
    @JSONField(serialize = false)
    private String targetType;
    @JSONField(serialize = false)
    private SendOrReceiveICCInfo sendTriple;
    @JSONField(serialize = false)
    private List<SendOrReceiveICCInfo> receiveTriple = new ArrayList<>();
    @JSONField(serialize = false)
    private boolean isImplicit = false;
    public IntentSummaryModel(PathSummaryModel pathSummary) {
        super(pathSummary);
        setReceiveFromOutList(new ArrayList<Unit>());
        setSendIntent2ICCList(new ArrayList<Unit>());

        // intent data get operation
        setGetActionCandidateList(new ArrayList<String>());
        setGetCategoryCandidateList(new ArrayList<String>());
        setGetDataCandidateList(new ArrayList<String>());
        setGetTypeCandidateList(new ArrayList<String>());
        setGetExtrasCandidateList(new BundleType());

        setListActionValueList(new ArrayList<String>());
        setListCategoryValueList(new ArrayList<String>());
        setListDataValueList(new ArrayList<String>());
        setListTypeValueList(new ArrayList<String>());

        setListDestinationList(new ArrayList<String>());
        setListFlagsList(new ArrayList<String>());
        setListExtrasValueList(new BundleType());
    }


    public SendOrReceiveICCInfo getSendTriple() {
        return sendTriple;
    }

    public void setSendTriple(SendOrReceiveICCInfo sendTriple) {
        this.sendTriple = sendTriple;
    }

    public List<SendOrReceiveICCInfo> getReceiveTriple() {
        return receiveTriple;
    }

    public void setReceiveTriple(List<SendOrReceiveICCInfo> receiveTriple) {
        this.receiveTriple = receiveTriple;
    }


    public void copy(IntentSummaryModel temp) {
        super.copy(temp);
        setReceiveFromOutList(temp.getReceiveFromOutList());
        setSendIntent2ICCList(temp.getSendIntent2ICCList());

        // intent data get operation
        setGetActionCandidateList(temp.getGetActionCandidateList());
        setGetCategoryCandidateList(temp.getGetCategoryCandidateList());
        setGetDataCandidateList(temp.getGetDataCandidateList());
        setGetTypeCandidateList(temp.getGetTypeCandidateList());
        setGetExtrasCandidateList(temp.getGetExtrasCandidateList());

        setListActionValueList(temp.getSetActionValueList());
        setListCategoryValueList(temp.getSetCategoryValueList());
        setListDataValueList(temp.getSetDataValueList());
        setListTypeValueList(temp.getSetTypeValueList());
        setListDestinationList(temp.getSetDestinationList());
        setListFlagsList(temp.getSetFlagsList());
        setListExtrasValueList(temp.getSetExtrasValueList());
    }

    @Override
    public void merge(ObjectSummaryModel temp) {
        super.merge(temp);
        IntentSummaryModel temp2 = (IntentSummaryModel) temp;
        getReceiveFromOutList().addAll(temp2.getReceiveFromOutList());
        getSendIntent2ICCList().addAll(temp2.getSendIntent2ICCList());

        // intent data get operation
        getGetActionCandidateList().addAll(temp2.getGetActionCandidateList());
        getGetCategoryCandidateList().addAll(temp2.getGetCategoryCandidateList());
        getGetDataCandidateList().addAll(temp2.getGetDataCandidateList());
        getGetTypeCandidateList().addAll(temp2.getGetTypeCandidateList());
        Map<String, List<ExtraData>> extraMap = temp2.getGetExtrasCandidateList().obtainBundle();
        for (Entry<String, List<ExtraData>> en : extraMap.entrySet()) {
            getGetExtrasCandidateList().obtainBundle().put(en.getKey(), en.getValue());
        }
        for (Entry<String, List<ExtraData>> entry : temp2.getGetExtrasCandidateList().obtainBundle().entrySet())
            getGetExtrasCandidateList().obtainBundle().put(entry.getKey(), entry.getValue());

        addSetActionValueList(temp2.getSetActionValueList());
        addSetCategoryValueList(temp2.getSetCategoryValueList());
        addSetDataValueList(temp2.getSetDataValueList());
        addSetTypeValueList(temp2.getSetTypeValueList());
        addSetDestinationList(temp2.getSetDestinationList());
        addSetFlagsList(temp2.getSetFlagsList());
        for (Entry<String, List<ExtraData>> entry : temp2.getSetExtrasValueList().obtainBundle().entrySet())
            getSetExtrasValueList().obtainBundle().put(entry.getKey(), entry.getValue());
        setTargetType(temp2.getTargetType());

    }

    @Override
    public String toHashString() {
        String res = super.toHashString();

        res += PrintUtils.printList(setActionValueList);
        res += PrintUtils.printList(setCategoryValueList);
        res += PrintUtils.printList(setDataValueList);
        res += PrintUtils.printList(setTypeValueList);
        res += PrintUtils.printList(setFlagsList);
        res += PrintUtils.printList(setFlagsList);
        res += PrintUtils.printList(setDestinationList);
        res += setExtrasValueList.obtainBundle();
        return res;
    }

    @Override
    public String toString() {
        String res = super.toString();
        res += "receiveFromOutList:" + PrintUtils.printList(receiveFromOutList) + "\n";
        res += "sendIntent2ICCList:" + PrintUtils.printList(sendIntent2ICCList) + "\n";

        res += "getActionCandidateList:" + PrintUtils.printList(getActionCandidateList) + "\n";
        res += "getCategoryCandidateList:" + PrintUtils.printList(getCategoryCandidateList) + "\n";
        res += "getDataCandidateList:" + PrintUtils.printList(getDataCandidateList) + "\n";
        res += "getTypeCandidateList:" + PrintUtils.printList(getTypeCandidateList) + "\n";
        res += "getExtrasCandidateList:" + getExtrasCandidateList + "\n";

        res += "ListActionValueList:" + PrintUtils.printList(setActionValueList) + "\n";
        res += "ListCategoryValueList:" + PrintUtils.printList(setCategoryValueList) + "\n";
        res += "ListDataValueList:" + PrintUtils.printList(setDataValueList) + "\n";
        res += "ListTypeValueList:" + PrintUtils.printList(setTypeValueList) + "\n";
		    res += "ListExtrasValueList:" + setExtrasValueList.toString() + "\n";
        res += "ListDestinationList:" + PrintUtils.printList(setDestinationList) + "\n";
        res += "ListFlagsList:" + PrintUtils.printList(setFlagsList) + "\n";
        return res;
    }

    public BundleType getGetExtrasCandidateList() {
        return getExtrasCandidateList;
    }

    //for json seed output
    public Set<ExtraData> getExtras_send() {
        if (setExtrasValueList.getExtraDatas().size() > 0)
            return setExtrasValueList.getExtraDatas();
        return null;
    }

    //for json seed output
    public Set<ExtraData> getExtras_receive() {
        if (getExtrasCandidateList.getExtraDatas().size() > 0)
            return getExtrasCandidateList.getExtraDatas();
        return null;
    }

    public void setGetExtrasCandidateList(BundleType getExtrasCandidateList) {
        this.getExtrasCandidateList = getExtrasCandidateList;
    }

    public List<String> getGetActionCandidateList() {
        return getActionCandidateList;
    }

    public void setGetActionCandidateList(List<String> getActionCandidateList) {
        this.getActionCandidateList = getActionCandidateList;
    }

    public List<String> getGetCategoryCandidateList() {
        return getCategoryCandidateList;
    }

    public void setGetCategoryCandidateList(List<String> getCategoryCandidateList) {
        this.getCategoryCandidateList = getCategoryCandidateList;
    }

    public List<String> getGetDataCandidateList() {
        return getDataCandidateList;
    }

    public void setGetDataCandidateList(List<String> List) {
        this.getDataCandidateList = List;
    }

    public List<String> getGetTypeCandidateList() {
        return getTypeCandidateList;
    }

    public void setGetTypeCandidateList(List<String> getTypeCandidateList) {
        this.getTypeCandidateList = getTypeCandidateList;
    }

    public List<String> getSetActionValueList() {
        return setActionValueList;
    }
    public void addSetActionValueList(String string) {
        if(!setActionValueList.contains(string))
            setActionValueList.add(string);
    }
    public void addSetActionValueList(List <String> list) {
        for(String string: list){
            if(!setActionValueList.contains(string))
                setActionValueList.add(string);
        }
    }
    public void setListActionValueList(List<String> setActionValueList) {
        this.setActionValueList = setActionValueList;
    }

    public List<String> getSetCategoryValueList() {
        return setCategoryValueList;
    }

    public void addSetCategoryValueList(String string) {
        if(!setCategoryValueList.contains(string))
            setCategoryValueList.add(string);
    }
    public void addSetCategoryValueList(List <String> list) {
        for(String string: list){
            if(!setCategoryValueList.contains(string))
                setCategoryValueList.add(string);
        }
    }

    public void setListCategoryValueList(List<String> setCategoryValueList) {
        this.setCategoryValueList = setCategoryValueList;
    }

    public List<String> getSetDataValueList() {
        return setDataValueList;
    }

    public void addSetDataValueList(String string) {
        if(!setDataValueList.contains(string))
            setDataValueList.add(string);
    }
    public void addSetDataValueList(List <String> list) {
        for(String string: list){
            if(!setDataValueList.contains(string))
                setDataValueList.add(string);
        }
    }
    public void setListDataValueList(List<String> setDataValueList) {
        this.setDataValueList = setDataValueList;
    }

    public List<String> getSetTypeValueList() {
        return setTypeValueList;
    }

    public void addSetTypeValueList(String string) {
        if(!setTypeValueList.contains(string))
            setTypeValueList.add(string);
    }
    public void addSetTypeValueList(List <String> list) {
        for(String string: list){
            if(!setTypeValueList.contains(string))
                setTypeValueList.add(string);
        }
    }
    public void setListTypeValueList(List<String> setTypeValueList) {
        this.setTypeValueList = setTypeValueList;
    }

    public BundleType getSetExtrasValueList() {
        return setExtrasValueList;
    }

    public void setListExtrasValueList(BundleType setExtrasValueList) {
        this.setExtrasValueList = setExtrasValueList;
    }

    public List<String> getSetDestinationList() {
        return setDestinationList;
    }

    public void addSetDestinationList(String string) {
        if(!setDestinationList.contains(string))
            setDestinationList.add(string);
    }
    public void addSetDestinationList(List <String> list) {
        for(String string: list){
            if(!setDestinationList.contains(string))
                setDestinationList.add(string);
        }
    }

    public void setListDestinationList(List<String> setDestinationList) {
        this.setDestinationList = setDestinationList;
    }

    public List<Unit> getSendIntent2ICCList() {
        return sendIntent2ICCList;
    }

    public void setSendIntent2ICCList(List<Unit> sendIntent2ICCList) {
        this.sendIntent2ICCList = sendIntent2ICCList;
    }

    public List<String> getSetFlagsList() {
        return setFlagsList;
    }


    public void addSetFlagsList(String string) {
        if(!setFlagsList.contains(string))
            setFlagsList.add(string);
    }
    public void addSetFlagsList(List <String> list) {
        for(String string: list){
            if(!setFlagsList.contains(string))
                setFlagsList.add(string);
        }
    }

    public void setListFlagsList(List<String> setFlagsList) {
        this.setFlagsList = setFlagsList;
    }

    public List<Unit> getReceiveFromOutList() {
        return receiveFromOutList;
    }

    public void setReceiveFromOutList(List<Unit> receiveFromOutList) {
        this.receiveFromOutList = receiveFromOutList;
    }

    /**
     * @return the targetType
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * @param targetType the targetType to List
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }


    public boolean isImplicit() {
        return isImplicit;
    }

    public void setImplicit(boolean implicit) {
        isImplicit = implicit;
    }
}
