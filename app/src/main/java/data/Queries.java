package data;

public final class Queries {

    public static final String OP1_CREATE_ACCOUNT = """
    INSERT INTO Account (nickname, email, password, dataNascita, genere, link, DataCreazione, sospeso, nazione, admin)
    VALUES (?, ?, ?, ?, ?, ?, ?, False, ?, False)
    """;
    public static final String OP1_TIPO_PAGAMENTO = """
    INSERT INTO Tipo_Pagamento (codPagamento, codAccount, tipo, numeroCarta, scadenzaCarta, dataCreazione, nazione)
    VALUES (1, ?, ?, ?, ?, ?, ?)
    """;
    public static final String OP1_ABBONAMENTO = """
    INSERT INTO Abbonamento (codPagamento, codAccount, dataPagamento, dataScadenza, tipoAbbonamento)
    VALUES (1, ?, ?, ?, ?)
    """;
    public static final String OP1_UPDATE_CODABBONAMENTO = """
    UPDATE Account
    SET CodAbbonamentoAttivo = ?
    WHERE Account.email = ?
    """;
    public static final String OP3_FOLLOW_ARTIST = """
    INSERT INTO Follow_Account (accountSeguito, accountSeguente)
    VALUES (?, ?)
    """;
    public static final String OP7_SEARCH_SONG = """
    SELECT *
    FROM Brano B
    WHERE B.titolo LIKE ?
    """;
}
