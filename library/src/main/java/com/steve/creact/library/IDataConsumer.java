package com.steve.creact.library;

import java.util.List;

/**
 * An interface can consume data
 * @param <T> data type
 */
public interface IDataConsumer<T>
{
    /**
     * load data
     * @param dataSet
     */
    void loadData(List<? extends T> dataSet);
}
