package com.devfun.cartoon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.devfun.cartoon.R;
import com.devfun.cartoon.adapter.ChannelAdapter;
import com.devfun.cartoon.helper.ItemDecorationAlbumColumns;
import com.devfun.cartoon.helper.OnItemClickListener;
import com.devfun.cartoon.model.BaseModel;
import com.devfun.cartoon.model.ChannelModel;
import com.devfun.cartoon.utils.AppUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView fRecyclerView = (RecyclerView) findViewById(R.id.activityMain_recyclerView);
        fRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        fRecyclerView.addItemDecoration(new ItemDecorationAlbumColumns(2, 20, true));
        //
        List<ChannelModel> fModels = AppUtils.getInstance()
                .loadJSONFromAsset(getApplicationContext());
        ChannelAdapter fAdapter = new ChannelAdapter();
        fAdapter.setData(fModels);
        fRecyclerView.setAdapter(fAdapter);
        fAdapter.setListener(this);
    }

    @Override
    public void onItemClick(BaseModel item, int position) {
        if (item instanceof ChannelModel) {
            Intent fIntent = new Intent(getApplicationContext(), VideoOfChannelActivity.class);
            fIntent.putExtra("channelId", ((ChannelModel) item).getId());
            fIntent.putExtra("channelName", ((ChannelModel) item).getName());
            startActivity(fIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent fIntent = new Intent(getApplicationContext(), VideoOfChannelActivity.class);
                startActivity(fIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
