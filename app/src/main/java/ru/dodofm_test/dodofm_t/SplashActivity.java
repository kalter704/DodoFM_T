package ru.dodofm_test.dodofm_t;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    private int mWaitTime = 1000;
    private int mIterationTime = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashThread.start();

    }

    Thread splashThread = new Thread() {
        public void run() {
            try {
                int i = 0;
                while (i < mWaitTime) {
                    sleep(mIterationTime);
                    i += mIterationTime;
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                finish();
            }
        }
    };

}
