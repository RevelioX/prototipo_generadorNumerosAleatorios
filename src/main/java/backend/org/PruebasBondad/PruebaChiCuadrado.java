package backend.org.PruebasBondad;

import java.util.ArrayList;
import java.util.List;

public class PruebaChiCuadrado {
  private String distribucion;
  private ArrayList<Double> frecuenciasEsperadas = new ArrayList<>();
  private Long media;
  private Long desviacion;
  private int n;

  public Double calculoChi(List<String> intervalos, List<Long> frecuencias, String distrib, Long desv, Long med) {
    this.distribucion = distrib;
    this.desviacion = desv;
    this.media = med;
    this.n = (int) frecuencias.stream().mapToLong(Long::longValue).sum();

    ArrayList<Double> frecuenciasEsperadasTemporales = new ArrayList<>();
    List<String> intervalosCombinados = new ArrayList<>();
    List<Long> frecuenciasObservadasCombinadas = new ArrayList<>();

    if ("normal".equals(distribucion)) {
      for (int i = 0; i < intervalos.size(); i++) {
        String[] partes = intervalos.get(i).split("-");
        Double limInf = Double.parseDouble(partes[0]);
        Double limSup = Double.parseDouble(partes[1]);

        Double marcaClase = (limInf + limSup) / 2;
        Double probabilidad = (1 / (desv * Math.sqrt(2 * Math.PI))) *
                Math.exp(-0.5 * Math.pow((marcaClase - med) / desv, 2));
        Double frecuenciaEsperada = probabilidad * n;

        frecuenciasEsperadasTemporales.add(frecuenciaEsperada);
        intervalosCombinados.add(intervalos.get(i));
        frecuenciasObservadasCombinadas.add(frecuencias.get(i));
      }

      // Asegurar que todas las frecuencias esperadas superen un mínimo establecido antes de imprimir los resultados
      asegurarFrecuenciasMinimas(frecuenciasEsperadasTemporales, intervalosCombinados, frecuenciasObservadasCombinadas, 5);
    }

    Double chiCalculado = 0.0;
    for (int i = 0; i < intervalos.size(); i++) {
      chiCalculado += Math.pow(frecuenciasObservadasCombinadas.get(i) - frecuenciasEsperadasTemporales.get(i), 2) / frecuenciasEsperadasTemporales.get(i);
    }

    // Imprimir los resultados combinados
    for (int i = 0; i < frecuenciasEsperadasTemporales.size(); i++) {
      System.out.println("Intervalo: " + intervalosCombinados.get(i) +
              ", Frecuencia Esperada: " + frecuenciasEsperadasTemporales.get(i) +
              ", Frecuencia Observada: " + frecuenciasObservadasCombinadas.get(i));
    }
    System.out.println("Chi calculado:" + chiCalculado);

    return chiCalculado; // Este retorno podría ser el resultado del cálculo de Chi-cuadrado
  }

  private void asegurarFrecuenciasMinimas(ArrayList<Double> frecuenciasEsperadas, List<String> intervalos, List<Long> frecuenciasObservadas, double minimoEsperado) {
    int i = 0;
    while (i < frecuenciasEsperadas.size()) {
      if (frecuenciasEsperadas.get(i) < minimoEsperado) {
        if (i < frecuenciasEsperadas.size() - 1) {  // Combina hacia adelante si es posible
          frecuenciasEsperadas.set(i, frecuenciasEsperadas.get(i) + frecuenciasEsperadas.remove(i + 1));
          frecuenciasObservadas.set(i, frecuenciasObservadas.get(i) + frecuenciasObservadas.remove(i + 1));
          String nuevoIntervalo = intervalos.get(i).split("-")[0] + "-" + intervalos.remove(i + 1).split("-")[1];
          intervalos.set(i, nuevoIntervalo);
          continue;  // Re-evaluar el mismo índice después de la combinación
        } else if (i > 0) {  // Combina hacia atrás si es el último elemento y el adelante no es posible
          int lastIndex = i;
          frecuenciasEsperadas.set(lastIndex - 1, frecuenciasEsperadas.get(lastIndex - 1) + frecuenciasEsperadas.remove(lastIndex));
          frecuenciasObservadas.set(lastIndex - 1, frecuenciasObservadas.get(lastIndex - 1) + frecuenciasObservadas.remove(lastIndex));
          String nuevoIntervalo = intervalos.get(lastIndex - 1).split("-")[0] + "-" + intervalos.remove(lastIndex).split("-")[1];
          intervalos.set(lastIndex - 1, nuevoIntervalo);
          i--;  // Mueve el índice hacia atrás para re-evaluar el nuevo último elemento
        }
      }
      i++;
    }
  }
}



