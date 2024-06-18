package data;

public final class Queries {

    public static final String OP1_CREATE_ACCOUNT = """
            INSERT INTO Account (nickname, email, password, dataNascita, genere, link, DataCreazione, sospeso, nazione, admin)
            VALUES (?, ?, ?, ?, ?, ?, ?, False, ?, False)

            """;
    public static final String OP1_TIPO_PAGAMENTO = """
            INSERT INTO Tipo_Pagamento (codPagamento, account, tipo, numeroCarta*, scadenzaCarta*, dataCreazione, nazione, account)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String OP1_ABBONAMENTO = """
            INSERT INTO Abbonamento (codPagamento, account, dataPagamento, dataScadenza, tipoAbbonamento)
            VALUES (?, ?, ?, ?, ?)

            """;
}
