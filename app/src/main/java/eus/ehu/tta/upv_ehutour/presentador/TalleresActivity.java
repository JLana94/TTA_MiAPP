package eus.ehu.tta.upv_ehutour.presentador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.upv_ehutour.R;

public class TalleresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talleres);
    }

    public void avanzar(View view) {
        Intent intent=new Intent(this,MapActivity.class);
        startActivity(intent);
    }
}
