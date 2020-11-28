package Banking;

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

        } else if (action.equalsIgnoreCase("withdraw")){
            WithdrawFromAccount(input);
        } else if (action.equalsIgnoreCase("pass")){
            PassTime(input);
        } else if (action.equalsIgnoreCase("transfer")){
            Transfer(input);
        }

        return false;
    }

    public void Transfer(String input){
        String[] parts = input.split("\\s");
        String fromId = parts[1];
        String toId = parts[2];
        String amount = parts[3];
        if (Double.parseDouble(amount) > 0){
            bank.makeWithdrawal(Integer.parseInt(fromId), Double.parseDouble(amount));
            bank.makeDeposit(Integer.parseInt(toId), Double.parseDouble(amount));
        }
    }


    public void WithdrawFromAccount(String input){
        String[] parts = input.split("\\s");
        String id = parts[1];
        String amount = parts[2];
        if (Double.parseDouble(amount) > 0) {
            bank.makeWithdrawal(Integer.parseInt(id), Double.parseDouble(amount));

        }
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

    public void PassTime( String input){
        String[] parts = input.split("\\s");
        Integer months = Integer.parseInt(parts[1]);

        for(Integer accountId: bank.getAllAccountIds()){
            Account account = bank.getAccount(accountId);

            for(int month=1; month <= months; month++){

                if(account.getAccountBalance() == 0 ){
                    bank.closeAccount(accountId);
                    continue;
                }

                if(account.getAccountBalance() < 100){
                    account.deduct(25.0);
                }

                account.addInterestToBalance();

            }

            account.passMonths(months);




        }
    }
}