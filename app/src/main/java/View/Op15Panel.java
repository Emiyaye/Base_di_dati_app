package View;

import java.util.List;

import data.Dati;

public class Op15Panel extends GeneralSelectPanel<Dati.Op15Data> {

    public Op15Panel() {
        super("Artist email",
                List.of(new Table<>(
                        "Artist", new String[] { "Nickname", "Verificato" }, Op15Panel::artistFunction),
                        new Table<>("Songs", new String[] { "Titolo", "Durata", "numRipproduzioni" },
                                Op15Panel::songsFunction)));
    }

    private static Object[] artistFunction(Dati.Op15Data data) {
        return new Object[] {
                data.nickname(),
                data.verificato()
        };
    }

    private static Object[] songsFunction(Dati.Op15Data data) {
        return new Object[]{
                data.titolo(),
                data.durata(),
                data.numRiproduzioni()
        };
    }
}
