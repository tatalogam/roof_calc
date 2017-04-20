package com.tatalogam.roof_calc.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

//import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.tatalogam.roof_calc.R;
import com.tatalogam.roof_calc.helper.DBHelper;
import io.fabric.sdk.android.Fabric;

import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterTextChange;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Locale;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    public Toolbar toolbar;

    @ViewById(R.id.user_name)
    public EditText user_name;

    @ViewById(R.id.email)
    public EditText email;

    @ViewById(R.id.btn_steelframe)
    public LinearLayout btn_steelframe;

    @ViewById(R.id.btn_steelframe_metalroof)
    public LinearLayout btn_steelframe_metalroof;

    @ViewById(R.id.btn_metalroof)
    public LinearLayout btn_metalroof;

    //Create the bundle for passing data between activity
    Bundle bundle = new Bundle();
    //Declare JSONObject
    JSONObject user_info = new JSONObject();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Get Telephony Service
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        //set locale
        String lang = DBHelper.getLang();
        Locale locale = null;
        if(lang!=null && lang.toUpperCase()=="ID") {
            locale.setDefault(new Locale("in", "ID"));
        }
        else{
            lang=tMgr.getNetworkCountryIso();
            locale.setDefault(Locale.US);
        }

        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        //Add locale to bundle
        bundle.putString("LANG", lang);

        //set user_info
        user_info = DBHelper.getUserInfo();
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            if (user_info.has("name")) {
                //if exists, fill in the field and disabling input, then update current phone number
                user_name.setText(user_info.optString("name"));
                user_name.setEnabled(false);
                email.setText(user_info.optString("email"));
                email.setEnabled(false);

                try {
                    user_info.put("phone", tMgr.getLine1Number());
                    user_info.put("sim_country", tMgr.getSimCountryIso());
                    user_info.put("location", tMgr.getCellLocation());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                //if user_info not found, then use telephony manager to feed
                try {
                    user_info.put("name", "");
                    user_info.put("email", "");
                    user_info.put("phone", tMgr.getLine1Number());
                    user_info.put("sim_country", tMgr.getNetworkCountryIso());
                    user_info.put("location", tMgr.getCellLocation());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @AfterViews
    public void init() {
        Fabric.with(this, new Crashlytics());
        //draw();
    }

    /*
    @UiThread
    public void draw() {
        //toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white_100_percent));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tl_blue_80_percent));
        //toolbar.setLogo(ContextCompat.getDrawable(this,R.drawable.ic_roofcalc));
        setSupportActionBar(toolbar);

    }*/

    @AfterTextChange(R.id.user_name)
    public void setUser_name(){
        try{
            user_info.put("name", user_name.getText().toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @AfterTextChange(R.id.email)
    public void setEmail(){
        try{
            user_info.put("email", email.getText());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Click(R.id.btn_steelframe)
    public void openCalcType1() {
        Intent i = new Intent(this, OrderActivity_.class);
        //Add user_info to bundle
        bundle.putString("USER_INFO", user_info.toString());
        //Add selected_calctype to bundle
        bundle.putString("CALC_TYPE", "steelframe");
        //Add the bundle to the intent
        i.putExtras(bundle);
        startActivity(i);
    }

    @Click(R.id.btn_steelframe_metalroof)
    public void openCalcType2() {
        Intent i = new Intent(this, OrderActivity_.class);
        //Add user_info to bundle
        bundle.putString("USER_INFO", user_info.toString());
        //Add selected_calctype to bundle
        bundle.putString("calctype", "steelframe_metalroof");
        //Add the bundle to the intent
        i.putExtras(bundle);
        startActivity(i);
    }

    @Click(R.id.btn_metalroof)
    public void openCalcType3() {
        Intent i = new Intent(this, OrderActivity_.class);
        //Add user_info to bundle
        bundle.putString("USER_INFO", user_info.toString());
        //Add selected_calctype to bundle
        bundle.putString("CALC_TYPE", "metalroof");
        //Add the bundle to the intent
        i.putExtras(bundle);
        startActivity(i);
    }
}
