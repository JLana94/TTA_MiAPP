package eus.ehu.tta.upv_ehutour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MagisterioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magisterio);
    }

    public void comenzarTour(View view) {
        Intent intent=new Intent(this,SecretariaActivity.class);
        startActivity(intent);
    }
}
