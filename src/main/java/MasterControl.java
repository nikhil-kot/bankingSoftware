import java.util.List;

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

        return storage.getInvalidCommands();
    }
}
