package eus.ehu.tta.upv_ehutour.presentador;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import eus.ehu.tta.upv_ehutour.R;


public class MapActivity extends AppCompatActivity {

    private final int ESCALA=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),R.drawable.leioa_vista_pajaro,options);

        LinearLayout background=(LinearLayout) findViewById(R.id.backgroundMap);

        background.setBackgroundDrawable(new BitmapDrawable(imagen));

        int pruebasRestantes = comporbarPruebas();
        if (pruebasRestantes == 0) {
            //FragmentManager fragmentManager = getSupportFragmentManager();
            //Dialogo dialogo = new Dialogo();
            //dialogo.show(fragmentManager, "tag");
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle(getResources().getString(R.string.enhorabuena));

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        } else {
            Toast.makeText(this, getResources().getString(R.string.falta) + " " + String.valueOf(pruebasRestantes) + " " + getResources().getString(R.string.pruebas), Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
        }

    }


    private int comporbarPruebas() {
        SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);

        int pruebaBiblioteca=prefs.getInt(LoginActivity.PRUEBA_BIBLIOTECA,0);
        int pruebaPlazaLaboa=prefs.getInt(LoginActivity.PRUEBA_PLAZA_LABOA,0);
        int pruebaSecretaria=prefs.getInt(LoginActivity.PRUEBA_SECRETARIA,0);
        int pruebaSalaEstudios=prefs.getInt(LoginActivity.PRUEBA_SALA_ESTUDIOS,0);
        int pruebaDespachos=prefs.getInt(LoginActivity.PRUEBA_DESPACHOS,0);
        int pruebaCafeteria=prefs.getInt(LoginActivity.PRUEBA_CAFETERIA,0);
        int pruebaComedor=prefs.getInt(LoginActivity.PRUEBA_COMEDOR,0);

        return 7-(pruebaBiblioteca+pruebaCafeteria+pruebaComedor+pruebaDespachos+pruebaPlazaLaboa+pruebaSecretaria+pruebaSalaEstudios);

    }

    public void polideportivo(View view) {
        Intent intent = new Intent(this,PolideportivoActivity.class);
        startActivity(intent);
    }

    public void plazaLaboa(View view) {
        Intent intent = new Intent(this,PlazaLaboaActivity.class);
        startActivity(intent);
    }

    public void arboretum(View view) {
        Intent intent = new Intent(this,ArboretumActivity.class);
        startActivity(intent);
    }

    public void biblioteca(View view) {
        Intent intent = new Intent(this,BibliotecaActivity.class);
        startActivity(intent);
    }

    public void hosteleria(View view) {
        Intent intent = new Intent(this,HosteleriaActivity.class);
        startActivity(intent);
    }

    public void bizkaibus(View view) {
        Intent intent = new Intent(this,BusActivity.class);
        startActivity(intent);
    }

    public void magisterio(View view) {
        Intent intent = new Intent(this,MagisterioActivity.class);
        startActivity(intent);
    }
}
