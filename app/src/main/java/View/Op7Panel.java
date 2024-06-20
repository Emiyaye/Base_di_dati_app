package View;

import data.Dati;

public class Op7Panel extends GeneralSelectPanel<Dati.Op7Data> {

    public Op7Panel() {
        super("Song Name",
                new String[] { "Numero", "Titolo", "NumRiproduzioni", "Durata", "Esplicito", "FonteCrediti",
                        "FileAudio", "CodicePubblicazione" },
                Op7Panel::function);
    }

    // return an array with all the attributes of Op7Data
    private static Object[] function(Dati.Op7Data data) {
        return new Object[] {
                data.numero(),
                data.titolo(),
                data.numRiproduzioni(),
                data.durata(),
                data.esplicito(),
                data.fonteCrediti(),
                data.fileAudio(),
                data.codicePubblicazione()
        };
    }
}
