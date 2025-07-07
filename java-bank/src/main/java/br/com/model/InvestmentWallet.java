package br.com.model;

import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.model.BankService.INVESTMENT;

@Getter
public class InvestmentWallet extends Wallet {

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "investmento");
    }

    public void updateAmount(final long percent) {
        long amount = getFunds() * percent / 100;
        MoneyAudit history = new MoneyAudit(UUID.randomUUID(), getService(), "rendimentos", OffsetDateTime.now());
        List<Money> money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(money);
    }
    @Override
    public String toString() {
        return String.format(
                "Carteira de Investimento {\n" +
                        "  • Investimento: ID=%d | Taxa=%d%% | Inicial=R$ %,d\n" +
                        "  • Conta: Pix=%s | Saldo=R$ %,d\n" +
                        "  • Saldo Investido: R$ %,d\n" +
                        "}",
                investment.id(),
                investment.tax(),
                investment.initialFunds(),
                String.join(", ", account.getPix()),
                account.getFunds(),
                getFunds()
        );
    }
}
