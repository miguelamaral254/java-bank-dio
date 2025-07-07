package br.com.model;

import java.text.NumberFormat;
import java.util.Locale;

public record Investment(
        long id,
        long tax,
        long initialFunds
) {

    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    public String toString() {
        return String.format(
                "Investimento: ID=%d | Taxa=%d%% | Inicial=%s",
                id,
                tax,
                currencyFormatter.format(initialFunds)
        );
    }
}