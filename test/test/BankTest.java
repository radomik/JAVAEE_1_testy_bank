/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pkg1_testy_bank.Bank;
import pkg1_testy_bank.BankImpl;

/**
 *
 * @author darek
 * Kolejność wywoływania metod:
 * 1. \@BeforeClass -- metody statyczne, wywoływane w dowolnej kolejności
 * 2. Testy uruchamiane w dowolnej kolejności wg. schematu:
 *		2.1. tworzona nowa instancja klasy BankTest
 *		2.2.	wywoływane wszystkie metody \@Before w dowolnej kolejności
 *				ale raz ustalona kolejność powtarza się dla wszystkich testów
 *				w danym uruchomieniu
 *		2.3. wywoływana metoda \@Test
 *		2.4.	wywoływane wszystkie metody \@After w dowolnej kolejności
 *				ale raz ustalona kolejność powtarza się dla wszystkich testów
 *				w danym uruchomieniu
 * 3. \@AfterClass -- metody statyczne, wywoływane w dowolnej kolejności
 */
public class BankTest {
	private static final boolean VERBOSE = true;
	private Bank bank;

	public BankTest() {
		if (VERBOSE) System.out.println("BankTest.BankTest() this: " + this.hashCode()+" this.bank: " + bank);
	}

	@BeforeClass
	public static void setUpClass() {
		if (VERBOSE) System.out.println("BankTest.setUpClass()");
	}

	@BeforeClass
	public static void setUpClass1() {
		if (VERBOSE) System.out.println("BankTest.setUpClass1()");
	}

	@BeforeClass
	public static void setUpClass2() {
		if (VERBOSE) System.out.println("BankTest.setUpClass2()");
	}

	@AfterClass
	public static void tearDownClass() {
		if (VERBOSE) System.out.println("BankTest.tearDownClass()");
	}

	@AfterClass
	public static void tearDownClass1() {
		if (VERBOSE) System.out.println("BankTest.tearDownClass1()");
	}

	@AfterClass
	public static void tearDownClass2() {
		if (VERBOSE) System.out.println("BankTest.tearDownClass2()");
	}

	@Before
	public void setUp() {
		bank = new BankImpl();
		if (VERBOSE) System.out.println("BankTest.setUp() this: " + this.hashCode()+" this.bank: " + bank);
	}

	@Before
	public void setUp1() {
		if (VERBOSE) System.out.println("BankTest.setUp1() this: " + this.hashCode()+" this.bank: " + bank);
	}

	@Before
	public void setUp2() {
		if (VERBOSE) System.out.println("BankTest.setUp2() this: " + this.hashCode()+" this.bank: " + bank);
	}

	@After
	public void tearDown() {
		bank = null;
		if (VERBOSE) System.out.println("BankTest.tearDown() this: " + this.hashCode()+" this.bank: " + bank);
	}

	@After
	public void tearDown1() {
		if (VERBOSE) System.out.println("BankTest.tearDown1() this: " + this.hashCode()+" this.bank: " + bank);
	}

	@After
	public void tearDown2() {
		if (VERBOSE) System.out.println("BankTest.tearDown2() this: " + this.hashCode()+" this.bank: " + bank);
	}

