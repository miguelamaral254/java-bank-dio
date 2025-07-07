package br.com;

import br.com.exception.AccountNotFoundException;
import br.com.exception.NotFUndsEnoughException;
import br.com.exception.PixUserException;
import br.com.model.AccountWallet;
import br.com.model.Investment;
import br.com.model.InvestmentWallet;
import br.com.repository.AccountRepository;
import br.com.repository.InvestmentRepository;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Olá, seja bem vindo ao DIO Bank");
        while (true) {
            System.out.println("Selecionea operação desejada:");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Fazer um investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Transferencia entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar investimentos");
            System.out.println("11 - Listar carteiras de investimento");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Histório de conta");
            System.out.println("14 - Sair");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    createInvestment();
                    break;
                case 3:
                    createWalletInvestment();
                    break;
                case 4:
                    deposit();
                    break;
                case 5:
                    withdraw();
                    break;
                case 6:
                    transferToAccount();
                    break;
                case 7:
                    initInvestment();
                    break;
                case 8:
                    rescueInvestment();
                    break;
                case 9:
                    accountRepository.list().forEach(System.out::println);
                    break;
                case 10:
                    investmentRepository.list().forEach(System.out::println);
                    break;
                case 11:
                    investmentRepository.listWallets().forEach(System.out::println);
                    break;
                case 12:
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos reajustados com sucesso!");
                    break;
                case 13:
                    checkHistory();
                    break;
                case 14:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void createAccount() {
        scanner.nextLine();
        System.out.println("Informe as chaves pix(separadas por ';'):");
        var pix = Arrays.stream(scanner.nextLine().split(";")).toList();

        System.out.println("Informe o valor inicial de depósito:");
        long amount = scanner.nextLong();

        try {
            AccountWallet wallet = accountRepository.create(pix, amount);
            System.out.println("Conta criada " + wallet + " com sucesso!");
        } catch (PixUserException e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
        }
    }

    private static void createInvestment() {
        scanner.nextLine();
        System.out.println("Informe a taxa do investimento:");
        int tax = scanner.nextInt();
        System.out.println("Informe o valor inicial de depósito:");
        long initialFunds = scanner.nextLong();

        try {
            Investment investment = investmentRepository.create(tax, initialFunds);
            System.out.println("Investimento " + investment + " criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro inesperado ao criar investimento.");
            e.printStackTrace();
        }
    }
    private static void withdraw() {
        scanner.nextLine();
        System.out.println("Informe a chave pix da conta para saque: ");
        String pix = scanner.nextLine();

        try {
            AccountWallet account = accountRepository.findByPix(pix);
            if (account == null) {
                throw new AccountNotFoundException("Conta não encontrada!");
            }
            System.out.println("Informe o valor que será sacado: ");
            long amount = scanner.nextLong();

            if (amount > account.getFunds()) {
                throw new NotFUndsEnoughException("A conta não possui saldo suficiente!");
            }

            accountRepository.withdraw(pix, amount);
            System.out.println("Saque realizado com sucesso!");
        } catch (AccountNotFoundException | NotFUndsEnoughException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao realizar saque.");
            e.printStackTrace();
        }
    }

    private static void deposit() {
        scanner.nextLine();
        System.out.println("Informe a chave pix: ");
        String pix = scanner.nextLine();

        try {
            System.out.println("Informe o valor que será depositado: ");
            long amount = scanner.nextLong();
            accountRepository.deposit(pix, amount);
            System.out.println("Depósito realizado com sucesso!");
        } catch (AccountNotFoundException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao tentar depositar.");
            e.printStackTrace();
        }
    }

    private static void transferToAccount() {
        try {
            scanner.nextLine(); // limpa buffer
            System.out.println("Informe a chaves pix da conta de origem: ");
            String sourcePix = scanner.nextLine();
            AccountWallet sourceAccount = accountRepository.findByPix(sourcePix);

            System.out.println("Informe a chaves pix da conta de destino: ");
            String targetPix = scanner.nextLine();
            AccountWallet targetAccount = accountRepository.findByPix(targetPix);

            System.out.println("Informe o valor que será depositado: ");
            long amount = scanner.nextLong();

            accountRepository.transferMoney(sourcePix, targetPix, amount);
            System.out.println("Transferência realizada com sucesso!");

        } catch (br.com.exception.NotFUndsEnoughException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (br.com.exception.AccountNotFoundException e) {
            System.out.println("Conta não encontrada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void createWalletInvestment() {
        scanner.nextLine();
        System.out.println("Informe a chave pix da conta: ");
        String pix = scanner.nextLine();

        try {
            AccountWallet account = accountRepository.findByPix(pix);
            if (account == null) {
                throw new AccountNotFoundException("Conta não encontrada!");
            }

            System.out.println("Informe o identificador do investimento: ");
            int investmentId = scanner.nextInt();

            InvestmentWallet investmentWallet = investmentRepository.initInvestment(account, investmentId);
            System.out.println(investmentWallet);
        } catch (AccountNotFoundException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao criar carteira de investimento.");
            e.printStackTrace();
        }
    }

    private static void initInvestment() {
        scanner.nextLine();
        System.out.println("Informe as chaves pix da conta para investimento: ");
        String pix = scanner.nextLine();

        try {
            AccountWallet account = accountRepository.findByPix(pix);
            if (account == null) {
                throw new AccountNotFoundException("Conta não encontrada!");
            }

            System.out.println("Informe o valor que será investido: ");
            long amount = scanner.nextLong();

            if (amount > account.getFunds()) {
                throw new NotFUndsEnoughException("Saldo insuficiente para investimento!");
            }

            investmentRepository.deposit(pix, amount);
            System.out.println("Investimento realizado com sucesso!");
        } catch (AccountNotFoundException | NotFUndsEnoughException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao iniciar investimento.");
            e.printStackTrace();
        }
    }

    private static void rescueInvestment() {
        scanner.nextLine();
        System.out.println("Informe a chave pix da conta para resgate do investimento: ");
        String pix = scanner.nextLine();

        try {
            AccountWallet account = accountRepository.findByPix(pix);
            if (account == null) {
                throw new AccountNotFoundException("Conta não encontrada!");
            }

            System.out.println("Informe o valor que será sacado: ");
            long amount = scanner.nextLong();

            if (amount > account.getFunds()) {
                throw new NotFUndsEnoughException("Saldo insuficiente para saque!");
            }

            investmentRepository.withdraw(pix, amount);
            System.out.println("Resgate realizado com sucesso!");
        } catch (AccountNotFoundException | NotFUndsEnoughException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao resgatar investimento.");
            e.printStackTrace();
        }
    }

    private static void checkHistory() {
        scanner.nextLine();
        System.out.println("Informe a chave pix da conta para verificar o extrato: ");
        String pix = scanner.nextLine();

        try {
            var sortedHistory = accountRepository.getHistory(pix);
            if (sortedHistory.isEmpty()) {
                System.out.println("Nenhuma transação encontrada para esta conta.");
            } else {
                sortedHistory.forEach(audit -> System.out.println(audit.format()));            }
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}