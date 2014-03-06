/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg1_testy_bank;

/**
 *
 * @author darek
 */
public interface Bank {
	/**
	* Tworzy nowe lub zwraca id istniejącego konta.
	* @param name
	* @param address
	* @return id utworzonego lub istniejacego konta.
	*/
	Integer createAccount(String name, String address);

	/**
	* Znajduje identyfikator konta.
	* @param name
	* @param address
	* @return id konta lub null, gdy brak konta o podanych parametrach
	*/
	Integer findAccount(String name, String address);

	/**
	* Dodaje srodki do konta.
	* @param id
	* @param amount srodki
	* @throws AccountIdException, gdy id konta jest nieprawidlowe
	*/
	void deposit(Integer id, double amount);

	/**
	* Zwraca ilosc srodkow na koncie.
	* @param id
	* @return srodki
	* @throws AccountIdException, gdy id konta jest nieprawidlowe
	*/
	double getBalance(Integer id);

	/**
	* Pobiera srodki z konta.
	* @param id
	* @param amount srodki
	* @throws AccountIdException, gdy id konta jest nieprawidlowe
	* @throws InsufficientFundsException, gdy srodki na koncie nie sa wystarczajace do wykonania operacji
	*/
	void withdraw(Integer id, double amount);

	/**
	* Przelewa srodki miedzy kontami.
	* @param idSource
	* @param idDestination
	* @param amount srodki
	* @throws AccountIdException
	* @throws InsufficientFundsException
	*/
	void transfer(Integer idSource, Integer idDestination, double amount);

	class InsufficientFundsException extends RuntimeException {};
	class AccountIdException extends RuntimeException {};
}
