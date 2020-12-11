package Banking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MasterControl {
    Bank bank;
    CommandProcessor commandProcessor;
    Validator validator;
    Storage storage;

    public MasterControl(Bank bank, Validator validator, CommandProcessor commandProcessor, Storage storage) {
        this.bank = bank;
        this.commandProcessor = commandProcessor;
        this.validator = validator;
        this.storage = storage;

    }

    public List<String> start(List<String> commands) {

        for (String command : commands) {
            if (!validator.isValid(command)) {
                storage.addInvalidCommand(command);
            } else {

                commandProcessor.processInput(command);
            }
        }
        Set<Integer> AccountIDs = bank.getAllAccountIds();

        List output = new ArrayList();
        DecimalFormat df = new DecimalFormat("0.00");

        for (Integer Id : AccountIDs){

            //current state of the account
            Account account = bank.getAccount(Id);
            String currentState = account.getType() + " "  + Id + " " + df.format(account.getAccountBalance())
                    + " " + df.format(account.getApr());
            output.add(currentState);

            List validCommands = storage.getCommandHistory(Id);
            if(validCommands!=null){
                output.addAll(validCommands);
            }
        }

        output.addAll(storage.getInvalidCommands());
        return output;

//        return storage.getInvalidCommands();
    }
}
