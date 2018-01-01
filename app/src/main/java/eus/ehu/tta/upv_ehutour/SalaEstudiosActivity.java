package eus.ehu.tta.upv_ehutour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
            //Aqui falta escribir que deje almaxenado en local que superado esta prueba
        }
        else
        {
            Toast.makeText(this,getResources().getString(R.string.fallo),Toast.LENGTH_SHORT).show();
        }


        Button avanzar=(Button) findViewById(R.id.botonAvanzarSalaEstudios);
        avanzar.setEnabled(true);
    }
}
