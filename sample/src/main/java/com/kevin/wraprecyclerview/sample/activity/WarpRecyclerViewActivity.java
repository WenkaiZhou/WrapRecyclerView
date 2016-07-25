package com.kevin.wraprecyclerview.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kevin.jsontool.JsonTool;
import com.kevin.loopview.AdLoopView;
import com.kevin.wraprecyclerview.BaseRecyclerAdapter;
import com.kevin.wraprecyclerview.WrapRecyclerView;
import com.kevin.wraprecyclerview.sample.R;
import com.kevin.wraprecyclerview.sample.adapter.PictureAdapter;
import com.kevin.wraprecyclerview.sample.bean.PictureData;
import com.kevin.wraprecyclerview.sample.utils.LocalFileUtils;

/**
 * Created by zhouwk on 2015/12/25 0025.
 */
public class WarpRecyclerViewActivity extends AppCompatActivity {

    private WrapRecyclerView mWrapRecyclerView;

    private String refreshDate, addHeader, addFooter;
    private boolean isFirstData = true;
    private PictureAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrap_recycler);
        refreshDate = getString(R.string.menu_refresh_data);
        addHeader = getString(R.string.menu_add_header);
        addFooter = getString(R.string.menu_add_footer);

        initViews();
        initEvent();
    }

    /**
     * 初始化View
     */
    private void initViews() {
        mWrapRecyclerView = (WrapRecyclerView) this.findViewById(R.id.wrap_recycler_act_recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWrapRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        addHeaderView();
//        addFooterView();
        // 创建数据适配器
        mAdapter = new PictureAdapter(this);
        mWrapRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(refreshDate);
        menu.add(addHeader);
        menu.add(addFooter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals(refreshDate)) {

            // 初始化数据,这里模拟网络获取数据
            PictureData pictureData = initData();

            mAdapter.setItemLists(pictureData.pictures); // 数据适配器设置数据
        } else if(item.getTitle().toString().equals(addHeader)) {
            addHeaderView();
        } else if(item.getTitle().toString().equals(addFooter)) {
            addFooterView();
        }
        return true;
    }

    private void initEvent() {
        mAdapter.setOnRecyclerViewListener(new BaseRecyclerAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(WarpRecyclerViewActivity.this, "you clicked item " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
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
        mWrapRecyclerView.addHeaderView(layout);

        // 初始化LoopView数据
        String json = LocalFileUtils.getStringFormAsset(this, "loopview.json");
        mAdLoopView.refreshData(json);
        mAdLoopView.startAutoLoop();
    }

    /**
     * 添加头部View LoopView
     *
     * 这里使用的是LoopView开源项目，项目地址：https://github.com/xuehuayous/Android-LoopView
     *
     * @return void
     */
    private void addFooterView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.recycler_header, null);
        AdLoopView mAdLoopView = (AdLoopView) layout.findViewById(R.id.home_frag_rotate_vp);
        mWrapRecyclerView.addFooterView(layout);

        // 初始化LoopView数据
        String json = LocalFileUtils.getStringFormAsset(this, "loopview.json");
        mAdLoopView.refreshData(json);
        mAdLoopView.startAutoLoop();
    }
}
