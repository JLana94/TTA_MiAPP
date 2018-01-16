package eus.ehu.tta.upv_ehutour.vista;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.List;

import eus.ehu.tta.upv_ehutour.R;
import eus.ehu.tta.upv_ehutour.presentador.Data;
import eus.ehu.tta.upv_ehutour.presentador.Localizador;
import eus.ehu.tta.upv_ehutour.presentador.ProgressTask;

public class ArboretumActivity extends AppCompatActivity {


    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int REQUEST_PERMISION_LOCATION=90;
    private final int REQUEST_PERMISION_WRITE=91;
    private Uri pictureURI;
    private final String LATITUD="43.3276665";
    private final String LONGITUD="-2.9702265";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arboretum);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",LATITUD);
        intent.putExtra("longitud",LONGITUD);
        intent.putExtra("nombre",getResources().getString(R.string.titleArboretum));
        startActivity(intent);
    }

    public void sacarFoto() {

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this,getResources().getString(R.string.noCamara),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
        else
        {

            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!= null)
            {
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                try
                {
                    File file= File.createTempFile("arboretum",".jpg",dir);
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
            //TODO Mandar la foto al servidor

        }
    }

    public void prueba (View view)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                checkPostion();
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISION_WRITE);

            }




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
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    checkPostion();
                }
                else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISION_WRITE);

                }
            } else {
                Toast.makeText(this,getResources().getString(R.string.permisoDenegado),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
            }
        }
        if (requestCode == REQUEST_PERMISION_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPostion();
            } else {
                Toast.makeText(this,getResources().getString(R.string.permisoDenegado),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
            }
        }

    }
    public void checkPostion()
    {
        Localizador loc=new Localizador();
        Location posicion=loc.getLocation(getApplicationContext());
        LatLng ubicacion=new LatLng(posicion.getLatitude(),posicion.getLongitude());

        Double difLat=Math.abs(ubicacion.latitude-Double.valueOf(LATITUD));
        Double difLong=Math.abs(ubicacion.longitude-Double.valueOf(LONGITUD));
        //if(difLat<0.002&&difLong<0.003)
        if(difLat<1&&difLong<1)
        {
            sacarFoto();
        }
        else
            Toast.makeText(this,getResources().getString(R.string.lejos),Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.


    }

    public void verFotos(View view) {
        new ProgressTask<List<String>>(this){
            @Override
            protected List<String> work() throws Exception{
                Data data =new Data();
                return data.pedirFotos();
            }

            @Override
            protected void onFinish(List<String> result)
            {
                for(int i=0;i<result.size();i++)
                {
                    Log.d("Control",result.get(i));
                }

            }
        }.execute();
    }
}
