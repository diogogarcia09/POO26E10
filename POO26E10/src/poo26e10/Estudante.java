package poo26e10;

import java.util.ArrayList;

public class Estudante extends Utilizador {

    private ArrayList<Reserva> reservas;

    public Estudante(String nome, String numId, String email, String password) {
        super(nome, numId, email, password, "estudante");
        this.reservas = new ArrayList<>();
    }

    public ArrayList<Reserva> getReservas() { return reservas; }

    public void adicionarReserva(Reserva r) {
        reservas.add(r);
    }

    public void cancelarReserva(String idReserva) {
        for (Reserva r : reservas) {
            if (r.getIdReserva().equals(idReserva)) {
                r.cancelar();
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "\nEstudante: " + getNome() + "\nID: " + getNumId();
    }
}