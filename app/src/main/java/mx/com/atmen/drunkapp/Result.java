package mx.com.atmen.drunkapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dd.morphingbutton.MorphingButton;

public class Result extends AppCompatActivity {

    TextView textView;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);
        int result = getIntent().getIntExtra("score", 0);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.bckg);
        textView = (TextView)findViewById(R.id.textView);
        final Handler handler = new Handler();
        final MorphingButton btnMorph = (MorphingButton)findViewById(R.id.btnOk);
        btnMorph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorphingButton.Params circle = MorphingButton.Params.create()
                        .duration(1000)
                        .cornerRadius((int)getResources().getDimension(R.dimen.mg_56))
                        .width((int)getResources().getDimension(R.dimen.mg_56))
                        .height((int)getResources().getDimension(R.dimen.mg_56))
                        .color(getResources().getColor(R.color.green))
                        .icon(R.drawable.ic_check);
                btnMorph.morph(circle);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Result.this, Drunk.class));
                    }
                }, 2000);
            }
        });
        if(result == 5){
            textView.setText("¡Puedes manejar sin problemas! Recuerda respetar los límites de velocidad.");
            coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.green));
        }else if(result < 5 && result > 1){
            textView.setText("No estás mal ni tampoco bien, te recomendamos que preferentemente te vayas en taxi o Uber");
            coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
        }else{
            textView.setText("No eres apto para manejar en estas condiciones...");
            coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    public void onBackPressed(){}

}
