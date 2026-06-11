package poo26e10;
import java.util.Date;

public class Reserva {
	
	 	private String idReserva;
	    private Date dataReserva;
	    private EstadoReserva estado;
	    private String idPagamento;
	    private double valor;
	    private Date dataPagamento;
	    private Refeicao refeicao;
	    
	    
	    public Reserva(String idReserva, Date dataReserva, double valor, Refeicao refeicao) {
	        this.idReserva = idReserva;
	        this.dataReserva = dataReserva;
	        this.valor = valor;
	        this.refeicao = refeicao;
	        this.estado = EstadoReserva.Pendente;
	        this.idPagamento = null;
	        this.dataPagamento = null;
	    }
	    
	    
	    //confirmar reserva
		public void confirmar() {
	        if (estado == EstadoReserva.Pendente) {
	            estado = EstadoReserva.Paga;
	            System.out.println("Reserva " + idReserva + " confirmada.");
	        } else {
	            System.out.println("A reserva não pode ser confirmada.");
	        }
	    }
		 
		//cancelar reserva
		public void cancelar() {
		        if (estado == EstadoReserva.Pendente || estado == EstadoReserva.Paga) {
		            estado = EstadoReserva.Cancelada;
		            System.out.println("Reserva " + idReserva + " cancelada.");
		        } else {
		            System.out.println("A reserva não pode ser cancelada.");
		        }
		    }
		// marcar reserva como pronta 
		
		 public void marcarPronta() {
		        if (estado == EstadoReserva.Paga) {
		            estado = EstadoReserva.Pronta;
		            System.out.println("Reserva " + idReserva + " pronta para levantamento.");
		        } else {
		            System.out.println("A reserva não pode ser marcada como pronta.");
		        }
		    }
		 // marcar reserva como levantada
		 
		 public void marcarLevantada() {
		        if (estado == EstadoReserva.Pronta) {
		        	estado = EstadoReserva.Levantada;
		            System.out.println("Reserva " + idReserva + " levantada.");
		        } else {
		            System.out.println("A reserva não pode ser marcada como levantada.");
		        }
		    }
		 
		 // validar 
		 public boolean validar() {
		        return idReserva != null && refeicao != null && valor > 0;
		    }


		public String getIdPagamento() {
			return idPagamento;
		}


		public void setIdPagamento(String idPagamento) {
			this.idPagamento = idPagamento;
		}


		public Date getDataPagamento() {
			return dataPagamento;
		}


		public void setDataPagamento(Date dataPagamento) {
			this.dataPagamento = dataPagamento;
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


		public double getValor() {
			return valor;
		}


		public Refeicao getRefeicao() {
			return refeicao;
		}


		@Override
		public String toString() {
			    return "Reserva: " + idReserva + "\nRefeição: " + refeicao.getProduto().getNome() + "\nEstado: " + estado + "\nValor: " + valor + "€";
	}
		 
}
