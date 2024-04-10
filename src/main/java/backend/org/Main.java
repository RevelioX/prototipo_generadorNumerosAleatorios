package backend.org;

public class Main {
    public static void main(String[] args) {
        double media = 6;
        double desviacion = 2;

        GeneradorNumerosNormales gen = new GeneradorNumerosNormales(media, desviacion);
        gen.generarValor(4);
        double acumulador = 0;
        while(gen.hasNumbers()){
            double valor = gen.getValor();
            System.out.println(valor);
            acumulador = (acumulador + valor);
        }
        System.out.println("Media:" + (acumulador/100));
        }
    }
