package backend.org.generators;

import backend.org.errors.NoMoreRandomNumbers;

import java.util.ArrayList;
import java.util.List;

public abstract class Generador {

    protected List<Double> valores;

    public Generador()
    {
        this.valores = new ArrayList<Double>();
    }
    public void generarValor(int cantidad){

    }

    public double getValor() {
        if(valores.isEmpty()){
            throw new NoMoreRandomNumbers();
        }
        Double valor = valores.remove(0);
        return valor;
    }

    public boolean hasNumbers() {
        if(valores.isEmpty()){
            return false;
        }
        return true;
    }

    public List<Double> getAll() {
        List<Double> retornoValores = valores;
        valores = new ArrayList<>();
        return retornoValores   ;
    }
}
