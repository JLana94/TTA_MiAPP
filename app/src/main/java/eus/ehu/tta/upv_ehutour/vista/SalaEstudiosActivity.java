package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import eus.ehu.tta.upv_ehutour.R;

public class SalaEstudiosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_estudios);
    }



    public void avanzar(View view) {
        Intent intent=new Intent(this,AulasActivity.class);
        startActivity(intent);
    }


    public void check(View view) {
        Spinner spinner=(Spinner) findViewById(R.id.spinner);


        if(String.valueOf(spinner.getSelectedItem()).equals(getResources().getString(R.string.spinnerSalaEstudiosText)))
        {
            Toast.makeText(this,getResources().getString(R.string.acierto),Toast.LENGTH_SHORT).show();
            SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt(LoginActivity.PRUEBA_SALA_ESTUDIOS,1);
            editor.commit();
            Button avanzar=(Button) findViewById(R.id.botonAvanzarSalaEstudios);
            avanzar.setEnabled(true);
        }
        else
        {
            Toast.makeText(this,getResources().getString(R.string.fallo),Toast.LENGTH_SHORT).show();
        }



    }
}
