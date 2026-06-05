package poo26e10;

import java.util.Date;

public class Reserva {

    private String idReserva;
    private Date dataReserva;
    private EstadoReserva estado;
    private String idPagamento;
    private double valor;
    private Date dataPagamento;
    private String idRefeicao;

    public Reserva(String idReserva, Date dataReserva, double valor, String idRefeicao) {
        this.idReserva= idReserva;
        this.dataReserva= dataReserva;
        this.valor= valor;
        this.idRefeicao= idRefeicao;
        this.estado= EstadoReserva.Pendente;
        this.idPagamento= "";
        this.dataPagamento= null;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public double getValor() {
        return valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public String getIdRefeicao() {
        return idRefeicao;
    }

    public void setIdPagamento(String idPagamento) {
        this.idPagamento= idPagamento;
    }

    public void setValor(double valor) {
        this.valor= valor;
    }

    // metodos
    public void confirmar() {
        if (estado == EstadoReserva.Pendente) {
            estado= EstadoReserva.Paga;
            dataPagamento= new Date();
        }
    }

    public void cancelar() {
        if (estado == EstadoReserva.Pendente ||
            estado == EstadoReserva.Paga) {

            estado= EstadoReserva.Cancelada;
        }
    }

    public void marcarPronta() {
        if (estado == EstadoReserva.Paga) {
            estado= EstadoReserva.Pronta;
        }
    }

    public void marcarLevantada() {
        if (estado == EstadoReserva.Pronta) {
            estado= EstadoReserva.levantada;
        }
    }

    public boolean validar() {
        return idReserva != null && !idReserva.isEmpty()
                && dataReserva != null
                && valor > 0
                && idRefeicao != null
                && !idRefeicao.isEmpty();
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva='" + idReserva + '\'' +
                ", dataReserva=" + dataReserva +
                ", estado=" + estado +
                ", idPagamento='" + idPagamento + '\'' +
                ", valor=" + valor +
                ", dataPagamento=" + dataPagamento +
                ", idRefeicao='" + idRefeicao + '\'' +
                '}';
    }
}