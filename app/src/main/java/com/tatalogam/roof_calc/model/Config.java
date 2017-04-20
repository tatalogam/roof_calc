package com.tatalogam.roof_calc.model;

import com.orm.SugarRecord;

public class Config extends SugarRecord {

    private String name;
    private String value;

    public Config(String name,String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
