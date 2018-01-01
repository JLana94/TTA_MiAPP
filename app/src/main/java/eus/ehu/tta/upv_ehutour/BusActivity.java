package eus.ehu.tta.upv_ehutour;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class BusActivity extends AppCompatActivity {

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
}