	@Test
	public void doNotCreateTwice() {
		if (VERBOSE) System.out.println("BankTest.doNotCreateTwice() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		Integer id2 = bank.createAccount("any", "x");
		assert id2 != null; // createAccount() cannot return null

		assert id.equals(id2);
	}

	@Test
	public void findAccountNone() {
		if (VERBOSE) System.out.println("BankTest.findAccountNone() this: " + this.hashCode()+" this.bank: " + bank);
		Integer a_id = bank.findAccount("any", "x"); // not existing -> null id
		assert a_id == null;
	}

	@Test
	public void createManyAccounts() {
		if (VERBOSE) System.out.println("BankTest.createManyAccounts() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		Integer id2 = bank.createAccount("any2", "x2");
		assert id2 != null; // createAccount() cannot return null

		assert id != id2;

		Integer id3 = bank.createAccount("any3", "x2");
		assert id3 != null; // createAccount() cannot return null

		assert id  != id3;
		assert id2 != id3;

		Integer id4 = bank.createAccount("any3", "x2");
		assert id4 != null; // createAccount() cannot return null

		assert id != id2;
		assert id != id3;
		assert id != id4;
		assert id2 != id3;
		assert id3 == id4;
	}

	@Test
	public void findAccountCreated() {
		if (VERBOSE) System.out.println("BankTest.findAccountCreated() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		Integer a_id = bank.findAccount("any", "x"); // existing account -> not null
		assert a_id != null;
		assert a_id == id; // found id the same as created

		Integer _null = bank.findAccount("none", " "); // not existing account -> null
		assert _null == null;
	}

	@Test(expected = Bank.AccountIdException.class)
	public void testBalanceNone() {
		if (VERBOSE) System.out.println("BankTest.testBalanceNone() this: " + this.hashCode()+" this.bank: " + bank);
		bank.getBalance(3); // get balance of not existing account -> exception
	}

	@Test(expected = Test.None.class)
	public void testBalanceCreated() {
		if (VERBOSE) System.out.println("BankTest.testBalanceCreated() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		double amount = bank.getBalance(id); // get balance of existing account -> expected no exception
		assert amount == 0.0; // amount of newly created account should be 0
	}

	@Test(expected = Bank.AccountIdException.class)
	public void testDepositNone() {
		if (VERBOSE) System.out.println("BankTest.testDepositNone() this: " + this.hashCode()+" this.bank: " + bank);
		bank.deposit(3, 3.0); // add money to not existing account -> exception
	}

	@Test(expected = Test.None.class)
	public void testDepositCreated() {
		if (VERBOSE) System.out.println("BankTest.testDepositCreated() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		bank.deposit(id, 3.0);	// add money to existing account -> no exception

		double ammount = bank.getBalance(id); // get balance of existing account -> expected no exception
		assert ammount == 3.0; // should be the same as added

		bank.deposit(id, -3.0);	// add money to existing account -> no exception
								// added negative value -> should be abs()
		ammount = bank.getBalance(id); // get balance of existing account -> expected no exception
		assert ammount == 6.0; // should be 3.0 + abs(-3.0) = 6.0
	}

	@Test(expected = Bank.AccountIdException.class)
	public void testWithdrawNone() {
		if (VERBOSE) System.out.println("BankTest.testWithdrawNone() this: " + this.hashCode()+" this.bank: " + bank);
		bank.withdraw(3, 3.0); // get money from not existing account -> exception
	}

	@Test(expected = Bank.InsufficientFundsException.class)
	public void testWithdrawCreatedInsufficient() {
		if (VERBOSE) System.out.println("BankTest.testWithdrawCreatedInsufficient() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		bank.withdraw(id, 3.0);	// get money from newly created account (empty) -> exception
	}

	@Test(expected = Bank.InsufficientFundsException.class)
	public void testWithdrawCreatedInsufficientNegative() {
		if (VERBOSE) System.out.println("BankTest.testWithdrawCreatedInsufficientNegative() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		bank.withdraw(id, -3.0);	// get money from newly created account (empty) -> exception
									// is absolute -> should be abs()
	}

	@Test(expected = Test.None.class)
	public void testWithdrawCreated() {
		if (VERBOSE) System.out.println("BankTest.testWithdrawCreated() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		bank.deposit(id, 10.0);	// add money to newly created account (empty) -> no exception

		bank.withdraw(id, 3.0); // 10 - 3 = 7
		double ammount = bank.getBalance(id); // get balance of existing account -> expected no exception
		assert ammount == 7.0; // should be 10.0 - 3.0 = 7.0

		bank.withdraw(id, -3.0); // 7 - abs(-3) = 4
		ammount = bank.getBalance(id); // get balance of existing account -> expected no exception
		assert ammount == 4.0; // should be 7.0 - abs(-3.0) = 4.0

		bank.deposit(id, -10.0);	// 4.0 + abs(-10.0) = 14.0
		ammount = bank.getBalance(id); // get balance of existing account -> expected no exception
		assert ammount == 14.0; // should be 4.0 + abs(-10.0) = 14.0
	}

	@Test(expected = Bank.AccountIdException.class)
	public void transferNoneToNone() {
		if (VERBOSE) System.out.println("BankTest.transferNoneToNone() this: " + this.hashCode()+" this.bank: " + bank);
		bank.transfer(2, 3, 3.0);
	}

	@Test(expected = Bank.AccountIdException.class)
	public void transferNoneToExist() {
		if (VERBOSE) System.out.println("BankTest.transferNoneToExist() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		bank.transfer(2, id, 3.0);
	}

	@Test(expected = Bank.InsufficientFundsException.class)
	public void transferExistEmptyToNone() {
		if (VERBOSE) System.out.println("BankTest.transferExistEmptyToNone() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		bank.transfer(id, 2, 3.0);
	}

	@Test(expected = Bank.AccountIdException.class)
	public void transferExistNotEmptyToNone() {
		if (VERBOSE) System.out.println("BankTest.transferExistNotEmptyToNone() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id = bank.createAccount("any", "x");
		assert id != null; // createAccount() cannot return null

		bank.deposit(id, 10.0);	// add money to newly created account (empty) -> no exception

		bank.transfer(id, 2, 3.0);
	}

	@Test(expected = Test.None.class)
	public void transferExistNotEmptyToExistEmpty() {
		if (VERBOSE) System.out.println("BankTest.transferExistNotEmptyToExistEmpty() this: " + this.hashCode()+" this.bank: " + bank);
		Integer id_src = bank.createAccount("any", "x");
		assert id_src != null; // createAccount() cannot return null

		bank.deposit(id_src, 10.0);	// add money to newly created account (empty) -> no exception

		double ammount = bank.getBalance(id_src); // get balance of existing account -> expected no exception
		assert ammount == 10.0; // should be 10.0

		Integer id_dest = bank.createAccount("any2", "y2");
		assert id_dest != null; // createAccount() cannot return null

		assert id_src != id_dest;

		//System.out.println("TEST: transfer from "+id_src+" to "+id_dest);
		bank.transfer(id_src, id_dest, 3.0);

		ammount = bank.getBalance(id_src); // get balance of existing account -> expected no exception
		assert ammount == 7.0; // should be 10.0 - 3.0 = 7.0

		ammount = bank.getBalance(id_dest); // get balance of existing account -> expected no exception
		assert ammount == 3.0; // should be 10.0 - 3.0 = 7.0

		bank.transfer(id_dest, id_src, -3.0); // -3.0 -> abs(-3.0)

		ammount = bank.getBalance(id_dest); // get balance of existing account -> expected no exception
		assert ammount == 0.0; // should be 3.0 - abs(-3.0) = 0.0

		ammount = bank.getBalance(id_src); // get balance of existing account -> expected no exception
		assert ammount == 10.0; // should be 7.0 + abs(-3.0) = 10.0

	}
}
