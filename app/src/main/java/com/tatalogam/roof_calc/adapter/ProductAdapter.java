package com.tatalogam.roof_calc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tatalogam.roof_calc.activity.OrderActivity;

import com.tatalogam.roof_calc.R;
import com.tatalogam.roof_calc.Constant;
import com.tatalogam.roof_calc.model.ProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context mContext;
    RecyclerView mRecyclerView;

    private OrderActivity orderActivity;

    private List<ProductModel> pmList;
    private int selIndex=-1;
    //private List<MyViewHolder> vHolder;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout container_cv;
        public ImageView thumbnail;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            container_cv = (LinearLayout) view.findViewById(R.id.container_cv);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public ProductAdapter(Context mContext, List<ProductModel> pmList) {
        this.mContext = mContext;
        this.pmList = pmList;

        //get orderactivity to access calculationbean
        orderActivity = (OrderActivity) mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product, parent, false);
        mRecyclerView = (RecyclerView) parent;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductModel pm = pmList.get(position);

        holder.itemView.setSelected(selIndex == position);
        //setting title
        holder.title.setText(pm.getName());

        //resize and setting image
        String url = (Constant.URL).substring(0,(Constant.URL).length()-1)+pm.getImage();
        //Picasso.with(mContext).load(url).fit().into(holder.thumbnail);

        // loading product list using Glide library
        Glide.with(mContext).load(url).into(holder.thumbnail); //better than picasso in memory mgmt and chaching

        //setting listener
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try{
                //send to calculation bean
                orderActivity.cb.setPm(pmList.get(position));
                orderActivity.cb.setCoeff(pmList.get(position).getCoeff());

                //if selected product exists, set selected to false first
                mRecyclerView.findViewHolderForAdapterPosition(selIndex).itemView.setSelected(false);

                /*new MaterialDialog.Builder(mContext)
                        .title("test")
                        .backgroundColorRes(R.color.tl_blue_80_percent)
                        .content(orderActivity.cb.getPm().getName())
                        .positiveText(R.string.ok)
                        .show();*/

            }
            catch(NullPointerException ex){
                //do nothing
            }

            //save new index
            selIndex = position;
            holder.itemView.setSelected(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pmList.size();
    }
}