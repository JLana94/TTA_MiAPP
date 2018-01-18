package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.tta.upv_ehutour.R;
import eus.ehu.tta.upv_ehutour.modelo.Server;
import eus.ehu.tta.upv_ehutour.presentador.ProgressTask;

public class FotosActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> listaFotos=new ArrayList<>();
    private ImageView imagen;
    private  String URL_STATIC="http://u017633.ehu.eus:28080/static/ServidorRemoto/img/";
    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fotos);
        Intent intent=getIntent();
        listaFotos=intent.getStringArrayListExtra(ArboretumActivity.LISTA_FOTOS);
        imagen=(ImageView)findViewById(R.id.imagen);
        imagen.setOnClickListener(this);
        
        i=9;
        
        cargarImagen(i);

    }

    private void cargarImagen(final int i) {
        new ProgressTask<Bitmap>(this){
            @Override
            protected Bitmap work() throws Exception{
                Server server =new Server(URL_STATIC);
                Log.d("URL",URL_STATIC+listaFotos.get(i));
                return server.recibirFoto(listaFotos.get(i));
            }

            @Override
            protected void onFinish(Bitmap result)
            {
                if(result!=null)
                    imagen.setImageBitmap(result);
                else
                    Log.d("Error","Error al cargar la imagen");
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        if(i>0)
            i--;
        else
            i=9;
        cargarImagen(i);

    }

    public void salir(View view) {
        Intent intent = new Intent(this,MapActivity.class);
        startActivity(intent);
    }
}
