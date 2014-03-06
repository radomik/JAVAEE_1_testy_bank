/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg1_testy_bank;

import java.util.HashMap;
import java.util.Map;

public class BankImpl implements Bank {
	private final Map<Integer, Account> accounts = new HashMap();

	/**
	* Tworzy nowe lub zwraca id istniejącego konta.
	* @param name
	* @param address
	* @return id utworzonego lub istniejacego konta.
	*/
	@Override
	public Integer createAccount(String name, String address) {
		Integer max_key = 0;
		for (Account a : accounts.values()) {
			if ((a.name.equals(name)) && (a.address.equals(address)))
				return a.id;
			else { max_key = Math.max(a.id, max_key); }
		}
		max_key++; // added thanks to tests
		accounts.put(max_key, new Account(max_key, name, address));
		return max_key;
	}

	/**
	* Znajduje identyfikator konta.
	* @param name
	* @param address
	* @return id konta lub null, gdy brak konta o podanych parametrach
	*/
	@Override
	public Integer findAccount(String name, String address) {
		for (Account a : accounts.values()) {
			if ((a.name.equals(name)) && (a.address.equals(address)))
				return a.id;
		}
		return null;
	}

	/**
	* Dodaje srodki do konta.
	* @param id
	* @param amount srodki
	* @throws AccountIdException, gdy id konta jest nieprawidlowe
	*/
	@Override
	public void deposit(Integer id, double amount) {
		deposit(accounts.get(id), amount);
	}

	/**
	* Zwraca ilosc srodkow na koncie.
	* @param id
	* @return srodki
	* @throws AccountIdException, gdy id konta jest nieprawidlowe
	*/
	@Override
	public double getBalance(Integer id) {
		Account a = accounts.get(id);
		if (a == null) throw new AccountIdException();
		return a.amount;
	}

	/**
	* Pobiera srodki z konta.
	* @param id
	* @param amount srodki
	* @throws AccountIdException, gdy id konta jest nieprawidlowe
	* @throws InsufficientFundsException, gdy srodki na koncie nie sa wystarczajace do wykonania operacji
	*/
	@Override
	public void withdraw(Integer id, double amount) {
		withdraw(accounts.get(id), amount);
	}

	/**
	* Przelewa srodki miedzy kontami.
	* @param idSource
	* @param idDestination
	* @param amount srodki
	* @throws AccountIdException
	* @throws InsufficientFundsException
	*/
	@Override
	public void transfer(Integer idSource, Integer idDestination, double amount) {
		Account a_src = accounts.get(idSource);
		try { withdraw(a_src, amount); }
		catch (AccountIdException | InsufficientFundsException e) { throw e; }

		try { deposit(accounts.get(idDestination), amount); }
		catch (AccountIdException e) {
			deposit(a_src, amount); // return money to sender
			throw e;
		}
	}

	private void withdraw(Account a, double amount) {
		if (a == null) throw new AccountIdException();
		amount = Math.abs(amount);

		if (a.amount < amount) {
			throw new InsufficientFundsException();
		}
		a.amount -= amount;
	}

	private void deposit(Account a, double amount) {
		if (a == null) throw new AccountIdException();
		a.amount += Math.abs(amount);
	}

	private class Account {
		Integer id;
		String  name;
		String  address;
		double  amount;

		Account(Integer id, String name, String address) {
			this.id = id;
			this.name = name;
			this.address = address;
			this.amount = 0;
		}
	}
}
