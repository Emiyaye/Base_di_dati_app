package View;

import data.Dati;
import data.Dati.Op13Data;

public class Op13Panel extends AbstractInsertPanel<Dati.Op13Data> {

    public static final String FIELD1 = "codBrano";
    public static final String FIELD2 = "Immagine per Radio";

    public Op13Panel() {
        super(new String[] {FIELD1, FIELD2}, "Analyze", "Cancel");
    }

    @Override
    public Op13Data getData() {
        final var data = getTextFieldMap();
        return new Dati.Op13Data(Integer.parseInt(data.get(FIELD1).getText()),
                data.get(FIELD2).getText());
    }



}
