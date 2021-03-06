package eus.ehu.tta.upv_ehutour.presentador;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import eus.ehu.tta.upv_ehutour.R;
import eus.ehu.tta.upv_ehutour.modelo.Foto;
import eus.ehu.tta.upv_ehutour.modelo.Server;

public class ArboretumActivity extends AppCompatActivity {


    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int REQUEST_PERMISION_LOCATION=90;
    private final int REQUEST_PERMISION_WRITE=91;
    private final int ESCALA=2;
    public static final String LISTA_FOTOS="listaFotos";
    private Uri pictureURI;
    private final String LATITUD="43.3276665";
    private final String LONGITUD="-2.9702265";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arboretum);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),R.drawable.foto_arboretum,options);

        LinearLayout background=(LinearLayout) findViewById(R.id.backgroundArbretum);

        background.setBackgroundDrawable(new BitmapDrawable(imagen));

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
            Toast.makeText(this,getResources().getString(R.string.noCamara),Toast.LENGTH_SHORT).show();
        else
        {

            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!= null)
            {
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                try
                {
                    File file= File.createTempFile("arb",".jpg",dir);
                    pictureURI= Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureURI);
                    startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
                }
                catch (IOException e)
                {

                }
            }
            else
                Toast.makeText(this,getResources().getString(R.string.noApp),Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String uriAcotada=pictureURI.toString().substring(7);
            File file=new File(uriAcotada);
            final String filename=file.getName();
            InputStream is=null;
            try {
                is=new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            SharedPreferences prefs=getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
            String login=prefs.getString(LoginActivity.LOGIN,"");
            String date = new SimpleDateFormat("yyyyMMddHHmm_ss").format(Calendar.getInstance().getTime());
            String timestamp=date.split("_")[0];
            final Foto foto=new Foto(filename,timestamp,login);
            final String fotoCorrecta=getResources().getString(R.string.fotoCorrecta);
            final String fotoIncorrecta=getResources().getString(R.string.fotoIncorrecta);

            final InputStream finalIs = is;
            new ProgressTask<Boolean>(this){
                @Override
                protected Boolean work() throws Exception{
                    Server server =new Server();
                    return server.enviarFoto(foto, finalIs,filename);
                }

                @Override
                protected void onFinish(Boolean result)
                {
                    if(result)
                        Toast.makeText(context,fotoCorrecta,Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context,fotoIncorrecta,Toast.LENGTH_SHORT).show();
                }
            }.execute();


        }
    }

    public void prueba (View view)
    {
        NetworkChecker networkChecker=new NetworkChecker(this);
        if(networkChecker.checkConexion())
        {
            if(networkChecker.conexionType().equals("WIFI")==false)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.noWifi).setTitle(R.string.cuidado);
                builder.setPositiveButton(R.string.entiendo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        checkPermission();
                    }
                });
                builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else
                checkPermission();

        }
        else
            Toast.makeText(this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();




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
                Toast.makeText(this,getResources().getString(R.string.permisoDenegado),Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_PERMISION_WRITE) {
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
        //if(difLat<0.002&&difLong<0.003)
        if(difLat<180&&difLong<360)
        {
            sacarFoto();
        }
        else
            Toast.makeText(this,getResources().getString(R.string.lejos),Toast.LENGTH_SHORT).show();


    }

    public void verFotos(View view) {

        NetworkChecker networkChecker=new NetworkChecker(this);
        if(networkChecker.checkConexion())
        {
            if(networkChecker.conexionType().equals("WIFI")==false)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.noWifi).setTitle(R.string.cuidado);
                builder.setPositiveButton(R.string.entiendo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        lanzarVerFotos();
                    }
                });
                builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else
                lanzarVerFotos();

        }
        else
            Toast.makeText(this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();

    }

    private void lanzarVerFotos()
    {
        new ProgressTask<List<String>>(this){
            @Override
            protected List<String> work() throws Exception{
                Server server =new Server();
                return server.pedirFotos();
            }

            @Override
            protected void onFinish(List<String> result)
            {
                for(int i=0;i<result.size();i++)
                {
                    Intent intent = new Intent(context,FotosActivity.class);
                    intent.putExtra(LISTA_FOTOS, (Serializable) result);
                    startActivity(intent);

                }

            }
        }.execute();
    }

    private void checkPermission()
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
}
