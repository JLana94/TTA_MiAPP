package eus.ehu.tta.upv_ehutour.presentador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.upv_ehutour.R;

public class PolideportivoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polideportivo);
    }
    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",getResources().getString(R.string.latitudPolideportivo));
        intent.putExtra("longitud",getResources().getString(R.string.longitudPolideportivo));
        intent.putExtra("nombre",getResources().getString(R.string.titlePolideportivo));
        startActivity(intent);
    }
}
