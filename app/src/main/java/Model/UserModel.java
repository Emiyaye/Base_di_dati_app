package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JOptionPane;

import data.DAOUtils;
import data.Dati;
import data.Queries;

public class UserModel {

    private final Connection connection;

    public UserModel(final Connection connection) {
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
            psCreateAbbonamento = DAOUtils.prepare(connection, Queries.OP1_ABBONAMENTO);
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
        ResultSet rsCodAbbonamento = null;
        PreparedStatement psCreateInvitoAbbonamento = null;
        PreparedStatement psUpdateCodAbbonamento = null;
        try {
            connection.setAutoCommit(false);
            if (accountInvitante.equals(accountInvitato)) {
                rollBackWithCustomMessage("Non puoi invitare te stesso");
                return;
            }

            // Get codAbbonamento
            psGetCodAbbonamento = DAOUtils.prepare(connection, Queries.OP2_GET_CODABBONAMENTO, accountInvitante);
            rsCodAbbonamento = psGetCodAbbonamento.executeQuery();
            int codAbbonamento = -1;
            if (rsCodAbbonamento.next()) {
                codAbbonamento = rsCodAbbonamento.getInt("codAbbonamentoAttivo");
            } else {
                rollBackWithCustomMessage("Il tuo account non esiste");
                return;
            }

            // Insert in invito_Abbonamento
            psCreateInvitoAbbonamento = DAOUtils.prepare(connection, Queries.OP2_INVITE_ABBONAMENTO, accountInvitato,
                    codAbbonamento);
            psCreateInvitoAbbonamento.setString(1, accountInvitato);
            psCreateInvitoAbbonamento.setInt(2, codAbbonamento);
            psCreateInvitoAbbonamento.executeUpdate();

            // Update account codAbbonamentoAttivo
            psUpdateCodAbbonamento = DAOUtils.prepare(connection, Queries.OP1_UPDATE_CODABBONAMENTO, codAbbonamento,
                    accountInvitato);
            final int result = psUpdateCodAbbonamento.executeUpdate();
            if (result == 0) {
                rollBackWithCustomMessage("Account Invitato non esiste");
                return;
            }
            connection.commit();
            JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            rollBack(connection, e);
        } finally {
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
            // TODO: maybe check if the account exist, when the database is complete need some testing
            // Insert into Follow account
            ps = DAOUtils.prepare(connection, Queries.OP3_FOLLOW_ARTIST);
            ps.setString(1, accountSeguito);
            ps.setString(2, accountSeguente);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Operation Succeed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closePreparedStatement(ps);
        }
    }

    public List<Dati.Op7Data> Op7_searchSong(final String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final List<Dati.Op7Data> list = new ArrayList<>();
        try {
            ps = DAOUtils.prepare(connection, Queries.OP7_SEARCH_SONG, name + '%');
            rs = ps.executeQuery();
            while (rs.next()) {
                // get data from the database
                final int codiceBrano = rs.getInt("codiceBrano");
                final int numero = rs.getInt("numero");
                final String titolo = rs.getString("titolo");
                final int numRiproduzioni = rs.getInt("numRiproduzioni");
                final int durata = rs.getInt("durata");
                final boolean esplicito = rs.getBoolean("esplicito");
                final String fonteCrediti = rs.getString("fonteCrediti");
                final String fileAudio = rs.getString("fileAudio");
                final int codicePubblicazione = rs.getInt("codicePubblicazione");
                list.add(new Dati.Op7Data(codiceBrano, numero, titolo, numRiproduzioni, durata, esplicito, fonteCrediti,
                        fileAudio,
                        codicePubblicazione));
            }
        } catch (final SQLException e) {
            rollBack(connection, e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }

        return list;
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
            } catch (SQLException exc) {
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