package Banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandProcessorTest {
    Bank bank = new Bank();
    CommandProcessor commandProcessor = new CommandProcessor(bank);

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
}
