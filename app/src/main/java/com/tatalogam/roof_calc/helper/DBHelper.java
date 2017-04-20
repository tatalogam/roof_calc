package com.tatalogam.roof_calc.helper;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.tatalogam.roof_calc.model.Config;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.List;

public class DBHelper {

    public static String getLang() {
        String lo = null;

        List<Config> configs = Select.from(Config.class).where(Condition.prop("name").eq("lang")).list();
        if (configs.size() > 0) {
            lo = configs.get(0).getValue();
        }

        return lo;
    }

    public static JSONObject getUserInfo(){
        //for name,email,sim_country,network_country
        JSONObject user_info = new JSONObject();

        //get user_info from sugar
        List<Config> configs = Select.from(Config.class).where(Condition.prop("name").eq("user_info")).list();

        if (configs.size() > 0) {
            try{
                user_info = new JSONObject(configs.get(0).getValue());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return user_info;
    }
}
