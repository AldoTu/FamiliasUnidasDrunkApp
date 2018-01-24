package mx.com.atmen.drunkapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Drunk extends AppCompatActivity {

    Button btnYes;
    Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drunk);
        btnYes = (Button)findViewById(R.id.btnYes);
        btnNo = (Button)findViewById(R.id.btnNo);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Drunk.this, Test.class));
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Drunk.this, Result.class);
                intent.putExtra("Result", 5);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){}

}
