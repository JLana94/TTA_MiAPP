package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import eus.ehu.tta.upv_ehutour.R;

public class DespachosActivity extends AppCompatActivity {

    private final int ESCALA=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despachos);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),R.drawable.foto_despachos,options);

        LinearLayout background=(LinearLayout) findViewById(R.id.backgroundDespachos);

        background.setBackgroundDrawable(new BitmapDrawable(imagen));
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
            SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt(LoginActivity.PRUEBA_DESPACHOS,1);
            editor.commit();
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
