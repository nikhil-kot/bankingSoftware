package Banking;

public class Input {
    private String action;
    private String accountType;
    private Integer id;
    private Double apr;
    private Double amount;

    public Input(String action, String accountType, Integer id, Double apr) {
        this.action = action;
        this.accountType = accountType;
        this.id = id;
        this.apr = apr;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }
}
