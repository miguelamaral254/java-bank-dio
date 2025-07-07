package br.com.model;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record MoneyAudit(
        UUID transactionId,
        BankService targetService,
        String description,
        OffsetDateTime createdAt
) {
    public String format() {
        return String.format(
                """
                Data: %s
                ID da Transação: %s
                Descrição: %s
                ------------------------------
                """,
                createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                transactionId,
                description
        );
    }
}