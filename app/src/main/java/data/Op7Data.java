package data;

public class Op7Data {
    private final int numero;
    private final String titolo;
    private final int numRiproduzioni;
    private final int durata;
    private final boolean esplicito;
    private final String fonteCrediti;
    private final String fileAudio;
    private final int codicePubblicazione;

    public Op7Data(final int numero, final String titolo, final int numRiproduzioni, final int durata,
            final boolean esplicito, final String fonteCrediti, final String fileAudio, final int codicePubblicazione) {
        this.numero = numero;
        this.titolo = titolo;
        this.numRiproduzioni = numRiproduzioni;
        this.durata = durata;
        this.esplicito = esplicito;
        this.fonteCrediti = fonteCrediti;
        this.fileAudio = fileAudio;
        this.codicePubblicazione = codicePubblicazione;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getNumRiproduzioni() {
        return numRiproduzioni;
    }

    public int getDurata() {
        return durata;
    }

    public boolean isEsplicito() {
        return esplicito;
    }

    public String getFonteCrediti() {
        return fonteCrediti;
    }

    public String getFileAudio() {
        return fileAudio;
    }

    public int getCodicePubblicazione() {
        return codicePubblicazione;
    }
}
