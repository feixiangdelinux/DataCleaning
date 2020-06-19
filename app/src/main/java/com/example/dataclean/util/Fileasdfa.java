package com.example.dataclean.util;

import android.os.CountDownTimer;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-6-19 下午6:50
 */
public class Fileasdfa {
    private long mTotalTime = 90 * 1000;
    private long mTickTime = 1 * 1000;

    private CountDownTimer mCountDownTimer;
    public void startCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.start();
        } else {
            mCountDownTimer = new CountDownTimer(mTotalTime, mTickTime) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                }
            };
            mCountDownTimer.start();
        }
    }
    /*
     * 重启倒计时
     */
    public void restartCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer.start();
        } else {
            startCountDown();
        }
}
}
