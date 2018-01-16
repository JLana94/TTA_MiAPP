package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.upv_ehutour.R;

public class PolideportivoActivity extends AppCompatActivity {
    private final String LATITUD="43.3295184";
    private final String LONGITUD="-2.960398";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polideportivo);
    }
    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",LATITUD);
        intent.putExtra("longitud",LONGITUD);
        intent.putExtra("nombre",getResources().getString(R.string.titlePolideportivo));
        startActivity(intent);
    }
}
