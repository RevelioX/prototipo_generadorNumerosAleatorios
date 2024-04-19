package backend.org;

import backend.org.boundary.Histograma;
import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosExponencial;
import backend.org.generators.GeneradorNumerosNormales;
import backend.org.generators.GeneradorNumerosUniformes;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double media = 30;
        double desviacion = 4;

        Generador gen = new GeneradorNumerosExponencial(0.5);
        gen.generarValor(10000);
        List<Double> valores = gen.getAll();
        double sumatoria = 0;
        for (double v: valores){
            sumatoria = sumatoria + v;
        }
//        System.out.println(valores);
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Ingrese tamaño de intervalo: ");
//        int amplitud = Integer.parseInt(scanner.nextLine());


        new Histograma( valores);
        System.out.println(sumatoria/100000);
        }
    }
