package backend.org;

import backend.org.PruebasBondad.PruebaChiCuadrado;

import java.util.ArrayList;
import java.util.List;

public class Pruebas {

    public static void main(String[] args) {
        List<String> intervalos = new ArrayList<String>();
        intervalos.add("1.0-2.0");
        intervalos.add("2.0-3.0");
        intervalos.add("3.0-4.0");

        List<Long> frecuencias = new ArrayList<Long>();
        frecuencias.add( (long) 5);
        frecuencias.add( (long) 6);
        frecuencias.add( (long) 9);
        PruebaChi prueba = new PruebaChi();
        String distribucion = "Normal";
        double media = (double) 5;
        double desv = (double) 5;

        prueba.calculoChi(intervalos, frecuencias, distribucion, desv, media);

    }

}
