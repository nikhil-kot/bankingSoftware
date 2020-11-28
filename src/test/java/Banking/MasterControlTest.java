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

    private void assertSingleCommand(String command, List<String> invalidCommands) {
        assertEquals(1, invalidCommands.size());
        assertEquals(command, invalidCommands.get(0));
    }

    @BeforeEach
    void setup() {
        Bank bank = new Bank();
        Validator validator = new Validator(bank);
        masterControl = new MasterControl(bank, new Validator(bank), new CommandProcessor(bank), new Storage());
    }


    @Test
    void typo_in_create_is_invalid() {
        List<String> commands = new ArrayList<String>();
        commands.add("creat checking 12345678 1.0");
        List<String> invalidCommands = masterControl.start(commands);
        assertSingleCommand("creat checking 12345678 1.0", invalidCommands);
    }

    @Test
    void typo_in_deposit_is_invalid() {
        List<String> commands = new ArrayList<String>();
        commands.add("depositt 12345678 500");
        List<String> invalidCommands = masterControl.start(commands);
        assertSingleCommand("depositt 12345678 500", invalidCommands);

    }

    @Test
    void create_is_valid() {
        List<String> commands = new ArrayList<String>();
        commands.add("create checking 12345888 1.0");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(0, invalidCommands.size());
    }


    @Test
    void two_typo_commands_both_invalid() {
        List<String> commands = new ArrayList<String>();
        commands.add("depositt 12345678 1.0");
        commands.add("creat checking 12345678 1.0");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(2, invalidCommands.size());
        assertEquals("depositt 12345678 1.0", invalidCommands.get(0));
        assertEquals("creat checking 12345678 1.0", invalidCommands.get(1));

    }

    @Test
    void two_id() {
        List<String> commands = new ArrayList<String>();
        commands.add("create checking 12345678 1.0");
        commands.add("create checking 12345678 1.0");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(1, invalidCommands.size());
        assertEquals("create checking 12345678 1.0", invalidCommands.get(0));
    }


    @Test
    void two_withdraw_from_savings() {
        List<String> commands = new ArrayList<String>();
        commands.add("create savings 12345678 1.0");
        commands.add("deposit 12345678 1000");
        commands.add("withdraw 12345678 100");
        commands.add("withdraw 12345678 100");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(1, invalidCommands.size());
        assertEquals("withdraw 12345678 100", invalidCommands.get(0));
    }

    @Test
    void two_withdraw_after_passtime() {
        List<String> commands = new ArrayList<String>();
        commands.add("create savings 12345678 1.0");
        commands.add("deposit 12345678 1000");
        commands.add("withdraw 12345678 100");
        commands.add("pass 1");
        commands.add("withdraw 12345678 100");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(0, invalidCommands.size());

    }

    @Test
    void three_withdraw_after_passtime_invalid() {
        List<String> commands = new ArrayList<String>();
        commands.add("create savings 12345678 1.0");
        commands.add("deposit 12345678 1000");
        commands.add("withdraw 12345678 100");
        commands.add("pass 1");
        commands.add("withdraw 12345678 100");
        commands.add("withdraw 12345678 100");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(1, invalidCommands.size());
        assertEquals("withdraw 12345678 100", invalidCommands.get(0));

    }

    @Test
    void typo_in_withdraw_is_invalid() {
        List<String> commands = new ArrayList<String>();
        commands.add("create checking 12345678 1.0");
        commands.add("deposit 12345678 1000");
        commands.add("withdraww 12345678 100");
        List<String> invalidCommands = masterControl.start(commands);
        assertSingleCommand("withdraww 12345678 100", invalidCommands);
    }

    @Test
    void withdraw_account_does_not_exist() {
        List<String> commands = new ArrayList<String>();
        commands.add("create checking 12345678 1.0");
        commands.add("deposit 12345678 1000");
        commands.add("withdraw 22345678 100");
        List<String> invalidCommands = masterControl.start(commands);
        assertSingleCommand("withdraw 22345678 100", invalidCommands);
    }

    @Test
    void withdraw_from_cd_before_12_months() {
        List<String> commands = new ArrayList<String>();
        commands.add("create cd 12345678 1.0 2000");
        commands.add("withdraw 12345678 2000");
        List<String> invalidCommands = masterControl.start(commands);
        assertSingleCommand("withdraw 12345678 2000", invalidCommands);
    }

    @Test
    void withdraw_from_cd_after_12_months() {
        List<String> commands = new ArrayList<String>();
        commands.add("create cd 12345678 1.2 2000");
        commands.add("pass 12");
        commands.add("withdraw 12345678 2098.29");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(0, invalidCommands.size());

    }

    @Test
    void invalid_amount_withdraw_from_cd_after_12_months() {
        List<String> commands = new ArrayList<String>();
        commands.add("create cd 12345678 1.2 2000");
        commands.add("pass 12");
        commands.add("withdraw 12345678 2000");
        List<String> invalidCommands = masterControl.start(commands);
        assertSingleCommand("withdraw 12345678 2000", invalidCommands);
    }

    @Test
    void invalid_withdraw_from_cd_after_11_months() {
        List<String> commands = new ArrayList<String>();
        commands.add("create cd 12345678 1.2 2000");
        commands.add("pass 11");
        commands.add("withdraw 12345678 2022.12");
        List<String> invalidCommands = masterControl.start(commands);
        assertSingleCommand("withdraw 12345678 2022.12", invalidCommands);

    }

    @Test
    void invalid_transfer_amount_twice_in_month_for_savings(){
        List<String> commands = new ArrayList<String>();
        commands.add("create savings 10000000 1.2");
        commands.add("create savings 20000000 1.2");
        commands.add("deposit 10000000 500");
        commands.add("transfer 10000000 20000000 100");
        commands.add("transfer 10000000 20000000 200");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(1, invalidCommands.size());
        assertSingleCommand("transfer 10000000 20000000 200", invalidCommands);
    }

    @Test
    void invalid_transfer_into_cd(){
        List<String> commands = new ArrayList<String>();
        commands.add("create savings 10000000 1.2");
        commands.add("create cd 20000000 1.2 5000");
        commands.add("deposit 10000000 800");
        commands.add("transfer 10000000 20000000 100");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(1, invalidCommands.size());
        assertSingleCommand("transfer 10000000 20000000 100", invalidCommands);
    }

    @Test
    void transfer_from_checking_to_savings(){
        List<String> commands = new ArrayList<String>();
        commands.add("create checking 10000000 1.2");
        commands.add("create savings 20000000 1.2");
        commands.add("deposit 10000000 200");
        commands.add("deposit 20000000 200");
        commands.add("transfer 10000000 20000000 100");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(0, invalidCommands.size());

    }

    @Test
    void transfer_from_cd_to_savings_before_12_months(){
        List<String> commands = new ArrayList<String>();
        commands.add("create cd 10000000 1.2 1000");
        commands.add("create savings 20000000 1.2");
        commands.add("transfer 10000000 20000000 1000");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(1, invalidCommands.size());
        assertSingleCommand("transfer 10000000 20000000 1000", invalidCommands);

    }

    @Test
    void transfer_from_cd_to_savings_after_12_months(){
        List<String> commands = new ArrayList<String>();
        commands.add("create cd 10000000 1.2 1000");
        commands.add("create savings 20000000 1.2");
        commands.add("deposit 20000000 200");
        commands.add("pass 12");
        commands.add("transfer 10000000 20000000 1049.15");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(0, invalidCommands.size());


    }

    @Test
    void transfer_into_to_savings_from_checking_after_one_wothdrawal(){
        List<String> commands = new ArrayList<String>();
        commands.add("create checking 10000000 1.2");
        commands.add("create savings 20000000 1.2");
        commands.add("withdraw 20000000 200");
        commands.add("transfer 20000000 10000000 100");
        List<String> invalidCommands = masterControl.start(commands);
        assertEquals(1, invalidCommands.size());


    }





}
