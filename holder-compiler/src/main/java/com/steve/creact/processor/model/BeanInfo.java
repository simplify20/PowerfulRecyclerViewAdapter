package com.steve.creact.processor.model;

/**
 * Created by Administrator on 2016/4/9.
 */
public class BeanInfo {

    public String holderName = "";
    public String holderPackage = "";
    public String dataName = "";
    public String dataPackage = "";
    public String dataBeanName = "";
    public String dataBeanPackage = "";
    public int layoutId;

    @Override
    public String toString() {
        return "BeanInfo{" +
                "holderName='" + holderName + '\'' +
                ", holderPackage='" + holderPackage + '\'' +
                ", dataName='" + dataName + '\'' +
                ", dataPackage='" + dataPackage + '\'' +
                ", dataBeanName='" + dataBeanName + '\'' +
                ", dataBeanPackage='" + dataBeanPackage + '\'' +
                ", layoutId=" + layoutId +
                '}';
    }
}
