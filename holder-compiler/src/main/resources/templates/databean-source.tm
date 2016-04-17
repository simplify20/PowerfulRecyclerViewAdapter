package ${PACKAGE_NAME};

import android.view.ViewGroup;

import com.steve.creact.library.display.BaseDataBean;
import ${DATA_ENTITY_FULL_QUALIFIED_CLASS_NAME};
import ${VIEW_HOLDER_FULL_QUALIFIED_CLASS_NAME};

/**
 * Generated DataBean for ${VIEW_HOLDER_SIMPLE_CLASS_NAME}
 * Powered by Holder-Compiler
 */
public class ${DATA_BEAN_SIMPLE_CLASS_NAME} extends BaseDataBean<${DATA_ENTITY_SIMPLE_CLASS_NAME}, ${VIEW_HOLDER_SIMPLE_CLASS_NAME}> {

    public ${DATA_BEAN_SIMPLE_CLASS_NAME}(${DATA_ENTITY_SIMPLE_CLASS_NAME} data) {
        super(data);
    }

    @Override
    public ${VIEW_HOLDER_SIMPLE_CLASS_NAME} createHolder(ViewGroup parent) {
        return new ${VIEW_HOLDER_SIMPLE_CLASS_NAME}(getView(parent, ${VIEW_HOLDER_SIMPLE_CLASS_NAME}.LAYOUT_ID));
    }
}