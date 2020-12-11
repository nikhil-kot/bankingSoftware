package Banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MasterControlTest {
    MasterControl masterControl;
    Bank bank;
    List<String> input;


    private void assertSingleCommand(String command, List<String> invalidCommands) {
        assertEquals(1, invalidCommands.size());
        assertEquals(command, invalidCommands.get(0));
    }

    @BeforeEach
    void setup() {
        input = new ArrayList<String>();
        input = new ArrayList<String>();
        Bank bank = new Bank();
        Storage storage = new Storage();
        Validator validator = new Validator(bank);
        masterControl = new MasterControl(bank, new Validator(bank), new CommandProcessor(bank, storage), storage);
    }


    @Test
    void typo_in_create_is_invalid() {
        input.add("creat checking 12345678 1.0");
        List<String> invalidCommands = masterControl.start(input);
        assertSingleCommand("creat checking 12345678 1.0", invalidCommands);
    }

    @Test
    void typo_in_deposit_is_invalid() {
        input.add("depositt 12345678 500");
        List<String> actual = masterControl.start(input);
        assertSingleCommand("depositt 12345678 500", actual);

    }

    @Test
    void create_is_valid() {
        input.add("create checking 12345888 1.0");
        List<String> actual = masterControl.start(input);
        assertEquals(1, actual.size());
        assertEquals("Checking 12345888 0.00 1.00", actual.get(0));
    }


    @Test
    void two_typo_commands_both_invalid() {
        List<String> input = new ArrayList<String>();
        input.add("depositt 12345678 1.0");
        input.add("creat checking 12345678 1.0");
        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("depositt 12345678 1.0", actual.get(0));
        assertEquals("creat checking 12345678 1.0", actual.get(1));

    }

    @Test
    void two_id() {
        List<String> input = new ArrayList<String>();
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");
        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
        assertEquals("create checking 12345678 1.0", actual.get(1));
    }


    @Test
    void invalid_withdraw_from_savings() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 12345678 1000");
        input.add("withdraw 12345678 100");
        input.add("withdraw 12345678 100");
        List<String> actual = masterControl.start(input);
        assertEquals(4, actual.size());
        assertEquals("Savings 12345678 900.00 1.00", actual.get(0));
        assertEquals("deposit 12345678 1000", actual.get(1));
        assertEquals("withdraw 12345678 100", actual.get(2));
        assertEquals("withdraw 12345678 100", actual.get(3));
    }

    @Test
    void two_withdraw_after_passtime() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 12345678 1000");
        input.add("withdraw 12345678 100");
        input.add("pass 1");
        input.add("withdraw 12345678 100");
        List<String> actual = masterControl.start(input);
        assertEquals(4, actual.size());
        assertEquals("Savings 12345678 800.75 1.00", actual.get(0));
        assertEquals("deposit 12345678 1000", actual.get(1));
        assertEquals("withdraw 12345678 100", actual.get(2));
        assertEquals("withdraw 12345678 100", actual.get(3));



    }

    @Test
    void three_withdraw_after_passtime_invalid() {
        input.add("create savings 12345678 1.0");
        input.add("deposit 12345678 1000");
        input.add("withdraw 12345678 100");
        input.add("pass 1");
        input.add("withdraw 12345678 100");
        input.add("withdraw 12345678 100");
        List<String> actual = masterControl.start(input);
        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 800.75 1.00", actual.get(0));
        assertEquals("deposit 12345678 1000", actual.get(1));
        assertEquals("withdraw 12345678 100", actual.get(2));
        assertEquals("withdraw 12345678 100", actual.get(3));
        assertEquals("withdraw 12345678 100", actual.get(4));

    }

    @Test
    void typo_in_withdraw_is_invalid() {
        input.add("create checking 12345678 1.0");
        input.add("deposit 12345678 1000");
        input.add("withdraww 12345678 100");
        List<String> actual = masterControl.start(input);
        assertEquals("Checking 12345678 1000.00 1.00", actual.get(0));
        assertEquals("deposit 12345678 1000", actual.get(1));
        assertEquals("withdraww 12345678 100", actual.get(2));
    }

    @Test
    void withdraw_account_does_not_exist() {
        input.add("create checking 12345678 1.0");
        input.add("deposit 12345678 1000");
        input.add("withdraw 22345678 100");
        List<String> actual = masterControl.start(input);
        assertEquals(3, actual.size());
        assertEquals("Checking 12345678 1000.00 1.00", actual.get(0));
        assertEquals("deposit 12345678 1000", actual.get(1));
        assertEquals("withdraw 22345678 100", actual.get(2));
    }

    @Test
    void withdraw_from_cd_before_12_months() {
        List<String> commands = new ArrayList<String>();
        input.add("create cd 12345678 1.0 2000");
        input.add("withdraw 12345678 2000");
        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("Cd 12345678 2000.00 1.00", actual.get(0));
        assertEquals("withdraw 12345678 2000", actual.get(1));
    }

    @Test
    void withdraw_from_cd_after_12_months() {
        input.add("create cd 12345678 1.2 2000");
        input.add("pass 12");
        input.add("withdraw 12345678 2098.29");
        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("Cd 12345678 0.00 1.20", actual.get(0));
        assertEquals("withdraw 12345678 2098.29", actual.get(1));

    }

    @Test
    void invalid_amount_withdraw_from_cd_after_12_months() {
        input.add("create cd 12345678 1.2 2000");
        input.add("pass 12");
        input.add("withdraw 12345678 2000");
        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("Cd 12345678 2098.29 1.20", actual.get(0));
        assertEquals("withdraw 12345678 2000", actual.get(1));
    }

    @Test
    void invalid_withdraw_from_cd_after_11_months() {
        input.add("create cd 12345678 1.2 2000");
        input.add("pass 11");
        input.add("withdraw 12345678 2089.92");
        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("Cd 12345678 2089.92 1.20", actual.get(0));
        assertEquals("withdraw 12345678 2089.92", actual.get(1));

    }

    @Test
    void invalid_transfer_amount_twice_in_month_for_savings(){
        input.add("create savings 10000000 1.2");
        input.add("create savings 20000000 1.2");
        input.add("deposit 10000000 500");
        input.add("transfer 10000000 20000000 100");
        input.add("transfer 10000000 20000000 200");
        List<String> actual = masterControl.start(input);
        assertEquals(6, actual.size());
        assertEquals("Savings 10000000 400.00 1.20", actual.get(0));
        assertEquals("deposit 10000000 500", actual.get(1));
        assertEquals("transfer 10000000 20000000 100", actual.get(2));
        assertEquals("Savings 20000000 100.00 1.20", actual.get(3));
        assertEquals("transfer 10000000 20000000 100", actual.get(4));
        assertEquals("transfer 10000000 20000000 200", actual.get(5));

    }

    @Test
    void invalid_transfer_into_cd(){
        input.add("create savings 10000000 1.2");
        input.add("create cd 20000000 1.2 5000");
        input.add("deposit 10000000 800");
        input.add("transfer 10000000 20000000 100");
        List<String> actual = masterControl.start(input);
        assertEquals(4, actual.size());
        assertEquals("Savings 10000000 800.00 1.20", actual.get(0));
        assertEquals("deposit 10000000 800", actual.get(1));
        assertEquals("Cd 20000000 5000.00 1.20", actual.get(2));
        assertEquals("transfer 10000000 20000000 100", actual.get(3));
    }

    @Test
    void transfer_from_checking_to_savings(){
        input.add("create checking 10000000 1.2");
        input.add("create savings 20000000 1.2");
        input.add("deposit 10000000 200");
        input.add("deposit 20000000 200");
        input.add("transfer 10000000 20000000 100");
        List<String> actual = masterControl.start(input);
        assertEquals(6, actual.size());
        assertEquals("Checking 10000000 100.00 1.20", actual.get(0));
        assertEquals("deposit 10000000 200", actual.get(1));
        assertEquals("transfer 10000000 20000000 100", actual.get(2));
        assertEquals("Savings 20000000 300.00 1.20", actual.get(3));
        assertEquals("deposit 20000000 200", actual.get(4));
        assertEquals("transfer 10000000 20000000 100", actual.get(5));
    }

    @Test
    void transfer_from_cd_to_savings_before_12_months(){
        input.add("create cd 10000000 1.2 1000");
        input.add("create savings 20000000 1.2");
        input.add("transfer 10000000 20000000 1000");
        List<String> actual = masterControl.start(input);
        assertEquals(3, actual.size());
        assertEquals("Cd 10000000 1000.00 1.20", actual.get(0));
        assertEquals("Savings 20000000 0.00 1.20", actual.get(1));
        assertEquals("transfer 10000000 20000000 1000", actual.get(2));
    }

    @Test
    void invalid_transfer_into_to_savings_from_checking_after_one_withdrawal(){
        input.add("create checking 10000000 1.2");
        input.add("create savings 20000000 1.2");
        input.add("Deposit 20000000 500");
        input.add("withdraw 20000000 200");
        input.add("transfer 20000000 10000000 100");
        List<String> actual = masterControl.start(input);
        assertEquals(5, actual.size());
        assertEquals("Checking 10000000 0.00 1.20", actual.get(0));
        assertEquals("Savings 20000000 300.00 1.20", actual.get(1));
        assertEquals("Deposit 20000000 500", actual.get(2));
        assertEquals("withdraw 20000000 200", actual.get(3));
        assertEquals("transfer 20000000 10000000 100", actual.get(4));
    }

    @Test
    void just_pass_time(){
        input.add("Pass 12");
        List<String> actual = masterControl.start(input);
        assertEquals(0, actual.size());;
    }




    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);
        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

}
