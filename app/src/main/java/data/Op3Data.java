package data;

public class Op3Data {
    private final String accountSeguito;
    private final String accountSeguente;

    public Op3Data(final String accountSeguito, final String accountSeguente) {
        this.accountSeguito = accountSeguito;
        this.accountSeguente = accountSeguente;
    }

    public String getAccountSeguito() {
        return accountSeguito;
    }

    public String getAccountSeguente() {
        return accountSeguente;
    }
}
