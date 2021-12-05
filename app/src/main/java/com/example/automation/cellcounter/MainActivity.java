package com.example.automation.cellcounter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button calculadora,ajuste,placas,resumen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculadora=(Button)findViewById(R.id.btnContador) ;
        placas=(Button)findViewById(R.id.btnSembrarPlacas) ;

        calculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogo();

            }
        });

        placas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent=new Intent(getApplicationContext(),SembrarPlacaActivity.class);
                startActivity(miIntent);
            }
        });

        ajuste=(Button)findViewById(R.id.btnAjustarConcentracion);
        ajuste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ajustar_concentracion.class);
                startActivity(intent);
            }
        });


        resumen=(Button)findViewById(R.id.btnVeresumen);
        resumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DatosActivity.class);
                startActivity(intent);
            }
        });



        Intent miIntent=new Intent(getApplicationContext(),Conteo_Inicial.class);
        startActivity(miIntent);




    }




    public void abrirDialogo()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.aviso_perder))
                .setTitle(getString(R.string.aviso))
                .setCancelable(true).setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getApplicationContext(),Conteo_Inicial.class);
                startActivity(intent);
            }
        }).show();


    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.salir))
                .setTitle(getString(R.string.aviso))
                .setCancelable(true).setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        }).show();


    }
}
