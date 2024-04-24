package backend.org.PruebasBondad;

import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosNormales;

import java.util.List;

public class PruebaChi {
    List<String> intervalos;
    List<Long> frecuencias;
    Generador generador;

    public PruebaChi(List<String> intrvalos, List<Long> frecuencias, Generador generador) {
        this.generador = generador;
        this.intervalos = intrvalos;
        this.frecuencias = frecuencias;
    }

    public double getValorCalculado() {
        List<Double> probabilidadIntervalo;
        switch (generador.getNombre()){
            case "Normal":
                for(String intervalo : intervalos){
                    Double limInf = Double.valueOf(intervalo.split("-")[0]);
                    Double limSup = Double.valueOf(intervalo.split("-")[1]);

                    Double marcaClase = (limInf + limSup) / 2;

                    Double probabilidad = (1 / (generador.getMedia() * Math.sqrt(2 * Math.PI))) *
                            Math.exp(-0.5 * Math.pow((marcaClase - generador.getMedia()) / generador.getDesviacion(), 2));

                    System.out.println("Intervalo: [" + limInf + "," + limSup + "]" + probabilidad);
                }
        }
        return 0;
    }
}
