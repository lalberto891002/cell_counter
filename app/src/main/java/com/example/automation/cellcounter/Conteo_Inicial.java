package com.example.automation.cellcounter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

/**
 * Created by AUTOMATION on 9/3/2017.
 */

public class Conteo_Inicial extends Activity {

   private Button c1,c2,c3,c4,calcular;
    public CalculadoraCelular miCalculadora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteo_inicial_layout);

        miCalculadora=new CalculadoraCelular();
        c1=(Button)findViewById(R.id.cuadrante1);
        c2=(Button)findViewById(R.id.cuadrante2);
        c3=(Button)findViewById(R.id.cuadrante3);
        c4=(Button)findViewById(R.id.cuadrante4);
        calcular=(Button)findViewById(R.id.calcular);
        c1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IniciaConteo(1);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IniciaConteo(2);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IniciaConteo(3);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciaConteo(4);
            }
        });
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent=new Intent(getApplicationContext(),Calculadora.class);
                miIntent.putExtra("viabilidad",miCalculadora.calculaViabilidad());
                miIntent.putExtra("0vivas",miCalculadora.getVivas()[0]);
                miIntent.putExtra("0muertas",miCalculadora.getMuertas()[0]);


                miIntent.putExtra("1vivas",miCalculadora.getVivas()[1]);
                miIntent.putExtra("1muertas",miCalculadora.getMuertas()[1]);

                miIntent.putExtra("2vivas",miCalculadora.getVivas()[2]);
                miIntent.putExtra("2muertas",miCalculadora.getMuertas()[2]);


                miIntent.putExtra("3vivas",miCalculadora.getVivas()[3]);
                miIntent.putExtra("3muertas",miCalculadora.getMuertas()[3]);
               startActivity(miIntent);
                finish();

            }
        });
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("volumen","0.0");

        editor.putString("concentracion","0.0");

        editor.putString("viabilidad","0.0");

        editor.putString("numero_placas","0.0");


        editor.putString("densidad_celular","0.0");


        editor.putString("volumen_celulas","0.0");


        editor.putString("concentracion_deseada","0.0");


        editor.putString("volumen_ajuste","0.0");

        editor.apply();

    }


    public void IniciaConteo(int requestCode)
    {
        Intent miIntent=new Intent(this,ConteoActivity.class);

        miIntent.putExtra("cuadrante",requestCode);
        startActivityForResult(miIntent,1);




    }

    public void onActivityResult(int requestCode,int resultCode,Intent miIntent)
    {
        if(resultCode!=RESULT_OK||requestCode!=1)
            return;
        int cuadrante=miIntent.getIntExtra("cuadrante",0);
        int vivas=miIntent.getIntExtra("vivas",0);
        int muertas=miIntent.getIntExtra("muertas",0);
       miCalculadora.insertarConteo(cuadrante,vivas,muertas);

        switch (cuadrante)
        {
            case 1:
            c1.setText( getText(R.string.vivas)+": "+vivas+"\n"+getText(R.string.muertas)+": "+muertas);
            break;

            case 2:
                c2.setText( getText(R.string.vivas)+": "+vivas+"\n"+getText(R.string.muertas)+": "+muertas);
                break;

            case 3:
                c3.setText( getText(R.string.vivas)+": "+vivas+"\n"+getText(R.string.muertas)+": "+muertas);
                break;
            case 4:
                c4.setText( getText(R.string.vivas)+": "+vivas+"\n"+getText(R.string.muertas)+": "+muertas);
                break;

        }


    }
}

