package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.swing.JOptionPane;

import data.DAOUtils;
import data.Dati;
import data.Queries;

public class Model {

    private final Connection connection;
    private static final int PLAYLIST_LENGTH = 25;
    private static final int N_ADVISED_PLAYLISTS = 5;

    public Model(final Connection connection) {
        Objects.requireNonNull(connection);
        this.connection = connection;
    }

    public void Op1_addAccount(
            final String nickname, final String email, final String password, final LocalDate dataNascita,
            final String genere, final String nazione,
            final Integer tipoPagamento, final Optional<Integer> numeroCarta, final Optional<LocalDate> scadenzaCarta,
            final Integer tipoAbbonamento) {
        PreparedStatement psCreateAccount = null;
        PreparedStatement psCreateTipoPagamento = null;
        PreparedStatement psCreateAbbonamento = null;
        PreparedStatement psUpdateCodAbbonamento = null;
        PreparedStatement psGetSubLength = null;
        ResultSet rsAbbonamento = null;
        ResultSet rsGetSubLength = null;

        try {
            connection.setAutoCommit(false);

            // Insert into Account
            psCreateAccount = DAOUtils.prepare(connection, Queries.OP1_CREATE_ACCOUNT);
            psCreateAccount.setString(1, nickname);
            psCreateAccount.setString(2, email);
            psCreateAccount.setString(3, password);
            psCreateAccount.setObject(4, dataNascita);
            psCreateAccount.setString(5, genere);
            psCreateAccount.setString(6, "link." + email); // TODO: HOW DO I GENERATE A LINK :D
            psCreateAccount.setObject(7, LocalDate.now());
            psCreateAccount.setString(8, nazione);
            psCreateAccount.executeUpdate();

            // Insert into Tipo_Pagamento
            psCreateTipoPagamento = DAOUtils.prepare(connection, Queries.OP1_TIPO_PAGAMENTO);
            psCreateTipoPagamento.setString(1, email);
            psCreateTipoPagamento.setInt(2, tipoPagamento);
            insertOptionalInteger(psCreateTipoPagamento, 3, numeroCarta);
            insertOptionalDate(psCreateTipoPagamento, 4, scadenzaCarta);
            psCreateTipoPagamento.setObject(5, LocalDate.now());
            psCreateTipoPagamento.setString(6, nazione);
            psCreateTipoPagamento.executeUpdate();

            // Leggo durata abbonamento
            psGetSubLength = DAOUtils.prepare(connection, Queries.OP1_DURATA_ABBONAMENTO, tipoAbbonamento);
            rsGetSubLength = psGetSubLength.executeQuery();

            int durataMesi = -1;
            if (rsGetSubLength.next()) {
                durataMesi = rsGetSubLength.getInt(1);
            }

            // Insert into Abbonamento
            psCreateAbbonamento = DAOUtils.prepare(connection, Queries.OP1_INSERT_ABBONAMENTO);
            psCreateAbbonamento.setString(1, email);
            psCreateAbbonamento.setObject(2, LocalDate.now());
            psCreateAbbonamento.setObject(3, LocalDate.now().plusMonths(durataMesi));
            psCreateAbbonamento.setInt(4, tipoAbbonamento);
            psCreateAbbonamento.executeUpdate();

            rsAbbonamento = psCreateAbbonamento.getGeneratedKeys();
            int codAbbonamento = -1;
            if (rsAbbonamento.next()) {
                // getGeneratedKeys non da i nomi delle colonne, usare i numeri :(
                codAbbonamento = rsAbbonamento.getInt(1);
            }

            // Update codAbbonameto
            psUpdateCodAbbonamento = DAOUtils.prepare(connection, Queries.OP1_UPDATE_CODABBONAMENTO, codAbbonamento,
                    email);
            psUpdateCodAbbonamento.executeUpdate();

            connection.commit();
            JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rsAbbonamento);
            closeResultSet(rsGetSubLength);
            closePreparedStatement(psGetSubLength);
            closePreparedStatement(psCreateAccount);
            closePreparedStatement(psCreateTipoPagamento);
            closePreparedStatement(psCreateAbbonamento);
            closePreparedStatement(psUpdateCodAbbonamento);
        }
    }

    public void Op2_inviteAbbonamento(final String accountInvitato, final String accountInvitante) {
        PreparedStatement psGetCodAbbonamento = null;
        PreparedStatement psGetCodAbbonamentoInvitato = null;
        PreparedStatement psGetNumGuests = null;
        PreparedStatement psGetMaxGuests = null;
        ResultSet rsGetNumGuests = null;
        ResultSet rsGetMaxGuests = null;
        ResultSet rsCodAbbonamento = null;
        ResultSet rsCodAbbonamentoInvitato = null;
        PreparedStatement psCreateInvitoAbbonamento = null;
        PreparedStatement psUpdateCodAbbonamento = null;
        try {
            connection.setAutoCommit(false);

            if (checkAdmin(accountInvitante) || checkAdmin(accountInvitato)) {
                rollBackWithCustomMessage("Cannot operate as admin");
                return;
            }

            // Get codAbbonamento
            psGetCodAbbonamento = DAOUtils.prepare(connection, Queries.OP2_GET_CODABBONAMENTO, accountInvitante);
            rsCodAbbonamento = psGetCodAbbonamento.executeQuery();
            int codAbbonamento = -1;
            if (rsCodAbbonamento.next()) {
                codAbbonamento = rsCodAbbonamento.getInt("codAbbonamentoAttivo");
            }

            // CHECK
            psGetCodAbbonamentoInvitato = DAOUtils.prepare(connection, Queries.OP2_GET_CODABBONAMENTO, accountInvitato);
            rsCodAbbonamentoInvitato = psGetCodAbbonamentoInvitato.executeQuery();
            // If i can find any result that mean that the account has already an
            // abbonamento
            if (rsCodAbbonamentoInvitato.next() && rsCodAbbonamentoInvitato.getInt("codAbbonamentoAttivo") != 0) {
                rollBackWithCustomMessage("L'account invitato ha gia' un abbonanamento");
                return;
            }

            psGetNumGuests = DAOUtils.prepare(connection, Queries.OP2_NUM_GUESTS, codAbbonamento);
            rsGetNumGuests = psGetNumGuests.executeQuery();

            int accountInvitati = -1;
            if (rsGetNumGuests.next()) {
                accountInvitati = rsGetNumGuests.getInt(1);
            }

            psGetMaxGuests = DAOUtils.prepare(connection, Queries.OP2_MAX_GUESTS, codAbbonamento);
            rsGetMaxGuests = psGetMaxGuests.executeQuery();

            int maxPersoneAccount = -1;
            if (rsGetMaxGuests.next()) {
                maxPersoneAccount = rsGetMaxGuests.getInt(1);
            }

            if (accountInvitati + 1 >= maxPersoneAccount) {
                rollBackWithCustomMessage("Non puoi invitare altri account");
                return;
            }

            // Insert in invito_Abbonamento
            psCreateInvitoAbbonamento = DAOUtils.prepare(connection, Queries.OP2_INVITE_ABBONAMENTO, accountInvitato,
                    codAbbonamento);
            psCreateInvitoAbbonamento.executeUpdate();

            // Update account codAbbonamentoAttivo
            psUpdateCodAbbonamento = DAOUtils.prepare(connection, Queries.OP2_UPDATE_CODABBONAMENTO, codAbbonamento,
                    accountInvitato);
            psUpdateCodAbbonamento.executeUpdate();

            connection.commit();
            JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            closeResultSet(rsCodAbbonamento);
            closeResultSet(rsCodAbbonamentoInvitato);
            closeResultSet(rsGetMaxGuests);
            closeResultSet(rsGetNumGuests);
            closePreparedStatement(psGetMaxGuests);
            closePreparedStatement(psGetNumGuests);
            closePreparedStatement(psGetCodAbbonamento);
            closePreparedStatement(psGetCodAbbonamento);
            closePreparedStatement(psUpdateCodAbbonamento);
        }
    }

    public void Op3_followArtist(final String accountSeguito, final String accountSeguente) {
        PreparedStatement ps = null;
        try {

            if (checkAdmin(accountSeguente)) {
                rollBackWithCustomMessage("Cannot operate as admin");
                return;
            }

            // Insert into Follow account
            ps = DAOUtils.prepare(connection, Queries.OP3_FOLLOW_ARTIST, accountSeguito, accountSeguente);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    public void Op4_createPlaylist(final String nome, final String descrizione, final String immagine,
            final boolean privato,
            final String accountCreatore, final String accountCollaboratore) {
        PreparedStatement psCreatePlaylist = null;
        PreparedStatement psInsertCollaborator = null;
        PreparedStatement psCollabFollowPlay = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);

            if (checkAdmin(accountCreatore) || checkAdmin(accountCollaboratore)) {
                rollBackWithCustomMessage("Cannot operate as admin");
                return;
            }

            // Create a new Playlist
            psCreatePlaylist = DAOUtils.prepare(connection, Queries.OP4_CREATE_PLAYLIST);
            psCreatePlaylist.setString(1, nome);
            psCreatePlaylist.setString(2, descrizione);
            psCreatePlaylist.setString(3, immagine);
            psCreatePlaylist.setBoolean(4, privato);
            psCreatePlaylist.setString(5, accountCreatore);
            psCreatePlaylist.executeUpdate();

            // get the playlist id
            rs = psCreatePlaylist.getGeneratedKeys();
            int playlistId = -1;
            if (rs.next()) {
                playlistId = rs.getInt(1);
            }

            psInsertCollaborator = DAOUtils.prepare(connection, Queries.OP4_INSERT_COLLABORATOR, accountCollaboratore,
                    playlistId);
            psInsertCollaborator.executeUpdate();

            psCollabFollowPlay = DAOUtils.prepare(connection, Queries.OP4_FOLLOW_PLAYLIST, playlistId,
                    accountCollaboratore);
            psCollabFollowPlay.executeUpdate();

            connection.commit();
            JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            closeResultSet(rs);
            closePreparedStatement(psCreatePlaylist);
            closePreparedStatement(psInsertCollaborator);
            closePreparedStatement(psCollabFollowPlay);

        }
    }

    public void Op5_DeleteSong(final int codPlaylist, final int numero) {
        PreparedStatement ps = null;
        PreparedStatement psCheck = null;
        ResultSet rsCheck = null;
        try {

            psCheck = DAOUtils.prepare(connection, Queries.OP5_CHECK_UTENTE, codPlaylist);
            rsCheck = psCheck.executeQuery();

            if (rsCheck.next() && rsCheck.getInt(1) != 0) {
                rollBackWithCustomMessage("Cannot modify, admin Playlist!");
                return;
            }

            ps = DAOUtils.prepare(connection, Queries.OP5_DELETE_SONG, codPlaylist, numero);
            final int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Could not find the Song or Playlist", "Fail",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    public Map<String, List<Dati.Op6Data>> Op6_viewUserLibrary(final String account) {
        PreparedStatement psGetArtist = null;
        ResultSet rsArtist = null;
        PreparedStatement psGetAlbum = null;
        ResultSet rsAlbum = null;
        PreparedStatement psGetPlaylist = null;
        ResultSet rsPlaylist = null;
        final Map<String, List<Dati.Op6Data>> result = new HashMap<>();

        try {
            // getArtist
            psGetArtist = DAOUtils.prepare(connection, Queries.OP6_GET_ARTIST, account);
            rsArtist = psGetArtist.executeQuery();
            final List<Dati.Op6Data> artistList = new ArrayList<>();
            while (rsArtist.next()) {
                final String nomeArtista = rsArtist.getString("ArtistiSeguiti");
                artistList.add(new Dati.Op6Data(nomeArtista, "", "", "", ""));
            }
            result.put("Artist", artistList);

            // getAlbum
            psGetAlbum = DAOUtils.prepare(connection, Queries.OP6_GET_ALBUM, account);
            rsAlbum = psGetAlbum.executeQuery();
            final List<Dati.Op6Data> albumList = new ArrayList<>();
            while (rsAlbum.next()) {
                final String nomeAlbum = rsAlbum.getString("AlbumSeguiti");
                final String autoreAlbum = rsAlbum.getString("AutoreAlbum");
                albumList.add(new Dati.Op6Data("", nomeAlbum, autoreAlbum, "", ""));
            }
            result.put("Album", albumList);

            // getPlaylist
            psGetPlaylist = DAOUtils.prepare(connection, Queries.OP6_GET_PLAYLIST, account, account);
            rsPlaylist = psGetPlaylist.executeQuery();
            final List<Dati.Op6Data> playlistList = new ArrayList<>();
            while (rsPlaylist.next()) {
                final String nomePlaylist = rsPlaylist.getString("PlaylistSeguiti");
                final String autorePlaylist = rsPlaylist.getString("nickname");
                playlistList.add(new Dati.Op6Data("", "", "", nomePlaylist, autorePlaylist));
            }
            result.put("Playlist", playlistList);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rsArtist);
            closeResultSet(rsAlbum);
            closeResultSet(rsPlaylist);
            closePreparedStatement(psGetArtist);
            closePreparedStatement(psGetAlbum);
            closePreparedStatement(psGetPlaylist);
        }

        return result;
    }

    public Map<String, List<Dati.Op7Data>> Op7_searchSong(final String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final Map<String, List<Dati.Op7Data>> result = new HashMap<>();

        try {
            ps = DAOUtils.prepare(connection, Queries.OP7_SEARCH_SONG, name + '%');
            rs = ps.executeQuery();
            final List<Dati.Op7Data> list = new ArrayList<>();
            while (rs.next()) {
                // get data from the database
                final String titolo = rs.getString("titolo");
                final int durataMin = rs.getInt("DurataMinuti");
                final int durataSec = rs.getInt("DurataSecondi");
                final boolean esplicito = rs.getBoolean("esplicito");
                final int numRiproduzioni = rs.getInt("numRiproduzioni");
                list.add(new Dati.Op7Data(titolo, durataMin, durataSec, esplicito, numRiproduzioni));
            }
            result.put("Songs", new ArrayList<>(list));
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
        return result;
    }

    public Map<String, List<Dati.Op8Data>> Op8_viewAlbum(final int codPubblicazione) {
        PreparedStatement psViewAlbum = null;
        ResultSet rsAlbum = null;
        PreparedStatement psViewDetail = null;
        ResultSet rsDetail = null;
        final Map<String, List<Dati.Op8Data>> result = new HashMap<>();
        try {

            // view Album
            psViewAlbum = DAOUtils.prepare(connection, Queries.OP_8_VIEW_ALBUM, codPubblicazione);
            rsAlbum = psViewAlbum.executeQuery();
            final List<Dati.Op8Data> albumList = new ArrayList<>();
            while (rsAlbum.next()) {
                final String nome = rsAlbum.getString("nome");
                final String artista = rsAlbum.getString("Artista");
                final int durataMin = rsAlbum.getInt("DurataMinuti");
                final int durataSecondi = rsAlbum.getInt("DurataSecondi");
                final int numeroBrani = rsAlbum.getInt("NumeroBrani");
                final int numFollowers = rsAlbum.getInt("numFollowers");
                albumList.add(new Dati.Op8Data(nome, artista, durataMin, durataSecondi, numeroBrani, numFollowers, "",
                        0, ""));
            }
            result.put("Visualizza album", albumList);

            // view details album
            psViewDetail = DAOUtils.prepare(connection, Queries.OP_8_VIEW_ALBUM_DETAILS, codPubblicazione);
            rsDetail = psViewDetail.executeQuery();
            final List<Dati.Op8Data> detailList = new ArrayList<>();
            while (rsDetail.next()) {
                final String titolo = rsDetail.getString("titolo");
                final int numRiproduzioni = rsDetail.getInt("NumRiproduzioni");
                final String cantante = rsDetail.getString("Cantante");
                detailList.add(new Dati.Op8Data("", "", 0, 0, 0, 0, titolo, numRiproduzioni, cantante));
            }
            result.put("Visualizza brani", detailList);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rsAlbum);
            closeResultSet(rsDetail);
            closePreparedStatement(psViewAlbum);
            closePreparedStatement(psViewDetail);
        }

        return result;
    }

    public Map<String, List<Dati.Op9Data>> Op9_subHistory(final String email) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final Map<String, List<Dati.Op9Data>> result = new HashMap<>();
        try {
            if (checkAdmin(email)) {
                rollBackWithCustomMessage("Cannot operate as admin");
                return result;
            }
            ps = DAOUtils.prepare(connection, Queries.OP_9_VIEW_SUB_HISTORY, email, email);
            rs = ps.executeQuery();
            final List<Dati.Op9Data> list = new ArrayList<>();
            while (rs.next()) {
                final String tipoAbbonamento = rs.getString("TipoAbbonamento");
                final int durataMesi = rs.getInt("durataMesi");
                final LocalDate dataPagamento = rs.getDate("dataPagamento").toLocalDate();
                final LocalDate dataScadenza = rs.getDate("dataScadenza").toLocalDate();
                final String accountPagante = rs.getString("AccountPagante");
                list.add(new Dati.Op9Data(tipoAbbonamento, durataMesi, dataPagamento, dataScadenza, accountPagante));
            }
            result.put("Subscription history", list);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return result;
    }

    public void OP10_ReproduceTrack(final int brano, final String account, final int msRiprodotti) {
        PreparedStatement psInsertRip = null;
        PreparedStatement psUpdateNumRip = null;
        PreparedStatement psCheckLength = null;
        ResultSet rsCheckLength = null;

        try {
            connection.setAutoCommit(false);

            if (checkAdmin(account)) {
                rollBackWithCustomMessage("Cannot operate as admin");
                return;
            }

            // CHECK
            psCheckLength = DAOUtils.prepare(connection, Queries.OP_10_GET_LENGTH, brano);
            rsCheckLength = psCheckLength.executeQuery();

            if (!rsCheckLength.next() || rsCheckLength.getInt(1) < msRiprodotti) {
                rollBackWithCustomMessage("Errore nella riproduzione");
                return;
            }

            // Insert in Riproduzione
            psInsertRip = DAOUtils.prepare(connection, Queries.OP_10_INSERT_RIP, account, account, msRiprodotti, brano,
                    account);
            psInsertRip.executeUpdate();

            // Update brano numRiproduzioni
            if (msRiprodotti >= 30000) {
                psUpdateNumRip = DAOUtils.prepare(connection, Queries.OP_10_UPDATE_NUM_RIP, brano);
                psUpdateNumRip.executeUpdate();
            }

            connection.commit();
            JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            closeResultSet(rsCheckLength);
            closePreparedStatement(psInsertRip);
            closePreparedStatement(psUpdateNumRip);
            closePreparedStatement(psCheckLength);
            closeResultSet(rsCheckLength);
        }
    }

    public void Op11_insertText(final Integer codiceBrano, final String testo) {
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            ps = DAOUtils.prepare(connection, Queries.OP11_ADD_TEXT_SONG);

            // Split testo
            final String[] lines = testo.split("\n");
            int numero = 1;

            for (final String line : lines) {
                ps.setInt(1, codiceBrano);
                ps.setInt(2, numero);
                numero++;
                ps.setString(3, line);
                ps.setNull(4, java.sql.Types.INTEGER);
                ps.executeUpdate();
            }
            connection.commit();
            JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            closePreparedStatement(ps);
        }
    }

    public void OP12_DisableEnableAccount(final boolean sospensione, final String account) {
        PreparedStatement psEnableDisable = null;

        try {
            connection.setAutoCommit(false);

            psEnableDisable = DAOUtils.prepare(connection, Queries.OP12_DISABLE_OR_ENABLE_ACCOUNT, sospensione,
                    account);
            psEnableDisable.executeUpdate();

            connection.commit();
            JOptionPane.showMessageDialog(null, "Operation Succeed! Set ACCOUNT.sospeso to " + sospensione, "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            closePreparedStatement(psEnableDisable);
        }
    }

    public void OP13_TrackAnalysis(final int brano, final String radioImage) {
        PreparedStatement psCheckAnalysis = null;
        PreparedStatement psCreateAdminPlaylist = null;
        PreparedStatement psGetTrackName = null;
        PreparedStatement psAnalysis = null;
        PreparedStatement psFillPlaylist = null;
        PreparedStatement psGetRadioTracks = null;
        PreparedStatement psGetAdvisedPlaylists = null;
        PreparedStatement psInsertAdvisedPlaylists = null;
        PreparedStatement psGetTrackGenre = null;
        PreparedStatement psInsertTrackGenre = null;
        ResultSet rsCheckAnalysis = null;
        ResultSet rsGetTrackName = null;
        ResultSet rsCreateAdminPlaylist = null;
        ResultSet rsGetRadioTracks = null;
        ResultSet rsGetAdvisedPlaylists = null;
        ResultSet rsGetTrackGenre = null;

        try {
            connection.setAutoCommit(false);

            // CHECK
            psCheckAnalysis = DAOUtils.prepare(connection, Queries.OP_13_CHECK_ANALISYS, brano);
            rsCheckAnalysis = psCheckAnalysis.executeQuery();
            if (rsCheckAnalysis.next()) {
                rollBackWithCustomMessage("Il brano è già stato analizzato");
                return;
            }

            psGetTrackName = DAOUtils.prepare(connection, Queries.OP_13_GET_TRACK_NAME, brano);
            rsGetTrackName = psGetTrackName.executeQuery();

            String trackTitle = "";
            if (rsGetTrackName.next()) {
                trackTitle = rsGetTrackName.getString(1);
            }

            psCreateAdminPlaylist = DAOUtils.prepare(connection, Queries.OP_13_CREATE_PLAYLIST_ADMIN,
                    "Radio di " + trackTitle, "", radioImage);
            psCreateAdminPlaylist.executeUpdate();

            rsCreateAdminPlaylist = psCreateAdminPlaylist.getGeneratedKeys();
            int codRadio = -1;
            if (rsCreateAdminPlaylist.next()) {
                codRadio = rsCreateAdminPlaylist.getInt(1);
            }

            final Object[] analysisValues = new Object[12];
            final Random random = new Random();

            analysisValues[0] = brano;
            analysisValues[1] = codRadio;

            for (int i = 2; i < analysisValues.length; i++) {
                analysisValues[i] = random.nextInt(256);
            }

            psAnalysis = DAOUtils.prepare(connection, Queries.OP_13_ANALISYS, analysisValues);
            psAnalysis.executeUpdate();

            psGetRadioTracks = DAOUtils.prepare(connection, Queries.OP_13_GET_RADIO_TRACKS, brano, PLAYLIST_LENGTH);
            rsGetRadioTracks = psGetRadioTracks.executeQuery();

            int i = 1;
            while (rsGetRadioTracks.next()) {
                final int codBrano = rsGetRadioTracks.getInt(1);
                psFillPlaylist = DAOUtils.prepare(connection, Queries.OP_13_FILL_PLAYLIST, codRadio, i++, codBrano);
                psFillPlaylist.executeUpdate();
            }

            psGetAdvisedPlaylists = DAOUtils.prepare(connection, Queries.OP_13_GET_ADVISED_PLAYLISTS, codRadio,
                    N_ADVISED_PLAYLISTS);
            rsGetAdvisedPlaylists = psGetAdvisedPlaylists.executeQuery();

            while (rsGetAdvisedPlaylists.next()) {
                final int codPlaylist = rsGetAdvisedPlaylists.getInt(1);
                psInsertAdvisedPlaylists = DAOUtils.prepare(connection, Queries.OP_13_PLAYLIST_ADVISE, codPlaylist,
                        codRadio);
                psInsertAdvisedPlaylists.executeUpdate();
            }

            psGetTrackGenre = DAOUtils.prepare(connection, Queries.OP_13_GET_GENRES);
            rsGetTrackGenre = psGetTrackGenre.executeQuery();

            while (rsGetTrackGenre.next()) {
                final int codSottogenere = rsGetTrackGenre.getInt(1);
                psInsertTrackGenre = DAOUtils.prepare(connection, Queries.OP_13_TRACK_GENRE, brano, codSottogenere);
                psInsertTrackGenre.executeUpdate();
            }

            connection.commit();
            JOptionPane.showMessageDialog(null, "Analysis Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            closeResultSet(rsCheckAnalysis);
            closeResultSet(rsGetTrackName);
            closeResultSet(rsCreateAdminPlaylist);
            closeResultSet(rsGetRadioTracks);
            closeResultSet(rsGetAdvisedPlaylists);
            closeResultSet(rsGetTrackGenre);
            closePreparedStatement(psCheckAnalysis);
            closePreparedStatement(psCreateAdminPlaylist);
            closePreparedStatement(psGetTrackName);
            closePreparedStatement(psAnalysis);
            closePreparedStatement(psFillPlaylist);
            closePreparedStatement(psGetRadioTracks);
            closePreparedStatement(psGetAdvisedPlaylists);
            closePreparedStatement(psInsertAdvisedPlaylists);
            closePreparedStatement(psGetTrackGenre);
            closePreparedStatement(psInsertTrackGenre);
        }
    }

    public Map<String, List<Dati.Op14Data>> OP14_Top50(final String nazione) {
        PreparedStatement psCreatePlaylist = null;
        PreparedStatement psPopulatePlaylist = null;
        PreparedStatement psInsertAdvisedPlaylists = null;
        PreparedStatement psgetCountry = null;
        PreparedStatement psGetAdvisedPlaylists = null;
        PreparedStatement psShow = null;
        ResultSet rsGetAdvisedPlaylists = null;
        ResultSet rsCreatePlaylist = null;
        ResultSet rsGetCountry = null;
        ResultSet rsShow = null;

        final Map<String, List<Dati.Op14Data>> result = new HashMap<>();
        final List<Dati.Op14Data> list = new ArrayList<>();

        try {

            psgetCountry = DAOUtils.prepare(connection, Queries.OP_14_GET_COUNTRY_NAME, nazione);
            rsGetCountry = psgetCountry.executeQuery();

            String nomeNazione = "";
            if (rsGetCountry.next()) {
                nomeNazione = rsGetCountry.getString(1);
            }

            psCreatePlaylist = DAOUtils.prepare(connection, Queries.OP_14_CREATE_TOP_50, "Top 50 " + nomeNazione,
                    "I brani più ascoltati in " + nomeNazione, "image/" + nomeNazione + ".jpg");
            psCreatePlaylist.executeUpdate();
            rsCreatePlaylist = psCreatePlaylist.getGeneratedKeys();

            int codTop50 = -1;
            if (rsCreatePlaylist.next()) {
                codTop50 = rsCreatePlaylist.getInt(1);
            }

            psPopulatePlaylist = DAOUtils.prepare(connection, Queries.OP_14_INSERT_TOP_50, codTop50, nazione);
            psPopulatePlaylist.executeUpdate();

            psGetAdvisedPlaylists = DAOUtils.prepare(connection, Queries.OP_14_GET_ADVISED_PLAYLISTS, codTop50,
                    N_ADVISED_PLAYLISTS);
            rsGetAdvisedPlaylists = psGetAdvisedPlaylists.executeQuery();

            while (rsGetAdvisedPlaylists.next()) {
                final int codPlaylist = rsGetAdvisedPlaylists.getInt(1);
                psInsertAdvisedPlaylists = DAOUtils.prepare(connection, Queries.OP_14_PLAYLIST_ADVISE, codPlaylist,
                        codTop50);
                psInsertAdvisedPlaylists.executeUpdate();
            }

            psShow = DAOUtils.prepare(connection, Queries.OP_14_SHOW_TOP50, codTop50);
            rsShow = psShow.executeQuery();
            while (rsShow.next()) {
                final String titolo = rsShow.getString(1);
                final String artista = rsShow.getString(2);
                final int numRiproduzioni = rsShow.getInt(3);
                final int durataMinuti = rsShow.getInt(4);
                final int durataSecondi = rsShow.getInt(5);
                final boolean esplicito = rsShow.getBoolean(6);
                list.add(new Dati.Op14Data(titolo, artista, numRiproduzioni, durataMinuti, durataSecondi, esplicito));

            }
            result.put("Top50 per nazione", list);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rsGetAdvisedPlaylists);
            closeResultSet(rsCreatePlaylist);
            closeResultSet(rsGetCountry);
            closeResultSet(rsShow);
            closePreparedStatement(psCreatePlaylist);
            closePreparedStatement(psPopulatePlaylist);
            closePreparedStatement(psInsertAdvisedPlaylists);
            closePreparedStatement(psgetCountry);
            closePreparedStatement(psGetAdvisedPlaylists);
            closePreparedStatement(psShow);
        }
        return result;
    }

    public Map<String, List<Dati.Op15Data>> Op15_artistSongs(final String artistId) {
        PreparedStatement psGetArtist = null;
        PreparedStatement psGetTracks = null;
        ResultSet rsGetTracks = null;
        ResultSet rsGetArtist = null;
        final Map<String, List<Dati.Op15Data>> result = new HashMap<>();
        try {

            psGetArtist = DAOUtils.prepare(connection, Queries.OP15_GET_ARTIST, artistId);
            rsGetArtist = psGetArtist.executeQuery();

            final List<Dati.Op15Data> listArtistInfo = new ArrayList<>();

            if (rsGetArtist.next()) {
                final String nickname = rsGetArtist.getString("nickname");
                final boolean verificato = rsGetArtist.getBoolean("verificato");
                listArtistInfo.add(new Dati.Op15Data(nickname, verificato, "", 0));
            }

            psGetTracks = DAOUtils.prepare(connection, Queries.OP15_VIEW_TOP5MOSTPOPULAR_SONG, artistId);
            rsGetTracks = psGetTracks.executeQuery();

            final List<Dati.Op15Data> listSongsInfo = new ArrayList<>();

            while (rsGetTracks.next()) {
                final String titolo = rsGetTracks.getString("titolo");
                final int numRiproduzioni = rsGetTracks.getInt("numRiproduzioni");
                listSongsInfo.add(new Dati.Op15Data("", false, titolo, numRiproduzioni));
            }

            result.put("Artist", listArtistInfo);
            result.put("Songs", listSongsInfo);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rsGetTracks);
            closeResultSet(rsGetArtist);
            closePreparedStatement(psGetTracks);
            closePreparedStatement(psGetArtist);
        }
        return result;
    }

    public Map<String, List<Dati.Op16Data>> Op16_viewActiveAbbonamento() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final Map<String, List<Dati.Op16Data>> result = new HashMap<>();
        final List<Dati.Op16Data> list = new ArrayList<>();
        try {
            ps = DAOUtils.prepare(connection, Queries.OP16_VIEW_ACTIVE_ABBONAMENTO);
            rs = ps.executeQuery();
            while (rs.next()) {
                final String nome = rs.getString("nome");
                final int durataMesi = rs.getInt("durataMesi");
                final int numAbbonamentiAttivi = rs.getInt("NumAbbonamentiAttivi");
                list.add(new Dati.Op16Data(nome, durataMesi, numAbbonamentiAttivi));
            }
            result.put("Abbonamenti Attivi", list);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
        return result;
    }

    public Map<String, List<Dati.Op17Data>> Op17_mostPopularArtist() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final Map<String, List<Dati.Op17Data>> result = new HashMap<>();
        final List<Dati.Op17Data> list = new ArrayList<>();
        try {
            ps = DAOUtils.prepare(connection, Queries.OP17_MOST_POPULAR_ARTIST);
            rs = ps.executeQuery();
            while (rs.next()) {
                final String nomeArtista = rs.getString("nickname");
                final int numAlbum = rs.getInt("NumAlbum");
                final int numSingoli = rs.getInt("NumSingoli");
                list.add(new Dati.Op17Data(nomeArtista, numAlbum, numSingoli));
            }
            result.put("Popular Artist", list);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return result;
    }

    public Map<String, List<Dati.Op18Data>> Op18_serviceTurnover(final int year) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final Map<String, List<Dati.Op18Data>> result = new HashMap<>();
        try {
            ps = DAOUtils.prepare(connection, Queries.OP18_SERVICE_TURNOVER, year);
            rs = ps.executeQuery();
            final List<Dati.Op18Data> list = new ArrayList<>();
            while (rs.next()) {
                final double fatturatoAnnuo = rs.getDouble("Fatturato_Annuo");
                list.add(new Dati.Op18Data(fatturatoAnnuo));
            }
            result.put("Fatturato Annuo", list);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return result;
    }

    // Optional integer
    private final void insertOptionalInteger(final PreparedStatement ps, final Integer index,
            final Optional<Integer> value)
            throws SQLException {
        if (value.isPresent()) {
            ps.setInt(index, value.get());
        } else {
            ps.setNull(index, java.sql.Types.INTEGER);
        }
    }

    private final void insertOptionalDate(final PreparedStatement ps, final Integer index,
            final Optional<LocalDate> value)
            throws SQLException {
        if (value.isPresent()) {
            ps.setDate(index, Date.valueOf(value.get()));
        } else {
            ps.setNull(index, java.sql.Types.DATE);
        }
    }

    private final void rollBack(final Connection connection, final SQLException e) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (final SQLException ex) {
                ex.printStackTrace();
            }
            try {
                connection.setAutoCommit(true);
            } catch (final SQLException exc) {
                exc.printStackTrace();
            }
        }
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Operation Failed because: " + e.getMessage(), "Fail",
                JOptionPane.ERROR_MESSAGE);
    }

    private final void rollBackWithCustomMessage(final String s) {
        rollBack(connection, new SQLException() {
            @Override
            public String getMessage() {
                return s;
            }
        });
    }

    private final void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private final void closePreparedStatement(final PreparedStatement st) {
        if (st != null) {
            try {
                st.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private final boolean checkAdmin(final String email) {
        PreparedStatement psCheckAdmin = null;
        ResultSet rsCheckAdmin = null;

        try {
            psCheckAdmin = DAOUtils.prepare(connection, Queries.CHECK_ADMIN, email);
            rsCheckAdmin = psCheckAdmin.executeQuery();

            if (rsCheckAdmin.next()) {
                return true;
            }

        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closePreparedStatement(psCheckAdmin);
            closeResultSet(rsCheckAdmin);
        }
        return false;
    }

}