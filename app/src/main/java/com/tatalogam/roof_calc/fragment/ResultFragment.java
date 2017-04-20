package com.tatalogam.roof_calc.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tatalogam.roof_calc.R;
import com.tatalogam.roof_calc.activity.OrderActivity;
import com.tatalogam.roof_calc.bean.CalculationBean;

import org.androidannotations.annotations.EFragment;

/**
 * Created by stephanuskoeswandi on 3/22/17.
 */
@EFragment(R.layout.fragment_result)
public class ResultFragment extends Fragment{
    private View rootView;

    private TextView results_rooftype;
    private TextView length;
    private TextView width;
    private TextView results_area;
    private TextView results_angle;
    private TextView results_pitch;
    private TextView results_rooftile;
    private TextView results_roofqty;
    private TextView results_ridge;
    private LinearLayout container_results_rooftile;
    private LinearLayout container_results_roofqty;
    private LinearLayout container_results_ridge;
    private LinearLayout container_materials;

    private OrderActivity orderActivity;
    private CalculationBean cb;

    private TextView qty_ts7575;
    private TextView qty_ts7580;
    private TextView qty_tr3245;
    private TextView qty_screw_truss;
    private TextView qty_screw_reng;
    private TextView qty_bracket_l;
    private TextView qty_dynabolt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //check if rootView is not removed yet and remove it
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }

        try {
            rootView = inflater.inflate(R.layout.fragment_result, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        //can only set from here, because annotation doesn't work
        results_rooftype = (TextView) rootView.findViewById(R.id.results_rooftype);
        length = (TextView) rootView.findViewById(R.id.length);
        width = (TextView) rootView.findViewById(R.id.width);
        results_area = (TextView) rootView.findViewById(R.id.results_area);
        results_angle = (TextView) rootView.findViewById(R.id.results_angle);
        results_pitch = (TextView) rootView.findViewById(R.id.results_pitch);
        results_rooftile = (TextView) rootView.findViewById(R.id.results_rooftile);
        results_roofqty = (TextView) rootView.findViewById(R.id.results_roofqty);
        results_ridge = (TextView) rootView.findViewById(R.id.results_ridge);
        container_results_rooftile = (LinearLayout) rootView.findViewById(R.id.container_results_rooftile);
        container_results_roofqty = (LinearLayout) rootView.findViewById(R.id.container_results_roofqty);
        container_results_ridge = (LinearLayout) rootView.findViewById(R.id.container_results_ridge);
        container_materials = (LinearLayout) rootView.findViewById(R.id.container_materials);

        qty_ts7575 = (TextView) rootView.findViewById(R.id.qty_ts7575);
        qty_ts7580 = (TextView) rootView.findViewById(R.id.qty_ts7580);
        qty_tr3245 = (TextView) rootView.findViewById(R.id.qty_tr3245);
        qty_screw_truss = (TextView) rootView.findViewById(R.id.qty_screw_truss);
        qty_screw_reng = (TextView) rootView.findViewById(R.id.qty_screw_reng);
        qty_bracket_l = (TextView) rootView.findViewById(R.id.qty_bracket_l);
        qty_dynabolt = (TextView) rootView.findViewById(R.id.qty_dynabolt);

        //get parent activity
        orderActivity = (OrderActivity)getActivity();

        //access calculate function in orderactivity
        orderActivity.calculateCost();

        try {
            //setup initial values
            String rt = "";
            switch (orderActivity.cb.getRoofType()) {
                case 1:
                    rt = getResources().getString(R.string.rooftype_gable); break;
                case 2:
                    rt = getResources().getString(R.string.rooftype_hip); break;
                case 3:
                    rt = getResources().getString(R.string.rooftype_shed); break;
                case 4:
                    rt = getResources().getString(R.string.rooftype_dutch); break;
            }
            results_rooftype.setText(rt);
            length.setText(orderActivity.cb.getLength().toString()+" "+getResources().getString(R.string.symbol_meter));
            width.setText(orderActivity.cb.getWidth().toString()+" "+getResources().getString(R.string.symbol_meter));
            results_area.setText(orderActivity.cb.getArea().toString()+" "+getResources().getString(R.string.symbol_sqrmeter));
            results_angle.setText(orderActivity.cb.getDegree().toString()+" "+getResources().getString(R.string.symbol_angle));
            results_pitch.setText(orderActivity.cb.getPitch().toString()+" "+getResources().getString(R.string.symbol_sqrmeter));

            //setVisibility to none if its not contains metalroof desc
            if (orderActivity.calctype.toLowerCase().contains("metalroof") != true) {
                container_results_rooftile.setVisibility(View.GONE);
                container_results_roofqty.setVisibility(View.GONE);
                container_results_ridge.setVisibility(View.GONE);
            } else {
                //setup metalroof values
                results_rooftile.setText(orderActivity.cb.getPm().getName());
                results_roofqty.setText(orderActivity.cb.getRoofqty().toString()+" "+getResources().getString(R.string.symbol_sheet));
                results_ridge.setText(orderActivity.cb.getRidge().toString()+" "+getResources().getString(R.string.symbol_meter));
            }

            //setVisibility to none if metalroof only option was chosen
            if (orderActivity.calctype.toLowerCase().compareTo("metalroof")==0) container_materials.setVisibility(View.GONE);
            else{
                //set material's contents
                if (orderActivity.cb.getLength() >= 10){
                    qty_ts7575.setText("0");
                    qty_ts7580.setText(orderActivity.cb.getTs7580().toString());
                }
                else{
                    qty_ts7575.setText(orderActivity.cb.getTs7575().toString());
                    qty_ts7580.setText("0");
                }

                qty_tr3245.setText(orderActivity.cb.getTr3245().toString());
                qty_screw_truss.setText(orderActivity.cb.getScrew_truss().toString());
                qty_screw_reng.setText(orderActivity.cb.getScrew_reng().toString());
                qty_bracket_l.setText(orderActivity.cb.getBracket_l().toString());
                qty_dynabolt.setText(orderActivity.cb.getDynabolt().toString());
            }
        }
        catch (NullPointerException ex){
            //do nothing
        }

        //YoYo.with(Techniques.RotateIn).duration(1000).playOn(rootView);
        return rootView;
    }
}
