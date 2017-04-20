package com.tatalogam.roof_calc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tatalogam.roof_calc.Constant;
import com.tatalogam.roof_calc.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Background;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {
    @ViewById(R.id.splash)
    ImageView splash;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String _PATH = (Constant.URL).substring(0,(Constant.URL).length()-1)+"/images/custom/advertise.jpg";
        // loading image using Glide library, if advertise.jpg not exists, immediately replace it with splash
        Glide.with(this)
                .load(_PATH)
                .error(R.drawable.splash)
                .into(splash); //better than picasso in memory mgmt and chaching

        //New Handler to start the Menu-Activity and close this Splash-Screen after some seconds.
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the Menu-Activity.
                Intent mainIntent = new Intent();
                mainIntent.setClass(SplashActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Background
    public void redirect() {

    }
}