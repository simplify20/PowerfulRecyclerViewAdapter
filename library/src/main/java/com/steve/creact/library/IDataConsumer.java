/**
 *
 * Copyright 2014 Travo, Inc. All rights reserved.
 * IDataConsumer.java
 *
 */
package com.steve.creact.library;

import java.util.List;

public interface IDataConsumer<T>
{
    void loadData(List<? extends T> data);
}
