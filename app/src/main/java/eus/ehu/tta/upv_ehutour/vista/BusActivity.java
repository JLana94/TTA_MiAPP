package eus.ehu.tta.upv_ehutour.vista;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.upv_ehutour.R;

public class BusActivity extends AppCompatActivity {

    private final String LATITUD="43.3320669";
    private final String LONGITUD="-2.9720285";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
    }

    public void irBizkaibus(View view) {

        //Para verlo en la propia app
        /*LinearLayout layout=(LinearLayout) findViewById(R.id.layoutBizkaibus);
        WebView web=new WebView(this);
        web.loadUrl(getResources().getString(R.string.URLBizkaibus));

        layout.addView(web);*/

        Uri uri= Uri.parse(getResources().getString(R.string.URLBizkaibus));
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);


    }

    public void llegarAqui(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("latitud",LATITUD);
        intent.putExtra("longitud",LONGITUD);
        intent.putExtra("nombre",getResources().getString(R.string.titleBus));
        startActivity(intent);
    }
}
