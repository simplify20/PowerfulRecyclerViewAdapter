package com.steve.creact.library;

import java.util.List;

/**
 * An interface can add|remove|get data
 */
public interface IDataArranger<T>{
    /**
     * Get data at the given position
     * @param position
     * @return
     */
    T getItem(int position);

    /**
     * Remove data at position in the given position array
     * @param positions
     */
    void removeData(int[] positions);

    /**
     * Remove data at the given position
     * @param position
     */
    void removeData(int position);

    /**
     * Remove data at the head
     */
    void removeFirst();

    /**
     * Remove data at the tail
     */
    void removeLast();

    /**
     * Remove obj if it exists in the dataSet
     * @param obj
     */
    void removeData(T obj);
    /**
     * Insert data @param obj to the given position
     * @param position
     * @param obj
     */
    void insertData(int position, T obj);

    /**
     * Insert data to the tail of the data set
     * @param obj
     */
    void insertLast(T obj);

    /**
     * Insert data to the head of the data set
     * @param obj
     */
    void insertFirst(T obj);
    /**
     * Insert data set to the given position
     * @param position
     * @param insertedList
     */
    void insertData(int position, List<T> insertedList);
}
