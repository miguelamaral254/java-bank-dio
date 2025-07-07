package br.com.repository;

import br.com.exception.NotFUndsEnoughException;
import br.com.model.Money;
import br.com.model.MoneyAudit;
import br.com.model.Wallet;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.model.BankService.ACCOUNT;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {
    public static void checkFundsForTransaction(final Wallet source, final long amount) {
        if (source.getFunds() < amount) {
            throw new NotFUndsEnoughException("Sua conta não tem dinheiro o suficiente para realizar a transação");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds,final String description) {
        MoneyAudit history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(()-> new Money(history)).limit(funds).toList();
    }
}
