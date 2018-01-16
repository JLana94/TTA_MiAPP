package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import eus.ehu.tta.upv_ehutour.R;

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
        Intent intent=new Intent(this,MapActivity.class);
        String login=((EditText)findViewById(R.id.login)).getText().toString();
        String pass=((EditText)findViewById(R.id.pass)).getText().toString();
        if (autenticar(login,pass))
        {
            //Aqui me queda guardar el login en almacenamiento local
            startActivity(intent);
        }


    }

    private boolean autenticar(String login, String pass) {
        boolean check=true;


        //Aqui estaría la parte de la llamada al modelo para comprobar contra el server


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
        return check;
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
