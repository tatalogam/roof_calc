package com.tatalogam.roof_calc.service;


import com.tatalogam.roof_calc.exception.TokenExpiredException;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yose on 6/10/2015.
 */
public class MyErrorHandler implements ErrorHandler {

    @Override public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null) {
            if (r.getStatus() == 401) {
                return new TokenExpiredException();
            } else {
                return new Exception(cause);
            }
        }

        return cause;
    }
}

