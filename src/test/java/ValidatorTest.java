import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest extends BaseTest {

    Validator validator = new Validator(bank);

    @Test
    void invalid_Action() {
        assertFalse(validator.isValid("delete 1234 12345679 0.07"));
    }

    @Test
    void typo_in_create() {
        assertFalse(validator.isValid("creete checking 12345679 0.07"));
    }

    @Test
    void typo_in_account_type() {
        assertFalse(validator.isValid("create checing 12345679 0.07"));
    }

    @Test
    void missing_apr() {
        assertFalse(validator.isValid("create savings 12345679"));
    }

    @Test
    void missing_id() {
        assertFalse(validator.isValid("create 12345679 0.07"));
    }

    @Test
    void missing_action() {
        assertFalse(validator.isValid("checking 12345679 0.07"));
    }

    @Test
    void account_type() {
        assertFalse(validator.isValid("create 12345679 0.07"));
    }

    @Test
    void invalid_Apr() {
        assertFalse(validator.isValid("create savings 12345679 55"));
    }

    @Test
    void valid_Apr() {
        assertTrue(validator.isValid("create savings 12345679 0.06"));
    }

    @Test
    void negative_Apr() {
        assertFalse(validator.isValid("create savings 12345679 -0.06"));
    }

    @Test
    void create_valid_Cd_Account() {
        assertTrue(validator.isValid("create cd 12345679 0.06 1000"));
    }

    @Test
    void create_valid_checking_Account() {
        assertTrue(validator.isValid("create checking 12345679 0.06"));
    }

    @Test
    void create_valid_savings_Account() {
        assertTrue(validator.isValid("create savings 12345679 0.06"));
    }

    @Test
    void create_duplicate_savings_Account() {
        bank.addAccount(Account.savingsAccount(savingsAccountID, savingsAccountApr));
        assertFalse(validator.isValid("create savings 12345678 0.06"));
    }

    @Test
    void create_Account_with_letter_in_id() {
        assertFalse(validator.isValid("create savings 12345h8 0.06"));
    }

    @Test
    void missing_Cd_Account_amount() {
        assertFalse(validator.isValid("create cd 12345679 0.06"));
    }

    @Test
    void invalid_Cd_Account_amount() {
        assertFalse(validator.isValid("create cd 12345679 0.06 999"));
    }

    @Test
    void negative_Cd_Account_amount() {
        assertFalse(validator.isValid("create cd 12345679 0.06 -999"));
    }

    @Test
    void case_sensitive() {
        assertTrue(validator.isValid("CreatE cd 12345679 0.06 1000"));
    }

    @Test
    void savings_Account_with_amount() {
        assertFalse(validator.isValid("create savings 12345679 0.06 100"));
    }

    @Test
    void deposit_into_cd() {
        bank.addAccount(Account.cdAccount(23456789, cdAccountApr, cdAccountBalance));
        assertFalse(validator.isValid("deposit 23456789 1000"));
    }

    @Test
    void isIdValid() {
        assertFalse(validator.isIdValid("1234567"));
        assertFalse(validator.isIdValid("12345678"));
        assertFalse(validator.isIdValid("123456789"));
        assertFalse(validator.isIdValid("1234567a"));
        assertFalse(validator.isIdValid("1234567.8"));
    }

}
