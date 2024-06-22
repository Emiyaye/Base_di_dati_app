package View;

import java.util.List;

import data.Dati;

public class Op18Panel extends GeneralSelectPanel<Dati.Op18Data> {

    public Op18Panel() {
        super("Year",
                List.of(
                        new Table<>("Fatturato_Annuo", new String[] { "Fatturato Annuo" }, Op18Panel::function)));
    }

    private static Object[] function(Dati.Op18Data data) {
        return new Object[] { data.fatturatoAnnuo() };
    }
}