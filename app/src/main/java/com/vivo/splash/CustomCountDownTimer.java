package com.vivo.splash;


import android.os.Handler;

public class CustomCountDownTimer implements Runnable {
    private  int time;
    private int countDownTime;
    private final ICountDownHandler countDownHandler;
    private Handler handler;
    private boolean isRun;
    //1、时时回调时间
    //2、支持传入时间
    //3、每过一秒总时间减一
    //4、总时间倒计时为0，回调完成

    public CustomCountDownTimer(int time, ICountDownHandler countDownHandler){
        handler = new Handler();
        this.time = time;
        this.countDownTime = time;
        this.countDownHandler = countDownHandler;
    }

    @Override
    public void run() {
        if(isRun){
            if(countDownHandler != null){
                countDownHandler.onTicker(countDownTime);
            }
            if(countDownTime == 0){
                cancel();
                if(countDownHandler != null){
                    countDownHandler.onfinish();
                }
            }else {
                countDownTime = time--;
                handler.postDelayed(this,1000);
            }
        }
    }

    public void start(){
        isRun = true;
        handler.post(this);
    }

    public void cancel(){
        isRun = false;
        handler.removeCallbacks(this);
    }

    public interface ICountDownHandler{
        void onTicker(int time);
        void onfinish();
    }

}
