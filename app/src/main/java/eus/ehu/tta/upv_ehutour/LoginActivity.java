package eus.ehu.tta.upv_ehutour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        boolean check=true;//Aqui estaría la parte de la llamada al modelo para comprobar contra el server
        return check;
    }

    public void mantenerSesion(View view) {
    }

    public void registro(View view) {
    }
}
