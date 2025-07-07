package br.com.model;

import lombok.Getter;

import java.util.List;

import static br.com.model.BankService.ACCOUNT;

@Getter
public class AccountWallet extends Wallet {

    private final List<String> pix;

    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "valor de criação da conta");
    }

    // Método addMoney mantido (igual ao original)
    public void addMoney(final long amount, final String description) {
        List<Money> money = generateMoney(amount, description);
        this.money.addAll(money);
    }

    // toString() modificado para evitar a repetição infinita
    @Override
    public String toString() {
        return "AccountWallet{pix=" + pix + ", saldo=" + getFunds() + "}";
    }
}