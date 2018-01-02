package eus.ehu.tta.upv_ehutour.presentador;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;

import eus.ehu.tta.upv_ehutour.R;

public class SecretariaActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private final String LATITUD="43.3330276";
    private final String LONGITUD="-2.9726828";
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Uri pictureURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretaria);
    }

    public void sacarFoto() {
        Log.d("Prueba","Antes de abrir la camara");

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this,getResources().getString(R.string.noCamara),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
        else
        {
            Log.d("Prueba","Detecta camara");

            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!= null)
            {
                Log.d("Prueba","Detecta app");
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                try
                {
                    File file= File.createTempFile("secretaria",".jpg",dir);
                    pictureURI= Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureURI);
                    startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
                }
                catch (IOException e)
                {

                }
            }
            else
                Toast.makeText(this,getResources().getString(R.string.noApp),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt(LoginActivity.PRUEBA_SECRETARIA,1);
            editor.commit();
            Button avanzar=(Button) findViewById(R.id.botonAvanzarSecretaria);
            avanzar.setEnabled(true);
        }
    }

    public void avanzar(View view) {
        Intent intent=new Intent(this,CafeteriaActivity.class);
        startActivity(intent);
    }

    public void prueba (View view)
    {

        checkPermission();


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
            sacarFoto();
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
