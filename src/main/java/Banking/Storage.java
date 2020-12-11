package Banking;

import java.util.*;

public class Storage {
    public Validator validator;
    private List<String> invalidCommands = new ArrayList<String>();

    private Map<Integer, List<String>> accountId2ValidCommands = new HashMap<>();
    // id1 -> [command1, command2, command3]
    // id2 -> [command1, command2, command3]

    public void addInvalidCommand(String invalidCommand) {
        invalidCommands.add(invalidCommand);

    }

    public void addValidCommand(Integer accountId, String command){
        List validCommands = accountId2ValidCommands.get(accountId);

        if(validCommands == null){
            //the first time we are seeing a command for this account id
            validCommands = new ArrayList();
            accountId2ValidCommands.put(accountId, validCommands);
        }

        //or is this ok -- works for both the first and subsequent times
        validCommands.add(command);

    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }

    public List<String> getCommandHistory(Integer accountId){
        return accountId2ValidCommands.get(accountId);
    }

}

