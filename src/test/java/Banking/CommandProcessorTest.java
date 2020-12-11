package Banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessorTest {
    Bank bank = new Bank();
    Storage storage = new Storage();
    CommandProcessor commandProcessor = new CommandProcessor(bank, storage);

    @Test
    void create_checking_account() {
        commandProcessor.processInput("create checking 77777777 1.0");
        assertEquals(77777777, bank.getAccount(77777777).getID());
        assertEquals(1.0, bank.getAccount(77777777).getApr());
        assertTrue(bank.getAccount(Integer.parseInt(String.valueOf(77777777))).isCheckingAccount());

    }

    @Test
    void create_savings_account() {
        commandProcessor.processInput("create savings 77777777 1.0");
        assertEquals(77777777, bank.getAccount(77777777).getID());
        assertEquals(1.0, bank.getAccount(77777777).getApr());
    }

    @Test
    void create_cd_account() {
        commandProcessor.processInput("create cd 55555555 1.0 1000");
        assertEquals(55555555, bank.getAccount(55555555).getID());
        assertEquals(1.0, bank.getAccount(55555555).getApr());
    }

    @Test
    void deposit_into_savings() {
        bank.addAccount(Account.savingsAccount(77777777, 0.99));
        Double oldBalance = bank.getAccountBalance(77777777);
        commandProcessor.processInput("deposit 77777777 100");
        assertEquals(oldBalance + 100.00, bank.getAccountBalance(77777777));
    }

    @Test
    void deposit_into_checking() {
        bank.addAccount(Account.checkingAccount(99999999, 0.99));
        Double oldBalance = bank.getAccountBalance(99999999);
        commandProcessor.processInput("deposit 99999999 100");
        assertEquals(oldBalance + 100.00, bank.getAccountBalance(99999999));
    }

    @Test
    void withdraw_from_checking() {
        bank.addAccount(Account.checkingAccount(99999999, 0.99));
        commandProcessor.processInput("deposit 99999999 100");
        commandProcessor.processInput("deposit 99999999 100");
        Double oldBalance = bank.getAccountBalance(99999999);
        commandProcessor.processInput("withdraw 99999999 100");
        assertEquals(oldBalance - 100.00, bank.getAccountBalance(99999999));
    }


    @Test
    void accrue_interest(){
        bank.addAccount(Account.checkingAccount(99999999, 0.6));
        bank.addAccount(Account.cdAccount(88888888, 0.6,5000));
        bank.makeDeposit(99999999, 5000.00);
        commandProcessor.processInput("pass 12");
        assertEquals(5030.08, bank.getAccountBalance(99999999));
        assertEquals(5121.42, bank.getAccountBalance(88888888));
    }

    @Test
    void transfer_from_checking_to_checking(){
        bank.addAccount(Account.checkingAccount(99999999, 0.6));
        bank.addAccount(Account.checkingAccount(11111111, 0.6));
        commandProcessor.processInput("deposit 99999999 1000");
        Double oldChecking9 = bank.getAccountBalance(99999999);
        Double oldChecking1 = bank.getAccountBalance(11111111);
        commandProcessor.processInput("transfer 99999999 11111111 200");
        assertEquals(oldChecking9 - 200, bank.getAccountBalance(99999999));
        assertEquals(oldChecking1 + 200, bank.getAccountBalance(11111111));
    }

    @Test
    void transfer_from_cd_to_checking(){
        bank.addAccount(Account.cdAccount(99999999, 1.2, 1000));
        bank.addAccount(Account.checkingAccount(11111111, 1.2));
        commandProcessor.processInput("deposit 11111111 1000");
        commandProcessor.processInput("pass 12");
        commandProcessor.processInput("transfer 99999999 11111111 1049.15");
        assertEquals(0, bank.getAccountBalance(99999999));
        assertEquals(2061.22, bank.getAccountBalance(11111111));
    }


    @Test
    void deduct_money_from_account(){
        bank.addAccount(Account.checkingAccount(99999999, 0.6));
        bank.makeDeposit(99999999, 90.00);
        Double oldChecking9 = bank.getAccountBalance(99999999);
        commandProcessor.processInput("pass 1");
        assertEquals(65.03, bank.getAccountBalance(99999999));
    }

    @Test
    void deduct_money_from_account_until_zero(){
        bank.addAccount(Account.checkingAccount(99999999, 0.6));
        bank.makeDeposit(99999999, 90.00);
        commandProcessor.processInput("pass 4");
        assertEquals(0, bank.getAccountBalance(99999999));
    }

    @Test
    void deduct_money_from_account_until_zero_and_remove(){
        bank.addAccount(Account.checkingAccount(99999999, 0.6));
        bank.makeDeposit(99999999, 90.00);
        commandProcessor.processInput("pass 5");
        assertFalse(bank.doesAccountExist(99999999));
    }









}
