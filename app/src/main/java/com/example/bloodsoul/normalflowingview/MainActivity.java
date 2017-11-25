package com.example.bloodsoul.normalflowingview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickFlow(View view) {
        startActivity(new Intent(this, FlowActivity.class));
    }

    public void clickSweep(View view) {
        startActivity(new Intent(this, SweepActivity.class));
    }
}
