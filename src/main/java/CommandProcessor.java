public class CommandProcessor {
    public Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public boolean processInput(String input) {
        String action = input.split("\\s")[0];

        if (action.equalsIgnoreCase("create")) {
            CreateAccount(input);

        } else if (action.equalsIgnoreCase("deposit")) {
            DepositInAccount(input);

        }
        return false;
    }


    public void CreateAccount(String input) {
        String[] parts = input.split("\\s");

        if (parts.length == 4) {
            String type = parts[1];
            String id = parts[2];
            String apr = parts[3];
            if (type.equals("checking")) {
                bank.addAccount(Account.checkingAccount(Integer.parseInt(id), Double.parseDouble(apr)));
            } else if (type.equals("savings")) {
                bank.addAccount(Account.savingsAccount(Integer.parseInt(id), Double.parseDouble(apr)));
            }
        } else if (parts.length == 5) {
            String type = parts[1];
            String id = parts[2];
            String apr = parts[3];
            String amount = parts[4];
            if (type.equals("cd")) {
                bank.addAccount(Account.cdAccount(Integer.parseInt(id), Double.parseDouble(apr), Double.parseDouble(amount)));
            }

        }
    }

    public void DepositInAccount(String input) {
        String[] parts = input.split("\\s");
        String id = parts[1];
        String amount = parts[2];
        if (Integer.parseInt(amount) > 0) {
            bank.makeDeposit(Integer.parseInt(id), Double.parseDouble(amount));
        }
    }
}