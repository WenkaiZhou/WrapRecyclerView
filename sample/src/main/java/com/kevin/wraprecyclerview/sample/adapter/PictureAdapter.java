package com.kevin.wraprecyclerview.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.kevin.wraprecyclerview.BaseRecyclerAdapter;
import com.kevin.wraprecyclerview.sample.R;
import com.kevin.wraprecyclerview.sample.bean.PictureData;

import java.util.LinkedList;

/**
 * 版权所有：----有限公司</br>
 * <p/>
 * HomeAdapter </br>
 *
 * @author zhou.wenkai ,Created on 2015-8-5 12:20:34</br>
 * @author mender，Modified Date Modify Content:
 * @Description Major Function：主界面 RecyclerView 数据适配器 </br>
 * <p/>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！</br>
 */
public class PictureAdapter extends BaseRecyclerAdapter<PictureData.Picture, PictureAdapter.MyViewHolder> {

    public PictureAdapter(Context context) {
        super(context);
    }

    public PictureAdapter(Context mContext, LinkedList mItemLists) {
        super(mContext, mItemLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(mContext).load(mItemLists.get(position).imgUrl).into(holder.bgImage);
        holder.position = position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        View rootView;
        ImageView bgImage;
        int position;

        public MyViewHolder(View view) {
            super(view);
            rootView = view.findViewById(R.id.home_item_root);
            bgImage = (ImageView) view.findViewById(R.id.home_item_image);

            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(v, position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }

}
