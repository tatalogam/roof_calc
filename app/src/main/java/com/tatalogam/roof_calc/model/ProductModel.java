package com.tatalogam.roof_calc.model;

import com.orm.SugarRecord;

/**
 * Created by Yose on 2/20/2016.
 */
public class ProductModel extends SugarRecord {

    private String name;
    private Integer product_category_id;
    private String image;
    private Integer batuan;
    private Integer is_package;
    private Double coeff;
    private String json;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getBatuan() {
        return batuan;
    }

    public void setBatuan(Integer batuan) {
        this.batuan = batuan;
    }

    public Double getCoeff() {
        return coeff;
    }

    public void setCoeff(Double coeff) {
        this.coeff = coeff;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Integer getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(Integer product_category_id) {
        this.product_category_id = product_category_id;
    }

    public Integer getIs_package() {
        return is_package;
    }

    public void setIs_package(Integer is_package) {
        this.is_package = is_package;
    }
}
