package poo26e10;

public class Utilizador {
	private String nome;
	private String numId;
	private String email;
	private String password;
	private String tipo;
	private int tentativasLogin;
	private boolean bloqueado;
	
	
	public Utilizador(String nome, String numId, String email, String password, String tipo) {
		this.nome = nome;
		this.numId = numId;
		this.email = email;
		this.password = password;
		this.tipo = tipo;
		this.tentativasLogin = 0;
		this.bloqueado = false;
		
	}
	
	
	public boolean login(String numId, String password) {
		if (bloqueado) {
			System.out.println("Conta bloquada. Tente mais tarde");
			return false;
		}
		if (this.numId.equals(numId) && this.password.equals(password)) {
			tentativasLogin = 0;
			return true;
		}
		else {
			tentativasLogin++;
			if (tentativasLogin >= 3) {
				bloqueado = true;
				System.out.println("Conta bloqueada após 3 tentativas falhadas.");
			}
			return false;
		}
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


	public int getTentativasLogin() {
		return tentativasLogin;
	}


	public boolean isBloqueado() {
		return bloqueado;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}


	@Override
	public String toString() {
		return "Utilizador [nome=" + nome + ", numId=" + numId + ", email=" + email + ", password=" + password
				+ ", tipo=" + tipo + "]";
	}
	
	

}
