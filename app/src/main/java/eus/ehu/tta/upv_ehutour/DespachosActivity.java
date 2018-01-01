package eus.ehu.tta.upv_ehutour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DespachosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despachos);
    }

    public void check(View view) {
        int correct=1;
        int selected=-1;
        RadioGroup grupo=(RadioGroup) findViewById(R.id.pruebaDespachos);
        int opciones=grupo.getChildCount();
        for(int i=0;i<opciones;i++)
        {
            if(grupo.getChildAt(i).getId()==grupo.getCheckedRadioButtonId())
            {
                selected=i;
            }
        }
        if(selected==correct)
        {
            Toast.makeText(this,getResources().getString(R.string.acierto),Toast.LENGTH_SHORT).show();
            grupo.getChildAt(correct).setBackgroundColor(getResources().getColor(R.color.verde));
            Button avanzar=(Button) findViewById(R.id.botonAvanzarDespachos);
            avanzar.setEnabled(true);
            //Aqui falta escribir que deje almaxenado en local que superado esta prueba
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.fallo), Toast.LENGTH_SHORT).show();
            grupo.getChildAt(selected).setBackgroundColor(getResources().getColor(R.color.rojo));
        }


    }

    public void avanzar(View view) {
        Intent intent=new Intent(this,SalaActosActivity.class);
        startActivity(intent);
    }
}
