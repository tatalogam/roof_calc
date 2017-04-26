package com.tatalogam.roof_calc.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.BottomNavigationView;

import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;

import com.tatalogam.roof_calc.bean.UsersRoofcalc;
import com.google.gson.Gson;

import com.tatalogam.roof_calc.R;
import com.tatalogam.roof_calc.Constant;
import com.tatalogam.roof_calc.model.Config;
import com.tatalogam.roof_calc.model.ProductModel;
import com.tatalogam.roof_calc.bean.CalculationBean;
import com.tatalogam.roof_calc.bean.Product;
import com.tatalogam.roof_calc.bean.ResponseBean;
import com.tatalogam.roof_calc.fragment.SteelFrameFragment;
import com.tatalogam.roof_calc.fragment.MetalRoofFragment;
import com.tatalogam.roof_calc.fragment.ResultFragment;
import com.tatalogam.roof_calc.service.RestService;

import io.fabric.sdk.android.Fabric;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

@EActivity(R.layout.activity_order)
public class OrderActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    public Toolbar toolbar;

    @ViewById(R.id.toolbar_user_name)
    public TextView toolbar_user_name;

    @ViewById(R.id.toolbar_date)
    public TextView toolbar_date;

    @ViewById(R.id.native_progress_bar)
    ProgressBar progressBar;

    @ViewById(R.id.btn_next)
    Button btn_next;

    @ViewById(R.id.btn_back)
    ImageButton btn_back;

    @ViewById(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    //Declare JSONObject
    JSONObject user_info = new JSONObject();
    private int dataLoaded = 0;
    private int[] PAGE_SEL = Constant.PAGE_STEELFRAME; //instatiate using default steelframe to avoid errors
    private int PAGE_INDEX = 0; //set zero index as default

    public CalculationBean cb = new CalculationBean();
    public String calctype = "";
    public Integer mail_count = 0;
    private Fragment mFragment = null;
    Bundle bundle;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //update information from user_info
        try {
            bundle = OrderActivity.this.getIntent().getExtras();

            //set calctype
            calctype = bundle.getString("CALC_TYPE");

            //set page
            if (calctype.contains("metalroof")) PAGE_SEL = Constant.PAGE_METALROOF;
            else PAGE_SEL = Constant.PAGE_STEELFRAME;

            //set into CalculationBean
            cb.setCalctype(calctype);

            //put user_info into json object for future uses
            user_info = new JSONObject(bundle.getString("USER_INFO"));
        } catch (Exception e) {
            //do nothing
        }
    }

    @AfterViews
    public void init() {
        //Start Crashlytics
        Fabric.with(this, new Crashlytics());

        //check connection first
        String msg = null;
        if (isNetworkConnected() != true) {
            msg = getResources().getString(R.string.err_no_network);
        }
        /*
        if(isInternetAvailable()!=true){
            msg = getResources().getString(R.string.err_no_internet);
        }*/

        //rest service get list products into productmodel
        if (msg != null) showRestMessage(msg);

        load();
        updateConfig();
    }

    @Background
    public void load() {
        //rest service, get products
        try {
            ResponseBean<Product> lprod = RestService.getService().listProduct();
            if (lprod.getStatus() == 1) {
                ProductModel.deleteAll(ProductModel.class);
                Gson gs = new Gson();

                for (Product pp : lprod.getData()) {
                    ProductModel pm = new ProductModel();
                    pm.setName(pp.getName());
                    pm.setBatuan(pp.getBatuan());
                    pm.setCoeff(pp.getCoeff());
                    if (pp.getImage() != null && pp.getImage().size() > 0) {
                        pm.setImage(pp.getImage().get(0).get("ori"));
                    }

                    pm.setIs_package(pp.getIs_package());
                    pm.setProduct_category_id(pp.getProduct_category_id());
                    pm.setJson(gs.toJson(pp));
                    pm.save();
                }
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    @Background
    public void updateConfig(){
        //delete all first
        Config.deleteAll(Config.class);
        //save user_info inside config table
        Config configs = new Config("user_info", bundle.getString("USER_INFO"));
        configs.save();

        configs = new Config("lang", bundle.getString("LANG"));
        configs.save();

        dataLoaded = 1; //indicate that process is done
        draw();
    }

    @UiThread
    public void draw() {
        /*
        //setup toolbar
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white_100_percent));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tl_blue_80_percent));
        //toolbar.setTitle(getResources().getString(R.string.app_name));
        //toolbar.setLogo(ContextCompat.getDrawable(this,R.drawable.ic_roofcalc));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        */

        //setup user_name
        String display_name = (user_info.optString("name").length() > 0 ? user_info.optString("name") : getResources().getString(R.string.guest));
        if (display_name.length() > 12) display_name = display_name.substring(0, 11) + "..";
        toolbar_user_name.setText(display_name);

        //setup current date based on current locale
        Locale current = getResources().getConfiguration().locale;
        Date today = Calendar.getInstance(current).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
        toolbar_date.setText(sdf.format(today));

        //attach a fragment into this activity
        drawFragment(PAGE_SEL[PAGE_INDEX]);

        //setup bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.btnmenu_shopping_cart:
                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Constant.PACKAGE_SIMANTAPP)));
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CUSTOM_URL)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Constant.PACKAGE_SIMANTAPP)));
                                }

                                break;
                            case R.id.btnmenu_mail:
                                //send into valid email via background & showing success message
                                if(mail_count<1) {
                                    String mail_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                                    if (user_info.optString("email").trim().matches(mail_pattern)) {
                                        sendEmail();
                                        mail_count += 1;
                                    }
                                    else showMessage(getResources().getString(R.string.err),getResources().getString(R.string.email_invalid));
                                }
                                else showMessage(getResources().getString(R.string.err),getResources().getString(R.string.email_sendlimit));

                                break;
                            case R.id.btnmenu_replay:
                                home();
                                break;
                        }

                        return true;
                    }
                });
    }

    @Background
    public void sendEmail(){
        Gson gson = new Gson();
        String json = gson.toJson(cb);
        String msg = getResources().getString(R.string.email_sent);

        try{
            user_info.put("order", json);
            UsersRoofcalc ur = new UsersRoofcalc(
                    user_info.optString("name"),
                    user_info.optString("email"),
                    user_info.optString("phone"),
                    user_info.optString("sim_country"),
                    user_info.optString("location"),
                    user_info.optString("order")
            );

            ResponseBean<String> ret = RestService.getService().saveRoofcalcUser(ur);
            //if (ret.getStatus() == 1 && ret.getMessage() != null) msg=getResources().getString(R.string.email_sent);
            //else msg = ret.getMessage();
        }
        catch(Exception e){
            //do nothing
        }

        //display rest message if its not empty
        showMessage(getResources().getString(R.string.message),msg);
    }

    @UiThread
    public void showMessage(String t,String msg){
        new MaterialDialog.Builder(OrderActivity.this).title(t)
                .backgroundColorRes(R.color.tl_blue_80_percent)
                .content(msg)
                .positiveText(R.string.ok)
                .show();
    }

    @UiThread
    public void showRestMessage(String msg){
        new MaterialDialog.Builder(OrderActivity.this).title(R.string.err)
            .dismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    home();
                }
            })
            .backgroundColorRes(R.color.tl_blue_80_percent)
            .content(msg)
            .positiveText(R.string.ok)
            .show();
    }

    @UiThread
    public void drawFragment(int STEP_NEW) {
        //detach and attach process
        FragmentManager fragmentManager = getSupportFragmentManager();

        //detach old fragment first if fragment is not null, and add a new one
        if(mFragment!=null){
            //save old fragment first
            Fragment oldFragment = mFragment;
            setFragment(STEP_NEW);
            fragmentManager
                    .beginTransaction()
                    .detach(oldFragment)
                    .replace(R.id.main_content, mFragment)
                    .commit();
        }
        else {
            setFragment(STEP_NEW);
            //first load
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, mFragment)
                    .commit();
        }

        //hide progressbar when fragment was drawn
        progressBar.setVisibility(View.GONE);
    }

    public void setFragment(int FragNumb){
        //decide which fragment
        switch (FragNumb) {
            case 1:
                mFragment = new SteelFrameFragment();
                bottomNavigationView.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);
                break;
            case 2:
                mFragment = new MetalRoofFragment();
                bottomNavigationView.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);
                break;
            case 3:
                mFragment = new ResultFragment();
                calculateCost(); //when reaching result page, calculate cost needed
                bottomNavigationView.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.GONE);
                break;
            default:
                //do nothing
                break;
        }
    }

    @Click(R.id.btn_next)
    public void next() {
        if(dataLoaded==1 && (PAGE_INDEX+1)<PAGE_SEL.length) {
            //increment step
            PAGE_INDEX++;
            drawFragment(PAGE_SEL[PAGE_INDEX]);
        }
    }

    @Click(R.id.btn_back)
    public void back() {
        if(dataLoaded==1 && PAGE_INDEX>0) {
            //decrement step
            PAGE_INDEX--;
            drawFragment(PAGE_SEL[PAGE_INDEX]);
        }
        else home();
    }

    //override back button for phone
    @Override
    public void onBackPressed(){
        back();
    }

    public void home() {
        Intent intent = new Intent(this, MainActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void calculateCost(){
        processRangka();

        if(cb.getCalctype().toLowerCase().contains("metalroof")){
            processGenteng();
        }
    }

    private void processGenteng(){
        try {
            int roofqty = (int) Math.ceil((100 + cb.getBuffer()) / 100 * (cb.getPitch() * cb.getCoeff()));
            cb.setRoofqty(roofqty);
            Double ridge = cb.getWidth() - (0.5 * cb.getLength());
            cb.setRidge(ridge);
        }
        catch(NullPointerException ex){
            //do nothing
        }
    }

    private void processRangka() {
        try {
            int area = cb.getLength() * cb.getWidth();

            int lba = 0;
            if (cb.getJumlahAnakan() != null && cb.getJumlahAnakan() > 0) {
                int jmlan = cb.getJumlahAnakan();
                for (int i = 1; i <= jmlan; i++) {
                    int tmp = 0;
                    switch (i) {
                        case 1:
                            if (cb.getAnakan1Length() != null && cb.getAnakan1Width() != null) {
                                tmp = cb.getAnakan1Length() * cb.getAnakan1Width();
                            }
                            break;
                        case 2:
                            if (cb.getAnakan2Length() != null && cb.getAnakan2Width() != null) {
                                tmp = cb.getAnakan2Length() * cb.getAnakan2Width();
                            }
                            break;
                        case 3:
                            if (cb.getAnakan3Length() != null && cb.getAnakan3Width() != null) {
                                tmp = cb.getAnakan3Length() * cb.getAnakan3Width();
                            }
                            break;
                    }
                    lba += tmp;
                }
                area += lba;
            }

            cb.setArea(area);

            int pitch = (int) Math.ceil(area * (1.0 / Math.cos(Math.toRadians(cb.getDegree()))));
            cb.setPitch(pitch);

            int pitchWaste = (int) Math.ceil((100 + cb.getBuffer()) / 100 * pitch);
            cb.setPitchWaste(pitchWaste);

            //if pelana ( buffer 5% ) - metal
            if (cb.getRoofType() == 1 || cb.getRoofType() == 3) {
                cb.setTs7575((int) Math.ceil(0.5 * pitchWaste));
                cb.setTs7580((int) Math.ceil(0.5 * pitchWaste));
                cb.setTr3245((int) Math.ceil(0.6 * pitchWaste));
            } else {
                //limasan ( buffer 10% ) - metal
                cb.setTs7575((int) Math.ceil(0.6 * pitchWaste));
                cb.setTs7580((int) Math.ceil(0.6 * pitchWaste));
                cb.setTr3245((int) Math.ceil(0.7 * pitchWaste));
            }

            cb.setScrew_truss((int) Math.ceil(11 * pitchWaste));
            cb.setScrew_reng((int) Math.ceil(10 * pitchWaste));
            cb.setBracket_l((int) Math.ceil(0.7 * pitchWaste));
            cb.setDynabolt((int) Math.ceil(0.7 * pitchWaste));
        }
        catch(NullPointerException ex){
            //do nothing
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /*
    public boolean isInternetAvailable() {
        boolean connected = false;
        try {
            InetAddress ipAddr = InetAddress.getByName(Constant.CONNECTIVITY_URL); //You can replace it with your name
            int timeOut = 5000;
            connected = ipAddr.isReachable(timeOut);
        }
        catch (Exception e) {
            //do nothing
        }

        return connected;
    }*/
}
