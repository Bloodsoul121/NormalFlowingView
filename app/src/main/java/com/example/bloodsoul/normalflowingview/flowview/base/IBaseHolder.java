package com.example.bloodsoul.normalflowingview.flowview.base;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface IBaseHolder {

    void updateAndDraw(Canvas canvas, Paint paint);

    void stop(boolean flag);

}
