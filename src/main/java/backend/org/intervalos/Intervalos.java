package backend.org.intervalos;

import java.util.List;
import java.util.Map;

public class Intervalos {

    public List<Map.Entry<String, Long>> calcularIntervalos(List<Map.Entry<String, Long>> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                double firstLowerBound = parseFirstPart(arr.get(j).getKey());
                double nextLowerBound = parseFirstPart(arr.get(j + 1).getKey());

                if (firstLowerBound > nextLowerBound) {
                    Map.Entry<String, Long> temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break; // Salir del bucle si no hay cambios, lo que indica que la lista está ordenada.
        }
        System.out.println(arr); // Imprimir la lista ordenada para verificar.
        return arr;
    }


    private double parseFirstPart(String interval) {
        String firstPart = interval.split(" - ")[0];
        // Remover paréntesis si están presentes
        firstPart = firstPart.replace("(", "").replace(")", "");
        try {
            return Double.parseDouble(firstPart);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + firstPart);
            return Double.MAX_VALUE; // Devuelve un valor alto para manejar errores de formato.
        }
    }
}

