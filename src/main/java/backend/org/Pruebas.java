package backend.org;

import backend.org.PruebasBondad.PruebaChi;
import backend.org.PruebasBondad.PruebaChiCuadrado;
import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosNormales;

import java.util.ArrayList;
import java.util.List;

public class Pruebas {

    public static void main(String[] args) {
        Generador generador = new GeneradorNumerosNormales(10,5);
        generador.generarValor(10000);
        List<Double> numeros = generador.getAll();

        PruebaChi p = PruebaChi(intervalos,frecuencias,generador);
        System.out.println(p.getValorCalculado());
    }

}
