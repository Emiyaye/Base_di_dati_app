package View;

import java.util.List;

import data.Dati;
import data.Dati.Op14Data;

public class Op14Panel extends GeneralSelectPanel<Op14Data>{

    public Op14Panel() {
        super("Nazione",
                List.of(new Table<>("Top50 per nazione",
                        new String[]{"Titolo",
                            "Artista",
                            "numRiproduzioniTotali",
                            "Durata Minuti",
                            "Durata Secondi",
                            "Esplicito"}, Op14Panel::function)));
        this.setTextButtonSearch("Create");
    }

    private static Object[] function(Dati.Op14Data data) {
        return new Object[]{
                data.titolo(),
                data.artista(),
                data.numRiproduzioni(),
                data.durataMinuti(),
                data.durataSecondi(),
                data.esplicito(),
        };
    }

}
