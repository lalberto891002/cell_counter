package com.example.automation.cellcounter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by AUTOMATION on 3/10/2018.
 */

public class ajustar_concentracion extends Activity {
    private EditText concentracionDeseada;
    private TextView volumenAjuste;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustar_concentracio_layout);
        concentracionDeseada=(EditText)findViewById(R.id.concentracionDeseada);
        volumenAjuste=(TextView) findViewById(R.id.volumenAjuste);

        concentracionDeseada.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE)
                {
                    calcula();
                    InputMethodManager miteclado=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    miteclado.hideSoftInputFromWindow(concentracionDeseada.getWindowToken(),0);
                    return true;

                }

                return false;
            }
        });

    }

    private void calcula()
    {
        double conc=0.0;
        double volumen_inicial=0.0;
        double concentracioDeseada=0.0;
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       try {
          conc = Double.parseDouble(preferences.getString("concentracion", "0.0"));
       }
       catch (Exception e)
       {

           Toast.makeText(getApplicationContext(),"debe realizar un conteo valido",Toast.LENGTH_LONG).show();
       }

        try {
            volumen_inicial = Double.parseDouble(preferences.getString("volumen", "0.0"));
        }
        catch (Exception e)
        {

            Toast.makeText(getApplicationContext(),"debe realizar un conteo valido",Toast.LENGTH_LONG).show();
        }


        if(conc==0.0 ||  volumen_inicial==0.0)
        {
            Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();

        }

        try {
            concentracioDeseada = Double.parseDouble(concentracionDeseada.getText().toString());
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();

        }

        if(concentracioDeseada==0.0)
        {
            Toast.makeText(getApplicationContext(),getString(R.string.nodata),Toast.LENGTH_LONG).show();
            return;
        }

        double va=((conc*volumen_inicial)/concentracioDeseada)-volumen_inicial;

        String volc=String.format("%6.3f",va);
        volumenAjuste.setText(volc);

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("concentracion_deseada",concentracionDeseada.getText().toString());
        editor.putString("volumen_ajuste",volumenAjuste.getText().toString());
        editor.apply();






    }
}
