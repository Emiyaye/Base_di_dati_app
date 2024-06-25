package View;

import java.util.List;

import data.Dati;

public class Op7Panel extends GeneralSelectPanel<Dati.Op7Data> {

        public Op7Panel() {
                super("Song Name",
                                List.of(
                                                new Table<>(
                                                                "Songs",
                                                                new String[] { "Titolo", "DurataMin", "DurataSec",
                                                                                "Esplicito", "NumRiproduzioni" },
                                                                Op7Panel::function)));
        }

        // return an array with all the attributes of Op7Data
        private static Object[] function(Dati.Op7Data data) {
                return new Object[] {
                                data.titolo(),
                                data.durataMin(),
                                data.durataSec(),
                                data.esplicito(),
                                data.numRiproduzionis()
                };
        }
}
