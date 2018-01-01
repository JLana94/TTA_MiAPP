package eus.ehu.tta.upv_ehutour.presentador;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import eus.ehu.tta.upv_ehutour.R;

public class BibliotecaActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        RadioGroup grupo=(RadioGroup) findViewById(R.id.pruebaBiblioteca);
        int opciones=grupo.getChildCount();
        for(int i=0;i<opciones;i++)
        {
            grupo.getChildAt(i).setOnClickListener(this);
        }
    }

    public void checkPrueba(View view) {
        int correct=0;
        int selected=-1;
        RadioGroup grupo=(RadioGroup) findViewById(R.id.pruebaBiblioteca);
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
            //Aqui falta escribir que deje almaxenado en local que superado esta prueba
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.fallo), Toast.LENGTH_SHORT).show();
            grupo.getChildAt(selected).setBackgroundColor(getResources().getColor(R.color.rojo));
        }

    }

    public void prueba(View view) {
        RadioGroup prueba=(RadioGroup) findViewById(R.id.pruebaBiblioteca);
        prueba.setVisibility(View.VISIBLE);

        Button botonPrueba=(Button)findViewById(R.id.botonPruebaBiblioteca);
        botonPrueba.setVisibility(View.GONE);
        Button botonLlegar=(Button)findViewById(R.id.botonLlegarBiblioteca);
        botonLlegar.setVisibility(View.GONE);




    }


    @Override
    public void onClick(View v) {
        Button botonCheck=(Button)findViewById(R.id.botonCheckBilioteca);
        botonCheck.setVisibility(View.VISIBLE);

    }
}
