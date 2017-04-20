package com.tatalogam.roof_calc.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tatalogam.roof_calc.adapter.ProductAdapter;
import com.orm.query.Select;

import com.tatalogam.roof_calc.R;
import com.tatalogam.roof_calc.activity.OrderActivity;
import com.tatalogam.roof_calc.model.ProductModel;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by stephanuskoeswandi on 3/22/17.
 */
@EFragment(R.layout.fragment_metalroof)
public class MetalRoofFragment extends Fragment{
    private View rootView;
    public RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductModel> lpgt;

    private TextView header_rooftile;

    private OrderActivity orderActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //check if rootView is not removed yet and remove it
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_metalroof, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        //get parent activity
        orderActivity = (OrderActivity)getActivity();

        //can only set from here, because annotation doesn't work
        recyclerView = (RecyclerView)rootView.findViewById(R.id.rv_product);
        loadRoof(); //load product models ( roof )
        adapter = new ProductAdapter(rootView.getContext(), lpgt);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        header_rooftile = (TextView) rootView.findViewById(R.id.header_rooftile);
       /* header_rooftile.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                ((LinearLayout)recyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.container_cv))
                    .setBackgroundColor(getResources().getColor(R.color.transparent));
                //adapter.setViewAtPos(0,"abc");
                new MaterialDialog.Builder(rootView.getContext()).title(R.string.err)
                        .backgroundColorRes(R.color.tl_blue_80_percent)
                        .content(String.valueOf(adapter.get))
                        .positiveText(R.string.ok)
                        .show();
            }
        });*/

        //YoYo.with(Techniques.RotateIn).duration(1000).playOn(rootView);
        return rootView;
    }

    @Background
    public void loadRoof() {
        List<ProductModel> k = Select.from(ProductModel.class).list();
        lpgt = new ArrayList<>();
        for (ProductModel ll : k) {
            if (ll.getProduct_category_id() == 3) {
                lpgt.add(ll);
            }
        }

        if(lpgt.size()>=1) {
            orderActivity.cb.setPm(lpgt.get(0));

            Double coeff = (lpgt.get(0).getCoeff()>0 ? lpgt.get(0).getCoeff() : 1.0);
            orderActivity.cb.setCoeff(coeff);
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
