package eus.ehu.tta.upv_ehutour.presentador;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import eus.ehu.tta.upv_ehutour.R;

public class BibliotecaActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LATITUD="43.331359";
    private final String LONGITUD="-2.9688432";
    private final int REQUEST_PERMISION_LOCATION=90;
    private final int ESCALA=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        RadioGroup grupo=(RadioGroup) findViewById(R.id.pruebaBiblioteca);
        int opciones=grupo.getChildCount();
        for(int i=0;i<opciones;i++)
        {
            grupo.getChildAt(i).setOnClickListener(this);
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),R.drawable.foto_biblioteca,options);

        LinearLayout background=(LinearLayout) findViewById(R.id.backgroundBiblioteca);

        background.setBackgroundDrawable(new BitmapDrawable(imagen));

    }

    public void checkPrueba(View view) {
        int correct=0;
        int selected=-1;
        RadioGroup grupo=(RadioGroup) findViewById(R.id.pruebaBiblioteca);
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
            SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt(LoginActivity.PRUEBA_BIBLIOTECA,1);
            editor.commit();
            Intent intent=new Intent(this,MapActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.fallo), Toast.LENGTH_SHORT).show();
            grupo.getChildAt(selected).setBackgroundColor(getResources().getColor(R.color.rojo));
        }

    }


    public void activarPrueba()
    {
        RadioGroup prueba=(RadioGroup) findViewById(R.id.pruebaBiblioteca);
        prueba.setVisibility(View.VISIBLE);

        Button botonPrueba=(Button)findViewById(R.id.botonPruebaBiblioteca);
        botonPrueba.setVisibility(View.GONE);
        Button botonLlegar=(Button)findViewById(R.id.botonLlegarBiblioteca);
        botonLlegar.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        Button botonCheck=(Button)findViewById(R.id.botonCheckBilioteca);
        botonCheck.setVisibility(View.VISIBLE);

    }

    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",LATITUD);
        intent.putExtra("longitud",LONGITUD);
        intent.putExtra("nombre",getResources().getString(R.string.titleBiblioteca));
        startActivity(intent);
    }


    public void prueba(View view)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            checkPostion();



        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISION_LOCATION);

        }

    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPostion();
            } else {
                Toast.makeText(this,getResources().getString(R.string.permisoDenegado),Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void checkPostion()
    {
        Location posicion=Localizador.getLocation(getApplicationContext());
        LatLng ubicacion=new LatLng(posicion.getLatitude(),posicion.getLongitude());
        Double difLat=Math.abs(ubicacion.latitude-Double.valueOf(LATITUD));
        Double difLong=Math.abs(ubicacion.longitude-Double.valueOf(LONGITUD));
        //if(difLat<0.0007&&difLong<0.0009)
        if(difLat<1&&difLong<1)
        {
            activarPrueba();
        }
        else
            Toast.makeText(this,getResources().getString(R.string.lejos),Toast.LENGTH_SHORT).show();


    }
}
