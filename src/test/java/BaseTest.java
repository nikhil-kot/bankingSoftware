import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    public static final Integer savingsAccountID = 12345678;
    public static final Integer cdAccountID = 23456789;
    public static final Integer checkingAccountID = 34567899;

    public static final Double savingsAccountApr = 1.0;
    public static final Double cdAccountApr = 2.0;
    public static final Double checkingAccountApr = 3.0;

    public static final Double savingsAccountBalance = 0.0;
    public static final Integer cdAccountBalance = 2000;
    public static final Double checkingAccountBalance = 0.0;

    Bank bank = new Bank();

    @BeforeEach
    void beforeEach() {

        bank.addAccount(Account.savingsAccount(savingsAccountID, savingsAccountApr));
        bank.addAccount(Account.cdAccount(cdAccountID, cdAccountApr, cdAccountBalance));
        bank.addAccount(Account.checkingAccount(checkingAccountID, checkingAccountApr));
    }

}
