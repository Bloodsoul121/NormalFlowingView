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

    private final Object lock = new Object();

    public ContainerView(Context context) {
        super(context);
        init();
    }

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
        startDrawer();
        if (!isStartThread) {
            mDrawThread.start();
        }
        isStartThread = true;
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
        if (w == 0 || h == 0) {
            return;
        }
        synchronized (lock) {
            if (this.mDrawer != null) {
                mDrawer.setSize(w, h);
            }
        }
    }

    public void onResume() {
        mDrawThread.mRunning = true;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void onPause() {
        mDrawThread.mRunning = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void onDestroy() {
        mDrawThread.mQuit = true;
        synchronized (lock) {
            lock.notify();
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
        Log.i("ContainerView", " --> surfaceCreated" + ", " + isStartThread);
        synchronized (lock) {
            mDrawThread.mSurface = holder;
            lock.notify();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("ContainerView", " --> surfaceDestroyed" + ", " + isStartThread);
        synchronized (lock) {
            mDrawThread.mSurface = holder;
            lock.notify();
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
                synchronized (lock) {
                    while (mSurface == null || !mRunning) {
                        if (mActive) {
                            mActive = false;
                            lock.notify();
                        }
                        if (mQuit) {
                            return;
                        }
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!mActive) {
                        mActive = true;
                        lock.notify();
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
