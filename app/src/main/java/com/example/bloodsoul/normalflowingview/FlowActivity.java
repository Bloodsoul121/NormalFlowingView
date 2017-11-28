package com.example.bloodsoul.normalflowingview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.bloodsoul.normalflowingview.flowview.ContainerView;
import com.example.bloodsoul.normalflowingview.flowview.FloatingDrawer;
import com.example.bloodsoul.normalflowingview.flowview.base.BaseDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowActivity extends Activity implements BaseDrawer.OnItemClickListener, View.OnClickListener {

    private ContainerView containerView;

    private Handler mHandler = new Handler();

    private List<String> mChannels = new ArrayList<>();

    private Map<String, Boolean> mSelects = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        initView();
    }

    private void initView() {
        TextView skipTv = (TextView) findViewById(R.id.skip);
        TextView choosedTv = (TextView) findViewById(R.id.choosed);
        skipTv.setOnClickListener(this);
        choosedTv.setOnClickListener(this);
        containerView = (ContainerView) findViewById(R.id.floatingView);
        FloatingDrawer drawer = new FloatingDrawer();
        drawer.setOnItemClickListener(this);
        containerView.setDrawer(drawer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        containerView.onResume();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                containerView.start();
            }
        }, 100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip:
                break;
            case R.id.choosed:
                break;
        }
    }

    @Override
    public void onChannelItemClick(String childTitle) {
        Boolean isSelected = mSelects.get(childTitle);
        isSelected = isSelected == null ? false : isSelected;
        if (isSelected) {
            mChannels.add(childTitle);
        } else {
            mChannels.remove(childTitle);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        containerView.onPause();
        containerView.endDrawer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        containerView.onDestroy();
    }
}
