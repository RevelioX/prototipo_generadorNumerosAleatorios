package backend.org.PruebasBondad;

import java.util.List;
import java.util.Map;

public class PruebaKs {
  private List<Double> frecuenciasEsperadas;
  private List<Double> frecuenciasObservadas;

    public PruebaKs(List<Double> frecuenciasEsperadas, List<Long> frecuenciasObservadas, int n){
        Double maxDiferencia = (double) 0;
        Double probabilidadEsperadaAcum = (double) 0;
        Double probabilidadObservadaAcum = (double) 0;

        for( int i = 0; i < frecuenciasEsperadas.size(); i++ ){
            probabilidadEsperadaAcum += frecuenciasEsperadas.get(i)/n;
            probabilidadObservadaAcum += (double) frecuenciasObservadas.get(i) /n;
            if ( Math.abs(probabilidadObservadaAcum - probabilidadEsperadaAcum) > maxDiferencia){
                maxDiferencia = Math.abs(probabilidadObservadaAcum - probabilidadEsperadaAcum);
            }
        }

        Double tablaKS = 1.36 / Math.sqrt(n);
        System.out.println("Valor KS: " + maxDiferencia);
        System.out.println("Valor TablaKS:" +  tablaKS);
        if( maxDiferencia < tablaKS) System.out.println("ACEPTA KS");
        if ( maxDiferencia > tablaKS) System.out.println("RECHAZA KS");
  }
}
