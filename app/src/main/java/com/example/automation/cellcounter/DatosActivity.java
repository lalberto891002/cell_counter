package com.example.automation.cellcounter;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AUTOMATION on 3/11/2018.
 */

public class DatosActivity extends Activity {
    private TextView volumen,concentracion,viabilidad,nPlacas,densidadCelular,volumenCelulas,concentracionDeseada,volumenAjuste;
    private String leer;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.datos_layout);

        volumen = (TextView) findViewById(R.id.volumenResumen);
        concentracion = (TextView) findViewById(R.id.concentracionResumen);
        viabilidad = (TextView) findViewById(R.id.viabilidadResumen);
        nPlacas = (TextView) findViewById(R.id.nPlacasResumen);
        densidadCelular = (TextView) findViewById(R.id.densidadCelularResumen);
        volumenCelulas = (TextView) findViewById(R.id.volumenCelulaResumen);
        concentracionDeseada = (TextView) findViewById(R.id.concentracionDeseadaResumen);
        volumenAjuste = (TextView) findViewById(R.id.volumenAjusteResumen);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        leer = preferences.getString("volumen", "0.0");
        volumen.setText(leer);
        leer = preferences.getString("concentracion", "0.0");
        concentracion.setText(leer);
        leer = preferences.getString("viabilidad", "0.0");
        viabilidad.setText(leer);

        leer = preferences.getString("numero_placas", "0.0");
        nPlacas.setText(leer);

        leer = preferences.getString("densidad_celular", "0.0");
        densidadCelular.setText(leer);

        leer = preferences.getString("volumen_celulas", "0.0");
        volumenCelulas.setText(leer);

        leer = preferences.getString("concentracion_deseada", "0.0");
        concentracionDeseada.setText(leer);

        leer = preferences.getString("volumen_ajuste", "0.0");
        volumenAjuste.setText(leer);

        guardar = (Button) findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrir dialogo para poner nombre
                try {
                    if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                        return;
                    }
                    mostrarDialogo(guardar);

            }
                catch (Exception e)
                {

                }
            }

        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mostrarDialogo(guardar);
        }
    }





    private void guardarEnFichero(String nombre) {
        OutputStreamWriter writer;
        File f=null;
        Date date=Calendar.getInstance().getTime();
        int dia=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int mes=date.getMonth();
        int anyo=date.getYear()+1900;
        int hora=date.getHours();
        int minutos=date.getMinutes();
        int seconds=date.getSeconds();
        String temp=String.valueOf(dia)+String.valueOf(mes+1)+String.valueOf(anyo)+String.valueOf(hora)+String.valueOf(minutos)+String.valueOf(seconds)+'-'+nombre;
        nombre=temp+".txt";

        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"\\CellCounter");
        if(!file.exists())
            file.mkdirs();
        try {
             f=new File(getExternalFilesDir(null),nombre);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            writer = new OutputStreamWriter(new FileOutputStream(f.getAbsolutePath()),"UTF-8");


            writer.write(getString(R.string.volumenA)+"="+volumen.getText().toString()+" ml\n");
            writer.write(getString(R.string.concentracionCelular)+"="+concentracion.getText().toString()+" 10^6c/ml\n");
            writer.write(getString(R.string.viabil)+"="+viabilidad.getText().toString()+" %\n");
            writer.write(getString(R.string.numeroPlacas)+"="+nPlacas.getText().toString()+" u\n");
            writer.write(getString(R.string.densidadCelular)+"="+densidadCelular.getText().toString()+" 10^6c/p\n");
            writer.write(getString(R.string.volumenCelulas)+"="+volumenCelulas.getText().toString()+" ml\n");
            writer.write(getString(R.string.concentracionDeseada)+"="+concentracionDeseada.getText().toString()+" 10^6c/ml\n");
            writer.write(getString(R.string.volumenA)+"="+volumenAjuste.getText().toString()+" ml\n");
            writer.flush();
            writer.close();

            Toast.makeText(getApplicationContext(), getString(R.string.fiheroEn)+ f.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.noFichero), Toast.LENGTH_LONG).show();
            return;
        }



    }


    private void mostrarDialogo(View v)
    {


        final Button si,no;
        final EditText nombreF;
        final LinearLayout contenedor=(LinearLayout)findViewById(R.id.contenedorFicheroLocal);
        final LinearLayout contenedorFichero=(LinearLayout)findViewById(R.id.contenedorFichero);
        final LinearLayout animar4=(LinearLayout)findViewById(R.id.animar4) ;
        contenedor.setVisibility(View.VISIBLE);
        si=(Button)findViewById(R.id.aceptar);
        no=(Button)findViewById(R.id.cancelar);
        final LinearLayout animar1=(LinearLayout)findViewById(R.id.animar1);
        final TextView animar2=(TextView)findViewById(R.id.animar2);
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                contenedor.setVisibility(View.GONE);
            }
        };
        nombreF=(EditText)findViewById(R.id.nombreFichero);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ocultar);
                animar1.startAnimation(animation);
                contenedorFichero.startAnimation(animation);
                animar4.startAnimation(animation);
                handler.postDelayed(runnable,500);

            }
        });

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEnFichero(nombreF.getText().toString());
                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ocultar);
                animar1.startAnimation(animation);
                animar4.startAnimation(animation);
                contenedorFichero.startAnimation(animation);
                handler.postDelayed(runnable,500);

            }
        });




        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mostrar);
        animar1.startAnimation(animation);
        animar4.startAnimation(animation);
        contenedorFichero.startAnimation(animation);






    }



}
