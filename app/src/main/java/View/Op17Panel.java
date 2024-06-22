package View;

import java.util.Arrays;

import data.Dati;

public class Op17Panel extends GeneralSelectPanel<Dati.Op17Data> {

    public Op17Panel() {
        super("Popular Artist",
                Arrays.asList(new Table<>(
                        "Popular Artist",
                        new String[]{"Nome Artista", "Num Album", "Num Singoli"},
                        Op17Panel::function)
                )
        );
        removeSearchField();
        removeSearchButton();
        removeCancelButton();
    }

    private static Object[] function(Dati.Op17Data data) {
        return new Object[]{
                data.NomeArtista(),
                data.NumeroAlbum(),
                data.NumeroSingoli()
        };
    }
}
