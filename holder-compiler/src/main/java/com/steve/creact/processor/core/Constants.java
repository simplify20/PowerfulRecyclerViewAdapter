package com.steve.creact.processor.core;

/**
 * Created by Administrator on 2016/4/10.
 */
public final class Constants {
    private Constants(){}

    public static final String TEMPLATE_PATH = "templates/databean-source.tm";
    public static final String PACKAGE_NAME = "${PACKAGE_NAME}";
    public static final String DATA_ENTITY_FULL_QUALIFIED_CLASS_NAME = "${DATA_ENTITY_FULL_QUALIFIED_CLASS_NAME}";
    public static final String VIEW_HOLDER_FULL_QUALIFIED_CLASS_NAME = "${VIEW_HOLDER_FULL_QUALIFIED_CLASS_NAME}";
    public static final String VIEW_HOLDER_SIMPLE_CLASS_NAME = "${VIEW_HOLDER_SIMPLE_CLASS_NAME}";
    public static final String DATA_BEAN_SIMPLE_CLASS_NAME = "${DATA_BEAN_SIMPLE_CLASS_NAME}";
    public static final String DATA_ENTITY_SIMPLE_CLASS_NAME = "${DATA_ENTITY_SIMPLE_CLASS_NAME}";
}
