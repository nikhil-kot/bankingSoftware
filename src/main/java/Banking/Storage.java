package Banking;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    public Validator validator;
    private List<String> invalidCommands = new ArrayList<String>();

    public void addInvalidCommand(String invalidCommand) {
        invalidCommands.add(invalidCommand);

    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }

}

