package View;

import java.util.List;

import data.Dati;
import data.Dati.Op8Data;

public class Op8Panel extends GeneralSelectPanel<Op8Data> {

    public Op8Panel() {
        super("Visualizza album",
                List.of(new Table<>("Visualizza album",
                        new String[]{"Nome", "Artista", "DurataMin", "DurataSec", "NumBrani", "NumFollowers"},
                        Op8Panel::albumFunction),
                        new Table<>("Visualizza brani",
                        new String[]{"Titolo","NumRiproduzioni","Cantante"},
                        Op8Panel::albumDetailFunction)));
    }

    private static Object[] albumFunction(Dati.Op8Data data) {
        return new Object[]{
                data.nome(),
                data.artista(),
                data.durataMin(),
                data.durataSec(),
                data.numBrani(),
                data.numFollowers()
        };
    }

    private static Object[] albumDetailFunction(Dati.Op8Data data) {
        return new Object[]{
                data.titolo(),
                data.numRiproduzioni(),
                data.cantante()
        };
    }

}
