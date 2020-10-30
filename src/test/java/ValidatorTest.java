import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest extends BaseTest {

    Validator validator = new Validator(bank);

    @Test
    void invalid_Action() {
        assertFalse(validator.isValid("transfer 1234 12345679 0.07"));
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
    void invalid_Apr() {
        assertFalse(validator.isValid("create savings 12345679 55"));
    }

    @Test
    void valid_Apr() {
        assertTrue(validator.isValid("create savings 12345679 0.06"));
    }

    @Test
    void create_valid_Cd_Account() {
        assertTrue(validator.isValid("create cd 12345679 0.06 1000"));
    }

    @Test
    void invalid_Cd_Account_amount() {
        assertFalse(validator.isValid("create cd 12345679 0.06 999"));
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
