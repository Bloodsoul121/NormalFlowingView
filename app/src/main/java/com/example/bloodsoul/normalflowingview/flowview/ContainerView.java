package com.example.bloodsoul.normalflowingview.flowview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;

import com.example.bloodsoul.normalflowingview.flowview.base.BaseDrawer;

public class ContainerView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread mDrawThread;

    private BaseDrawer mDrawer;

    private float curDrawerAlpha = 0.5f;

    private int mWidth, mHeight;

    private boolean isStartThread;

    public ContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        curDrawerAlpha = 0f;
        mDrawThread = new DrawThread();
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
    }

    public void startDrawer() {
        mDrawer.setStop(false);
    }

    public void endDrawer() {
        mDrawer.setStop(true);
    }

    public void start(){
        if (isStartThread) {
            return;
        }
        isStartThread = true;
        startDrawer();
        mDrawThread.start();
    }

    public void setDrawer(BaseDrawer drawer) {
        if (drawer == null) {
            return;
        }
        if (mDrawer != drawer) {
            initDrawer(drawer);
        }
    }

    private void initDrawer(BaseDrawer drawer) {
        curDrawerAlpha = 0f;
        mDrawer = drawer;
        updateDrawerSize(getWidth(), getHeight());
        invalidate();
    }

    public void updateDrawerSize(int w, int h) {
        Log.i("ContainerView", "w " + w + ", h " + h);
        if (w == 0 || h == 0) {
            return;
        }
        if (this.mDrawer != null) {
            synchronized (mDrawer) {
                if (this.mDrawer != null) {
                    mDrawer.setSize(w, h);
                }
            }
        }
    }

    public void onResume() {
        synchronized (mDrawThread) {
            mDrawThread.mRunning = true;
            mDrawThread.notify();
        }
    }

    public void onPause() {
        synchronized (mDrawThread) {
            mDrawThread.mRunning = false;
            mDrawThread.notify();
        }
    }

    public void onDestroy() {
        synchronized (mDrawThread) {
            mDrawThread.mQuit = true;
            mDrawThread.notify();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateDrawerSize(w, h);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mDrawer.onTouch(e);
        return super.onTouchEvent(e);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (mDrawThread) {
            mDrawThread.mSurface = holder;
            mDrawThread.notify();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (mDrawThread) {
            mDrawThread.mSurface = holder;
            mDrawThread.notify();
            while (mDrawThread.mActive) {
                try {
                    mDrawThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        holder.removeCallback(this);
    }

    private class DrawThread extends Thread {

        SurfaceHolder mSurface;
        boolean       mRunning;
        boolean       mActive;
        boolean       mQuit;

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    while (mSurface == null || !mRunning) {
                        if (mActive) {
                            mActive = false;
                            notify();
                        }
                        if (mQuit) {
                            return;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!mActive) {
                        mActive = true;
                        notify();
                    }

                    final long startTime = AnimationUtils.currentAnimationTimeMillis();
                    // Lock the canvas for drawing.
                    Canvas canvas = mSurface.lockCanvas();

                    if (canvas != null) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        // Update graphics.
                        drawSurface(canvas);
                        // All done!
                        mSurface.unlockCanvasAndPost(canvas);
                    }

                    final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
                    final long needSleepTime = 16 - drawTime;
                    if (needSleepTime > 0) {
                        try {
                            Thread.sleep(needSleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

    }

    private void drawSurface(Canvas canvas) {
        final int w = mWidth;
        final int h = mHeight;
        if (w == 0 || h == 0) {
            return;
        }
        if (mDrawer != null) {
            mDrawer.setSize(w, h);
            mDrawer.draw(canvas, curDrawerAlpha);
        }

        if (curDrawerAlpha < 1f) {
            curDrawerAlpha += 0.04f;

            if (curDrawerAlpha > 1) {
                curDrawerAlpha = 1f;
            }
        }
    }

    public void setOnItemClickListener(BaseDrawer.OnItemClickListener listener) {
        mDrawer.setOnItemClickListener(listener);
    }

}
