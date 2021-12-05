package com.example.automation.cellcounter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by AUTOMATION on 9/3/2017.
 */

public class Calculadora extends Activity {


    private int[]celulasMuertas;
    private int []celulasVivas;
    private CalculadoraCelular miCalculadora;
    TextView concentracionCelular,cantidadCelulas,viabilidad;
    EditText volumen,dilucion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculadora_layout);
        miCalculadora=new CalculadoraCelular();
        celulasMuertas=new int[4];
        celulasVivas=new int[4];

        celulasVivas[0]=getIntent().getExtras().getInt("0vivas");

        celulasVivas[1]=getIntent().getExtras().getInt("1vivas");
        celulasVivas[2]=getIntent().getExtras().getInt("2vivas");
        celulasVivas[3]=getIntent().getExtras().getInt("3vivas");

        celulasMuertas[0]=getIntent().getExtras().getInt("0muertas");
        celulasMuertas[1]=getIntent().getExtras().getInt("1muertas");
        celulasMuertas[2]=getIntent().getExtras().getInt("2muertas");
        celulasMuertas[3]=getIntent().getExtras().getInt("3muertas");

        miCalculadora.setVivas(celulasVivas);
        miCalculadora.setMuertas(celulasMuertas);

        volumen=(EditText)findViewById(R.id.volumen);
        dilucion=(EditText)findViewById(R.id.dilucion);
        concentracionCelular=(TextView)findViewById(R.id.concentracionCelular);
        cantidadCelulas=(TextView)findViewById(R.id.cantidadCelulas);
        viabilidad=(TextView)findViewById(R.id.viabilidad);

        volumen.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE)
                {
                    calcula();
                    InputMethodManager miteclado=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    miteclado.hideSoftInputFromWindow(volumen.getWindowToken(),0);
                    SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor=preferences.edit();
                    double conc=miCalculadora.calculaConcentracionVivas()/1000000;
                    editor.putString("concentracion",String.valueOf(conc));
                    editor.putString("volumen",String.valueOf(volumen.getText().toString()));
                    editor.putString("viabilidad",viabilidad.getText().toString());
                    editor.apply();
                    return true;



                }

                return false;
            }
        });

        dilucion.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_DONE)
                {
                    calcula();
                    InputMethodManager miteclado=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    miteclado.hideSoftInputFromWindow(dilucion.getWindowToken(),0);
                    return true;
                }

                return false;
            }
        });

    }
    public void calcula()
    {
        if(dilucion.getText().toString()!="0"&&volumen.getText().toString()!="0")
        {
            try {
                miCalculadora.setVolumen(Double.parseDouble(volumen.getText().toString()));
                miCalculadora.setDilusion(Double.parseDouble(dilucion.getText().toString()));
            }
            catch (Exception e)
            {
                return;

            }


            concentracionCelular.setText(miCalculadora.calculaConcentracionVivas()/1000000+"");
            cantidadCelulas.setText(miCalculadora.calculaConcentracionPorVolumenVivas()/1000000+"");
            float viabilidad1=(float)miCalculadora.calculaViabilidad();
           viabilidad1= Math.round(viabilidad1);
            viabilidad.setText(viabilidad1+"");


        }

    }

}
