package mx.com.atmen.drunkapp;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.dd.CircularProgressButton;

import static java.security.AccessController.getContext;

public class Drunk extends AppCompatActivity {

    CircularProgressButton btnYes;
    CircularProgressButton btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drunk);
        VideoView videoview = (VideoView) findViewById(R.id.videoViewD);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.activacion);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        btnYes = (CircularProgressButton) findViewById(R.id.btnYes);
        btnNo = (CircularProgressButton) findViewById(R.id.btnNo);
        final Handler handler = new Handler();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYes.setProgress(100);
                final Dialog dialog = new Dialog(Drunk.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                Button btnGo = (Button)dialog.findViewById(R.id.btnFinalYes);
                btnGo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Drunk.this, Test.class));
                            }
                        });
                dialog.show();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNo.setProgress(100);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Drunk.this, Result.class);
                        intent.putExtra("score", 5);
                        startActivity(intent);
                    }
                }, 1250);
            }
        });
    }

    @Override
    public void onBackPressed(){}

}
