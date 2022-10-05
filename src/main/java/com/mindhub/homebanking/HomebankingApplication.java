package com.mindhub.homebanking;

import com.mindhub.homebanking.Services.*;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ClientService clientService, AccountService accountService,
									   ClientLoanService clientLoanService, CardService cardService,
									   TransactionService transactionService, LoanService loanService){
		return (args) -> {
			//CLIENTES
			Client client1 = new Client("Melba", "Lorenzo",
					"melbalorenzo@gmail.com",
					passwordEncoder.encode("melba123"),
					Authority.CLIENT);
			clientService.saveClient(client1);

			Client client2 = new Client("Jorge", "Brito",
					"jorgebrito@empresa.com",
					passwordEncoder.encode("jorge123"),
					Authority.CLIENT);
			clientService.saveClient(client2);

			Client admin = new Client("admin", "admin",
					"admin", passwordEncoder.encode("admin"),
					Authority.ADMIN);
			clientService.saveClient(admin);

			//CUENTAS
			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000.00);
			clientService.findClientByEmail("melbalorenzo@gmail.com").addAccount(account1);
			accountService.saveAccount(account1);

			Account account2 = new Account("VIN002", LocalDateTime.now(), 7500.00);
			clientService.findClientByEmail("melbalorenzo@gmail.com").addAccount(account2);
			accountService.saveAccount(account2);

			Account account3 = new Account("VIN003", LocalDateTime.now(), 500.00);
			clientService.findClientByEmail("jorgebrito@empresa.com").addAccount(account3);
			accountService.saveAccount(account3);

			//TRANSACCIONES
			LocalDateTime date = LocalDateTime.now();
			LocalDateTime date2 = LocalDateTime.now().plusDays(2);
			LocalDateTime date3 = LocalDateTime.now().plusDays(5);

			Transaction transaction1 = new Transaction(-1500.00, "Alquiler", date2, TransactionType.DEBITO,
					account1.getBalance() - 1500);

			account1.addTransaction(transaction1);
			account1.setBalance(account1.getBalance() - 1500);
			transactionService.saveTransaction(transaction1);


			Transaction transaction2 =
					new Transaction(-200.00, "Servicios", date, TransactionType.DEBITO,
							account1.getBalance() - 200);

			account1.addTransaction(transaction2);
			account1.setBalance(account1.getBalance() - 200);
			transactionService.saveTransaction(transaction2);


			Transaction transaction3 =
					new Transaction(-1000.00, "Servicios", date, TransactionType.DEBITO,
							account2.getBalance() - 1000);

			account2.addTransaction(transaction3);
			account2.setBalance(account2.getBalance() - 1000);
			transactionService.saveTransaction(transaction3);


			Transaction transaction4 =
					new Transaction(700.00, "Servicios", date, TransactionType.CREDITO,
							account3.getBalance() + 700);
			account3.addTransaction(transaction4);
			account3.setBalance(account3.getBalance() + 700);
			transactionService.saveTransaction(transaction4);


			Transaction transaction5 =
					new Transaction(5000.00, "Sueldo", date3, TransactionType.CREDITO,
							account1.getBalance() + 5000);
			account1.addTransaction(transaction5);
			account1.setBalance(account1.getBalance() + 5000);
			transactionService.saveTransaction(transaction5);

			//PRESTAMOS
			List<Integer> paymentsMortgage = List.of(12, 24, 36, 48, 60);
			List<Integer> paymentsPersonal = List.of(6, 12, 24);
			List<Integer> paymentsCar = List.of(6, 12, 24 ,36);

			Loan mortgageLoan =
					new Loan("Mortgage Loan", 500000, paymentsMortgage);
			loanService.saveLoan(mortgageLoan);
			Loan personalLoan = new Loan("Personal Loan",100000, paymentsPersonal);
			loanService.saveLoan(personalLoan);
			Loan carLoan = new Loan("Automotive Loan",300000, paymentsCar);
			loanService.saveLoan(carLoan);

			//CLIENTLOANS
			ClientLoan clientLoan1 = new ClientLoan(400000,60, client1, mortgageLoan);
			clientLoanService.saveClientLoan(clientLoan1);
			ClientLoan clientLoan3 = new ClientLoan(100000,24, client2, personalLoan);
			clientLoanService.saveClientLoan(clientLoan3);
			ClientLoan clientLoan4 = new ClientLoan(200000,36, client1, carLoan);
			clientLoanService.saveClientLoan(clientLoan4);

			//TARJETAS
			Card card1 = new Card("Melba Lorenzo",
					"4532-1707-6914-0203",
					CardType.DEBITO, CardColor.GOLD, 586,
					LocalDate.now(), LocalDate.now().plusYears(5),client1, account1);
			cardService.saveCard(card1);

			Card card2 = new Card("Melba Lorenzo",
					"4532 1226 2726 3552",
					CardType.DEBITO, CardColor.TITANIUM,
					246, LocalDate.now(), LocalDate.now().plusYears(5), client1, account2);
			cardService.saveCard(card2);

			Card card3 = new Card("Jorge Brito", "4205-2646-5746-4538", CardType.CREDITO, CardColor.GOLD, 125,
					LocalDate.now(), LocalDate.now().plusYears(5), 10000, client2);
			cardService.saveCard(card3);
		};
	}

}
