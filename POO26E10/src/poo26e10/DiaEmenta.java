package poo26e10;

import java.util.ArrayList;
import java.util.Date;

public class DiaEmenta {

    private Date data;
    private ArrayList<Refeicao> refeicoes;

    public DiaEmenta(Date data, ArrayList<Refeicao> refeicoes) {
        this.data = data;
        this.refeicoes = refeicoes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ArrayList<Refeicao> getRefeicoes() {
        return refeicoes;
    }

    public void setRefeicoes(ArrayList<Refeicao> refeicoes) {
        this.refeicoes = refeicoes;
    }
    
    @Override
    public String toString() { return "Data: " + data + "\nRefeições: " + refeicoes; }
}