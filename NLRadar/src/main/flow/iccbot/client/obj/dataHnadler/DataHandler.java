package iccbot.client.obj.dataHnadler;

import iccbot.client.obj.dataHnadler.ictg.*;
import iccbot.client.obj.model.ctg.IntentSummaryModel;

import java.util.Set;

public abstract class DataHandler {

    public abstract void handleData(IntentSummaryModel intentSummary, String className, Set<String> dataSet);

    /**
     * get the correct handler of target unit
     *
     * @param dataType
     * @return
     */
    public static DataHandler getDataHandler(String dataType) {
        if (dataType == null)
            return null;
        if (dataType.equals("setAction")) {
            return new SetActionHandler();
        } else if (dataType.equals("setCategory")) {
            return new SetCategoryHandler();
        } else if (dataType.equals("setData")) {
            return new SetDataHandler();
        } else if (dataType.equals("setType")) {
            return new SetTypeHandler();
        } else if (dataType.equals("setFlag")) {
            return new SetFlagHandler();
        } else if (dataType.equals("setComponent")) {
            return new SetComponentHandler();
        } else if (dataType.equals("getAction")) {
            return new GetActionHandler();
        } else if (dataType.equals("getCategory")) {
            return new GetCategoryHandler();
        } else if (dataType.equals("getData")) {
            return new GetDataHandler();
        } else if (dataType.equals("getType")) {
            return new GetTypeHandler();
        }
        return null;
    }

}
