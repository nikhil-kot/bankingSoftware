package Banking;

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
    void missing_create_command() {
        assertFalse(validator.isValid("checking 12345679 0.07"));
    }

    @Test
    void account_type() {
        assertFalse(validator.isValid("create 12345679 0.07"));
    }

    @Test
    void invalid_Apr() {
        assertFalse(validator.isValid("create savings 12345679 101"));
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
        bank.addAccount(Account.savingsAccount(33333333, 0.07));
        assertFalse(validator.isValid("create savings 33333333 0.06"));
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
    void test_case_sensitive_for_deposit() {
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
    void deposit_into_checking() {
        bank.addAccount(Account.checkingAccount(12222222, checkingAccountApr));
        assertTrue(validator.isValid("deposit 12222222 1000"));
    }

    @Test
    void deposit_invalid_amount_into_checking() {
        bank.addAccount(Account.checkingAccount(12222222, checkingAccountApr));
        assertFalse(validator.isValid("deposit 23456789 1100"));
    }

    @Test
    void deposit_invalid_amount_into_savings() {
        bank.addAccount(Account.savingsAccount(12222222, savingsAccountApr));
        assertFalse(validator.isValid("deposit 23456789 2600"));
    }

    @Test
    void deposit_negative_amount_into_checking() {
        bank.addAccount(Account.checkingAccount(12222222, checkingAccountApr));
        assertFalse(validator.isValid("deposit 12222222 -1100"));
    }

    @Test
    void missing_deposit_command() {
        assertFalse(validator.isValid("23456789 1000"));
    }

    @Test
    void id_does_not_exist() {
        assertFalse(validator.isValid("deposit 11111111 1000"));
    }

    @Test
    void typo_in_deposit() {
        assertFalse(validator.isValid("depositt 12345678 1000"));
    }

    @Test
    void test_case_insensitive_for_deposit() {
        bank.addAccount(Account.checkingAccount(12222222, checkingAccountApr));
        assertTrue(validator.isValid("Deposit 12222222 1000"));
    }

    @Test
    void letter_in_deposit_amount() {
        assertFalse(validator.isValid("deposit 12345678 10A0"));
    }

    @Test
    void missing_id_for_deposit() {
        bank.addAccount(Account.checkingAccount(12222222, checkingAccountApr));
        assertFalse(validator.isValid("deposit 1000"));
    }

    @Test
    void missing_amount_for_deposit() {
        bank.addAccount(Account.checkingAccount(12222222, checkingAccountApr));
        assertFalse(validator.isValid("deposit 12222222"));
    }

}
