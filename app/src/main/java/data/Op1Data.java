package data;

import java.time.LocalDate;
import java.util.Optional;

public class Op1Data {
    private final String nickname;
    private final String email;
    private final String password;
    private final LocalDate dataNascita;
    private final String genere;
    private final String nazione;
    private final int tipoPagamento;
    private final Optional<Integer> numeroCarta;
    private final Optional<Integer> scadenzaCarta;
    private final int tipoAbbonamento;

    public Op1Data(final String nickname, final String email, final String password,
            final LocalDate dataNascita, final String genere,
            final String nazione, final int tipoPagamento, final Optional<Integer> numeroCarta,
            final Optional<Integer> scadenzaCarta,
            final int tipoAbbonamento) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.dataNascita = dataNascita;
        this.genere = genere;
        this.nazione = nazione;
        this.tipoPagamento = tipoPagamento;
        this.numeroCarta = numeroCarta;
        this.scadenzaCarta = scadenzaCarta;
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public String getGenere() {
        return genere;
    }

    public String getNazione() {
        return nazione;
    }

    public int getTipoPagamento() {
        return tipoPagamento;
    }

    public Optional<Integer> getNumeroCarta() {
        return numeroCarta;
    }

    public Optional<Integer> getScadenzaCarta() {
        return scadenzaCarta;
    }

    public int getTipoAbbonamento() {
        return tipoAbbonamento;
    }
}
