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
    public static final String OP5_DELETE_SONG =
    """
    DELETE FROM Dettaglio_Playlist
    WHERE codPlaylist = ?
    AND numero = ?
    """;
    public static final String OP6_GET_ARTIST = """
    SELECT AC.nickname AS ArtistiSeguiti
    FROM Account AC, Artista AR, Follow_Account F
    WHERE F.accountSeguito = AC.email
    AND AR.email = AC.email
    AND F.AccountSeguente = ?
    """;
    public static final String OP6_GET_ALBUM = """
    SELECT P.nome AS AlbumSeguiti, AC.nickname AS AutoreAlbum
    FROM Pubblicazione P, Follow_Pubblicazione FP, Artista AR, Account AC
    WHERE P.codPubblicazione = FP.codPubblicazione
    AND P.codArtista = AR.email
    AND AC.email = AR.email
    AND FP.codAccount = ?        
    """;
    public static final String OP6_GET_PLAYLIST = """
    SELECT P.nome AS PlaylistSeguiti,  A.nickname
    FROM Playlist P, Follow_Playlist FP, Account A
    WHERE P.codPlaylist = FP.codPlaylist
    AND P.accountCreatore = A.email
    AND FP.codAccount = ?    
    """;
    public static final String OP7_SEARCH_SONG = """
    SELECT *
    FROM Brano B
    WHERE B.titolo LIKE ?
    """;
    public static final String OP_9_VIEW_SUB_HISTORY = """
    SELECT TA.nome AS TipoAbbonamento, TA.durataMesi, A.dataPagamento, A.dataScadenza, A.codAccount AS AccountPagante
    FROM Tipo_abbonamento TA, Tipo_pagamento TP, Abbonamento A LEFT OUTER JOIN Invito_abbonamento I ON (I.codAbbonamento = A.codAbbonamento)
    WHERE A.tipoAbbonamento = TA.codTipoAbbonamento
    AND A.codAccount = TP.codAccount
    AND A.codPagamento = TP.codPagamento
    AND (A.codAccount = ? OR I.codAccount =?)
    ORDER BY dataPagamento   
    """;
    public static final String OP16_VIEW_ACTIVE_ABBONAMENTO = """
    SELECT T.nome, T.durataMesi, COUNT(AB.tipoAbbonamento) AS NumAbbonamentiAttivi
    FROM Account AC, Abbonamento AB, Tipo_abbonamento T
    WHERE AC.codAbbonamentoattivo = AB.codAbbonamento
    AND AB.tipoAbbonamento = T.codTipoAbbonamento
    GROUP BY T.nome, T.durataMesi
    """;
    public static final String OP17_MOST_POPULAR_ARTIST = """
    SELECT AC.nickname, SUM(IF (P.album = True, 1, 0)) AS NumAlbum, SUM(IF (P.album = True, 0, 1)) AS NumSingoli
    FROM Artista AR, Account AC, Pubblicazione P, Brano B
    WHERE AR.email = AC.email
    AND P.codArtista = AR.email
    AND B.codPubblicazione = P.codPubblicazione
    GROUP BY AC.email, AC.nickname
    ORDER BY SUM(B.numRiproduzioni) DESC
    LIMIT 5
    """;
    public static final String OP18_SERVICE_TURNOVER = """
    SELECT SUM(TA.prezzo) AS Fatturato_Annuo
    FROM Abbonamento A, Tipo_Abbonamento TA
    WHERE year(A.dataPagamento) = ?
    AND A.tipoAbbonamento = TA.codTipoAbbonamento
    """;
}
