public class Validator {
    private Bank bank;

    public Validator(Bank bank) {
        this.bank = bank;
    }

    public boolean isValid(String input) {
        String action = input.split("\\s")[0];

        if (action.equalsIgnoreCase("create")) {
            return validateCreateCommand(input);

        } else if (action.equalsIgnoreCase("deposit")) {
            return validateDepositCommand(input);

        } else {

            //invalid action
            System.out.println("Invalid action " + action);
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
            return isIdValid(id) && isAprValid(apr);

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

    public boolean validateDepositCommand(String input) {
        String[] parts = input.split("\\s");
        if (parts.length < 2) {
            return false;
        }

        String accountType = "";
        if (accountType.equalsIgnoreCase("checking") ||
                accountType.equalsIgnoreCase("savings")) {
            if (parts.length != 3) {
                return false;
            }
            String id = parts[2];
            String depositAmount = parts[3];
            if (isIdValid(id)) {
                if (accountType == "checking") {
                    if (Integer.parseInt(depositAmount) > 1000 || Integer.parseInt(depositAmount) < 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (accountType == "savings") {
                    if (Integer.parseInt(depositAmount) > 2500 || Integer.parseInt(depositAmount) < 0) {
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

    public boolean isIdValid(String id) {
        return id.matches("\\d{8}") &&
                (bank.doesAccountExist(Integer.parseInt(id)) == false);
    }

    public boolean isAprValid(String apr) {
        try {
            double x = Double.parseDouble(apr);
            if (x == (int) x) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isAmountValid(String amount) {
        if (Integer.parseInt(amount) >= 1000) {
            return true;
        } else {
            return false;
        }
    }


}


