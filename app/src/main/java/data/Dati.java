package data;

import java.time.LocalDate;
import java.util.Optional;

public class Dati {
        public record Op1Data(String nickname, String email, String password, LocalDate dataNascita, String genere,
                        String nazione,
                        int tipoPagamento, Optional<Integer> numeroCarta, Optional<Integer> scadenzaCarta,
                        int tipoAbbonamento) {
        }

        public record Op2Data(String accountInvitante, String accountInvitato) {
        }

        public record Op3Data(String accountSeguito, String accountSeguente) {
        }

        public record Op4Data(String account, String collaboratore, String nome, String descrizione, String immagine, boolean privato) {
        }

        public record Op5Data(Integer codPlaylist, Integer numero) {
        }

        public record  Op6Data(String NomeArtista, String NomeAlbum, String NomeAutore, String NomePlaylist, String AutorePlaylist) {
        }

        public record Op7Data(int codiceBrano, int numero, String titolo, int numRiproduzioni, int durata,
                        boolean esplicito, String fonteCrediti,
                        String fileAudio, int codicePubblicazione) {
        }

        public record Op16Data(String nome, int durataMesi, int NumAbbonamentiAttivi) {
        }

        public record Op17Data(String NomeArtista, int NumeroAlbum, int NumeroSingoli) {
        }

        public record Op18Data(double fatturatoAnnuo) {
        }
}
