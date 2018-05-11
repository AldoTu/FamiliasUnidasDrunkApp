package mx.com.atmen.drunkapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dd.morphingbutton.MorphingButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        final MorphingButton btnMorph = (MorphingButton)findViewById(R.id.btnContinue);
        btnMorph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorphingButton.Params circle = MorphingButton.Params.create()
                        .duration(1000)
                        .cornerRadius((int)getResources().getDimension(R.dimen.mg_56)) // 56 dp
                        .width((int)getResources().getDimension(R.dimen.mg_56)) // 56 dp
                        .height((int)getResources().getDimension(R.dimen.mg_56)) // 56 dp
                        .color(getResources().getColor(R.color.green))
                        .icon(R.drawable.ic_check);
                btnMorph.morph(circle);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, Drunk.class));
                    }
                }, 2000);
            }
        });
    }
}
