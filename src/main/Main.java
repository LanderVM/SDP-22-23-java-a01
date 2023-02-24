package main;

import domain.DomainController;
import exceptions.UserDoesntExistException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run();
	}

	private static void run() {
		// TODO Auto-generated method stub
		DomainController dc = new DomainController();
		try {
			boolean gebruiker = dc.checkUser("testAdmin@mail.com", "testAdmin");
			System.out.println(gebruiker);
			System.out.println(dc.userIsAdmin());
			boolean gebruiker2 = dc.checkUser("testMagazijnier@mail.com", "testMagazijnier");
			System.out.println(gebruiker2);
			System.out.println(dc.userIsAdmin());
		} catch (UserDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
