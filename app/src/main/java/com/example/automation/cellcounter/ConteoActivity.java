package com.example.automation.cellcounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by AUTOMATION on 9/3/2017.
 */

public class ConteoActivity extends Activity {

    Button vivas,muertas;
    int celulasVivas,celulasMuertas,cuadrante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteo_activity_layout);
        vivas=(Button)findViewById(R.id.vivas);
        muertas=(Button)findViewById(R.id.muertas);
        celulasMuertas=0;
        celulasVivas=0;
        cuadrante=getIntent().getExtras().getInt("cuadrante");

        vivas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celulasVivas++;
                vivas.setText( getText(R.string.vivas)+": "+celulasVivas);
            }
        });

        muertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celulasMuertas++;
                muertas.setText(getText(R.string.muertas)+": "+celulasMuertas);
            }
        });

    }

    public void onBackPressed()
    {
        Intent miIntent=new Intent();
        miIntent.putExtra("cuadrante",cuadrante);
        miIntent.putExtra("vivas",celulasVivas);
        miIntent.putExtra("muertas",celulasMuertas);
        setResult(RESULT_OK,miIntent);
        finish();
    }

}
