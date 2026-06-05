package poo26e10;

import java.util.ArrayList;

public class Ementa {
       
    private String semana;
    private ArrayList<DiaEmenta> diasUteis;
    
    public Ementa(String semana) {
        this.semana = semana;
        this.diasUteis = new ArrayList<>();
    }

    public void adicionarDia(DiaEmenta dia) {
    	diasUteis.add(dia);
    	}

    public DiaEmenta filtrarPorDia(int indice) {
    	return diasUteis.get(indice); 
    	}

    public void guardar() {
    	System.out.println("Ementa da semana " + semana + " guardada com sucesso."); 
    	}

    public String getSemana() { 
    	return semana;
    	}
    public ArrayList<DiaEmenta> getDiasUteis() { 
    	return diasUteis;
    	}

    public void setSemana(String semana) { 
    	this.semana = semana; 
    	}

    @Override
    public String toString() { return "Semana: " + semana + "\nDias: " + diasUteis; }
}
