package Banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
