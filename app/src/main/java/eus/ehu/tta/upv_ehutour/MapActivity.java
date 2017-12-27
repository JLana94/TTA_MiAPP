package eus.ehu.tta.upv_ehutour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


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
    }

    public void arboretum(View view) {
    }

    public void biblioteca(View view) {
    }

    public void hosteleria(View view) {
    }

    public void bizkaibus(View view) {
    }

    public void magisterio(View view) {
    }
}
