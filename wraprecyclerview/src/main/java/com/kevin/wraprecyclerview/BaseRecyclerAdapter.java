package com.kevin.wraprecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 版权所有：----有限公司</br>
 *
 * BaseRecyclerAdapter </br>
 *
 * @author zhou.wenkai ,Created on 2015-8-5 12:27:56</br>
 * @Description Major Function：RecyclerView Adapter基类 </br>
 *
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！</br>
 * @author mender，Modified Date Modify Content:
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    protected final Context mContext;

    /** 数据集合 */
    protected LinkedList<T> mItemLists = new LinkedList();

    /** 删除条目监听 */
    protected OnDeleteListener mOnDeleteListener;
    /** 条目操作的回调监听 */
    protected OnRecyclerViewListener onRecyclerViewListener;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public BaseRecyclerAdapter(Context mContext, LinkedList<T> mItemLists) {
        this.mContext = mContext;
        this.mItemLists = mItemLists;
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    /**
     * 获取数据集合
     *
     * @return mItemLists
     * @date 2015-5-11
     */
    public LinkedList<T> getItemLists() {
        return mItemLists;
    }

    /**
     * 获取数据集合
     *
     * @return mItemLists
     * @date 2015-8-30
     */
    public ArrayList<T> getItemArrayLists() {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (T mItem:mItemLists) {
            arrayList.add(mItem);
        }
        return arrayList;
    }

    /**
     * 设置数据集合
     *
     * @param itemLists
     * @return void
     * @date 2015-5-11
     */
    public void setItemLists(LinkedList<T> itemLists) {
        this.mItemLists = null;
        this.mItemLists = itemLists;
        notifyDataSetChanged();
    }

    /**
     * 刷新数据集合
     *
     * @param itemLists
     * @return void
     * @date 2015-8-20
     */
    public void refreshItemList(List<T> itemLists) {
        setItemLists(itemLists);
    }

    /**
     * 设置数据集合
     *
     * @param itemLists
     * @return void
     * @date 2015-5-11
     */
    public void setItemLists(List<T> itemLists) {
        if(null == itemLists) return;
        this.mItemLists.clear();
        for (int i = 0; i < itemLists.size(); i++) {
            this.mItemLists.add(i, itemLists.get(i));
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据到尾部
     *
     * @param listDatas
     * @return void
     * @date 2015-7-29 13:22:52
     */
    public void addToLast(List<T> listDatas) {
        if (listDatas!= null) {
            mItemLists.addAll(listDatas);
        }
        notifyDataSetChanged();
    }

    /**
     * @Description: 添加数据到首部
     *
     * @param listDatas
     * @return void
     * @date 2015-7-29 13:22:41
     */
    public void addToFirst(List<T> listDatas) {
        if (listDatas!= null) {
            for (T data : listDatas) {
                mItemLists.addFirst(data);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * @Description: 清空数据
     *
     * @return void
     * @date 2015-7-29 13:22:31
     */
    public void clear() {
        mItemLists.clear();
        notifyDataSetChanged();
    }

    /**
     * 根据下标删除对应项
     *
     * @param index
     * @return void
     * @date 2015-7-29 13:22:25
     */
    public void deleteForIndex(int index) {
        if ((mOnDeleteListener != null && !mOnDeleteListener
                .onDeleteItem(index)) || index >= getItemCount()) {
            return;
        }
        mItemLists.remove(index);
    }

    /**
     * 根据下标删除对应项
     *
     * @param indexs
     * @return void
     * @date 2015-7-29 13:22:05
     */
    public void deleteForIndex(int[] indexs) {
        if (indexs.length <= 0) {
            return;
        }
        Arrays.sort(indexs);
        for (int index = indexs.length - 1; index >= 0; index--) {
            deleteForIndex(indexs[index]);
        }
    }

    /**
     * 设置删除监听器
     *
     * @param onDeleteListener
     * @return void
     * @date 2015-7-29 13:21:47
     */
    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }

    /**
     * 获取当前上下文对象
     *
     * @return 上下文
     * @date 2015-7-29 13:21:29
     */
    protected Context getContext() {
        return this.mContext;
    }

    /**
     * 删除监听接口
     *
     * @date 2015-7-29 13:21:08
     */
    public interface OnDeleteListener {
        boolean onDeleteItem(int index);
    }

    /**
     * 设置条目操作监听
     *
     * @param l
     * @return void
     * @date 2015-8-8 22:20:56
     */
    public void setOnRecyclerViewListener(OnRecyclerViewListener l) {
        this.onRecyclerViewListener = l;
    }

    /**
     * 条目操作回调监听接口
     *
     * @date 2015-8-8 22:18:29
     */
    public interface OnRecyclerViewListener {
        /**
         * 条目点击的监听回调
         *
         * @param position
         */
        void onItemClick(View view, int position);

        /**
         * 长按点击的监听回调
         *
         * @param position
         * @return
         */
        boolean onItemLongClick(int position);
    }
}
