package backend.org.generators;

import backend.org.errors.NoMoreRandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class GeneradorNumerosExponencial extends Generador {
    private double lambda;

    public GeneradorNumerosExponencial(double lambda){
        super();
        this.lambda = lambda;
    }


    public void generarValor(int cantidad) {
        double mu = 1/lambda;
        for(int i = 0; i < cantidad; i++){
            double random = Math.random();
            double generado = - mu * Math.log(1 - random);
            valores.add(generado);
        }
    }
    public String getNombre(){
        return "Exponencial";
    }

    public Double getLambda(){
        return lambda;
    }

}
