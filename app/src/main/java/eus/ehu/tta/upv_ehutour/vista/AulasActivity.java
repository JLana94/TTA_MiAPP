package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.upv_ehutour.R;

public class AulasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);
    }

    public void avanzar(View view) {
        Intent intent=new Intent(this,DespachosActivity.class);
        startActivity(intent);
    }
}
