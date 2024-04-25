package backend.org.PruebasBondad;

import backend.org.generators.Generador;
import backend.org.generators.GeneradorNumerosNormales;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

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

                    Double limInf = (Double) Double.parseDouble(intervalo.split(":")[0]);
                    Double limSup = (Double) Double.parseDouble(intervalo.split(":")[1]);

                    Double marcaClase = (limInf + limSup) / 2;


                    Double probabilidad = ((Math.exp(-0.5 * Math.pow((marcaClase - generador.getMedia()) / generador.getDesviacion(), 2)))/ (generador.getDesviacion() * Math.sqrt(2 * Math.PI))) * (limSup - limInf);
                    frecuenciasEsperadas.add(probabilidad * n);

                    //System.out.println("Intervalo: [" + limInf + "," + limSup + "]" + probabilidad + " - " + probabilidad*n);
                }
                break;
            case "Uniforme":
                for(String intervalo: intervalos){
                    Double limInf = (Double) Double.parseDouble(intervalo.split(":")[0]);
                    Double limSup = (Double) Double.parseDouble(intervalo.split(":")[1]);

                    Double marcaClase = (limInf + limSup) / 2;

                    Double probabilidad = (double) (n / intervalos.size());
                    frecuenciasEsperadas.add(probabilidad);

                }
        }
        double valorChi = 0;
        for(int i = 0; i < frecuencias.size(); i++){
            System.out.println("INVERVALO -> FREC.ESPERADA:" + frecuenciasEsperadas.get(i) + " FREC.OBSERVADA:" + frecuencias.get(i));
            valorChi = valorChi + (Math.pow(frecuenciasEsperadas.get(i) - frecuencias.get(i),2))/frecuenciasEsperadas.get(i);
        }
        return valorChi;
    }

    public double chiTabla(double alfa) {
        // Crear una distribución chi cuadrado
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(intervalos.size() - 1 - 2);

        // Calcular el valor crítico
        return chiSquaredDistribution.inverseCumulativeProbability(1 - alfa);
    }
}
