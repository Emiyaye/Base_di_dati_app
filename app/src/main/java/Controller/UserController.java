package Controller;

import java.util.Objects;

import Model.UserModel;
import View.Op1Panel;
import View.Op3Panel;
import View.Op7Panel;
import View.UserPanel;
import data.Dati;

public class UserController {

    private final UserModel model;
    private final UserPanel panel;
    private final Op1Panel Op1Panel = new Op1Panel();
    private final Op3Panel Op3Panel = new Op3Panel();
    private final Op7Panel Op7Panel = new Op7Panel();


    public UserController(final UserModel model, final UserPanel panel) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(panel);
        this.model = model;
        this.panel = panel;

        this.panel.addOp1Listener(e -> panel.UpdateCenterPanel(Op1Panel));
        this.Op1Panel.addSubmitListener(e -> {
            final Dati.Op1Data data = this.Op1Panel.getData();
            this.model.Op1_addAccount(data.nickname(), data.email(), data.password(),
                    data.dataNascita(), data.genere(), data.nazione(),
                    data.tipoPagamento(), data.numeroCarta(), data.scadenzaCarta(),
                    data.tipoAbbonamento());

        });
        this.panel.addOp3Listener(e -> panel.UpdateCenterPanel(Op3Panel));
        this.Op3Panel.addSubmitListener(e -> {
            final Dati.Op3Data data = this.Op3Panel.getData();
            this.model.Op3_followArtist(data.accountSeguito(), data.accountSeguente());
        });
        this.panel.addOp7Listener(e -> panel.UpdateCenterPanel(Op7Panel));
        this.Op7Panel.addSearchListener(e -> {
            final String data = this.Op7Panel.getOp7Data();
            this.Op7Panel.updateTable(this.model.Op7_searchSong(data));
        });
    }
}
