package eus.ehu.tta.upv_ehutour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class PlazaLaboaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_laboa);
    }

    public void prueba (View view)
    {

        //Aqui falta la logica de comprobar la posición y sacar un mensaje o visualizar la prueba

        GridLayout prueba=(GridLayout) findViewById(R.id.pruebaPlazaLaboa);
        prueba.setVisibility(View.VISIBLE);

        Button botonPrueba=(Button)findViewById(R.id.botonPruebaPlazaLaboa);
        botonPrueba.setVisibility(View.GONE);
        Button botonLlegar=(Button)findViewById(R.id.botonLlegarPlazaLaboa);
        botonLlegar.setVisibility(View.GONE);

        Button botonCheck=(Button)findViewById(R.id.botonCheckPlazaLaboa);
        botonCheck.setVisibility(View.VISIBLE);
    }

    public void checkPrueba(View view) {
        Spinner spinner1=(Spinner) findViewById(R.id.spinner1);
        Spinner spinner2=(Spinner) findViewById(R.id.spinner2);
        Spinner spinner3=(Spinner) findViewById(R.id.spinner3);
        Spinner spinner4=(Spinner) findViewById(R.id.spinner4);
        Spinner spinner5=(Spinner) findViewById(R.id.spinner5);
        Spinner spinner6=(Spinner) findViewById(R.id.spinner6);

        if(String.valueOf(spinner1.getSelectedItem()).equals(getResources().getString(R.string.spinner1PlazaLaboaText))&&String.valueOf(spinner2.getSelectedItem()).equals(getResources().getString(R.string.spinner2PlazaLaboaText))&&String.valueOf(spinner3.getSelectedItem()).equals(getResources().getString(R.string.spinner3PlazaLaboaText))&&String.valueOf(spinner4.getSelectedItem()).equals(getResources().getString(R.string.spinner4PlazaLaboaText))&&String.valueOf(spinner5.getSelectedItem()).equals(getResources().getString(R.string.spinner5PlazaLaboaText))&&String.valueOf(spinner6.getSelectedItem()).equals(getResources().getString(R.string.spinner6PlazaLaboaText)))
        {
            Toast.makeText(this,getResources().getString(R.string.acierto),Toast.LENGTH_SHORT).show();
            //Aqui falta escribir que deje almaxenado en local que superado esta prueba
        }
        else
        {
            Toast.makeText(this,getResources().getString(R.string.fallo),Toast.LENGTH_SHORT).show();
        }



    }
}
