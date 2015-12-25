package com.kevin.wraprecyclerview.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.kevin.jsontool.JsonTool;
import com.kevin.loopview.AdLoopView;
import com.kevin.wraprecyclerview.WrapAdapter;
import com.kevin.wraprecyclerview.sample.R;
import com.kevin.wraprecyclerview.sample.adapter.PictureAdapter;
import com.kevin.wraprecyclerview.sample.bean.PictureData;
import com.kevin.wraprecyclerview.sample.utils.LocalFileUtils;

/**
 * Created by zhouwk on 2015/12/25 0025.
 */
public class WarpAdapterActivity extends AppCompatActivity {

    private boolean isFirstData = true;

    private RecyclerView mRecyclerView;
    // 数据适配器包装类
    private WrapAdapter<PictureAdapter> mWrapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrap_adapter);

        initViews();
    }

    /**
     * 初始化View
     */
    private void initViews() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.wrap_adapter_act_recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 创建数据适配器并将数据适配器添加到包装类中
        mWrapAdapter = new WrapAdapter<>(new PictureAdapter(this));
        mWrapAdapter.adjustSpanSize(mRecyclerView);
        mRecyclerView.setAdapter(mWrapAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("刷新数据");
        menu.add("添加头部");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("刷新数据")) {

            // 初始化数据,这里模拟网络获取数据
            PictureData pictureData = initData();
            mWrapAdapter.getWrappedAdapter().setItemLists(pictureData.pictures);
            mWrapAdapter.notifyDataSetChanged();
        } else if(item.getTitle().toString().equals("添加头部")) {
            addHeaderView();
        }
        return true;
    }

    /**
     * 初始化数据
     */
    private PictureData initData() {
        String json;
        if(isFirstData) {
            json = LocalFileUtils.getStringFormAsset(this, "picture.json");
        } else {
            json = LocalFileUtils.getStringFormAsset(this, "picture1.json");
        }
        isFirstData = !isFirstData;

        // 使用JsonTool工具将JSON数据封装到实例对象
        return JsonTool.toBean(json, PictureData.class);
    }

    /**
     * 添加头部View LoopView
     *
     * 这里使用的是LoopView开源项目，项目地址：https://github.com/xuehuayous/Android-LoopView
     *
     * @return void
     */
    private void addHeaderView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.recycler_header, null);
        AdLoopView mAdLoopView = (AdLoopView) layout.findViewById(R.id.home_frag_rotate_vp);
        mWrapAdapter.addHeaderView(layout);

        // 初始化LoopView数据
        String json = LocalFileUtils.getStringFormAsset(this, "loopview.json");
        mAdLoopView.refreshData(json);
        mAdLoopView.startAutoLoop();
    }

}
