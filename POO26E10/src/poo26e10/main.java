package poo26e10;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class main {

    static Scanner sc = new Scanner(System.in);
    static Gestor gestor = new Gestor("Admin", "00000", "admin@cantina.pt", "admin123");

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\nBEM-VINDO À CANTINA");
            System.out.println("1. Registar");
            System.out.println("2. Login");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: registar(); break;
                case 2: login(); break;
                case 0: System.out.println("Até logo!"); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ==================== REGISTO ====================
    static void registar() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Número de identificação: ");
        String numId = sc.nextLine();

        for (Utilizador u : gestor.getUtilizadores()) {
            if (u.getNumId().equals(numId)) {
                System.out.println("Número de identificação já registado!");
                return;
            }
        }

        System.out.print("Email (formato: utilizador@dominio.com): ");
        String email = sc.nextLine();
        if (!Utilizador.validarEmail(email)) {
            System.out.println("Email inválido!");
            return;
        }

        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.println("Tipo: 1.Estudante  2.Funcionário  3.Administrador");
        System.out.print("Opção: ");
        int tipo = sc.nextInt();
        sc.nextLine();

        switch (tipo) {
            case 1:
                gestor.adicionarUtilizador(new Estudante(nome, numId, email, password));
                System.out.println("Estudante registado com sucesso!");
                break;
            case 2:
                gestor.adicionarUtilizador(new Utilizador(nome, numId, email, password, "funcionario") {});
                System.out.println("Funcionário registado com sucesso!");
                break;
            case 3:
                gestor.adicionarUtilizador(new Utilizador(nome, numId, email, password, "administrador") {});
                System.out.println("Administrador registado com sucesso!");
                break;
            default:
                System.out.println("Tipo inválido!");
        }
    }

    // ==================== LOGIN ====================
    static void login() {
        System.out.print("Número de identificação: ");
        String numId = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        if (gestor.login(numId, password)) {
            System.out.println("Bem-vindo, " + gestor.getNome() + "!");
            menuAdministrador();
            return;
        }

        for (Utilizador u : gestor.getUtilizadores()) {
            if (u.getNumId().equals(numId)) {
                if (u.isBloqueado()) {
                    System.out.println("Conta bloqueada. Tente mais tarde.");
                    return;
                }
                if (u.login(numId, password)) {
                    System.out.println("Bem-vindo, " + u.getNome() + "!");
                    switch (u.getTipo()) {
                        case "estudante": menuEstudante((Estudante) u); break;
                        case "funcionario": menuFuncionario(u); break;
                        case "administrador": menuAdministrador(); break;
                    }
                    return;
                } else {
                    System.out.println("Password incorreta!");
                    return;
                }
            }
        }
        System.out.println("Utilizador não encontrado!");
    }

    // ==================== MENU ESTUDANTE ====================
    static void menuEstudante(Estudante estudante) {
        int opcao;
        do {
            System.out.println("\nMENU ESTUDANTE");
            System.out.println("1. Consultar ementa");
            System.out.println("2. Efetuar reserva");
            System.out.println("3. Efetuar pagamento");
            System.out.println("4. Cancelar reserva");
            System.out.println("5. Ver detalhes de uma reserva");
            System.out.println("6. Ver histórico de reservas");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: consultarEmenta(); break;
                case 2: efetuarReserva(estudante); break;
                case 3: efetuarPagamento(estudante); break;
                case 4: cancelarReserva(estudante); break;
                case 5: verDetalhesReserva(estudante); break;
                case 6: verHistoricoReservas(estudante); break;
                case 0: System.out.println("A sair..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ==================== MENU FUNCIONÁRIO ====================
    static void menuFuncionario(Utilizador funcionario) {
        int opcao;
        do {
            System.out.println("\nMENU FUNCIONÁRIO");
            System.out.println("1. Marcar reserva como pronta");
            System.out.println("2. Confirmar levantamento");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: marcarReservaPronta(); break;
                case 2: confirmarLevantamento(); break;
                case 0: System.out.println("A sair..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ==================== MENU ADMINISTRADOR ====================
    static void menuAdministrador() {
        int opcao;
        do {
            System.out.println("\nMENU ADMINISTRADOR");
            System.out.println("1. Criar ementa semanal");
            System.out.println("2. Editar ementa");
            System.out.println("3. Gerir utilizadores");
            System.out.println("4. Consultar resumo de receitas");
            System.out.println("5. Enviar aviso");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: criarEmenta(); break;
                case 2: editarEmenta(); break;
                case 3: gestor.gerirUtilizadores(); break;
                case 4: gestor.consultarResumoReceitas(); break;
                case 5: enviarAviso(); break;
                case 0: System.out.println("A sair..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ==================== FUNCIONALIDADES ESTUDANTE ====================
    static void consultarEmenta() {
        if (gestor.getEmentas().isEmpty()) {
            System.out.println("Não há ementas disponíveis.");
            return;
        }
        System.out.println("Filtrar por dia? 1.Sim  2.Não");
        int opcao = sc.nextInt();
        sc.nextLine();

        Ementa ementa = gestor.getEmentas().get(gestor.getEmentas().size() - 1);

        if (opcao == 1) {
            System.out.println("Escolhe o dia (1-5):");
            for (int i = 0; i < ementa.getDiasUteis().size(); i++) {
                System.out.println((i + 1) + ". " + ementa.getDiasUteis().get(i).getData());
            }
            int dia = sc.nextInt() - 1;
            sc.nextLine();
            DiaEmenta diaEmenta = ementa.filtrarPorDia(dia);
            System.out.println("\nRefeições disponíveis:");
            for (Refeicao r : diaEmenta.getRefeicoes()) {
                System.out.println(r);
            }
        } else {
            for (DiaEmenta d : ementa.getDiasUteis()) {
                System.out.println("\nDia: " + d.getData());
                for (Refeicao r : d.getRefeicoes()) {
                    System.out.println(r);
                }
            }
        }
    }

    static void efetuarReserva(Estudante estudante) {
        if (gestor.getEmentas().isEmpty()) {
            System.out.println("Não há ementas disponíveis.");
            return;
        }

        Ementa ementa = gestor.getEmentas().get(gestor.getEmentas().size() - 1);
        System.out.println("Escolhe o dia (1-5):");
        for (int i = 0; i < ementa.getDiasUteis().size(); i++) {
            System.out.println((i + 1) + ". " + ementa.getDiasUteis().get(i).getData());
        }
        int dia = sc.nextInt() - 1;
        sc.nextLine();

        DiaEmenta diaEmenta = ementa.filtrarPorDia(dia);
        System.out.println("Escolhe a refeição:");
        for (int i = 0; i < diaEmenta.getRefeicoes().size(); i++) {
            Refeicao r = diaEmenta.getRefeicoes().get(i);
            if (!r.esgotada()) {
                System.out.println((i + 1) + ". " + r);
            }
        }
        int escolha = sc.nextInt() - 1;
        sc.nextLine();

        Refeicao refeicaoEscolhida = diaEmenta.getRefeicoes().get(escolha);
        if (refeicaoEscolhida.esgotada()) {
            System.out.println("Refeição esgotada!");
            return;
        }

        String idReserva = "R" + (estudante.getReservas().size() + 1);
        Reserva reserva = new Reserva(idReserva, new Date(), refeicaoEscolhida.getPreco(), refeicaoEscolhida);
        estudante.adicionarReserva(reserva);
        refeicaoEscolhida.setQuantDisponivel(refeicaoEscolhida.getQuantDisponivel() - 1);
        System.out.println("Reserva efetuada com sucesso! Estado: Pendente");
    }

    static void efetuarPagamento(Estudante estudante) {
        ArrayList<Reserva> pendentes = new ArrayList<>();
        for (Reserva r : estudante.getReservas()) {
            if (r.getEstado() == EstadoReserva.Pendente) {
                pendentes.add(r);
            }
        }
        if (pendentes.isEmpty()) {
            System.out.println("Não tem reservas pendentes.");
            return;
        }
        System.out.println("Reservas pendentes:");
        for (int i = 0; i < pendentes.size(); i++) {
            System.out.println((i + 1) + ". " + pendentes.get(i));
        }
        System.out.print("Escolhe a reserva: ");
        int escolha = sc.nextInt() - 1;
        sc.nextLine();

        Reserva reserva = pendentes.get(escolha);
        System.out.println("Valor a pagar: " + reserva.getValor() + "€");
        System.out.println("Método de pagamento: 1.Multibanco  2.MBWay");
        int metodo = sc.nextInt();
        sc.nextLine();

        if (metodo == 1 || metodo == 2) {
            reserva.confirmar();
            reserva.setIdPagamento("PAG" + new Date().getTime());
            reserva.setDataPagamento(new Date());
        } else {
            System.out.println("Método inválido!");
        }
    }

    static void cancelarReserva(Estudante estudante) {
        ArrayList<Reserva> cancelaveis = new ArrayList<>();
        for (Reserva r : estudante.getReservas()) {
            if (r.getEstado() == EstadoReserva.Pendente || r.getEstado() == EstadoReserva.Paga) {
                cancelaveis.add(r);
            }
        }
        if (cancelaveis.isEmpty()) {
            System.out.println("Não tem reservas para cancelar.");
            return;
        }
        System.out.println("Reservas disponíveis para cancelar:");
        for (int i = 0; i < cancelaveis.size(); i++) {
            System.out.println((i + 1) + ". " + cancelaveis.get(i));
        }
        System.out.print("Escolhe a reserva: ");
        int escolha = sc.nextInt() - 1;
        sc.nextLine();

        Reserva reserva = cancelaveis.get(escolha);
        long diff = reserva.getDataReserva().getTime() - new Date().getTime();
        long diasRestantes = diff / (1000 * 60 * 60 * 24);

        if (diasRestantes < 1) {
            System.out.println("Não é possível cancelar. O prazo de cancelamento já passou.");
            return;
        }
        System.out.println("Tem a certeza? 1.Sim  2.Não");
        int confirmacao = sc.nextInt();
        sc.nextLine();
        if (confirmacao == 1) {
            reserva.cancelar();
        }
    }

    static void verDetalhesReserva(Estudante estudante) {
        if (estudante.getReservas().isEmpty()) {
            System.out.println("Não tem reservas.");
            return;
        }
        System.out.println("Reservas ativas:");
        for (int i = 0; i < estudante.getReservas().size(); i++) {
            System.out.println((i + 1) + ". " + estudante.getReservas().get(i));
        }
        System.out.print("Escolhe a reserva: ");
        int escolha = sc.nextInt() - 1;
        sc.nextLine();
        System.out.println(estudante.getReservas().get(escolha));
    }

    static void verHistoricoReservas(Estudante estudante) {
        if (estudante.getReservas().isEmpty()) {
            System.out.println("Não tem reservas.");
            return;
        }
        ArrayList<Reserva> historico = new ArrayList<>(estudante.getReservas());
        historico.sort((r1, r2) -> r2.getDataReserva().compareTo(r1.getDataReserva()));
        System.out.println("Histórico de reservas:");
        for (Reserva r : historico) {
            System.out.println(r);
        }
    }

    // ==================== FUNCIONALIDADES FUNCIONÁRIO ====================
    static void marcarReservaPronta() {
        System.out.print("ID da reserva: ");
        String id = sc.nextLine();
        for (Utilizador u : gestor.getUtilizadores()) {
            if (u instanceof Estudante) {
                for (Reserva r : ((Estudante) u).getReservas()) {
                    if (r.getIdReserva().equals(id)) {
                        r.marcarPronta();
                        return;
                    }
                }
            }
        }
        System.out.println("Reserva não encontrada.");
    }

    static void confirmarLevantamento() {
        System.out.print("ID da reserva: ");
        String id = sc.nextLine();
        for (Utilizador u : gestor.getUtilizadores()) {
            if (u instanceof Estudante) {
                for (Reserva r : ((Estudante) u).getReservas()) {
                    if (r.getIdReserva().equals(id)) {
                        r.marcarLevantada();
                        return;
                    }
                }
            }
        }
        System.out.println("Reserva não encontrada.");
    }

    // ==================== FUNCIONALIDADES ADMINISTRADOR ====================
    static void criarEmenta() {
        System.out.print("Semana (ex: 08/06/2026 - 12/06/2026): ");
        String semana = sc.nextLine();
        Ementa ementa = new Ementa(semana);

        String[] dias = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"};
        for (String dia : dias) {
            System.out.println("\nDia: " + dia);
            ArrayList<Refeicao> refeicoes = new ArrayList<>();
            CategoriaProduto[] categorias = {CategoriaProduto.Carne, CategoriaProduto.Peixe, CategoriaProduto.Vegetariano, CategoriaProduto.Sopa, CategoriaProduto.Sobremesa};

            for (CategoriaProduto cat : categorias) {
                System.out.println("Refeição - " + cat);
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Preço: ");
                double preco = sc.nextDouble();
                sc.nextLine();
                System.out.print("Quantidade disponível: ");
                int quant = sc.nextInt();
                sc.nextLine();

                Produto produto = new Produto("P" + cat, nome, cat, "");
                Refeicao refeicao = new Refeicao("R" + cat + dia, preco, quant, produto);
                refeicoes.add(refeicao);
            }

            DiaEmenta diaEmenta = new DiaEmenta(new Date(), refeicoes);
            ementa.adicionarDia(diaEmenta);
        }
        gestor.adicionarEmenta(ementa);
        ementa.guardar();
    }

    static void editarEmenta() {
        if (gestor.getEmentas().isEmpty()) {
            System.out.println("Não há ementas para editar.");
            return;
        }
        Ementa ementa = gestor.getEmentas().get(gestor.getEmentas().size() - 1);
        System.out.println("Escolhe o dia (1-5):");
        for (int i = 0; i < ementa.getDiasUteis().size(); i++) {
            System.out.println((i + 1) + ". " + ementa.getDiasUteis().get(i).getData());
        }
        int dia = sc.nextInt() - 1;
        sc.nextLine();

        DiaEmenta diaEmenta = ementa.filtrarPorDia(dia);
        System.out.println("Escolhe a refeição:");
        for (int i = 0; i < diaEmenta.getRefeicoes().size(); i++) {
            System.out.println((i + 1) + ". " + diaEmenta.getRefeicoes().get(i));
        }
        int escolha = sc.nextInt() - 1;
        sc.nextLine();

        Refeicao refeicao = diaEmenta.getRefeicoes().get(escolha);
        System.out.print("Novo preço: ");
        double preco = sc.nextDouble();
        sc.nextLine();
        System.out.print("Nova quantidade disponível: ");
        int quant = sc.nextInt();
        sc.nextLine();

        refeicao.setPreco(preco);
        refeicao.setQuantDisponivel(quant);
        System.out.println("Ementa atualizada com sucesso!");
    }

    static void enviarAviso() {
        System.out.print("Mensagem: ");
        String mensagem = sc.nextLine();
        System.out.println("Destinatários: 1.Todos  2.Só estudantes  3.Só funcionários");
        int opcao = sc.nextInt();
        sc.nextLine();

        String destinatario;
        switch (opcao) {
            case 1: destinatario = "todos"; break;
            case 2: destinatario = "estudantes"; break;
            case 3: destinatario = "funcionarios"; break;
            default:
                System.out.println("Opção inválida!");
                return;
        }

        Aviso aviso = new Aviso("A" + gestor.getAvisos().size(), mensagem, destinatario);
        aviso.enviar();
        gestor.adicionarAviso(aviso);
    }
}