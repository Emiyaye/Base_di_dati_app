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
    public static final String OP1_INSERT_ABBONAMENTO = """
    INSERT INTO Abbonamento (codPagamento, codAccount, dataPagamento, dataScadenza, tipoAbbonamento)
    VALUES (1, ?, ?, ?, ?)
    """;
    public static final String OP1_UPDATE_CODABBONAMENTO = """
    UPDATE Account
    SET CodAbbonamentoAttivo = ?
    WHERE Account.email = ?
    """;
    public static final String OP2_GET_CODABBONAMENTO = """
    SELECT A.codAbbonamentoAttivo
    FROM Account A
    WHERE A.email = ?
    """;
    public static final String OP2_INVITE_ABBONAMENTO = """
    INSERT INTO Invito_abbonamento (codAccount, codAbbonamento)
    VALUES (?, ?)
    """;
    public static final String OP2_UPDATE_CODABBONAMENTO = OP1_UPDATE_CODABBONAMENTO;
    public static final String OP3_FOLLOW_ARTIST = """
    INSERT INTO Follow_Account (accountSeguito, accountSeguente)
    VALUES (?, ?)
    """;
    public static final String OP4_CREATE_PLAYLIST = """
    INSERT INTO Playlist (nome, descrizione, immagine, admin, privato, radio, accountCreatore)
    VALUES (?, ?, ?, False, ?, False, ?)
    """;
    public static final String OP4_INSERT_COLLABORATOR = """
    INSERT INTO Collaborazione_Playlist (CodAccount, CodPlaylist)
    VALUES (?, ?)
    """;
    public static final String OP5_DELETE_SONG = """
    DELETE FROM Dettaglio_Playlist D
    WHERE D.cod_Playlist = ?
    AND D.numero = ?
    """;
    public static final String OP7_SEARCH_SONG = """
    SELECT *
    FROM Brano B
    WHERE B.titolo LIKE ?
    """;
}
