package kr.ac.cau.manny.appdevproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends Activity {

    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        // 액티비티 종료하는 Runnable
        mRunnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        // 2초 뒤 액티비티를 종료하는 Runnable을 실행
        Handler mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 2000);
    }
}
