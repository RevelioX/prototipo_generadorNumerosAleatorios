package backend.org;

import backend.org.PruebasBondad.PruebaChi;
import backend.org.PruebasBondad.PruebaChiCuadrado;
import backend.org.boundary.Histograma;
import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosNormales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pruebas {

    public static void main(String[] args) {
        Generador generador = new GeneradorNumerosNormales(10,5);
        generador.generarValor(10000);
        List<Double> numeros = generador.getAll();
        Histograma h = new Histograma(numeros,5);


        List<String> intervalos = new ArrayList<String>();
        List<Long> frecuencias = new ArrayList<Long>();

        Map<String, Long> m = h.distributionMap;


        for (Map.Entry<String, Long> entry : m.entrySet()) {
            intervalos.add(entry.getKey());
            frecuencias.add(entry.getValue());
        }


        PruebaChi p = new PruebaChi(intervalos,frecuencias,generador);
        System.out.println(p.getValorCalculado());
    }

}
