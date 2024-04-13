package backend.org;

import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosUniformes;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        double media = 30;
        double desviacion = 15;

        Generador gen = new GeneradorNumerosUniformes(0, 10);
        gen.generarValor(1000000);
        List<Double> valores = gen.getAll();
        double sumatoria = 0;
        for (double v: valores){
            sumatoria = sumatoria + v;
        }
        System.out.println(valores);
        System.out.println(sumatoria/1000000);
        }
    }
