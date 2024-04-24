package backend.org.PruebasBondad;

import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosNormales;

import java.util.ArrayList;
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
        int n = (int) frecuencias.stream().mapToLong(Long::longValue).sum();
        List<Double> frecuenciasEsperadas = new ArrayList<Double>();
        switch (generador.getNombre()){
            case "Normal":
                for(String intervalo : intervalos){
                    System.out.println(intervalo);

                    Double limInf = (Double) Double.parseDouble(intervalo.split("-")[0]);
                    Double limSup = (Double) Double.parseDouble(intervalo.split("-")[1]);

                    Double marcaClase = (limInf + limSup) / 2;

                    Double probabilidad = (1 / (generador.getMedia() * Math.sqrt(2 * Math.PI))) *
                            Math.exp(-0.5 * Math.pow((marcaClase - generador.getMedia()) / generador.getDesviacion(), 2)) * (limSup- limInf);

                    frecuenciasEsperadas.add(probabilidad * n);

                    System.out.println(n + "Intervalo: [" + limInf + "," + limSup + "]" + probabilidad + " - " + probabilidad*n);
                }
        }
        double valorChi = 0;
        for(int i = 0; i < frecuencias.size(); i++){
            valorChi = valorChi + Math.pow(frecuencias.get(i) - frecuenciasEsperadas.get(i),2)/frecuenciasEsperadas.get(i);
        }
        return valorChi;
    }
}
