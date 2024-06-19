package Controller;

import java.util.Objects;

import Model.UserModel;
import View.Op1Panel;
import View.Op3Panel;
import View.UserPanel;
import data.Op1Data;
import data.Op3Data;

public class UserController {

    private final UserModel model;
    private final UserPanel panel;
    private final Op1Panel Op1Panel = new Op1Panel();
    private final Op3Panel Op3Panel = new Op3Panel();

    public UserController(final UserModel model, final UserPanel panel) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(panel);
        this.model = model;
        this.panel = panel;

        this.panel.addOp1Listener(e -> panel.UpdateEastPanel(Op1Panel));
        this.Op1Panel.addInsertListener(e -> {
            Op1Data data = this.Op1Panel.getOp1Data();
            this.model.Op1_addAccount(data.getNickname(), data.getEmail(), data.getPassword(),
                    data.getDataNascita(), data.getGenere(), data.getNazione(),
                    data.getTipoPagamento(), data.getNumeroCarta(), data.getScadenzaCarta(),
                    data.getTipoAbbonamento());

        });
        this.panel.addOp3Listener(e -> panel.UpdateEastPanel(Op3Panel));
        this.Op3Panel.addFollowListener(e -> {
            Op3Data data = this.Op3Panel.getOp3Data();
            this.model.Op3_followArtist(data.getAccountSeguito(), data.getAccountSeguente());
        });
    }
}
