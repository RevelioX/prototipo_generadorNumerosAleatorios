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
        double desviacion = 15;

        Generador gen = new GeneradorNumerosExponencial(0.5);
        gen.generarValor(1000000);
        List<Double> valores = gen.getAll();
        double sumatoria = 0;
        for (double v: valores){
            sumatoria = sumatoria + v;
        }
        System.out.println(valores);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la cantidad de intervalos: ");
        int intervalos = Integer.parseInt(scanner.nextLine());

        new Histograma(intervalos, valores);
        System.out.println(sumatoria/1000000);
        }
    }
