package View;

import java.util.List;

import data.Dati;

public class Op16Panel extends GeneralSelectPanel<Dati.Op16Data> {

    public Op16Panel() {
        super("Abbonamenti Attivi",
                List.of(new Table<>(
                        "Abbonamenti Attivi",
                        new String[] { "Nome", "Durata Mesi", "Num Abbonamenti Attivi" },
                        Op16Panel::function)));
        removeSearchField();
        removeSearchButton();
        removeCancelButton();
    }

    private static Object[] function(Dati.Op16Data data) {
        return new Object[] {
                data.nome(),
                data.durataMesi(),
                data.NumAbbonamentiAttivi()
        };
    }
}
