package br.com.repository;

import static br.com.repository.CommonsRepository.checkFundsForTransaction;

import br.com.exception.AccountNotFoundException;
import br.com.exception.PixUserException;
import br.com.model.AccountWallet;
import br.com.model.MoneyAudit;

import java.util.*;

public class AccountRepository {
    private final List<AccountWallet> accounts = new ArrayList<>();

    public AccountWallet create(final List<String> pix, final long initialFunds) {
        for (String p : pix) {
            boolean pixEmUso = accounts.stream()
                    .anyMatch(a -> a.getPix().contains(p));
            if (pixEmUso) {
                throw new PixUserException("O pix " + p + " já está em uso");
            }
        }

        AccountWallet newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) {
        AccountWallet target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito");
    }

    public long withdraw(final String pix, final long amount) {
        AccountWallet source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        AccountWallet source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        AccountWallet target = findByPix(targetPix);
        String message = "Pix enviado de " + sourcePix + " para " + target;
        target.addMoney(source.reduceMoney(amount), target.getService(), message);
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream().filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("A conta com a chave pix" + pix + "não existe ou foi encerrada"));
    }

    public List<AccountWallet> list() {
        return this.accounts;
    }


    public List<MoneyAudit> getHistory(final String pix) {
        AccountWallet account = findByPix(pix);
        List<MoneyAudit> allAudits = new ArrayList<>(account.getFinancialTransactions());

        // Usando LinkedHashMap para preservar ordem e eliminar duplicatas por transactionId
        Map<UUID, MoneyAudit> uniqueAudits = new LinkedHashMap<>();
        for (MoneyAudit audit : allAudits) {
            uniqueAudits.putIfAbsent(audit.transactionId(), audit);
        }

        List<MoneyAudit> dedupedList = new ArrayList<>(uniqueAudits.values());

        // Ordena pelo createdAt (mais antigo para o mais recente)
        dedupedList.sort(Comparator.comparing(MoneyAudit::createdAt));

        return dedupedList;
    }
}