package eus.ehu.tta.upv_ehutour.presentador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import eus.ehu.tta.upv_ehutour.R;


public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



    }

    public void polideportivo(View view) {
        Intent intent = new Intent(this,PolideportivoActivity.class);
        startActivity(intent);
    }

    public void plazaLaboa(View view) {
        Intent intent = new Intent(this,PlazaLaboaActivity.class);
        startActivity(intent);
    }

    public void arboretum(View view) {
        Intent intent = new Intent(this,ArboretumActivity.class);
        startActivity(intent);
    }

    public void biblioteca(View view) {
        Intent intent = new Intent(this,BibliotecaActivity.class);
        startActivity(intent);
    }

    public void hosteleria(View view) {
        Intent intent = new Intent(this,HosteleriaActivity.class);
        startActivity(intent);
    }

    public void bizkaibus(View view) {
        Intent intent = new Intent(this,BusActivity.class);
        startActivity(intent);
    }

    public void magisterio(View view) {
        Intent intent = new Intent(this,MagisterioActivity.class);
        startActivity(intent);
    }
}
