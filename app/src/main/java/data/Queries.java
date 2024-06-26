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
    public static final String OP1_DURATA_ABBONAMENTO = """
    SELECT durataMesi
    FROM Tipo_abbonamento
    WHERE codTipoAbbonamento = ?     
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
    public static final String OP2_MAX_GUESTS = """
    SELECT T.maxPersoneAccount
    FROM Abbonamento A, Tipo_abbonamento T
    WHERE A.tipoAbbonamento = T.codTipoAbbonamento
    AND codAbbonamento = ?    
    """;
    public static final String OP2_NUM_GUESTS = """
    SELECT COUNT(*)
    FROM Invito_Abbonamento
    WHERE codAbbonamento = ?    
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
    public static final String OP4_FOLLOW_PLAYLIST = """
    INSERT INTO Follow_Playlist
    VALUES (?, ?)        
    """;
    public static final String OP5_CHECK_UTENTE = """
    SELECT codPlaylist
    FROM Playlist
    WHERE admin = true
    AND codPlaylist = ?
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
    FROM Playlist P LEFT OUTER JOIN Follow_Playlist FP ON (P.codPlaylist = FP.codPlaylist), Account A
    WHERE P.accountCreatore = A.email
    AND (FP.codAccount = ?
    OR P.accountCreatore = ?);  
    """;
    public static final String OP7_SEARCH_SONG = """
    SELECT B.titolo, ROUND(B.durata/60000) AS DurataMinuti, ROUND(B.durata/1000)%60 AS DurataSecondi, B.esplicito, B.numRiproduzioni
    FROM Brano B
    WHERE B.titolo LIKE ?
    """;

    public static final String OP_8_VIEW_ALBUM = """
    SELECT P.nome, AC.nickname AS Artista, ROUND(SUM(B.durata)/60000) AS DurataMinuti, ROUND((SUM(B.durata)/1000)%60) AS DurataSecondi, COUNT(codBrano) AS NumeroBrani, P.numFollowers
    FROM Pubblicazione P, Artista AR, Account AC, Brano B
    WHERE B.codPubblicazione = P.codPubblicazione
    AND P.codArtista = AR.email
    AND AR.email = AC.email
    AND P.codPubblicazione = ?
    GROUP BY P.nome, AC.nickname, P.numFollowers        
    """;

    public static final String OP_8_VIEW_ALBUM_DETAILS = """
    SELECT B.titolo, B.numRiproduzioni, AC.nickname AS Cantante
    FROM Brano B, Account AC, Artista AR, Esecuzione_brano E
    WHERE AC.email = AR.email
    AND E.codArtista = AR.email
    AND E.codBrano = B.codBrano
    AND B.codPubblicazione = ?
    order by B.numero
    """;

    public static final String OP_9_VIEW_SUB_HISTORY = """
    SELECT DISTINCT TA.nome AS TipoAbbonamento, TA.durataMesi, A.dataPagamento, A.dataScadenza, A.codAccount AS AccountPagante
    FROM Tipo_abbonamento TA, Tipo_pagamento TP, Abbonamento A LEFT OUTER JOIN Invito_abbonamento I ON (I.codAbbonamento = A.codAbbonamento)
    WHERE A.tipoAbbonamento = TA.codTipoAbbonamento
    AND A.codAccount = TP.codAccount
    AND A.codPagamento = TP.codPagamento
    AND (A.codAccount = ? OR I.codAccount =?)
    ORDER BY dataPagamento   
    """;

    public static final String OP_10_GET_LENGTH = """
    SELECT B.durata
    FROM brano B
    WHERE B.codBrano = ?     
    """;

    public static final String OP_10_UPDATE_NUM_RIP = """
    UPDATE Brano B
    SET B.numRiproduzioni = B.numRiproduzioni + 1
    WHERE B.codBrano = ?
    """;

    public static final String OP_10_INSERT_RIP = """
    INSERT INTO Riproduzione (codAccount, data, ora, numero, msRiprodotti, codBrano, nazione)
    VALUES(
    ?,
    CURDATE(),
    CURRENT_TIME(),
    (SELECT COUNT(*)+1
    FROM Riproduzione R 
    WHERE R.codAccount = ?
    AND R.data = CURDATE()),
    ?,
    ?,
    (SELECT A.nazione
    FROM Account A
    WHERE A.email = ?))
    """;

    public static final String OP11_ADD_TEXT_SONG = """
    INSERT INTO Riga_Testo (codBrano, numero, testo, msHighlight)
    VALUES (?, ?, ?, ?)        
    """;

    public static final String OP12_DISABLE_OR_ENABLE_ACCOUNT = """
    UPDATE Account
    SET sospeso = ?
    WHERE Account.email = ?
    """;

    public static final String OP_13_GET_TRACK_NAME = """
    SELECT B.titolo
    from brano B
    where B.codBrano = ?
    """;

    public static final String OP_13_CHECK_ANALISYS = """
    SELECT *
    FROM Analisi_brano
    WHERE codBrano = ?        
    """;

    public static final String OP_13_CREATE_PLAYLIST_ADMIN = """
    INSERT INTO Playlist (nome, descrizione, immagine, admin, privato, radio, accountCreatore)
    VALUES (?, ?, ?, True, False, True, "admin@spotify.com")   
    """;

    public static final String OP_13_ANALISYS = """
    INSERT INTO Analisi_Brano
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)   
    """;

    public static final String OP_13_GET_RADIO_TRACKS = """
    SELECT B.codBrano
    FROM brano B
    WHERE B.codBrano <> ?
    ORDER BY RAND()
    LIMIT ?        
    """;

    public static final String OP_13_FILL_PLAYLIST = """
    INSERT INTO Dettaglio_Playlist (codPlaylist, numero, dataAggiunta, codBrano, codAccount)
    VALUES (?, ?, CURDATE(), ?, "admin@spotify.com")  
    """;

    public static final String OP_13_GET_ADVISED_PLAYLISTS = """
    SELECT P.codPlaylist
    FROM Playlist P
    WHERE P.admin = true
    AND P.codPlaylist <> ?
    ORDER BY RAND()
    LIMIT ?
    """;

    public static final String OP_13_PLAYLIST_ADVISE = """
    INSERT INTO Consiglio_Playlist
    VALUES (?,?)
    """;

    public static final String OP_13_GET_GENRES = """
    SELECT codSottogenere
    FROM Sottogenere
    ORDER BY RAND()
    LIMIT 2
    """;

    public static final String OP_13_TRACK_GENRE = """
    INSERT INTO Genere_Brano
    VALUES(?,?)   
    """;

    public static final String OP_14_GET_COUNTRY_NAME = """
    SELECT nome
    FROM Nazione
    WHERE sigla = ?
    """;

    public static final String OP_14_CREATE_TOP_50 = """
    INSERT INTO Playlist (nome, descrizione, immagine, admin, privato, radio, accountCreatore)
    VALUES (?, ?, ?, True, False, False, "admin@spotify.com")
    """;

    public static final String OP_14_INSERT_TOP_50 = """
    INSERT INTO Dettaglio_Playlist (codPlaylist, numero, dataAggiunta, codBrano, codAccount)
    SELECT ?, ROW_NUMBER() OVER (), CURDATE(), R.codBrano, "admin@spotify.com"
    FROM Riproduzione R
    WHERE R.nazione = ?
    AND R.data = DATE_SUB(CURDATE(), INTERVAL 1 DAY)
    GROUP BY R.codBrano
    ORDER BY COUNT(R.codBrano) DESC
    LIMIT 50 
    """;

    public static final String OP_14_GET_ADVISED_PLAYLISTS = OP_13_GET_ADVISED_PLAYLISTS;

    public static final String OP_14_PLAYLIST_ADVISE = OP_13_PLAYLIST_ADVISE;

    public static final String OP_14_SHOW_TOP50 = """
    SELECT B.titolo, AC.nickname, B.numRiproduzioni AS NumRiproduzioniTotali, ROUND(B.durata/60000) AS DurataMinuti, ROUND((B.durata/1000)%60) AS DurataSecondi, B.esplicito
    FROM Brano B, Account AC, Artista AR, Esecuzione_brano E, Dettaglio_playlist D
    WHERE AC.email = AR.email
    AND E.codArtista = AR.email
    AND E.codBrano = B.codBrano
    AND B.codBrano = D.codBrano
    AND D.codPlaylist = ?
    ORDER BY D.numero
    """;

    public static final String OP15_GET_ARTIST = """
    SELECT AC.nickname, AR.verificato
    FROM Account AC, Artista AR
    WHERE AC.email = AR.email
    AND AR.email = ?      
    """;

    public static final String OP15_VIEW_TOP5MOSTPOPULAR_SONG = """
    SELECT B.titolo, B.numRiproduzioni
    FROM brano B, esecuzione_brano E
    WHERE E.codBrano = B.codBrano
    AND E.codArtista = ?
    ORDER BY B.numRiproduzioni DESC
    LIMIT 5
    """;

    public static final String OP16_VIEW_ACTIVE_ABBONAMENTO = """
    SELECT T.nome, T.durataMesi, COUNT(DISTINCT AB.codAbbonamento) AS NumAbbonamentiAttivi
    FROM Account AC, Abbonamento AB, Tipo_abbonamento T
    WHERE AC.codAbbonamentoattivo = AB.codAbbonamento
    AND AB.tipoAbbonamento = T.codTipoAbbonamento
    GROUP BY T.nome, T.durataMesi
    """;
    public static final String OP17_MOST_POPULAR_ARTIST = """
    SELECT AC.nickname, SUM(IF (P.album = True, 1, 0)) AS NumAlbum, SUM(IF (P.album = True, 0, 1)) AS NumSingoli
    FROM Artista AR, Account AC, Pubblicazione P
    WHERE AR.email = AC.email
    AND P.codArtista = AR.email
    AND AR.email IN (
	    SELECT AR1.email
        FROM artista AR1, Pubblicazione P, Brano B
        WHERE P.codArtista = AR.email
        AND B.codPubblicazione = P.codPubblicazione
        GROUP BY AR1.email
        ORDER BY SUM(B.numRiproduzioni) DESC)
    GROUP BY AC.email, AC.nickname
    LIMIT 5;
    """;
    public static final String OP18_SERVICE_TURNOVER = """
    SELECT SUM(TA.prezzo) AS Fatturato_Annuo
    FROM Abbonamento A, Tipo_Abbonamento TA
    WHERE year(A.dataPagamento) = ?
    AND A.tipoAbbonamento = TA.codTipoAbbonamento
    """;
}
