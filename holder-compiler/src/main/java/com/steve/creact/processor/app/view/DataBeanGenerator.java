package com.steve.creact.processor.app.view;

import com.steve.creact.processor.app.model.BeanInfo;
import com.steve.creact.processor.core.Constants;
import com.steve.creact.processor.core.view.BaseViewGenerator;

import java.util.Map;


/**
 *
 * Created by Administrator on 2016/4/10.
 */
public class DataBeanGenerator extends BaseViewGenerator<BeanInfo> {

    @Override
    public String replace(String input, Map<String, String> replaceValues) {
        if (realModel == null)
            return "";
        String output = input.replace(Constants.PACKAGE_NAME, realModel.dataBeanPackage);
        output = output.replace(Constants.DATA_ENTITY_FULL_QUALIFIED_CLASS_NAME, realModel.dataClassName());
        output = output.replace(Constants.VIEW_HOLDER_FULL_QUALIFIED_CLASS_NAME, realModel.holderClassName());
        output = output.replace(Constants.DATA_BEAN_SIMPLE_CLASS_NAME, realModel.dataBeanName);
        output = output.replace(Constants.DATA_ENTITY_SIMPLE_CLASS_NAME, realModel.dataName);
        output = output.replace(Constants.VIEW_HOLDER_SIMPLE_CLASS_NAME, realModel.holderName);
        return output;
    }

    @Override
    protected String getModelClassName() {
        return realModel == null ? "" : realModel.beanClassName();
    }

    @Override
    protected String getTemplateFilePath() {
        return Constants.TEMPLATE_PATH;
    }
}
