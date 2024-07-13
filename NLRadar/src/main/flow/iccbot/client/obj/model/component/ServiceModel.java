package iccbot.client.obj.model.component;

import iccbot.analyze.model.analyzeModel.AppModel;
import iccbot.analyze.utils.ConstantUtils;

public class ServiceModel extends ComponentModel {
    private static final long serialVersionUID = 3L;

    public ServiceModel(AppModel appModel) {
        super(appModel);
        type = "s";
    }

    @Override
    public String getComponentType() {
        return ConstantUtils.SERVICE;
    }
}
