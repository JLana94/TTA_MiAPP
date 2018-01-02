package eus.ehu.tta.upv_ehutour.presentador;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import eus.ehu.tta.upv_ehutour.R;

public class BibliotecaActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private final String LATITUD="43.331359";
    private final String LONGITUD="-2.9688432";
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
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
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.fallo), Toast.LENGTH_SHORT).show();
            grupo.getChildAt(selected).setBackgroundColor(getResources().getColor(R.color.rojo));
        }

    }

    public void prueba(View view) {
        checkPermission();




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


    private void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            checkPostion();



        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    99);

        }

    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 99) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPostion();
            } else {
                Toast.makeText(this,getResources().getString(R.string.permisoDenegado),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
            }
        }
    }
    public void checkPostion()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng ubicacion=new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());

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

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
