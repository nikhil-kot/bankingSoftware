package Banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    Storage storage = new Storage();


    @Test
    void test_with_two_invalid_commands() {
        storage.addInvalidCommand("command1");
        storage.addInvalidCommand("command2");
        assertEquals("command1", storage.getInvalidCommands().get(0));
        assertEquals("command2", storage.getInvalidCommands().get(1));
        assertTrue(storage.getInvalidCommands().size() == 2);
    }


}
