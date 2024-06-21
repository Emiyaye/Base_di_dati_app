package data;

import java.time.LocalDate;
import java.util.Optional;

public class Dati {
    public record Op1Data(String nickname, String email, String password, LocalDate dataNascita, String genere, String nazione,
            int tipoPagamento, Optional<Integer> numeroCarta, Optional<Integer> scadenzaCarta, int tipoAbbonamento) {
    }
    public record Op2Data(String accountInvitante, String accountInvitato) {
}
    public record Op3Data(String accountSeguito, String accountSeguente) {
    }

    public record Op7Data(int codiceBrano, int numero, String titolo, int numRiproduzioni, int durata, boolean esplicito, String fonteCrediti,
            String fileAudio, int codicePubblicazione) {
    }
}
