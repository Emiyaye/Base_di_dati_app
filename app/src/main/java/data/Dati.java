package data;

import java.time.LocalDate;
import java.util.Optional;

public class Dati {
        public record AdminLogin(String adminEmail, String password) {
        }

        public record Op1Data(String nickname, String email, String password, LocalDate dataNascita, String genere,
                        String nazione,
                        int tipoPagamento, Optional<Integer> numeroCarta, Optional<Integer> scadenzaCarta,
                        int tipoAbbonamento) {
        }

        public record Op2Data(String accountInvitante, String accountInvitato) {
        }

        public record Op3Data(String accountSeguito, String accountSeguente) {
        }

        public record Op4Data(String account, String collaboratore, String nome, String descrizione, String immagine,
                        boolean privato) {
        }

        public record Op5Data(int codPlaylist, int numero) {
        }

        public record Op6Data(String NomeArtista, String NomeAlbum, String NomeAutore, String NomePlaylist,
                        String AutorePlaylist) {
        }

        public record Op7Data( String titolo, int durataMin, int durataSec, boolean esplicito, int numRiproduzionis) {
        }

        public record Op8Data(String nome, String artista, int durataMin, int durataSec, int numBrani, int numFollowers,
                        String titolo, int numRiproduzioni, String cantante) {
        }

        public record Op9Data(String tipoAbbonamento, int durataMesi, LocalDate dataPagamento, LocalDate dataScadenza,
                        String accountPagante) {
        }

        public record Op10Data(int codBrano, String email, int msRiprodotti) {
        }

        public record  Op11Data(int codBrano, String testo) {
        }

        public record Op12Data(boolean disable, String account) {
        }

        public record Op13Data(int codBrano, String linkImage) {
        }

        public record Op14Data(String titolo, String artista, int numRiproduzioni, int durataMinuti, int durataSecondi, boolean esplicito){

        }

        public record Op15Data(String nickname, boolean verificato, String titolo, int numRiproduzioni) {
        }

        public record Op16Data(String nome, int durataMesi, int NumAbbonamentiAttivi) {
        }

        public record Op17Data(String NomeArtista, int NumeroAlbum, int NumeroSingoli) {
        }

        public record Op18Data(double fatturatoAnnuo) {
        }
}
