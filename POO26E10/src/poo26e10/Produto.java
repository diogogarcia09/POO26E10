package poo26e10;

public class Produto {
	
	private String idProduto;
	private String nome;
	private CategoriaProduto categoria;
	private String descricao;
	
	
	public Produto(String idProduto, String nome, CategoriaProduto categoria, String descrição, String descricao) {
		super();
		this.idProduto = idProduto;
		this.nome = nome;
		this.categoria = categoria;
		this.descricao = descricao;
	}


	public String getIdProduto() {
		return idProduto;
	}


	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public CategoriaProduto getCategoria() {
		return categoria;
	}


	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	@Override
	public String toString() { return "Nome: " + nome + "\nCategoria: " + categoria + "\nDescrição: " + descricao; }
	
	

}
