package app.sc.com.appalcomexcert;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Timer().schedule(new TimerTask() {

            public void run() {
                if(isOnline()){

                    Intent intent = new Intent(SplashScreen.this, ActividadPrincipal.class);
                    startActivity(intent);
                    finish();

                }else{

//                    Intent intent = new Intent(Splash.this,ErrorActivity.class);
//                    startActivity(intent);
//                    finish();

                    SplashScreen.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SplashScreen.this, "Error no hay internet", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }, 2000);

    }

    public boolean isOnline() {
        System.out.println("executeCommand");
        Runtime localRuntime = Runtime.getRuntime();
        try {
            int i = localRuntime.exec("/system/bin/ping -c 1 8.8.8.8")
                    .waitFor();
            System.out.println(" mExitValue " + i);
            boolean bool = false;
            if (i == 0) {
                bool = true;
            }
            return bool;
        } catch (InterruptedException localInterruptedException) {
            localInterruptedException.printStackTrace();
            System.out.println(" Exception:" + localInterruptedException);
            return false;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            System.out.println(" Exception:" + localIOException);
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();


    }



}
