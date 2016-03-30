package com.steve.creact.powerfuladapter.presentation.viewholder;

/**
 * Notes:
 * 1.使用接口以降低耦合，提高ViewHolder及DataBean的复用性
 * 2.在有复用需求的情况下，使用接口，没有这样的需求可以使用具体Data类
 * 3.这个接口表明了ViewHolder的需求,表明了ViewHolder上要展示的内容，或者潜在的交互
 * 4.在服务器接口还没有完成时，可以创建mock实现，以和服务器进行独立开发测试
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public interface ICategory {

    String getName();
    long getId();
}
