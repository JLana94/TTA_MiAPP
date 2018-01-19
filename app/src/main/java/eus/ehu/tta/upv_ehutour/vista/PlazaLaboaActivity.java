package eus.ehu.tta.upv_ehutour.vista;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import eus.ehu.tta.upv_ehutour.R;
import eus.ehu.tta.upv_ehutour.presentador.Localizador;

public class PlazaLaboaActivity extends AppCompatActivity {

    private final String LATITUD="43.3309575";
    private final String LONGITUD="-2.9670699";
    private final int REQUEST_PERMISION_LOCATION=90;
    private final int ESCALA=2;
    private final int ESCALA_MINI=3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_laboa);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),R.drawable.foto_plaza_laboa,options);
        LinearLayout background=(LinearLayout) findViewById(R.id.backgroundPlazaLaboa);
        background.setBackgroundDrawable(new BitmapDrawable(imagen));


        cargarFotoPequeña(R.drawable.foto_plaza_laboa_biblioteca,R.id.backgroundLaboaBiblioteca);
        cargarFotoPequeña(R.drawable.foto_plaza_laboa_eroski,R.id.backgroundLaboaEroski);
        cargarFotoPequeña(R.drawable.foto_plaza_laboa_futbolin,R.id.backgroundLaboaFutbolin);
        cargarFotoPequeña(R.drawable.foto_plaza_laboa_cafeteria,R.id.backgroundLaboaCafeteria);
        cargarFotoPequeña(R.drawable.foto_plaza_laboa_facultad_ciencia,R.id.backgroundLaboaFacCiencias);
        cargarFotoPequeña(R.drawable.foto_plaza_laboa_facultad_comunicacion,R.id.backgroundLaboaFacComunicaciones);

    }

    private void cargarFotoPequeña(int idFoto, int idImageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA_MINI;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),idFoto,options);
        ImageView imageView=(ImageView) findViewById(idImageView);
        imageView.setImageBitmap(imagen);
    }


    private void activarPrueba()
    {
        GridLayout prueba=(GridLayout) findViewById(R.id.pruebaPlazaLaboa);
        prueba.setVisibility(View.VISIBLE);

        Button botonPrueba=(Button)findViewById(R.id.botonPruebaPlazaLaboa);
        botonPrueba.setVisibility(View.GONE);
        Button botonLlegar=(Button)findViewById(R.id.botonLlegarPlazaLaboa);
        botonLlegar.setVisibility(View.GONE);

        Button botonCheck=(Button)findViewById(R.id.botonCheckPlazaLaboa);
        botonCheck.setVisibility(View.VISIBLE);
    }

    public void checkPrueba(View view) {
        Spinner spinner1=(Spinner) findViewById(R.id.spinner1);
        Spinner spinner2=(Spinner) findViewById(R.id.spinner2);
        Spinner spinner3=(Spinner) findViewById(R.id.spinner3);
        Spinner spinner4=(Spinner) findViewById(R.id.spinner4);
        Spinner spinner5=(Spinner) findViewById(R.id.spinner5);
        Spinner spinner6=(Spinner) findViewById(R.id.spinner6);

        if(String.valueOf(spinner1.getSelectedItem()).equals(getResources().getString(R.string.spinner1PlazaLaboaText))&&String.valueOf(spinner2.getSelectedItem()).equals(getResources().getString(R.string.spinner2PlazaLaboaText))&&String.valueOf(spinner3.getSelectedItem()).equals(getResources().getString(R.string.spinner3PlazaLaboaText))&&String.valueOf(spinner4.getSelectedItem()).equals(getResources().getString(R.string.spinner4PlazaLaboaText))&&String.valueOf(spinner5.getSelectedItem()).equals(getResources().getString(R.string.spinner5PlazaLaboaText))&&String.valueOf(spinner6.getSelectedItem()).equals(getResources().getString(R.string.spinner6PlazaLaboaText)))
        {
            Toast.makeText(this,getResources().getString(R.string.acierto),Toast.LENGTH_SHORT).show();
            SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt(LoginActivity.PRUEBA_PLAZA_LABOA,1);
            editor.commit();
            Intent intent=new Intent(this,MapActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,getResources().getString(R.string.fallo),Toast.LENGTH_SHORT).show();
        }



    }

    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",LATITUD);
        intent.putExtra("longitud",LONGITUD);
        intent.putExtra("nombre",getResources().getString(R.string.titlePlazaLaboa));
        startActivity(intent);
    }

    public void prueba (View view)
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
                Toast.makeText(this,getResources().getString(R.string.permisoDenegado),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
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
            Toast.makeText(this,getResources().getString(R.string.lejos),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.


    }

}
