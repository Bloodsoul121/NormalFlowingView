package com.example.bloodsoul.normalflowingview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AnimationUtils;

import com.example.bloodsoul.normalflowingview.flowview.ContainerView;
import com.example.bloodsoul.normalflowingview.flowview.PreferenceFloatingDrawer;

public class FlowActivity
        extends AppCompatActivity
{

    private ContainerView containerView;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);

        Log.i("FlowActivity", "start --> " + AnimationUtils.currentAnimationTimeMillis());

        containerView = (ContainerView) findViewById(R.id.floatingView);

        containerView.setDrawer(new PreferenceFloatingDrawer(this));

        Log.i("FlowActivity", "end --> " + AnimationUtils.currentAnimationTimeMillis());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("FlowActivity", "onResume --> " + AnimationUtils.currentAnimationTimeMillis());
        containerView.onResume();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                containerView.setDrawerStop(false);
                containerView.start();
            }
        }, 100);
    }
    @Override
    protected void onPause() {
        Log.i("FlowActivity", "onPause --> " + AnimationUtils.currentAnimationTimeMillis());
        super.onPause();
        containerView.onPause();
        containerView.setDrawerStop(true);
    }
    @Override
    protected void onDestroy() {
        Log.i("FlowActivity", "onDestroy --> " + AnimationUtils.currentAnimationTimeMillis());
        super.onDestroy();
        containerView.onDestroy();
    }

}
