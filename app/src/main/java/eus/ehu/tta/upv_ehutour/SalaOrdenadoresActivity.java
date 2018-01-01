package eus.ehu.tta.upv_ehutour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SalaOrdenadoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_orenadores);
    }

    public void avanzar(View view) {
        Intent intent=new Intent(this,TalleresActivity.class);
        startActivity(intent);
    }
}
