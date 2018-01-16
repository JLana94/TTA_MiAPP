package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.upv_ehutour.R;

public class SalaActosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_actos);
    }

    public void avanzar(View view) {
        Intent intent=new Intent(this,SalaOrdenadoresActivity.class);
        startActivity(intent);
    }
}
