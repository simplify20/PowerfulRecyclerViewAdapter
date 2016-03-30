package com.steve.creact.library;

import java.util.List;

/**
 * @author:YJJ
 * @date:2015/10/16
 * @email:yangjianjun@117go.com
 */
public interface IDataArranger<T>{
    public T getItem(int position);
    void removeDatas(int[] positions);
    void removeData(int position, boolean oneOfMore);
    void insertData(int position, T obj);
    void insertDatas(int position, List<T> insertedList);
}
