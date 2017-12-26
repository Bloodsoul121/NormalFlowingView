package com.example.bloodsoul.normalflowingview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

    private TextView mSkipTv;

    private TextView mChoosedTv;

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
        mSkipTv = (TextView) findViewById(R.id.skip);
        mChoosedTv = (TextView) findViewById(R.id.choosed);
        mSkipTv.setOnClickListener(this);
        mChoosedTv.setOnClickListener(this);
        containerView = (ContainerView) findViewById(R.id.floatingView);
        FloatingDrawer drawer = new FloatingDrawer();
        containerView.setDrawer(drawer);
        containerView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        containerView.onResume();
        containerView.startDrawer();
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
                Log.i("FlowActivity", "skip");
                break;
            case R.id.choosed:
                Log.i("FlowActivity", "choosed " + mChannels.toString());
                break;
        }
    }

    @Override
    public void onChannelItemClick(String childTitle) {
        Log.i("FlowActivity", "choosed childTitle --> " + childTitle);
        handleChannels(childTitle);
        refreshChoosedStatus();
    }

    private void handleChannels(String childTitle) {
        Boolean isSelected = mSelects.get(childTitle);
        isSelected = isSelected == null ? false : isSelected;
        mSelects.put(childTitle, !isSelected);
        if (isSelected) {
            mChannels.remove(childTitle);
        } else {
            mChannels.add(childTitle);
        }
    }

    private void refreshChoosedStatus() {
        if (mChannels != null && mChannels.size() > 0) {
            mChoosedTv.setEnabled(true);
        } else {
            mChoosedTv.setEnabled(false);
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
