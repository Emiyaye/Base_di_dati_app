package View;

import data.Dati;

public class Op4Panel extends AbstractInsertPanel<Dati.Op4Data> {

    private static final String FIELD1 = "Account";
    private static final String FIELD2 = "Collaboratore";
    private static final String FIELD3 = "Nome Playlist";
    private static final String FIELD4 = "Descrizione";
    private static final String FIELD5 = "Immagine";
    private static final String FIELD6 = "Privato (True or False, Default: false)";

    public Op4Panel() {
        super(new String[] { FIELD1, FIELD2, FIELD3, FIELD4, FIELD5, FIELD6 }, "Create", "Cancel");

    }

    @Override
    public Dati.Op4Data getData() {
        final var data = getTextFieldMap();
        return new Dati.Op4Data(data.get(FIELD1).getText(), data.get(FIELD2).getText(), data.get(FIELD3).getText(),
                data.get(FIELD4).getText(), data.get(FIELD5).getText(),
                data.get(FIELD6).getText().toLowerCase().equals("true") ? true : false);
    }
}
