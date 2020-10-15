import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    public static final Integer savingsAccountID = 12345678;
    public static final Integer cdAccountID = 23456789;
    public static final Integer checkingAccountID = 34567899;

    public static final Double savingsAccountApr = 1.0;
    public static final Double cdAccountApr = 2.0;
    public static final Double checkingAccountApr = 3.0;

    public static final Double savingsAccountBalance = 0.0;
    public static final Integer cdAccountBalance = 2000;
    public static final Double checkingAccountBalance = 0.0;

    Bank bank;

    @BeforeEach
    void beforeEach() {
        bank = new Bank();
        bank.addAccount(Account.savingsAccount(savingsAccountID, savingsAccountApr));
        bank.addAccount(Account.cdAccount(cdAccountID, cdAccountApr, cdAccountBalance));
        bank.addAccount(Account.checkingAccount(checkingAccountID, checkingAccountApr));
    }

    @Test
    void bank_has_no_accounts_initially() {
        Bank bank = new Bank();
        assertTrue(bank.getNumberOfAccounts() == 0);
    }

    @Test
    void add_checking_account_to_bank() {
        bank.addAccount(Account.checkingAccount(checkingAccountID, checkingAccountApr));
        assertEquals(checkingAccountID, bank.getAccount(checkingAccountID).getID());
        assertEquals(checkingAccountApr, bank.getAccount(checkingAccountID).getApr());
    }

    @Test
    void add_savings_account_to_bank() {
        bank.addAccount(Account.savingsAccount(savingsAccountID, savingsAccountApr));
        assertEquals(savingsAccountID, bank.getAccount(savingsAccountID).getID());
        assertEquals(savingsAccountApr, bank.getAccount(savingsAccountID).getApr());
    }

    @Test
    void add_cd_account_to_bank() {
        bank.addAccount(Account.cdAccount(cdAccountID, cdAccountApr, cdAccountBalance));
        assertEquals(cdAccountID, bank.getAccount(cdAccountID).getID());
        assertEquals(cdAccountApr, bank.getAccount(cdAccountID).getApr());
    }

    @Test
    void add_multiple_accounts_to_bank() {
        bank.addAccount(Account.savingsAccount(11111111, savingsAccountApr));
        bank.addAccount(Account.checkingAccount(22222222, checkingAccountApr));
        bank.addAccount(Account.cdAccount(33333333, cdAccountApr, cdAccountBalance));
        assertEquals(11111111, bank.getAccount(11111111).getID());
        assertEquals(22222222, bank.getAccount(22222222).getID());
        assertEquals(33333333, bank.getAccount(33333333).getID());

    }


    @Test
    void deposit_money_in_checking_account_in_bank() {
        Double oldBalance = bank.getAccountBalance(checkingAccountID);
        bank.makeDeposit(checkingAccountID, 100.0);
        assertEquals(oldBalance + 100.00, bank.getAccountBalance(checkingAccountID));
    }

    @Test
    void deposit_money_in_savings_account_in_bank() {
        Double oldBalance = bank.getAccountBalance(savingsAccountID);
        bank.makeDeposit(savingsAccountID, 100.0);
        assertEquals(oldBalance + 100.00, bank.getAccountBalance(savingsAccountID));
    }

    @Test
    void withdraw_money_from_checking_account_in_bank() {
        bank.makeDeposit(checkingAccountID, 200.0);
        Double oldBalance = bank.getAccountBalance(checkingAccountID);
        bank.makeWithdrawal(checkingAccountID, 100.0);
        assertEquals(oldBalance - 100.00, bank.getAccountBalance(savingsAccountID));
    }

    @Test
    void withdraw_money_from_savings_account_in_bank() {
        bank.makeDeposit(savingsAccountID, 200.0);
        Double oldBalance = bank.getAccountBalance(checkingAccountID);
        bank.makeWithdrawal(checkingAccountID, 100.0);
        assertEquals(oldBalance - 100.00, bank.getAccountBalance(checkingAccountID));
    }

    @Test
    void withdraw_money_from_cd_account_in_bank() {
        bank.addAccount(Account.cdAccount(cdAccountID, cdAccountApr, cdAccountBalance));
        Double oldBalance = bank.getAccountBalance(cdAccountID);
        bank.makeWithdrawal(cdAccountID, 100.0);
        assertEquals(oldBalance - 100.00, bank.getAccountBalance(cdAccountID));
    }



    @Test
    void withdraw_more_than_amount_in_savings_account_return_zero() {
        bank.makeWithdrawal(savingsAccountID, 200.00 );
        assertEquals(0, bank.getAccountBalance(savingsAccountID));

    }

    @Test
    void withdraw_more_than_amount_in_checking_account_return_zero() {
        bank.makeWithdrawal(checkingAccountID, 200.00 );
        assertEquals(0, bank.getAccountBalance(checkingAccountID));

    }

    @Test
    void verify_apr_from_bank_for_checking(){
        assertEquals(checkingAccountBalance, bank.getAccountBalance(checkingAccountID));
        assertEquals(checkingAccountApr, bank.getAccount(checkingAccountID).getApr());

    }
    @Test
    void verify_apr_from_bank_for_savings(){
        assertEquals(savingsAccountBalance, bank.getAccountBalance(savingsAccountID));
        assertEquals(savingsAccountApr, bank.getAccount(savingsAccountID).getApr());

    }



}
