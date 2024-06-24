package Model;

import java.sql.Connection;
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

import javax.swing.JOptionPane;

import data.DAOUtils;
import data.Dati;
import data.Queries;

public class Model {

    private final Connection connection;

    public Model(final Connection connection) {
        Objects.requireNonNull(connection);
        this.connection = connection;
    }

    public void Op1_addAccount(
            final String nickname, final String email, final String password, final LocalDate dataNascita,
            final String genere, final String nazione,
            final Integer tipoPagamento, final Optional<Integer> numeroCarta, final Optional<Integer> scadenzaCarta,
            final Integer tipoAbbonamento) {
        PreparedStatement psCreateAccount = null;
        PreparedStatement psCreateTipoPagamento = null;
        PreparedStatement psCreateAbbonamento = null;
        PreparedStatement psUpdateCodAbbonamento = null;
        ResultSet rsAbbonamento = null;

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
            insertOptionalInteger(psCreateTipoPagamento, 4, scadenzaCarta);
            psCreateTipoPagamento.setObject(5, LocalDate.now());
            psCreateTipoPagamento.setString(6, nazione);
            psCreateTipoPagamento.executeUpdate();

            // Insert into Abbonamento
            psCreateAbbonamento = DAOUtils.prepare(connection, Queries.OP1_INSERT_ABBONAMENTO);
            psCreateAbbonamento.setString(1, email);
            psCreateAbbonamento.setObject(2, LocalDate.now());
            psCreateAbbonamento.setObject(3, LocalDate.now()); // TODO: Datascadenza da prendere in tipo abbonamento
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
            closePreparedStatement(psCreateAccount);
            closePreparedStatement(psCreateTipoPagamento);
            closePreparedStatement(psCreateAbbonamento);
            closePreparedStatement(psUpdateCodAbbonamento);
        }
    }

    public void Op2_inviteAbbonamento(final String accountInvitato, final String accountInvitante) {
        PreparedStatement psGetCodAbbonamento = null;
        PreparedStatement psGetCodAbbonamentoInvitato = null;
        ResultSet rsCodAbbonamento = null;
        PreparedStatement psCreateInvitoAbbonamento = null;
        PreparedStatement psUpdateCodAbbonamento = null;
        try {
            connection.setAutoCommit(false);

            // Get codAbbonamento
            psGetCodAbbonamento = DAOUtils.prepare(connection, Queries.OP2_GET_CODABBONAMENTO, accountInvitante);
            rsCodAbbonamento = psGetCodAbbonamento.executeQuery();
            int codAbbonamento = -1;
            if (rsCodAbbonamento.next()) {
                codAbbonamento = rsCodAbbonamento.getInt("codAbbonamentoAttivo");
            }

            // CHECK
            psGetCodAbbonamentoInvitato = DAOUtils.prepare(connection, Queries.OP2_GET_CODABBONAMENTO, accountInvitato);
            rsCodAbbonamento = psGetCodAbbonamentoInvitato.executeQuery();
            // If i can find any result that mean that the account has already an
            // abbonamento
            if (rsCodAbbonamento.next()) {
                rollBackWithCustomMessage("L'account invitato ha gia' un abbonanamento");
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
            closePreparedStatement(psGetCodAbbonamento);
            closePreparedStatement(psGetCodAbbonamento);
            closePreparedStatement(psUpdateCodAbbonamento);
        }
    }

    public void Op3_followArtist(final String accountSeguito, final String accountSeguente) {
        PreparedStatement ps = null;
        try {
            if (accountSeguente.equals(accountSeguito)) {
                rollBackWithCustomMessage("Non puoi seguire te stesso");
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
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);

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
        }
    }

    public void Op5_DeleteSong(final int codPlaylist, final int numero) {
        PreparedStatement ps = null;
        try {
            ps = DAOUtils.prepare(connection, Queries.OP5_DELETE_SONG, codPlaylist, numero);
            final int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Could not find the Song", "Fail", JOptionPane.ERROR_MESSAGE);
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
            psGetPlaylist = DAOUtils.prepare(connection, Queries.OP6_GET_PLAYLIST, account);
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
                final int codiceBrano = rs.getInt("codBrano");
                final int numero = rs.getInt("numero");
                final String titolo = rs.getString("titolo");
                final int numRiproduzioni = rs.getInt("numRiproduzioni");
                final int durata = rs.getInt("durata");
                final boolean esplicito = rs.getBoolean("esplicito");
                final String fonteCrediti = rs.getString("fonteCrediti");
                final String fileAudio = rs.getString("fileAudio");
                final int codicePubblicazione = rs.getInt("codPubblicazione");
                list.add(new Dati.Op7Data(codiceBrano, numero, titolo, numRiproduzioni, durata, esplicito, fonteCrediti,
                        fileAudio,
                        codicePubblicazione));
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

    public Map<String, List<Dati.Op8Data>> Op8_viewAlbum (final int codPubblicazione) {
        PreparedStatement psViewAlbum = null;
        ResultSet rsAlbum = null;
        PreparedStatement psViewDetail = null;
        ResultSet rsDetail = null;
        final Map<String, List<Dati.Op8Data>> result = new HashMap<>();
        try {

            //view Album
            psViewAlbum = DAOUtils.prepare(connection, Queries.OP_8_VIEW_ALBUM, codPubblicazione);
            rsAlbum = psViewAlbum.executeQuery();
            final List<Dati.Op8Data> albumList = new ArrayList<>();
            while(rsAlbum.next()) {
                final String nome = rsAlbum.getString("nome");
                final String artista = rsAlbum.getString("Artista");
                final int durataMin = rsAlbum.getInt("DurataMinuti");
                final int durataSecondi = rsAlbum.getInt("DurataSecondi");
                final int numeroBrani = rsAlbum.getInt("NumeroBrani");
                final int numFollowers = rsAlbum.getInt("numFollowers");
                albumList.add(new Dati.Op8Data(nome, artista, durataMin, durataSecondi, numeroBrani, numFollowers, "", 0, ""));
            }
            result.put("Visualizza album", albumList);

            //view details album
            psViewDetail = DAOUtils.prepare(connection, Queries.OP_8_VIEW_ALBUM_DETAILS, codPubblicazione);
            rsDetail = psViewDetail.executeQuery();
            final List<Dati.Op8Data> detailList = new ArrayList<>();
            while(rsDetail.next()) {
                final String titolo = rsDetail.getString("titolo");
                final int numRiproduzioni = rsDetail.getInt("NumRiproduzioni");
                final String cantante = rsDetail.getString("Cantante");
                detailList.add(new Dati.Op8Data("","",0,0,0,0,titolo,numRiproduzioni,cantante));
            }
            result.put("Visualizza brani", detailList);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rsAlbum);
            closePreparedStatement(psViewAlbum);
            closeResultSet(rsDetail);
            closePreparedStatement(psViewDetail);
        }

        return result;
    }

    public Map<String, List<Dati.Op9Data>> Op9_subHistory(final String email) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final Map<String, List<Dati.Op9Data>> result = new HashMap<>();
        try {
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
}