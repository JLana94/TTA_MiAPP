package eus.ehu.tta.upv_ehutour.presentador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import eus.ehu.tta.upv_ehutour.R;

public class PolideportivoActivity extends AppCompatActivity {
    private final String LATITUD="43.3295184";
    private final String LONGITUD="-2.960398";
    private final int ESCALA=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polideportivo);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),R.drawable.foto_polideportivo,options);

        LinearLayout background=(LinearLayout) findViewById(R.id.backgroundPolideportivo);

        background.setBackgroundDrawable(new BitmapDrawable(imagen));
    }
    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",LATITUD);
        intent.putExtra("longitud",LONGITUD);
        intent.putExtra("nombre",getResources().getString(R.string.titlePolideportivo));
        startActivity(intent);
    }
}
