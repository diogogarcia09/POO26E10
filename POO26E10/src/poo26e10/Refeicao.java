package poo26e10;

public class Refeicao {
	
	 private String idRefeicao;
	    private double preco;
	    private int quantDisponivel;
	    private Produto produto;
	    
	    
		public Refeicao(String idRefeicao, double preco, int quantDisponivel, Produto produto) {
			this.idRefeicao = idRefeicao;
			this.preco = preco;
			this.quantDisponivel = quantDisponivel;
			this.produto = produto;
		}
	    
	    
		public boolean esgotada() {
	        return quantDisponivel == 0;
		}


		public double getPreco() {
			return preco;
		}


		public void setPreco(double preco) {
			this.preco = preco;
		}


		public int getQuantDisponivel() {
			return quantDisponivel;
		}


		public void setQuantDisponivel(int quantDisponivel) {
			this.quantDisponivel = quantDisponivel;
		}


		public String getIdRefeicao() {
			return idRefeicao;
		}


		public Produto getProduto() {
			return produto;
		}


		@Override
			public String toString() {
			return produto.getNome() + "\nPreço: " + preco + "€\nDisponível: " + quantDisponivel;
		}	
}

		