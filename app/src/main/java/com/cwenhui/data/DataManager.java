package com.cwenhui.data;


import com.cwenhui.data.remote.FactoryApi;

import javax.inject.Inject;


public class DataManager {
    private FactoryApi factoryApi;

    @Inject
    public DataManager(FactoryApi api) {
        this.factoryApi = api;
    }


}
