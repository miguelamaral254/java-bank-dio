# 💳 DIO Bank - Sistema Bancário em Java

Este projeto é parte do desafio prático da DIO (Digital Innovation One), cujo objetivo é consolidar os conhecimentos sobre Programação Orientada a Objetos (POO) com Java. A aplicação simula um sistema bancário básico com suporte a contas digitais, transferências via PIX, investimentos e histórico de transações.

## 🚀 Tecnologias Utilizadas

- Java 17
- POO (Herança, Encapsulamento, Polimorfismo e Abstração)
- Simulação de Repositórios em Memória
- Scanner para interação via console
- Lombok (opcional)
- Git + GitHub

---

## 🧠 Objetivos de Aprendizagem

Ao concluir este projeto, você estará apto a:

- Aplicar com confiança os princípios da Programação Orientada a Objetos.
- Estruturar entidades com herança e composição.
- Simular repositórios e persistência em memória.
- Criar fluxos interativos e menus no terminal.
- Documentar projetos técnicos de forma clara e objetiva.
- Compartilhar seu progresso técnico via GitHub.

---

## 📌 Funcionalidades

### ✅ Conta Bancária
- Criar contas com múltiplas chaves PIX.
- Depositar e sacar valores.
- Transferir valores entre contas via PIX.
- Listar todas as contas criadas.
- Exibir histórico detalhado de transações.

### ✅ Investimentos
- Criar produtos de investimento com taxa e valor inicial.
- Vincular carteiras de investimento a contas.
- Investir e resgatar valores.
- Atualizar valor dos investimentos.
- Listar investimentos e carteiras vinculadas.

---

## 🧾 Fluxo de Menu (via terminal)

```text
1  - Criar uma conta
2  - Criar um investimento
3  - Criar carteira de investimento para uma conta
4  - Depositar na conta
5  - Sacar da conta
6  - Transferência entre contas
7  - Investir
8  - Sacar investimento
9  - Listar contas
10 - Listar investimentos
11 - Listar carteiras de investimento
12 - Atualizar investimentos
13 - Histórico de conta
14 - Sair
```

⸻

🧩 Estrutura do Projeto
```text
src/
│
├── br.com/
│   ├── Main.java                # Classe principal com o menu e lógica de navegação
│   ├── model/                   # Entidades: AccountWallet, Investment, InvestmentWallet
│   ├── repository/              # Repositórios simulando banco de dados em memória
│   └── exception/               # Classes de exceções personalizadas
```

⸻

⚠️ Tratamento de Erros

O sistema conta com tratamento de exceções para garantir robustez nas seguintes situações:
	•	Chave PIX não encontrada
	•	Saldo insuficiente para saque ou investimento
	•	Dados inválidos
	•	Operações inesperadas

⸻

📥 Como Executar Localmente
	1.	Clone o repositório:

git clone https://github.com/miguelamaral254/java-bank-dio.git
cd java-bank-dio

	2.	Compile e execute o projeto:

Se estiver usando terminal:

javac br/com/Main.java
java br.com.Main

Ou utilize sua IDE preferida (IntelliJ, Eclipse, VS Code).

⸻

📌 Possíveis Melhorias Futuras
	•	Persistência real com banco de dados (PostgreSQL ou MongoDB)
	•	Interface gráfica com JavaFX
	•	Autenticação de usuários
	•	Logs com Log4j ou SLF4J
	•	Testes automatizados com JUnit

⸻

✍️ Conclusão

Este projeto proporcionou uma excelente oportunidade para aplicar conceitos essenciais da orientação a objetos na prática, além de desenvolver a habilidade de criar menus interativos e trabalhar com repositórios simulados em memória. Através desta experiência, foi possível reforçar a importância da modelagem de domínio, tratamento de exceções e estruturação de código limpa e organizada.

⸻

📚 Créditos

Projeto desenvolvido como parte do Bootcamp Java Developer - DIO.
Mentores e instrutores: @santanche, @douglas-m-s

⸻
