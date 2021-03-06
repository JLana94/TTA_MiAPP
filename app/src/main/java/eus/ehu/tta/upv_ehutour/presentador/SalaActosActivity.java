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

public class SalaActosActivity extends AppCompatActivity {

    private final int ESCALA=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_actos);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),R.drawable.foto_sala_actos,options);

        LinearLayout background=(LinearLayout) findViewById(R.id.backgroundSalaActos);

        background.setBackgroundDrawable(new BitmapDrawable(imagen));
    }

    public void avanzar(View view) {
        Intent intent=new Intent(this,SalaOrdenadoresActivity.class);
        startActivity(intent);
    }
}
