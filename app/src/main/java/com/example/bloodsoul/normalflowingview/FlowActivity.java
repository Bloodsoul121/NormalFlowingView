package com.example.bloodsoul.normalflowingview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.bloodsoul.normalflowingview.flowview.ContainerView;
import com.example.bloodsoul.normalflowingview.flowview.FloatingDrawer;
import com.example.bloodsoul.normalflowingview.flowview.base.BaseDrawer;

public class FlowActivity extends AppCompatActivity {

    private ContainerView containerView;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        containerView = (ContainerView) findViewById(R.id.floatingView);
        FloatingDrawer drawer = new FloatingDrawer();
        containerView.setDrawer(drawer);

        drawer.setOnItemClickListener(new BaseDrawer.OnItemClickListener() {
            @Override
            public void onItemClick(String childTitle) {
//                Toast.makeText(FlowActivity.this, childTitle, Toast.LENGTH_SHORT).show();
            }
        });
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
