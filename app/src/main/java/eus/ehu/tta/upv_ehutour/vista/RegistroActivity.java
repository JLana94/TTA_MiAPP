package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.tta.upv_ehutour.R;
import eus.ehu.tta.upv_ehutour.modelo.Server;
import eus.ehu.tta.upv_ehutour.modelo.User;
import eus.ehu.tta.upv_ehutour.presentador.ProgressTask;

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
        final String registroCorrecto=getResources().getString(R.string.registroCorrecto);
        final String registroIncorrecto=getResources().getString(R.string.registroIncorrecto);
        final User user=new User(usuario,pass,nombre,apellido,centro,telefono);

        if(user.getLogin().equals("")==false&&user.getPassword().equals("")==false&&user.getNombre().equals("")==false&&user.getApellido().equals("")==false&&user.getCentroPrevio().equals("")==false&&user.getTelefono().equals("")==false)
        {

            new ProgressTask<Boolean>(this){
                @Override
                protected Boolean work() throws Exception{
                    Server server =new Server();
                    return server.registro(user);
                }

                @Override
                protected void onFinish(Boolean result)
                {
                    if(result)
                    {
                        Toast.makeText(context, registroCorrecto, Toast.LENGTH_SHORT).show();
                        SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor=prefs.edit();
                        editor.putString(LoginActivity.LOGIN,user.getLogin());
                        editor.putInt(LoginActivity.PRUEBA_BIBLIOTECA,0);
                        editor.putInt(LoginActivity.PRUEBA_CAFETERIA,0);
                        editor.putInt(LoginActivity.PRUEBA_COMEDOR,0);
                        editor.putInt(LoginActivity.PRUEBA_DESPACHOS,0);
                        editor.putInt(LoginActivity.PRUEBA_PLAZA_LABOA,0);
                        editor.putInt(LoginActivity.PRUEBA_SALA_ESTUDIOS,0);
                        editor.putInt(LoginActivity.PRUEBA_SECRETARIA,0);
                        editor.commit();
                        Intent intent=new Intent(context,MapActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(context, registroIncorrecto, Toast.LENGTH_SHORT).show();

                }
            }.execute();

        }
        else
            Toast.makeText(this, getResources().getString(R.string.datosIncorrectos), Toast.LENGTH_SHORT).show();


    }
}
