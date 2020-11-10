package Banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {


    public static final Integer savingsAccountID = 12345678;
    public static final Integer cdAccountID = 23456789;
    public static final Integer checkingAccountID = 34567899;

    public static final Double savingsAccountApr = 1.0;
    public static final Double cdAccountApr = 2.0;
    public static final Double checkingAccountApr = 3.0;

    public static final Double savingsAccountBalance = 0.0;
    public static final Double checkingAccountBalance = 0.0;


    @Test
    public void checking_account_has_id() {
        Account checkingAccount = Account.checkingAccount(checkingAccountID, checkingAccountApr);
        assertEquals(checkingAccountID, checkingAccount.getID());
    }

    @Test
    public void savings_account_has_id() {
        Account savingsAccount = Account.savingsAccount(savingsAccountID, savingsAccountApr);
        assertEquals(savingsAccountID, savingsAccount.getID());
    }

    @Test
    public void cd_account_has_id() {
        Account cdAccount = Account.cdAccount(cdAccountID, cdAccountApr, 1000);
        assertEquals(cdAccountID, cdAccount.getID());
    }

    @Test
    public void checking_account_has_no_balance() {
        Account checkingAccount = Account.checkingAccount(checkingAccountID, checkingAccountApr);
        assertEquals(checkingAccountBalance, checkingAccount.getAccountBalance());

    }

    @Test
    public void savings_account_has_no_balance() {
        Account savingsAccount = Account.savingsAccount(checkingAccountID, checkingAccountApr);
        assertEquals(savingsAccountBalance, savingsAccount.getAccountBalance());

    }


    @Test
    public void deposit_into_checking_account() {
        Account checkingAccount = Account.checkingAccount(99999999, 0.07);
        Double oldBalance = checkingAccount.getAccountBalance();
        checkingAccount.deposit(100.00);
        assertEquals(oldBalance + 100.00, checkingAccount.getAccountBalance());
    }

    @Test
    public void deposit_twice_into_checking_account() {
        Account checkingAccount = Account.checkingAccount(checkingAccountID, checkingAccountApr);
        Double oldBalance = checkingAccount.getAccountBalance();
        checkingAccount.deposit(100.00);
        checkingAccount.deposit(100.00);
        assertEquals(oldBalance + 200.00, checkingAccount.getAccountBalance());
    }

    @Test
    public void deposit_into_savings_account_in_bank() {
        Account savingsAccount = Account.savingsAccount(savingsAccountID, savingsAccountApr);
        Double oldBalance = savingsAccount.getAccountBalance();
        savingsAccount.deposit(100.00);
        assertEquals(oldBalance + 100.00, savingsAccount.getAccountBalance());
    }

    @Test
    public void deposit_twice_into_savings_account_in_bank() {
        Account savingsAccount = Account.savingsAccount(savingsAccountID, savingsAccountApr);
        Double oldBalance = savingsAccount.getAccountBalance();
        savingsAccount.deposit(100.00);
        savingsAccount.deposit(100.00);
        assertEquals(oldBalance + 200.00, savingsAccount.getAccountBalance());
    }


    @Test
    public void withdraw_from_checking_account() {
        Account checkingAccount = Account.checkingAccount(checkingAccountID, checkingAccountApr);
        checkingAccount.deposit(300.00);
        Double oldBalance = checkingAccount.getAccountBalance();
        checkingAccount.withdraw(100.00);
        assertEquals(oldBalance - 100.00, checkingAccount.getAccountBalance());
    }

    @Test
    public void withdraw_twice_from_checking_account() {
        Account checkingAccount = Account.checkingAccount(checkingAccountID, checkingAccountApr);
        checkingAccount.deposit(300.00);
        Double oldBalance = checkingAccount.getAccountBalance();
        checkingAccount.withdraw(100.00);
        checkingAccount.withdraw(100.00);
        assertEquals(oldBalance - 200.00, checkingAccount.getAccountBalance());
    }

    @Test
    public void withdraw_from_savings_account() {
        Account savingsAccount = Account.savingsAccount(savingsAccountID, savingsAccountApr);
        savingsAccount.deposit(100.00);
        Double oldBalance = savingsAccount.getAccountBalance();
        savingsAccount.withdraw(100.00);
        assertEquals(oldBalance - 100.00, savingsAccount.getAccountBalance());
    }

    @Test
    public void withdraw_twice_from_savings_account() {
        Account savingsAccount = Account.savingsAccount(savingsAccountID, savingsAccountApr);
        savingsAccount.deposit(300.00);
        Double oldBalance = savingsAccount.getAccountBalance();
        savingsAccount.withdraw(100.00);
        savingsAccount.withdraw(100.00);
        assertEquals(oldBalance - 200.00, savingsAccount.getAccountBalance());
    }

    @Test
    public void withdraw_from_cd_account() {
        Account cdAccount = Account.cdAccount(cdAccountID, cdAccountApr, 1000);
        Double oldBalance = cdAccount.getAccountBalance();
        cdAccount.withdraw(100.00);
        assertEquals(oldBalance - 100.00, cdAccount.getAccountBalance());
    }

    @Test
    public void withdraw_twice_from_cd_account() {
        Account cdAccount = Account.cdAccount(cdAccountID, cdAccountApr, 1000);
        Double oldBalance = cdAccount.getAccountBalance();
        cdAccount.withdraw(100.00);
        cdAccount.withdraw(100.00);
        assertEquals(oldBalance - 200.00, cdAccount.getAccountBalance());
    }

}
