package com.tatalogam.roof_calc.service;

import com.tatalogam.roof_calc.bean.Product;
import com.tatalogam.roof_calc.bean.ResponseBean;
import com.tatalogam.roof_calc.bean.UsersRoofcalc;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ITataLogam {
    @GET("/api/product")
    public ResponseBean<Product> listProduct();

    @POST("/api/user/roofcalc")
    public ResponseBean<String> saveRoofcalcUser(@Body UsersRoofcalc m);

    @POST("/api/checksurvey")
    public ResponseBean<String> checkSurvey(@Body String sd);
}