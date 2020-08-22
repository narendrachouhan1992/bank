package com.bank.bankingapi;

import java.util.Scanner;

import com.bank.bankingapi.Entities.HeadOffice;
import com.bank.bankingapi.Exceptions.BankingException;

public class MainClass {
	public static void main(String[] args) {
		boolean keepGoining = true;
		Scanner sc = new Scanner(System.in);
		HeadOffice ho = null;
		String branchID;
		String pan;
		String accountNum;
		double ammount;
		while (keepGoining) {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("enter 1 for create head Office");
			System.out.println("enter 2 for create branch Office");
			System.out.println("enter 3 for create create account");
			System.out.println("enter 4 for withdraw ammount");
			System.out.println("enter 5 for deposit ammount");
			System.out.println("enter 6 to shutdown.....");

			int job = sc.nextInt();
			switch (job) {
			case 1:
				if (ho != null) {
					System.out.println("head office already exist");
				} else {
					ho = new HeadOffice("SBI");
					System.out.println("Head office created.....");
				}
				break;
			case 2:
				if(ho==null)
				{
					System.out.println("head office not created, create head office first");
					break;
				}
				ho.createBranch("name1");
				break;
			case 3:
				if(ho==null)
				{
					System.out.println("head office not created, create head office first");
					break;
				}
				System.out.println("enter Branch ID number");
				branchID = sc.next();
				System.out.println("enter customer PAN number");
				pan = sc.next();
				System.out.println("enter AccountType Saving/Current");
				String type = sc.next();
				System.out.println("enter the initial ammount");
				ammount = sc.nextDouble();
				try {
					ho.getBranchByBranchID(branchID).createBankAccount(pan, type, ammount, "");
				} catch (BankingException e) {
					System.out.println(e);
				}
				break;
			case 4:
				if(ho==null)
				{
					System.out.println("head office not created, create head office first");
					break;
				}
				System.out.println("enter Branch ID number");
				branchID = sc.next();
				System.out.println("enter account number");
				accountNum = sc.next();
				System.out.println("enter the ammount to withdraw");
				ammount = sc.nextDouble();
				try {
					ho.getBranchByBranchID(branchID).getAccountByAccountNumber(accountNum).withdraw(ammount);
				} catch (BankingException e) {
					System.out.println(e);
				}
				break;
			case 5:
				if(ho==null)
				{
					System.out.println("head office not created, create head office first");
					break;
				}
				System.out.println("enter Branch ID number");
				branchID = sc.next();
				System.out.println("enter account number");
				accountNum = sc.next();
				System.out.println("enter the ammount to deposit");
				ammount = sc.nextDouble();
				try {
					ho.getBranchByBranchID(branchID).getAccountByAccountNumber(accountNum).deposit(ammount);
				} catch (BankingException e) {
					System.out.println(e);
				}
				break;
			case 6:
				keepGoining = false;
				System.out.println("Shutting down .....");
				break;

			default:
				System.out.println("Invalid option, select again");
				break;
			}
			
			System.out.println();

			System.out.println();
			System.out.println();
			System.out.println();
		}
		sc.close();
	}
}
