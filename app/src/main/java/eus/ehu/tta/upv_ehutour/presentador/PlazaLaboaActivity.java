package eus.ehu.tta.upv_ehutour.presentador;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import eus.ehu.tta.upv_ehutour.R;

public class PlazaLaboaActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private final String LATITUD="43.3309575";
    private final String LONGITUD="-2.9670699";
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_laboa);
    }

    public void prueba (View view)
    {

        checkPermission();


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
            //TODO: Aqui falta escribir que deje almaxenado en local que superado esta prueba
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
