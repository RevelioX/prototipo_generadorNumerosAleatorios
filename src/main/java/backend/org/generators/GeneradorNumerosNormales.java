package backend.org.generators;

import backend.org.errors.NoMoreRandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class GeneradorNumerosNormales extends Generador {
    private double media;
    private double desviacion;



    public GeneradorNumerosNormales(double media, double desviacion){
        super();
        this.media = media;
        this.desviacion = desviacion;
    }

    public void generarValor(int cantidad){
        for(int i = 0; i < cantidad/2; i++){
            double nroAleatorio1 = Math.random();
            double nroAleatorio2 = Math.random();

            double raiz = Math.sqrt( -2 * Math.log(nroAleatorio1));


            double coseno = Math.cos(2 * Math.PI * nroAleatorio2);
            double seno = Math.sin(2 * Math.PI * nroAleatorio2);


            double nroNormal1 = raiz * coseno * desviacion + media;
            double nroNormal2 = raiz * seno * desviacion + media;

            valores.add(nroNormal1);
            valores.add(nroNormal2);
        }
    }

}
