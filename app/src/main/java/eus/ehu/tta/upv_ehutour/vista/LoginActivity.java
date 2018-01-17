package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.tta.upv_ehutour.R;
import eus.ehu.tta.upv_ehutour.modelo.Server;
import eus.ehu.tta.upv_ehutour.presentador.ProgressTask;

public class LoginActivity extends AppCompatActivity {

    public static String LOGIN="login";
    public static String SHARED_PREFERENCE_NAME="almacenamiento";
    public static String PRUEBA_PLAZA_LABOA="plazaLaboa";
    public static String PRUEBA_BIBLIOTECA="biblioteca";
    public static String PRUEBA_SECRETARIA="secretaria";
    public static String PRUEBA_CAFETERIA="cafeteria";
    public static String PRUEBA_COMEDOR="comedor";
    public static String PRUEBA_SALA_ESTUDIOS="salaEstudios";
    public static String PRUEBA_DESPACHOS="despachos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        String login=prefs.getString(LoginActivity.LOGIN,null);
        if(login!=null)
        {
            Button mantenerSesion=findViewById(R.id.botonMantenerSesion);
            mantenerSesion.setVisibility(View.VISIBLE);
        }
    }


    public void login(View view) {

        String login=((EditText)findViewById(R.id.login)).getText().toString();
        String pass=((EditText)findViewById(R.id.pass)).getText().toString();
        autenticar(login,pass);


    }

    private void autenticar(final String login, final String pass) {
        final String loginIncorrecto=getResources().getString(R.string.loginIncorrecto);

        new ProgressTask<Boolean>(this){
            @Override
            protected Boolean work() throws Exception{
                Server server =new Server();
                return server.checkLogin(login,pass);
            }

            @Override
            protected void onFinish(Boolean result)
            {
                if(result)
                {
                    SharedPreferences prefs=getSharedPreferences(SHARED_PREFERENCE_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putString(LOGIN,login);
                    editor.putInt(PRUEBA_BIBLIOTECA,0);
                    editor.putInt(PRUEBA_CAFETERIA,0);
                    editor.putInt(PRUEBA_COMEDOR,0);
                    editor.putInt(PRUEBA_DESPACHOS,0);
                    editor.putInt(PRUEBA_PLAZA_LABOA,0);
                    editor.putInt(PRUEBA_SALA_ESTUDIOS,0);
                    editor.putInt(PRUEBA_SECRETARIA,0);
                    editor.commit();
                    Intent intent=new Intent(context,MapActivity.class);
                    startActivity(intent);

                }
                else
                    Toast.makeText(context, loginIncorrecto, Toast.LENGTH_SHORT).show();

            }
        }.execute();
    }

    public void mantenerSesion(View view) {
        Intent intent=new Intent(this,MapActivity.class);
        startActivity(intent);
    }

    public void registro(View view) {
        Intent intent=new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }
}
