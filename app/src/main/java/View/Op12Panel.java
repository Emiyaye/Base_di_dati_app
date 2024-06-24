package View;

import data.Dati;

public class Op12Panel extends AbstractInsertPanel<Dati.Op12Data> {

    public static final String FIELD1 = "true/false (true = sospendi, false = riabilita)";
    public static final String FIELD2 = "email account";

    public Op12Panel() {
        super(new String[]{FIELD1, FIELD2}, "Update Account", "Cancel");
    }

    @Override
    public Dati.Op12Data getData() {
        final var data = getTextFieldMap();
        return new Dati.Op12Data(Boolean.parseBoolean(data.get(FIELD1).getText()),
                data.get(FIELD2).getText());
    }

    

}
