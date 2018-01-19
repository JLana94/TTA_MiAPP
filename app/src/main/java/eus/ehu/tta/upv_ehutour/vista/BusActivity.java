package eus.ehu.tta.upv_ehutour.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import eus.ehu.tta.upv_ehutour.R;
import eus.ehu.tta.upv_ehutour.presentador.NetworkChecker;

public class BusActivity extends AppCompatActivity {

    private final String LATITUD="43.3320669";
    private final String LONGITUD="-2.9720285";
    private final int ESCALA=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=ESCALA;
        Bitmap imagen = BitmapFactory.decodeResource(getResources(),R.drawable.foto_bizkaibus,options);

        LinearLayout background=(LinearLayout) findViewById(R.id.layoutBizkaibus);

        background.setBackgroundDrawable(new BitmapDrawable(imagen));
    }

    public void irBizkaibus(View view) {

        //Para verlo en la propia app
        /*LinearLayout layout=(LinearLayout) findViewById(R.id.layoutBizkaibus);
        WebView web=new WebView(this);
        web.loadUrl(getResources().getString(R.string.URLBizkaibus));

        layout.addView(web);*/
        NetworkChecker networkChecker=new NetworkChecker(this);
        if(networkChecker.checkConexion())
        {
            Uri uri= Uri.parse(getResources().getString(R.string.URLBizkaibus));
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);

        }
        else
            Toast.makeText(this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();



    }

    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",LATITUD);
        intent.putExtra("longitud",LONGITUD);
        intent.putExtra("nombre",getResources().getString(R.string.titleBus));
        startActivity(intent);
    }
}
