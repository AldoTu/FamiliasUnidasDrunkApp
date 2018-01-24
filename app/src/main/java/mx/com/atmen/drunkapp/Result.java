package mx.com.atmen.drunkapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    TextView textView;
    Button btnOk;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int result = getIntent().getIntExtra("Result", 0);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.bckg);
        textView = (TextView)findViewById(R.id.textView);
        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, Drunk.class);
                startActivity(intent);
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

}
