package backend.org.intervalos;

import backend.org.boundary.Histograma;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Intervalos {

    public List<Map.Entry<String, Long>> calcularIntervalos (List<Map.Entry<String, Long>> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                String key = arr.get(j).getKey();
                String[] parts = key.split("-");
                String secondPart = parts[1];
                double segundoElementoInt = Double.parseDouble(secondPart);

                String key2 = arr.get(j + 1).getKey();
                String[] parts2 = key2.split("-");
                String secodPart2 = parts2[0];
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
