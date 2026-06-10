package poo26e10;

import java.util.ArrayList;

public class Gestor {

    private String nome;
    private String numId;
    private String email;
    private String password;
    private String tipo;
    private ArrayList<Utilizador> utilizadores;
    private ArrayList<Ementa> ementas;
    private ArrayList<Aviso> avisos;
    private ArrayList<Produto> produtos;

    public Gestor(String nome, String numId, String email, String password) {
        this.nome = nome;
        this.numId = numId;
        this.email = email;
        this.password = password;
        this.tipo = "gestor";
        this.utilizadores = new ArrayList<>();
        this.ementas = new ArrayList<>();
        this.avisos = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    public boolean login(String numId, String password) {
        return this.numId.equals(numId) && this.password.equals(password);
    }
//gerir utilizadores
    public void gerirUtilizadores() {
        System.out.println("Gerir utilizadores:");
        for (Utilizador u : utilizadores) {
            System.out.println(u);
        }
    }
//adicionar produto
    public void adicionarProduto(Produto p) { produtos.add(p); }

    public void alterarProduto(String idProduto, String novoNome, String novaDescricao) {
        for (Produto p : produtos) {
            if (p.getIdProduto().equals(idProduto)) {
                p.setNome(novoNome);
                p.setDescricao(novaDescricao);
                System.out.println("Produto alterado com sucesso.");
                return;
            }
        }
        System.out.println("Produto não encontrado.");
    }
//gerir a ementa
    public void gerirEmenta() {
        System.out.println("Ementas disponíveis:");
        for (Ementa e : ementas) {
            System.out.println(e);
        }
    }
//gerir os avisos
    public void gerirAvisos() {
        System.out.println("Avisos enviados:");
        for (Aviso a : avisos) {
            System.out.println(a);
        }
    }
//consultar a ementa
    public void consultarEmenta() {
        for (Ementa e : ementas) {
            System.out.println(e);
        }
    }
//efetuar uma reserva
    public void efetuarReserva(Estudante estudante, Reserva reserva) {
        estudante.adicionarReserva(reserva);
        System.out.println("Reserva efetuada com sucesso.");
    }
//efetuar pagamento
    public void efetuarPagamento(Reserva reserva) {
        reserva.confirmar();
        System.out.println("Pagamento efetuado com sucesso.");
    }
//cancelar a reseva
    public void cancelarReserva(Reserva reserva) {
        reserva.cancelar();
    }
//consultar resumo das receitas (vazio porque a classe Gestor nao tem acesso ao scanner)
    public void consultarResumoReceitas() {
    }
//consultar adesao por categoria
    public void consultarAdesaoPorCategoria() {
        for (CategoriaProduto cat : CategoriaProduto.values()) {
            int count = 0;
            for (Utilizador u : utilizadores) {
                if (u instanceof Estudante) {
                    Estudante e = (Estudante) u;
                    for (Reserva r : e.getReservas()) {
                        if (r.getRefeicao().getProduto().getCategoria() == cat) {
                            count++;
                        }
                    }
                }
            }
            System.out.println("Categoria " + cat + ": " + count + " refeições");
        }
    }

    public void adicionarUtilizador(Utilizador u) { utilizadores.add(u); }
    public void adicionarEmenta(Ementa e) { ementas.add(e); }
    public void adicionarAviso(Aviso a) { avisos.add(a); }

    public String getNome() { return nome; }
    public String getNumId() { return numId; }
    public String getEmail() { return email; }
    public String getTipo() { return tipo; }
    public ArrayList<Utilizador> getUtilizadores() { return utilizadores; }
    public ArrayList<Ementa> getEmentas() { return ementas; }
    public ArrayList<Aviso> getAvisos() { return avisos; }
    public ArrayList<Produto> getProdutos() { return produtos; }

    @Override
    public String toString() { return "Gestor: " + nome + "\nID: " + numId; }
}