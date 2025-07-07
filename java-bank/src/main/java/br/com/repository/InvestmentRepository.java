package br.com.repository;

import br.com.exception.AccountWithInvestmentException;
import br.com.exception.WalletNotFoundException;
import br.com.model.AccountWallet;
import br.com.model.Investment;
import br.com.model.InvestmentWallet;

import java.util.ArrayList;
import java.util.List;

import static br.com.repository.CommonsRepository.checkFundsForTransaction;

public class InvestmentRepository {
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();
    private long nextId = 0;

    public Investment create(final long tax, final long initialFunds) {
        this.nextId++;
        Investment investment = new Investment(this.nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final long id) {
        if (!wallets.isEmpty()) {

            var accountInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();

            if (accountInUse.contains(account)) {
                throw new AccountWithInvestmentException("A conta " + account + " já possui um investimento");

            }
        }

        Investment investment = findById(id);
        checkFundsForTransaction(account, investment.initialFunds());
        InvestmentWallet wallet = new InvestmentWallet(investment, account, investment.initialFunds());
        wallets.add(wallet);
        return wallet;
    }

    public InvestmentWallet deposit(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getService(), "Investmento");
        return wallet;
    }

    public InvestmentWallet withdraw(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);

        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(), "Saque de investimentos");

        if (wallet.getFunds() == 0) {
            wallets.remove(wallet);
        }
        return wallet;
    }


    public void updateAmount() {
        wallets.forEach(w -> w.updateAmount(w.getInvestment().tax()));
    }

    public Investment findById(Long id) {
        return investments.stream()
                .filter(a -> a.id() == id)
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("A carteira não foi encontrada"));
    }

    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("A carteira não foi encontrada"));
    }

    public List<InvestmentWallet> listWallets() {
        return this.wallets;
    }

    public List<Investment> list() {
        return this.investments;
    }


}
