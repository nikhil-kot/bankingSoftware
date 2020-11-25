package Banking;

public class Validator {
    public Bank bank;

    public Validator(Bank bank) {
        this.bank = bank;
    }

    public boolean isValid(String input) {
        String action = input.split("\\s")[0];

        if (action.equalsIgnoreCase("create")) {
            return validateCreateCommand(input);

        } else if (action.equalsIgnoreCase("deposit")) {
            return validateDepositCommand(input);

        } else if (action.equalsIgnoreCase("withdraw")) {
            return validateWithdrawCommand(input);

        } else if (action.equalsIgnoreCase("pass time")){
            return validatePassTimeCommand(input);

        } else {
            return false;
        }
    }


    public boolean validateWithdrawCommand(String input){
        String[] parts = input.split("\\s");
        if (parts.length <= 2) {
            return false;
        }
        String id = parts[1];
        if (!id.matches("\\d{8}") ||
                (bank.doesAccountExist(Integer.parseInt(id)) == false)) {
            return false;
        }
        if (bank.getAccount(Integer.parseInt(id)).isCheckingAccount() ||
                bank.getAccount(Integer.parseInt(id)).isSavingsAccount()) {

            if (parts.length != 3) {
                return false;
            }
            String withdrawAmount = parts[2];
            if (id.matches("\\d{8}") && (bank.doesAccountExist(Integer.parseInt(id)) == true)) {
                if (CheckingAccount.class.isInstance(bank.getAccount(Integer.parseInt(id)))) {
                    if (isAmountValid(withdrawAmount) && Integer.parseInt(withdrawAmount) <= 400 && Integer.parseInt(withdrawAmount) > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (SavingsAccount.class.isInstance(bank.getAccount(Integer.parseInt(id)))) {
                    if (isAmountValid(withdrawAmount) && Integer.parseInt(withdrawAmount) <= 1000 && Integer.parseInt(withdrawAmount) > 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }




    public boolean validateDepositCommand(String input) {
        String[] parts = input.split("\\s");
        if (parts.length <= 2) {
            return false;
        }
        String id = parts[1];
        if (!id.matches("\\d{8}") ||
                (bank.doesAccountExist(Integer.parseInt(id)) == false)) {
            return false;
        }

        if (bank.getAccount(Integer.parseInt(id)).isCheckingAccount() ||
                bank.getAccount(Integer.parseInt(id)).isSavingsAccount()) {

            if (parts.length != 3) {
                return false;
            }
            String depositAmount = parts[2];
            if (id.matches("\\d{8}") && (bank.doesAccountExist(Integer.parseInt(id)) == true)) {
                if (CheckingAccount.class.isInstance(bank.getAccount(Integer.parseInt(id)))) {
                    if (isAmountValid(depositAmount) && Integer.parseInt(depositAmount) <= 1000 && Integer.parseInt(depositAmount) > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (SavingsAccount.class.isInstance(bank.getAccount(Integer.parseInt(id)))) {
                    if (isAmountValid(depositAmount) && Integer.parseInt(depositAmount) <= 2500 && Integer.parseInt(depositAmount) > 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean validatePassTimeCommand(String input){
        String[] parts = input.split("\\s");
        if (parts.length < 2) {
            return false;
        }
        String months = parts[1];

        if (Integer.parseInt(months) % 1 == 0){
            if (12 <= Integer.parseInt(months)  && Integer.parseInt(months) > 0){
                return true;
            }else{
                return false;
            }

        }else {
            return false;
        }

    }


    public boolean validateCreateCommand(String input) {
        String[] parts = input.split("\\s");

        //accountType is required. If there are fewer than 2 values, it implies account type was not provided
        if (parts.length < 2) {
            return false;
        }
        //assign to respective variables

        String accountType = parts[1];

        if (accountType.equalsIgnoreCase("checking") ||
                accountType.equalsIgnoreCase("savings")) {
            if (parts.length != 4) {
                return false;
            }
            String id = parts[2];
            String apr = parts[3];

            boolean isIdValid = isIdValid(id);
            boolean isAprValid = isAprValid(apr);
            return isIdValid && isAprValid;

        } else if (accountType.equalsIgnoreCase("cd")) {
            if (parts.length != 5) {
                return false;
            }
            String id = parts[2];
            String apr = parts[3];
            String amount = parts[4];
            return isIdValid(id) && isAprValid(apr) && isAmountValid(amount);
        } else {
            return false;
        }
    }


    public boolean isIdValid(String id) {
        return id.matches("\\d{8}") &&
                (bank.doesAccountExist(Integer.parseInt(id)) == false);
    }

    public boolean isAprValid(String apr) {
        try {
            double x = Double.parseDouble(apr);
            if (x < 0 || x > 100) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isAmountValid(String amount) {
        try {
            Integer.parseInt(amount);
            if (Integer.parseInt(amount) >= 1000) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;

        }

    }

}

