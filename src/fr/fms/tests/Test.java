package fr.fms.tests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import fr.fms.entities.Account;
import fr.fms.entities.CurrentAccount;
import fr.fms.entities.DepositOperation;
import fr.fms.entities.Operation;
import fr.fms.entities.SavingAccount;
import fr.fms.entities.User;
import fr.fms.entities.WithdrawalOperation;
import fr.fms.services.IBankAccountImpl;
import fr.fms.services.IBankCustomerImpl;
import fr.fms.services.IBankServiceImpl;

public class Test {

	public static void main(String[] args) {

		// Générer des customers
		User macron = new User(1, "Macron", "Emmanuel", "emmanuel.macron@gouv.fr",
				"55 Rue du Faubourg Saint-Honoré, 75008 Paris", "01 01 01 01 01",
				LocalDateTime.of(1977, 12, 21, 12, 0));
		User biden = new User(2, "Biden", "Jojo", "joe.biden@fms-ea.com", "1600, Pennsylvania Avenue, Washington DC",
				"0 563 241 115", LocalDateTime.of(1942, 11, 20, 5, 35));

		// Générer des comptes
		Account macronAccount = new CurrentAccount(1, 5000, "69", 25000, macron, LocalDate.now());
		Account bidenAccount = new CurrentAccount(2, 15000, "77", 250000, biden, LocalDate.now());
		Account macronSavingAccount = new SavingAccount(3, 2, "69S", 150000, macron, LocalDate.now());
		Account bidenSavingAccount = new SavingAccount(4, 3, "77S", 250000, biden, LocalDate.now());

		// Faire des opérations
		Operation deposit1 = new DepositOperation(1, LocalDate.now(), 200, macronAccount);
		Operation withdrawal1 = new WithdrawalOperation(2, LocalDate.now(), 333, macronAccount);

		// Affichage et jeux de test
		System.out.println("création et affichage de deux compte bancaires :\n");
		System.out.println(macronAccount);
		System.out.println(macronSavingAccount);
		System.out.println("----------------------------------------------");
		System.out.println("solde du compte courant de manu:" + macronAccount.getBalance());
		System.out.println("solde du compte epargne de manu:" + macronSavingAccount.getBalance());
		System.out.println("----------------------------------------------");
		IBankCustomerImpl customerImpl = new IBankCustomerImpl();

		// Ajout dans la hashMap
		customerImpl.addCustomer(macron);
		customerImpl.addCustomer(biden);
		System.out.println("liste de nos clients :\n");
		customerImpl.displayCustomer();
		System.out.println("------------------------------------------");

		// Liste des comptes
		IBankAccountImpl accountImpl = new IBankAccountImpl();
		accountImpl.addAccount(macronAccount);
		accountImpl.addAccount(bidenAccount);
		accountImpl.addAccount(macronSavingAccount);
		accountImpl.addAccount(bidenSavingAccount);
		System.out.println("la liste des comptes :\n");
		accountImpl.displayAccount();

		// Liste des opérations
		System.out.println("-----------------------------------------");
		IBankServiceImpl operationImpl = new IBankServiceImpl();
		int idAccount = 50;
		if (operationImpl.getAccount(idAccount) == null)
			System.out.println("Vous demandez un compte inexistant");
		else
			System.out.println(operationImpl.getAccount(idAccount));

		// Dépassement de capacité de retrait
		operationImpl.makeWithdrawal(1, 30002);

		// Virement sur le même compte{id}
		operationImpl.makeTransfer(1, 1, 1000);

		// Affichage des comptes qui appartiennent à un client
		System.out.println("\n--------------liste des comptes de Macron--------------------");
		accountImpl.findCustomerAccount(macron.getId());

		// liste des transactions de manu
		operationImpl.addOperations(withdrawal1);
		operationImpl.addOperations(deposit1);
		System.out.println("\n-------------Liste des transactions sur le compte courant de manu ---------------");
		Map<Integer, Operation> resultat = operationImpl.getOperations(macronAccount.getId());
		for (Operation ope : resultat.values()) {
			System.out.println(ope);
		}
	}
}