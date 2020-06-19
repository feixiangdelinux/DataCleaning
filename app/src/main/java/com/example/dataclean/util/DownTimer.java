/*
    ShengDao Android Client, DownTimer
    Copyright (c) 2014 ShengDao Tech Company Limited
 */
package com.example.dataclean.util;

import android.os.CountDownTimer;


/**
 * [倒计时类]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2014-12-1
 **/
public class DownTimer {

    private final String TAG = DownTimer.class.getSimpleName();
    private CountDownTimer mCountDownTimer;
    private DownTimerListener listener;
    private static DownTimer instance;

    private DownTimer() {
    }

    public static DownTimer getInstance() {
        if (instance == null) {
            instance = new DownTimer();
        }
        return instance;
    }

    /**
     * [开始倒计时功能]<BR>
     * [倒计为time长的时间，时间间隔为每秒]
     *
     * @param time
     */
    public void startDown(long time) {
        startDown(time, 1000);
    }

    /**
     * [倒计为time长的时间，时间间隔为mills]
     *
     * @param time
     * @param mills
     */
    public void startDown(long time, long mills) {
        stopDown();
        mCountDownTimer = new CountDownTimer(time, mills) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (listener != null) {
                    listener.onTick(millisUntilFinished);
                } else {
                }
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onFinish();
                } else {
                }
                stopDown();
            }

        }.start();
    }

    /**
     * [停止倒计时功能]<BR>
     */
    public void stopDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            listener = null;
            mCountDownTimer = null;
        }
    }

    /**
     * [设置倒计时监听]<BR>
     *
     * @param listener
     */
    public void setListener(DownTimerListener listener) {
        this.listener = listener;
    }
}

