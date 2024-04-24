package backend.org.intervalos;

import backend.org.boundary.Histograma;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Intervalos {

    public List<Map.Entry<String, Long>> calcularIntervalos(List<Map.Entry<String, Long>> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                String key = arr.get(j).getKey();
                String[] parts = key.split("-");
                if (parts.length != 2) {
                    // La cadena no tiene el formato esperado, continuar con la siguiente iteración
                    continue;
                }
                String secondPart = parts[1];
                // Verificar si la parte de la cadena no está vacía antes de convertirla a un número
                if (secondPart.isEmpty()) {
                    // La parte de la cadena está vacía, continuar con la siguiente iteración
                    continue;
                }
                double segundoElementoInt = Double.parseDouble(secondPart);

                String key2 = arr.get(j + 1).getKey();
                String[] parts2 = key2.split("-");
                if (parts2.length != 2) {
                    // La cadena no tiene el formato esperado, continuar con la siguiente iteración
                    continue;
                }
                String secodPart2 = parts2[0];
                // Verificar si la parte de la cadena no está vacía antes de convertirla a un número
                if (secodPart2.isEmpty()) {
                    // La parte de la cadena está vacía, continuar con la siguiente iteración
                    continue;
                }
                double segundoElementoInt2 = Double.parseDouble(secodPart2);

                if (segundoElementoInt > segundoElementoInt2) {
                    Map.Entry<String, Long> temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }

        return arr;
    }
}
