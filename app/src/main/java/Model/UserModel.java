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

import data.DAOUtils;
import data.Op7Data;
import data.Queries;

public class UserModel {

    private final Connection connection;

    public UserModel(final Connection connection) {
        Objects.requireNonNull(connection);
        this.connection = connection;
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

    private final void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
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

    public void Op1_addAccount(
            final String nickname, final String email, final String password, final LocalDate dataNascita,
            final String genere, final String nazione,
            final Integer tipoPagamento, final Optional<Integer> numeroCarta, final Optional<Integer> scadenzaCarta,
            final Integer tipoAbbonamento) {
        PreparedStatement psCreateAccount = null;
        PreparedStatement psCreateTipoPagamento = null;
        PreparedStatement psCreateAbbonamento = null;

        try {
            connection.setAutoCommit(false);

            // Insert into Account
            psCreateAccount = DAOUtils.prepare(connection, Queries.OP1_CREATE_ACCOUNT);
            psCreateAccount.setString(1, nickname);
            psCreateAccount.setString(2, email);
            psCreateAccount.setString(3, password);
            psCreateAccount.setObject(4, dataNascita);
            psCreateAccount.setString(5, genere);
            psCreateAccount.setString(6, "link." + email);
            psCreateAccount.setObject(7, LocalDate.now());
            psCreateAccount.setString(8, nazione);
            psCreateAccount.executeUpdate();

            // Insert into Tipo_Pagamento
            psCreateTipoPagamento = DAOUtils.prepare(connection, Queries.OP1_TIPO_PAGAMENTO);
            psCreateTipoPagamento.setInt(1, 1);
            psCreateTipoPagamento.setString(2, email);
            psCreateTipoPagamento.setInt(3, tipoPagamento);
            insertOptionalInteger(psCreateTipoPagamento, 4, numeroCarta);
            insertOptionalInteger(psCreateTipoPagamento, 5, scadenzaCarta);
            psCreateTipoPagamento.setObject(6, LocalDate.now());
            psCreateTipoPagamento.setString(7, nazione);
            psCreateTipoPagamento.executeUpdate();

            // Insert into Abbonamento
            psCreateAbbonamento = DAOUtils.prepare(connection, Queries.OP1_ABBONAMENTO);
            psCreateAbbonamento.setInt(1, 1);
            psCreateAbbonamento.setString(2, email);
            psCreateAbbonamento.setObject(3, LocalDate.now());
            psCreateAbbonamento.setObject(4, LocalDate.now()); // TODO: Datascadenza da prendere in tipo abbonamento
            psCreateAbbonamento.setInt(5, tipoAbbonamento);
            psCreateAbbonamento.executeUpdate();

            connection.commit();

        } catch (final SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (final SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            closePreparedStatement(psCreateAccount);
            closePreparedStatement(psCreateTipoPagamento);
            closePreparedStatement(psCreateAbbonamento);
        }
    }

    public void Op3_followArtist(final String accountSeguito, final String accountSeguente) {
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            ps = DAOUtils.prepare(connection, Queries.OP3_FOLLOW_ARTIST);
            ps.setString(1, accountSeguito);
            ps.setString(2, accountSeguente);
            ps.executeUpdate();

            connection.commit();

        } catch (final SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (final SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            closePreparedStatement(ps);
        }
    }

    public List<Op7Data> Op7_searchSong(final String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        final List<Op7Data> list = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            ps = DAOUtils.prepare(connection, Queries.OP7_SEARCH_SONG,'%' + name + '%');
            rs = ps.executeQuery();
            while (rs.next()) {
                final int numero = rs.getInt("numero");
                final String titolo = rs.getString("titolo");
                final int numRiproduzioni = rs.getInt("numRiproduzioni");
                final int durata = rs.getInt("durata");
                final boolean esplicito = rs.getBoolean("esplicito");
                final String fonteCrediti = rs.getString("fonteCrediti");
                final String fileAudio = rs.getString("fileAudio");
                final int codicePubblicazione = rs.getInt("codicePubblicazione");
                System.out.println(titolo);
                list.add(new Op7Data(numero, titolo, numRiproduzioni, durata, esplicito, fonteCrediti, fileAudio,
                        codicePubblicazione));
            }
            connection.commit();
        } catch (final SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (final SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
        
        return list;
    }
}