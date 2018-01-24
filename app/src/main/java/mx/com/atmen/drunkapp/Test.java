package mx.com.atmen.drunkapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class Test extends AppCompatActivity implements SensorEventListener{

    ImageView ivBlanco;
    ImageView ivNaranja;
    Random random = new Random();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    Rect v1_rect = new Rect();
    Rect v2_rect = new Rect();
    RelativeLayout layout;

    private int narX = 0;
    private int narY = 0;
    private int blaX = 0;
    private int blaY = 0;
    int score = 0;
    int match = 0;

    PowerManager pm;
    PowerManager.WakeLock wl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ivBlanco = (ImageView)findViewById(R.id.ivBlanco);
        ivNaranja = (ImageView)findViewById(R.id.ivNaranja);
        ivBlanco.setMaxHeight(24);
        ivBlanco.setMaxWidth(24);
        ivNaranja.setMaxHeight(24);
        ivNaranja.setMaxWidth(24);
        layout = (RelativeLayout) findViewById(R.id.relativeLayout);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        blaX = random.nextInt(displayMetrics.widthPixels);
        blaY = random.nextInt(displayMetrics.heightPixels);
        ivBlanco.setX(blaX);
        ivBlanco.setY(blaY);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            narX -= (int)event.values[0];
            narY += (int)event.values[1];
            ivNaranja.setY(narY);
            ivNaranja.setX(narX);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if((ivNaranja.getX() > ivBlanco.getX() && ivNaranja.getX() < ivBlanco.getX() + ivBlanco.getWidth()) && (ivNaranja.getY() > ivBlanco.getY() && ivNaranja.getY() < ivBlanco.getY() + ivBlanco.getHeight())){
                        score++;
                        match++;
                        blaX = random.nextInt(displayMetrics.widthPixels);
                        blaY = random.nextInt(displayMetrics.heightPixels);
                        ivBlanco.setX(blaX);
                        ivBlanco.setY(blaY);
                    }
                    if(match == 5){
                        Intent intent = new Intent(Test.this, Result.class);
                        intent.putExtra("Result", score);
                        wl.release();
                        startActivity(intent);
                        finish();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        wl.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        wl.release();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Test.this, Drunk.class);
        startActivity(intent);
        finish();
    }

}
