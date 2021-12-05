package com.example.automation.cellcounter;

/**
 * Created by Luis Alberto on 9/3/2017.
 */

public class CalculadoraCelular {

    public final static int CUADRANTES = 4;
    public final static int FACTOR_CAMARA=4;
    private int[] vivas;
    private int[] muertas;
    private double dilusion;
    private double volumen;
    private double viabilidad;

    public CalculadoraCelular() {
        vivas = new int[CUADRANTES];
        muertas = new int[CUADRANTES];
        dilusion = 0;
        volumen = 0;
        viabilidad = 0;


    }

    public final int[] getVivas() {
        return vivas;
    }

    public void setVivas(int[] vivas) {
        this.vivas = vivas;
    }

    public final int[] getMuertas() {
        return muertas;
    }

    public void setMuertas(int[] muertas) {
        this.muertas = muertas;
    }

    public double getDilusion() {
        return dilusion;
    }

    public void setDilusion(double dilusion) {
        this.dilusion = dilusion;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public double getViabilidad() {
        return viabilidad;
    }

    public void setViabilidad(double viabilidad) {
        this.viabilidad = viabilidad;
    }

    public double calculaViabilidad() {

      return 100* promedioVivas()/(promedioMuertas()+promedioVivas());
    }

    public double promedioVivas() {
        int suma = 0;
        for (int i : vivas
                ) {
            suma += i;

        }
        return (double) suma / CUADRANTES;

    }

    public double promedioMuertas() {
        int suma = 0;
        for (int i : muertas
                ) {
            suma += i;

        }
        return (double) suma / CUADRANTES;
    }

    public double calculaConcentracionVivas()
    {
        return promedioVivas()*Math.pow(10,FACTOR_CAMARA)*dilusion;


    }

    public  double calculaConcentracionTotal()
    {
        return (promedioVivas()+promedioMuertas())*Math.pow(10,FACTOR_CAMARA)*dilusion;

    }

    public double calculaConcentracionMuertas()
    {
        return promedioMuertas()*Math.pow(10,FACTOR_CAMARA)*dilusion;

    }

    public double calculaConcentracionPorVolumenVivas()
    {
        return calculaConcentracionVivas()*volumen;

    }

    public double calculaConcentracionPorVolumenMuertas()
    {
        return calculaConcentracionMuertas()*volumen;

    }

    public double calculaConcentracionPorVolumenTotal()
    {
        return calculaConcentracionTotal()*volumen;

    }

    public static double siembraPlaca(double volumen,double cantidadTotal,double cantidadDeseada)
    {
        return (volumen/cantidadTotal)*cantidadDeseada;


    }

    public static double siembraPlaca(double volumen,int cantidadPozos,int cantidadPlacas,double concentracionDeseada,double cantidadTotal )
    {
        double cantidad=cantidadPlacas*cantidadPozos*concentracionDeseada;
        return siembraPlaca(volumen,cantidadTotal,cantidad);

    }

    public static double ajustarConcentracion(double concentracion,double volumenInicial,double concentracionDeseada)
    {
        double v2=(concentracion*volumenInicial)/concentracionDeseada;
        return v2-volumenInicial;

    }

    public static double sembrar(double densidadCelular,int numeroPlacas,int numeroPozos,double concentracionInicial)
    {
       return  (densidadCelular*numeroPozos*numeroPlacas)/concentracionInicial;

    }

    public static double calcVolMedio(double densidadCelular,int numeroPlacas,int numeroPozos,double concentracionInicial){
        double volumenDeMedio;
        double volumenCelular = sembrar(densidadCelular,numeroPlacas,numeroPozos,concentracionInicial);
        if(numeroPozos<96)
            volumenDeMedio=numeroPlacas*12-volumenCelular;
        else
            volumenDeMedio=numeroPlacas*10-volumenCelular;
        return volumenDeMedio;
    }


  /*  public static double ajustarConcentracionVFinal(double concentracion,double volumenInicial,double concentracionDeseada)
    {
        double v2=(concentracion*volumenInicial)/concentracionDeseada;
        return v2;

    }
    */
    public void insertarConteo(int cuadrante,int vivas,int muertas)
    {
        this.vivas[cuadrante-1]=vivas;
        this.muertas[cuadrante-1]=muertas;

    }

}