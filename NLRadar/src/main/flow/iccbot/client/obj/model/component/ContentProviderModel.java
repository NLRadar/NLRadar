package iccbot.client.obj.model.component;

import iccbot.analyze.model.analyzeModel.AppModel;
import iccbot.analyze.utils.ConstantUtils;

public class ContentProviderModel extends ComponentModel {
    private static final long serialVersionUID = 3L;

    public ContentProviderModel(AppModel appModel) {
        super(appModel);
        type = "p";
    }

    @Override
    public String getComponentType() {
        return ConstantUtils.PROVIDER;
    }
}
