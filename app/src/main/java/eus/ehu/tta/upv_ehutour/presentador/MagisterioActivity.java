package eus.ehu.tta.upv_ehutour.presentador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.upv_ehutour.R;

public class MagisterioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magisterio);
    }

    public void comenzarTour(View view) {
        Intent intent=new Intent(this,SecretariaActivity.class);
        startActivity(intent);
    }
    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",getResources().getString(R.string.latitudMagisterio));
        intent.putExtra("longitud",getResources().getString(R.string.longitudMagisterio));
        intent.putExtra("nombre",getResources().getString(R.string.titleMagisterio));
        startActivity(intent);
    }
}
