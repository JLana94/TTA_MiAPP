package eus.ehu.tta.upv_ehutour.presentador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.tta.upv_ehutour.R;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void registro(View view) {
        String usuario=((EditText)findViewById(R.id.usuarioRegistro)).getText().toString();
        String pass=((EditText)findViewById(R.id.passRegistro)).getText().toString();
        String nombre=((EditText)findViewById(R.id.nombreRegistro)).getText().toString();
        String apellido=((EditText)findViewById(R.id.apellidoRegistro)).getText().toString();
        String centro=((EditText)findViewById(R.id.centroRegistro)).getText().toString();
        String telefono=((EditText)findViewById(R.id.telefonoRegistro)).getText().toString();

        if(usuario.equals("")==false&&pass.equals("")==false&&nombre.equals("")==false&&apellido.equals("")==false&&centro.equals("")==false&&telefono.equals("")==false)
        {
            Toast.makeText(this, getResources().getString(R.string.registroCorrecto), Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
            Intent intent=new Intent(this,MapActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, getResources().getString(R.string.datosIncorrectos), Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.


    }
}
