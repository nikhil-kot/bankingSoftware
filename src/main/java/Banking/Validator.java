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

        } else if (action.equalsIgnoreCase("pass")) {
            return validatePassTimeCommand(input);

        } else if (action.equalsIgnoreCase("transfer")){
            return validateTransferCommand(input);

        } else {
            return false;
        }
    }

    public boolean validateTransferCommand(String input){
        String[] parts = input.split("\\s");
        if (parts.length <= 3){
            return false;
        }
        String fromId = parts[1];
        String toId = parts[2];
        if (!fromId.matches("\\d{8}") || bank.doesAccountExist(Integer.parseInt(fromId)) == false ||
                !toId.matches("\\d{8}") || bank.doesAccountExist(Integer.parseInt(toId)) == false ){
            return false;
        }
        if ((bank.getAccount(Integer.parseInt(fromId)).isCheckingAccount() ||
                bank.getAccount(Integer.parseInt(fromId)).isSavingsAccount() ||
                bank.getAccount(Integer.parseInt(toId)).isSavingsAccount() ||
                bank.getAccount(Integer.parseInt(fromId)).isCheckingAccount())){
            if (parts.length != 4){
                return false;
            }
            String transferAmount = parts[3];
            if (fromId.matches("\\d{8}") && (bank.doesAccountExist(Integer.parseInt(fromId)) == true) &&
                    toId.matches("\\d{8}") && (bank.doesAccountExist(Integer.parseInt(toId)) == true)) {
                if (bank.getAccount(Integer.parseInt(toId)) instanceof CdAccount) {
                    return false;

                } else if (bank.getAccount(Integer.parseInt(fromId)) instanceof CdAccount && !bank.getAccount(Integer.parseInt(fromId)).isWithDrawalAllowed()){
                    return false;

                } else if (bank.getAccount(Integer.parseInt(fromId)) instanceof CdAccount && bank.getAccount(Integer.parseInt(fromId)).isWithDrawalAllowed()
                        && bank.getAccount(Integer.parseInt(toId)) instanceof SavingsAccount){
                    if (isNumeric(transferAmount) && (Double.parseDouble(transferAmount) == bank.getAccountBalance(Integer.parseInt(fromId)))
                            && bank.getAccount(Integer.parseInt(fromId)).isWithDrawalAllowed()) {
                        return true;
                    } else {
                        return false;
                    }

                } else if (bank.getAccount(Integer.parseInt(fromId)) instanceof CheckingAccount && bank.getAccount(Integer.parseInt(toId)) instanceof CheckingAccount) {
                    if (isNumeric(transferAmount) && Double.parseDouble(transferAmount) <= 400 && Double.parseDouble(transferAmount) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (bank.getAccount(Integer.parseInt(fromId)) instanceof SavingsAccount && bank.getAccount(Integer.parseInt(toId)) instanceof SavingsAccount){
                    if (isNumeric(transferAmount) && Double.parseDouble(transferAmount) <= 1000 && Double.parseDouble(transferAmount) >= 0
                            &&  bank.getAccount(Integer.parseInt(fromId)).isWithDrawalAllowed()){
                        return true;
                    } else {
                        return false;
                    }
                } else if (bank.getAccount(Integer.parseInt(fromId)) instanceof CheckingAccount && bank.getAccount(Integer.parseInt(toId)) instanceof SavingsAccount) {
                    if (isNumeric(transferAmount) && Double.parseDouble(transferAmount) <= 400 && Double.parseDouble(transferAmount) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (bank.getAccount(Integer.parseInt(fromId)) instanceof SavingsAccount && bank.getAccount(Integer.parseInt(toId)) instanceof CheckingAccount) {
                    if (isNumeric(transferAmount) && Double.parseDouble(transferAmount) <= 1000 && Double.parseDouble(transferAmount) >= 0
                            &&  bank.getAccount(Integer.parseInt(fromId)).isWithDrawalAllowed()) {
                        return true;
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            }else {
                return false;
            }
        }
        return false;
    }
    public boolean validateWithdrawCommand(String input) {
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
                bank.getAccount(Integer.parseInt(id)).isSavingsAccount() || bank.getAccount(Integer.parseInt(id)).isCdAccount()) {

            if (parts.length != 3) {
                return false;
            }
            String withdrawAmount = parts[2];
            if (id.matches("\\d{8}") && (bank.doesAccountExist(Integer.parseInt(id)) == true)) {
                if (bank.getAccount(Integer.parseInt(id)) instanceof CheckingAccount) {
                    if (isNumeric(withdrawAmount) && Double.parseDouble(withdrawAmount) <= 400 && Double.parseDouble(withdrawAmount) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (bank.getAccount(Integer.parseInt(id)) instanceof SavingsAccount) {
                    if (isNumeric(withdrawAmount) && Double.parseDouble(withdrawAmount) <= 1000 && Double.parseDouble(withdrawAmount) >= 0 && bank.getAccount(Integer.parseInt(id)).isWithDrawalAllowed()) {
                        return true;
                    } else {
                        return false;
                    }
                } else if  (bank.getAccount(Integer.parseInt(id)) instanceof CdAccount) {
                    if (isNumeric(withdrawAmount) && Double.parseDouble(withdrawAmount) == bank.getAccountBalance(Integer.parseInt(id)) && bank.getAccount(Integer.parseInt(id)).isWithDrawalAllowed()){
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
            if (id.matches("\\d{8}") && (bank.doesAccountExist(Integer.parseInt(id)))) {
                if (bank.getAccount(Integer.parseInt(id)) instanceof CheckingAccount) {
                    if (isNumeric(depositAmount) && Double.parseDouble(depositAmount) <= 1000 && Double.parseDouble(depositAmount) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (bank.getAccount(Integer.parseInt(id)) instanceof SavingsAccount) {
                    if (isNumeric(depositAmount) && Double.parseDouble(depositAmount) <= 2500 && Double.parseDouble(depositAmount) >= 0) {
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

    public boolean validatePassTimeCommand(String input) {
        String[] parts = input.split("\\s");
        if (parts.length < 2) {
            return false;
        }
        String months = parts[1];
        if (MonthsValid(months)) {
                if (Integer.parseInt(months) <= 60 && Integer.parseInt(months) > 0) {
                    return true;
                } else {
                    return false;
                }

            }
        return false;
    }


    public boolean validateCreateCommand(String input) {
        String[] parts = input.split("\\s");


        if (parts.length < 2) {
            return false;
        }


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
            return isIdValid(id) && isAprValid(apr) && isCdAmountValid(amount);
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
            if (x < 0 || x > 10) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isCdAmountValid(String amount) {
        try {
            Integer.parseInt(amount);
            if (Integer.parseInt(amount) >= 1000 && Integer.parseInt(amount) <= 10000) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;

        }

    }

    public boolean MonthsValid(String amount) {
        try {
            Integer.parseInt(amount);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }

}
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}

