package com.tatalogam.roof_calc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import com.tatalogam.roof_calc.Constant;

import java.lang.reflect.Type;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Yose on 6/10/2015.
 */
public class RestService {

    public static ITataLogam getService() {
        /*
        @Override
        public void failure(final RetrofitError error) {
            android.util.Log.i("example", "Error, body: " + error.getBody().toString());
        }*/

        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return sdf.parse(json.getAsString());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return sdf1.parse(json.getAsString());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }
                return null;
            }
        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return new JsonPrimitive(sdf.format(date));
            }
        });

        Gson gson = builder.create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                //.setEndpoint("http://tl.icodelabs.net/")
                .setEndpoint(Constant.URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new MyErrorHandler())
                .setConverter(new GsonConverter(gson))
                .build();

        ITataLogam service = restAdapter.create(ITataLogam.class);
        return service;
    }

}
