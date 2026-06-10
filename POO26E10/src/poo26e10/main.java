package poo26e10;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class main {
//exemplo de Admin
    static Scanner sc = new Scanner(System.in);
    static Gestor gestor = new Gestor("Admin", "00000", "admin@cantina.pt", "admin123");

    public static void main(String[] args) {
        int opcao;
        do {
            // menu principal
            System.out.println("\nBEM-VINDO À CANTINA");
            System.out.println("1. Registar");
            System.out.println("2. Login");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1: registar(); break;
                case 2: login(); break;
                case 0: System.out.println("Até logo!"); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // lê um inteiro, repete se o input não for válido
    static int lerOpcao() {
        while (!sc.hasNextInt()) {
            System.out.println("Entrada inválida! Introduz um número:");
            sc.next();
        }
        int opcao = sc.nextInt();
        sc.nextLine();
        return opcao;
    }

    // lê um double, repete se o input não for válido
    static double lerDouble() {
        while (!sc.hasNextDouble()) {
            System.out.println("Entrada inválida! Introduz um número:");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }

    // garante que o número introduzido está entre min e max
    static int lerEscolha(int min, int max) {
        int escolha = lerOpcao();
        while (escolha < min || escolha > max) {
            System.out.println("Opção inválida! Introduz um número entre " + min + " e " + max + ":");
            escolha = lerOpcao();
        }
        return escolha;
    }

    // lê um nome e rejeita se tiver números
    static String lerNome() {
        String nome = sc.nextLine();
        while (nome.matches(".*\\d.*")) {
            System.out.println("Nome inválido! O nome não pode conter números:");
            nome = sc.nextLine();
        }
        return nome;
    }

    // lê um número de identificação e rejeita se tiver letras
    static String lerNumId() {
        String numId = sc.nextLine();
        while (!numId.matches("\\d+")) {
            System.out.println("Número de identificação inválido! Deve conter apenas números:");
            numId = sc.nextLine();
        }
        return numId;
    }

    // registo 
    static void registar() {
        System.out.print("Nome: ");
        String nome = lerNome();
        System.out.print("Número de identificação: ");
        String numId = lerNumId();

        // verifica se o número de identificação já está registado
        for (Utilizador u : gestor.getUtilizadores()) {
            if (u.getNumId().equals(numId)) {
                System.out.println("Número de identificação já registado!");
                return;
            }
        }
        // valida o formato do email
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
        int tipo = lerEscolha(1, 3);

        // cria o utilizador consoante o tipo escolhido
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
        }
    }

    // login
    static void login() {
        System.out.print("Número de identificação: ");
        String numId = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        // verifica primeiro se é o admin pre definido
        if (gestor.login(numId, password)) {
            System.out.println("Bem-vindo, " + gestor.getNome() + "!");
            menuAdministrador();
            return;
        }

        // procura o utilizador na lista
        for (Utilizador u : gestor.getUtilizadores()) {
            if (u.getNumId().equals(numId)) {
                if (u.isBloqueado()) {
                    System.out.println("Conta bloqueada. Tente mais tarde.");
                    return;
                }
                if (u.login(numId, password)) {
                    System.out.println("Bem-vindo, " + u.getNome() + "!");
                    // redireciona para o menu correto consoante o tipo
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

    // menu estudante
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
            opcao = lerOpcao();

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

    // menu do funcionario
    static void menuFuncionario(Utilizador funcionario) {
        int opcao;
        do {
            System.out.println("\nMENU FUNCIONÁRIO");
            System.out.println("1. Marcar reserva como pronta");
            System.out.println("2. Confirmar levantamento");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1: marcarReservaPronta(); break;
                case 2: confirmarLevantamento(); break;
                case 0: System.out.println("A sair..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // menu admin
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
            opcao = lerOpcao();

            switch (opcao) {
                case 1: criarEmenta(); break;
                case 2: editarEmenta(); break;
                case 3: gestor.gerirUtilizadores(); break;
                case 4: consultarResumoReceitas(); break;
                case 5: enviarAviso(); break;
                case 0: System.out.println("A sair..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // FUNCIONALIDADES DO ESTUDANTE
    static void consultarEmenta() {
        if (gestor.getEmentas().isEmpty()) {
            System.out.println("Não há ementas disponíveis.");
            return;
        }
        System.out.println("Filtrar por dia? 1.Sim  2.Não");
        int opcao = lerEscolha(1, 2);

        // mostra a ementa mais recente
        Ementa ementa = gestor.getEmentas().get(gestor.getEmentas().size() - 1);

        if (opcao == 1) {
            String[] nomesDias = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"};
            System.out.println("Escolhe o dia (1-5):");
            for (int i = 0; i < ementa.getDiasUteis().size(); i++) {
                System.out.println((i + 1) + ". " + nomesDias[i]);
            }
            int dia = lerEscolha(1, ementa.getDiasUteis().size()) - 1;
            DiaEmenta diaEmenta = ementa.filtrarPorDia(dia);
            System.out.println("\nRefeições disponíveis:");
            for (Refeicao r : diaEmenta.getRefeicoes()) {
                System.out.println(r);
            }
        } else {
            // mostra todos os dias da ementa
            String[] nomesDias = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"};
            for (int i = 0; i < ementa.getDiasUteis().size(); i++) {
                System.out.println("\nDia: " + nomesDias[i]);
                for (Refeicao r : ementa.getDiasUteis().get(i).getRefeicoes()) {
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
        String[] nomesDias = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"};
        System.out.println("Escolhe o dia (1-5):");
        for (int i = 0; i < ementa.getDiasUteis().size(); i++) {
            System.out.println((i + 1) + ". " + nomesDias[i]);
        }
        int dia = lerEscolha(1, ementa.getDiasUteis().size()) - 1;

        DiaEmenta diaEmenta = ementa.filtrarPorDia(dia);
        // mostra só as refeições não esgotadas
        System.out.println("Escolhe a refeição:");
        for (int i = 0; i < diaEmenta.getRefeicoes().size(); i++) {
            Refeicao r = diaEmenta.getRefeicoes().get(i);
            if (!r.esgotada()) {
                System.out.println((i + 1) + ". " + r);
            }
        }
        int escolha = lerEscolha(1, diaEmenta.getRefeicoes().size()) - 1;

        Refeicao refeicaoEscolhida = diaEmenta.getRefeicoes().get(escolha);
        if (refeicaoEscolhida.esgotada()) {
            System.out.println("Refeição esgotada!");
            return;
        }

        double valorTotal = refeicaoEscolhida.getPreco();

        // procura a sopa do dia
        Refeicao sopa = null;
        for (Refeicao r : diaEmenta.getRefeicoes()) {
            if (r.getProduto().getCategoria() == CategoriaProduto.Sopa) {
                sopa = r;
                break;
            }
        }
        // pergunta se quer adicionar sopa, exceto se já escolheu sopa
        if (sopa != null && !sopa.esgotada() && refeicaoEscolhida.getProduto().getCategoria() != CategoriaProduto.Sopa) {
            System.out.println("Quer adicionar sopa? (" + sopa.getProduto().getNome() + " - " + sopa.getPreco() + "€) 1.Sim  2.Não");
            int opcaoSopa = lerEscolha(1, 2);
            if (opcaoSopa == 1) {
                valorTotal += sopa.getPreco();
                sopa.setQuantDisponivel(sopa.getQuantDisponivel() - 1);
            }
        }

        // procura a sobremesa do dia
        Refeicao sobremesa = null;
        for (Refeicao r : diaEmenta.getRefeicoes()) {
            if (r.getProduto().getCategoria() == CategoriaProduto.Sobremesa) {
                sobremesa = r;
                break;
            }
        }
        // pergunta se quer adicionar sobremesa, exceto se já escolheu sobremesa
        if (sobremesa != null && !sobremesa.esgotada() && refeicaoEscolhida.getProduto().getCategoria() != CategoriaProduto.Sobremesa) {
            System.out.println("Quer adicionar sobremesa? (" + sobremesa.getProduto().getNome() + " - " + sobremesa.getPreco() + "€) 1.Sim  2.Não");
            int opcaoSobremesa = lerEscolha(1, 2);
            if (opcaoSobremesa == 1) {
                valorTotal += sobremesa.getPreco();
                sobremesa.setQuantDisponivel(sobremesa.getQuantDisponivel() - 1);
            }
        }

        // cria a reserva e adiciona ao estudante
        String idReserva = "R" + (estudante.getReservas().size() + 1);
        Reserva reserva = new Reserva(idReserva, new Date(), valorTotal, refeicaoEscolhida);
        estudante.adicionarReserva(reserva);
        refeicaoEscolhida.setQuantDisponivel(refeicaoEscolhida.getQuantDisponivel() - 1);
        System.out.println("Reserva efetuada com sucesso! Estado: Pendente");
        System.out.println("Valor total: " + valorTotal + "€");
    }

    static void efetuarPagamento(Estudante estudante) {
        // filtra só as reservas pendentes
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
        int escolha = lerEscolha(1, pendentes.size()) - 1;

        Reserva reserva = pendentes.get(escolha);
        System.out.println("Valor a pagar: " + reserva.getValor() + "€");
        System.out.println("Pagamento via MBWay");
        System.out.print("Introduz o teu número de telemóvel: ");
        String telemovel = sc.nextLine();
        
        
        // valida que o número tem 9 dígitos
        while (telemovel.length() != 9 || !telemovel.matches("\\d+")) {
            System.out.println("Número de telemóvel inválido! Deve ter 9 dígitos:");
            telemovel = sc.nextLine();
        }
        reserva.confirmar();
        reserva.setIdPagamento("PAG" + new Date().getTime());
        reserva.setDataPagamento(new Date());
        System.out.println("Pagamento efetuado com sucesso!");
    }

    static void cancelarReserva(Estudante estudante) {
        // filtra reservas que ainda podem ser canceladas
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
        int escolha = lerEscolha(1, cancelaveis.size()) - 1;

        Reserva reserva = cancelaveis.get(escolha);
  
        
        System.out.println("Tem a certeza? 1.Sim  2.Não");
        int confirmacao = lerEscolha(1, 2);
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
        int escolha = lerEscolha(1, estudante.getReservas().size()) - 1;
        System.out.println(estudante.getReservas().get(escolha));
    }

    static void verHistoricoReservas(Estudante estudante) {
        if (estudante.getReservas().isEmpty()) {
            System.out.println("Não tem reservas.");
            return;
        }
        // ordena a reserva mais recente para a mais antiga
        ArrayList<Reserva> historico = new ArrayList<>(estudante.getReservas());
        historico.sort((r1, r2) -> r2.getDataReserva().compareTo(r1.getDataReserva()));
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Histórico de reservas:");
        for (Reserva r : historico) {
            System.out.println("Reserva: " + r.getIdReserva() + "\nRefeição: " + r.getRefeicao().getProduto().getNome() + "\nEstado: " + r.getEstado() + "\nValor: " + r.getValor() + "€" + "\nData: " + sdf.format(r.getDataReserva()));
        }
    }

    //FUNCIONALIDADES FUNCIONÁRIO 
    static void marcarReservaPronta() {
        System.out.print("ID da reserva: ");
        String id = sc.nextLine();
        // percorre todos os estudantes à procura da reserva para marcar como pronta
        for (Utilizador u : gestor.getUtilizadores()) {
            if (u instanceof Estudante) {
                for (Reserva r : ((Estudante) u).getReservas()) {
                    if (r.getIdReserva().equalsIgnoreCase(id)) {
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
        // percorre todos os estudantes à procura da reserva para marcar como levantada 
        for (Utilizador u : gestor.getUtilizadores()) {
            if (u instanceof Estudante) {
                for (Reserva r : ((Estudante) u).getReservas()) {
                    if (r.getIdReserva().equalsIgnoreCase(id)) {
                        r.marcarLevantada();
                        return;
                    }
                }
            }
        }
        System.out.println("Reserva não encontrada.");
    }

    //  FUNCIONALIDADES ADMINISTRADOR 
    static void criarEmenta() {
        System.out.print("Semana (ex: 08/06/2026 - 14/06/2026): ");
        String semana = sc.nextLine();
        Ementa ementa = new Ementa(semana);

        // extrai a data de início da string introduzida
        String dataInicioStr = semana.split(" - ")[0].trim();
        String[] partes = dataInicioStr.split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]) - 1;
        int ano = Integer.parseInt(partes[2]);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(ano, mes, dia, 0, 0, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);

        String[] dias = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"};
        for (String nomeDia : dias) {
            System.out.println("\nDia: " + nomeDia + " (" + cal.get(java.util.Calendar.DAY_OF_MONTH) + "/" + (cal.get(java.util.Calendar.MONTH) + 1) + "/" + cal.get(java.util.Calendar.YEAR) + ")");
            ArrayList<Refeicao> refeicoes = new ArrayList<>();
            CategoriaProduto[] categorias = {CategoriaProduto.Carne, CategoriaProduto.Peixe, CategoriaProduto.Vegetariano, CategoriaProduto.Sopa, CategoriaProduto.Sobremesa};

            for (CategoriaProduto cat : categorias) {
                // sopa e sobremesa não têm o prefixo "Refeição"
                if (cat != CategoriaProduto.Sopa && cat != CategoriaProduto.Sobremesa) {
                    System.out.println("Refeição - " + cat);
                } else {
                    System.out.println(cat);
                }
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Preço: ");
                double preco = lerDouble();
                System.out.print("Quantidade disponível: ");
                int quant = lerOpcao();

                Produto produto = new Produto("P" + cat, nome, cat, "");
                Refeicao refeicao = new Refeicao("R" + cat + nomeDia, preco, quant, produto);
                refeicoes.add(refeicao);
            }

            DiaEmenta diaEmenta = new DiaEmenta(cal.getTime(), refeicoes);
            ementa.adicionarDia(diaEmenta);
            // avança para o dia seguinte
            cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
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
        String[] nomesDias = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"};
        System.out.println("Escolhe o dia (1-5) ou prime 0 para voltar:");
        for (int i = 0; i < ementa.getDiasUteis().size(); i++) {
            System.out.println((i + 1) + ". " + nomesDias[i]);
        }
        int dia = lerOpcao();
        if (dia == 0) return;
        while (dia < 1 || dia > ementa.getDiasUteis().size()) {
            System.out.println("Opção inválida! Introduz um número entre 1 e " + ementa.getDiasUteis().size() + " ou 0 para voltar:");
            dia = lerOpcao();
            if (dia == 0) return;
        }
        dia = dia - 1;

        DiaEmenta diaEmenta = ementa.filtrarPorDia(dia);
        System.out.println("Escolhe a refeição ou prime 0 para voltar:");
        for (int i = 0; i < diaEmenta.getRefeicoes().size(); i++) {
            System.out.println((i + 1) + ". " + diaEmenta.getRefeicoes().get(i));
        }
        int escolha = lerOpcao();
        if (escolha == 0) return;
        while (escolha < 1 || escolha > diaEmenta.getRefeicoes().size()) {
            System.out.println("Opção inválida! Introduz um número entre 1 e " + diaEmenta.getRefeicoes().size() + " ou 0 para voltar:");
            escolha = lerOpcao();
            if (escolha == 0) return;
        }
        escolha = escolha - 1;

        // se o utilizador não escrever nada, mantém o valor atual
        Refeicao refeicao = diaEmenta.getRefeicoes().get(escolha);
        System.out.print("Novo nome (Enter para manter '" + refeicao.getProduto().getNome() + "'): ");
        String novoNome = sc.nextLine();
        if (!novoNome.isEmpty()) {
            refeicao.getProduto().setNome(novoNome);
        }
        System.out.print("Novo preço (Enter para manter " + refeicao.getPreco() + "€): ");
        String novoPrecoStr = sc.nextLine();
        if (!novoPrecoStr.isEmpty()) {
            refeicao.setPreco(Double.parseDouble(novoPrecoStr));
        }
        System.out.print("Nova quantidade disponível (Enter para manter " + refeicao.getQuantDisponivel() + "): ");
        String novaQuantStr = sc.nextLine();
        if (!novaQuantStr.isEmpty()) {
            refeicao.setQuantDisponivel(Integer.parseInt(novaQuantStr));
        }
        System.out.println("Ementa atualizada com sucesso!");
    }
//enviar aviso
    static void enviarAviso() {
        System.out.print("Mensagem: ");
        String mensagem = sc.nextLine();
        System.out.println("Destinatários: 1.Todos  2.Só estudantes  3.Só funcionários");
        int opcao = lerEscolha(1, 3);

        String destinatario;
        switch (opcao) {
            case 1: destinatario = "todos"; break;
            case 2: destinatario = "estudantes"; break;
            case 3: destinatario = "funcionarios"; break;
            default: destinatario = "todos";
        }

        Aviso aviso = new Aviso("A" + gestor.getAvisos().size(), mensagem, destinatario);
        aviso.enviar();
        gestor.adicionarAviso(aviso);
    }
//resumo financeiro 
    static void consultarResumoReceitas() {
        int opcao;
        do {
            System.out.println("\nRESUMO FINANCEIRO");
            System.out.println("1. Por período");
            System.out.println("2. Por categoria");
            System.out.println("3. Dia com maior volume de reservas");
            System.out.println("4. Categorias com mais refeições esgotadas");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1: resumoPorPeriodo(); break;
                case 2: resumoPorCategoria(); break;
                case 3: diaMaiorVolume(); break;
                case 4: categoriasMaisEsgotadas(); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void resumoPorPeriodo() {
        System.out.println("Período: 1.Diário  2.Semanal  3.Mensal");
        int opcao = lerEscolha(1, 3);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        Date hoje = new Date();

        double total = 0;
        int numRefeicoes = 0;

        for (Utilizador u : gestor.getUtilizadores()) {
            if (u instanceof Estudante) {
                for (Reserva r : ((Estudante) u).getReservas()) {
                    if (r.getEstado() != EstadoReserva.Cancelada) {
                        cal.setTime(r.getDataReserva());
                        java.util.Calendar calHoje = java.util.Calendar.getInstance();
                        calHoje.setTime(hoje);

                        boolean incluir = false;
                        // verifica se a reserva está dentro do período escolhido
                        if (opcao == 1) {
                            incluir = cal.get(java.util.Calendar.DAY_OF_YEAR) == calHoje.get(java.util.Calendar.DAY_OF_YEAR)
                                    && cal.get(java.util.Calendar.YEAR) == calHoje.get(java.util.Calendar.YEAR);
                        } else if (opcao == 2) {
                            incluir = cal.get(java.util.Calendar.WEEK_OF_YEAR) == calHoje.get(java.util.Calendar.WEEK_OF_YEAR)
                                    && cal.get(java.util.Calendar.YEAR) == calHoje.get(java.util.Calendar.YEAR);
                        } else if (opcao == 3) {
                            incluir = cal.get(java.util.Calendar.MONTH) == calHoje.get(java.util.Calendar.MONTH)
                                    && cal.get(java.util.Calendar.YEAR) == calHoje.get(java.util.Calendar.YEAR);
                        }

                        if (incluir) {
                            total += r.getValor();
                            numRefeicoes++;
                        }
                    }
                }
            }
        }
        System.out.println("Refeições servidas: " + numRefeicoes);
        System.out.println("Total faturado: " + total + "€");
    }

    static void resumoPorCategoria() {
        // conta reservas e receitas por cada categoria
        for (CategoriaProduto cat : CategoriaProduto.values()) {
            int count = 0;
            double total = 0;
            for (Utilizador u : gestor.getUtilizadores()) {
                if (u instanceof Estudante) {
                    for (Reserva r : ((Estudante) u).getReservas()) {
                        if (r.getEstado() != EstadoReserva.Cancelada
                                && r.getRefeicao().getProduto().getCategoria() == cat) {
                            count++;
                            total += r.getValor();
                        }
                    }
                }
            }
            System.out.println(cat + ": " + count + " refeições | " + total + "€");
        }
    }

    static void diaMaiorVolume() {
        String[] nomesDias = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
        int[] contagem = new int[7];
        double[] receitas = new double[7];

        // conta reservas e receitas por dia da semana
        for (Utilizador u : gestor.getUtilizadores()) {
            if (u instanceof Estudante) {
                for (Reserva r : ((Estudante) u).getReservas()) {
                    if (r.getEstado() != EstadoReserva.Cancelada) {
                        java.util.Calendar cal = java.util.Calendar.getInstance();
                        cal.setTime(r.getDataReserva());
                        int diaSemana = cal.get(java.util.Calendar.DAY_OF_WEEK) - 2;
                        if (diaSemana < 0) diaSemana = 6;
                        contagem[diaSemana]++;
                        receitas[diaSemana] += r.getValor();
                    }
                }
            }
        }

        // encontra o dia com mais reservas
        int maiorDia = 0;
        for (int i = 1; i < 7; i++) {
            if (contagem[i] > contagem[maiorDia]) maiorDia = i;
        }
        System.out.println("Dia com maior volume: " + nomesDias[maiorDia]);
        System.out.println("Reservas: " + contagem[maiorDia] + " | Receita: " + receitas[maiorDia] + "€");
    }

    static void categoriasMaisEsgotadas() {
        boolean encontrou = false;
        // percorre todas as ementas, dias e refeições à procura de esgotadas
        for (Ementa ementa : gestor.getEmentas()) {
            for (DiaEmenta dia : ementa.getDiasUteis()) {
                for (Refeicao r : dia.getRefeicoes()) {
                    // se a quantidade for 0, está esgotada
                    if (r.esgotada()) {
                        System.out.println(r.getProduto().getCategoria() + " - " + r.getProduto().getNome() + ": esgotada");
                        encontrou = true;
                    }
                }
            }
        }
        // se não encontrou nenhuma esgotada, mostra mensagem
        if (!encontrou) {
            System.out.println("Ainda nenhuma refeição foi esgotada!");
        }
    }
}