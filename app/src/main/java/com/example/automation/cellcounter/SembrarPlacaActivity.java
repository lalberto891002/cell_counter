package com.example.automation.cellcounter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by AUTOMATION on 3/10/2018.
 */

public class SembrarPlacaActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    private EditText densidad,nPlacas;
    private TextView volumenCelulas,volumenMedio;
    private Spinner nPozosSpinner;
    private int selectedWell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sembrar_placas_layout);

        densidad=(EditText)findViewById(R.id.densidad_celular);
        nPlacas=(EditText)findViewById(R.id.numero_placas);
        nPozosSpinner = (Spinner) findViewById(R.id.numero_pozos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pozos_array, R.layout.simple_item);
        adapter.setDropDownViewResource(R.layout.simple_item);
        nPozosSpinner.setAdapter(adapter);
        volumenCelulas=(TextView)findViewById(R.id.volumen_a_tomar);
        volumenMedio=(TextView)findViewById(R.id.volumen_de_medio);
        selectedWell = 6;
        densidad.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE)
                {
                    InputMethodManager miteclado=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    miteclado.hideSoftInputFromWindow(densidad.getWindowToken(),0);
                    if(selectedWell == 0)
                    {
                        Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();
                        return false;
                    }
                    calcula(selectedWell);
                    return true;

                }

                return false;
            }
        });

        nPlacas.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE)
                {
                    InputMethodManager miteclado=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    miteclado.hideSoftInputFromWindow(densidad.getWindowToken(),0);
                    if(selectedWell == 0)
                    {
                        Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();
                        return false;
                    }
                    calcula(selectedWell);
                    return true;

                }

                return false;
            }
        });
        nPozosSpinner.setOnItemSelectedListener(this);
    }

    private void calcula(int choice)
    {
        double concenracion=0.0;
        double volumenCelular=0.0;
        double volInicial = 0.0 ;
        CalculadoraCelular calculadora=null;
        double densidadCelular=0.0;
        int numeroPlacas=0;
        int numeroPozos=0;
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        volInicial =Double.parseDouble(preferences.getString("volumen","0"));
        try {
             concenracion = Double.parseDouble(preferences.getString("concentracion", "0.0"));
        }
        catch (Exception e)
        {e.printStackTrace();
        }
        if(concenracion==0.0)
        {
            Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();


        }
        else
        {
            try {
            densidadCelular = Double.parseDouble(densidad.getText().toString());
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();
            return;
        }
        try {
                numeroPlacas = Integer.parseInt(nPlacas.getText().toString());
            }
        catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();
                return;
            }
            numeroPozos = choice;

            volumenCelular=CalculadoraCelular.sembrar(densidadCelular,numeroPlacas,numeroPozos,concenracion);

            String volc=String.format("%6.3f",volumenCelular);
            volumenCelulas.setText(volc);
            double volumenDeMedio = CalculadoraCelular.calcVolMedio(densidadCelular,numeroPlacas,numeroPozos,concenracion);
            volc=String.format("%6.3f",volumenDeMedio);
            volumenMedio.setText(volc);
            if(volInicial < volumenCelular) {
                Toast.makeText(this, getString(R.string.noSuficeCells), Toast.LENGTH_LONG).show();
                volumenCelulas.setText("E!");
                volumenMedio.setText("E!");
            }
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("numero_placas",nPlacas.getText().toString());


            editor.putString("densidad_celular",densidad.getText().toString());


            editor.putString("volumen_celulas",volumenCelulas.getText().toString());

            editor.apply();




        }


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(i==0)
            return;
       try {
           selectedWell = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
       }catch (Exception e){
           selectedWell = 0;
           Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();
           return;
       }
        calcula(selectedWell);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
