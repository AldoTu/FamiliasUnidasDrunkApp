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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test extends AppCompatActivity implements SensorEventListener{

    ImageView ivBlanco;
    ImageView ivNaranja;
    Random random = new Random();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    Rect v1_rect;
    Rect v2_rect;
    RelativeLayout layout;
    List<ImageView> images = new ArrayList<>();
    List<Rect> rects = new ArrayList<>();

    private int narX = 0;
    private int narY = 0;
    int score = 0;
    int match = 0;

    PowerManager pm;
    PowerManager.WakeLock wl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ivBlanco = (ImageView)findViewById(R.id.ivBlanco);
        ivNaranja = (ImageView)findViewById(R.id.ivNaranja);
        ivBlanco.setX(random.nextFloat()*displayMetrics.widthPixels);
        ivBlanco.setY(random.nextFloat()*displayMetrics.heightPixels);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();
        v1_rect = new Rect((int)ivNaranja.getX() - 13, (int)ivNaranja.getY() - 13, (int)ivNaranja.getX() + 13, (int)ivNaranja.getY() + 13);
        v2_rect = new Rect((int)ivBlanco.getX() - 13, (int)ivBlanco.getY() - 13, (int)ivBlanco.getX() + 13, (int)ivBlanco.getY() + 13);
        layout = (RelativeLayout)findViewById(R.id.relativeLayout);
        for(int i = 0; i < 15; i++){
            ImageView imageView = new ImageView(this);
            Rect rect = new Rect();
            imageView.setImageResource(R.drawable.ic_explosion);
            float x = random.nextFloat()*displayMetrics.widthPixels;
            float y = random.nextFloat()*displayMetrics.heightPixels;
            imageView.setX(x);
            imageView.setY(y);
            images.add(imageView);
            rect.set((int)x - 13, (int)y - 13, (int)x + 13, (int)y + 13);
            rects.add(rect);
            layout.addView(imageView);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            narX -= (int)event.values[0];
            narY += (int)event.values[1];
            ivNaranja.setY(narY);
            ivNaranja.setX(narX);
            v1_rect.set((int)ivNaranja.getX() - 13, (int)ivNaranja.getY() - 13, (int)ivNaranja.getX() + 13, (int)ivNaranja.getY() + 13);
            for(int i = 0; i < 15; i++){
                if(Rect.intersects(v1_rect, rects.get(i))){
                    ivBlanco.setX(random.nextFloat()*displayMetrics.widthPixels);
                    ivBlanco.setY(random.nextFloat()*displayMetrics.heightPixels);
                    v2_rect.set((int)ivBlanco.getX() - 13, (int)ivBlanco.getY() - 13, (int)ivBlanco.getX() + 13, (int)ivBlanco.getY() + 13);
                    Toast.makeText(this, "¡Has chocado!", Toast.LENGTH_SHORT).show();
                    match++;
                    for(int j = 0; j < 15; j++){
                        float x = random.nextFloat()*displayMetrics.widthPixels;
                        float y = random.nextFloat()*displayMetrics.heightPixels;
                        ImageView imageView = images.get(j);
                        imageView.setX(x);
                        imageView.setY(y);
                        images.set(j, imageView);
                        Rect newRect = rects.get(j);
                        newRect.set((int)x - 13, (int)y - 13, (int)x + 13, (int)y + 13);
                        rects.set(j, newRect);
                    }
                }
            }
            if(Rect.intersects(v1_rect, v2_rect)){
                ivBlanco.setX(random.nextFloat()*displayMetrics.widthPixels);
                ivBlanco.setY(random.nextFloat()*displayMetrics.heightPixels);
                v2_rect.set((int)ivBlanco.getX() - 13, (int)ivBlanco.getY() - 13, (int)ivBlanco.getX() + 13, (int)ivBlanco.getY() + 13);
                Toast.makeText(this, "¡Bien hecho!", Toast.LENGTH_SHORT).show();
                match++;
                score++;
            }
            if(match == 5){
                Intent intent = new Intent(Test.this, Result.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
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
        startActivity(new Intent(Test.this, Drunk.class));
        finish();
    }

}
