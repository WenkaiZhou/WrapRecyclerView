package com.kevin.wraprecyclerview.sample.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by zhouwk on 2015/12/25 0025.
 */
public class LauncherActivity extends ListActivity {
    public static final String[] options = { "WarpAdapter demo", "WarpRecyclerView demo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent;

        switch (position) {
            default:
            case 0:
                intent = new Intent(this, WarpAdapterActivity.class);
                break;
            case 1:
                intent = new Intent(this, WarpRecyclerViewActivity.class);
                break;
        }

        startActivity(intent);
    }
}
