package backend.org.generators;

import backend.org.errors.NoMoreRandomNumbers;

import java.util.ArrayList;
import java.util.List;

public class GeneradorNumerosUniformes extends Generador {
    private double a;
    private double b;

    public GeneradorNumerosUniformes(double a, double b){
        super();
        this.a = a;
        this.b = b;
    }

    public void generarValor(int cantidad) {
        for(int i = 0; i < cantidad; i ++){
            double numero = a + Math.random() * (b - a);
            valores.add(numero);
        }

    }

    public String getNombre(){
        return "Uniforme";
    }



}

