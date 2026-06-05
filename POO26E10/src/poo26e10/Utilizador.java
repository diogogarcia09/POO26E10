package poo26e10;

public class Utilizador {
	private String nome;
	private String numId;
	private String email;
	private String password;
	private String tipo;
	public Utilizador(String nome, String numId, String email, String password, String tipo) {
		super();
		this.nome = nome;
		this.numId = numId;
		this.email = email;
		this.password = password;
		this.tipo = tipo;
	}
	
	
	public boolean login(String numId, String password) {
		return this.numId.equals(numId) && this.password.equals(password);
	}


	public String getNome() {
		return nome;
	}


	public String getNumId() {
		return numId;
	}


	public String getEmail() {
		return email;
	}


	public String getPassword() {
		return password;
	}


	public String getTipo() {
		return tipo;
	}
	
	

}
