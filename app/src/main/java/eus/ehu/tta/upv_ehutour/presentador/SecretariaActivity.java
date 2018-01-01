package eus.ehu.tta.upv_ehutour.presentador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eus.ehu.tta.upv_ehutour.R;

public class SecretariaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretaria);
    }

    public void sacarFoto(View view) {

        //Aqui estaría la llamada a la función de negocio que saca una foto
        Button avanzar=(Button) findViewById(R.id.botonAvanzarSecretaria);
        avanzar.setEnabled(true);
    }

    public void avanzar(View view) {
        Intent intent=new Intent(this,CafeteriaActivity.class);
        startActivity(intent);
    }
}
