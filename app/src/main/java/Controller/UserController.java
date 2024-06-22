package Controller;

import java.util.Objects;

import Model.Model;
import View.Op1Panel;
import View.Op2Panel;
import View.Op3Panel;
import View.Op4Panel;
import View.Op5Panel;
import View.Op6Panel;
import View.Op7Panel;
import View.UserPanel;
import data.Dati;

public class UserController {

    private final Model model;
    private final UserPanel panel;
    private final Op1Panel Op1Panel = new Op1Panel();
    private final Op2Panel Op2Panel = new Op2Panel();
    private final Op3Panel Op3Panel = new Op3Panel();
    private final Op4Panel Op4Panel = new Op4Panel();
    private final Op5Panel Op5Panel = new Op5Panel();
    private final Op6Panel Op6Panel = new Op6Panel();
    private final Op7Panel Op7Panel = new Op7Panel();

    public UserController(final Model model, final UserPanel panel) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(panel);
        this.model = model;
        this.panel = panel;

        this.panel.addOp1Listener(e -> panel.updateCenterPanel(Op1Panel));
        this.Op1Panel.addSubmitListener(e -> {
            final Dati.Op1Data data = this.Op1Panel.getData();
            this.model.Op1_addAccount(data.nickname(), data.email(), data.password(),
                    data.dataNascita(), data.genere(), data.nazione(),
                    data.tipoPagamento(), data.numeroCarta(), data.scadenzaCarta(),
                    data.tipoAbbonamento());

        });
        this.panel.addOp2Listener(e -> panel.updateCenterPanel(Op2Panel));
        this.Op2Panel.addSubmitListener(e -> {
            final Dati.Op2Data data = this.Op2Panel.getData();
            this.model.Op2_inviteAbbonamento(data.accountInvitato(), data.accountInvitante());
        });

        this.panel.addOp3Listener(e -> panel.updateCenterPanel(Op3Panel));
        this.Op3Panel.addSubmitListener(e -> {
            final Dati.Op3Data data = this.Op3Panel.getData();
            this.model.Op3_followArtist(data.accountSeguito(), data.accountSeguente());
        });

        this.panel.addOp4Listener(e -> panel.updateCenterPanel(Op4Panel));
        this.Op4Panel.addSubmitListener(e -> {
            final Dati.Op4Data data = this.Op4Panel.getData();
            this.model.Op4_createPlaylist(data.nome(), data.descrizione(), data.immagine(), data.privato(),
                    data.account(), data.collaboratore());
        });

        this.panel.addOp5Listener(e -> panel.updateCenterPanel(Op5Panel));
        this.Op5Panel.addSubmitListener(e -> {
            final Dati.Op5Data data = this.Op5Panel.getData();
            this.model.Op5_DeleteSong(data.codPlaylist(), data.numero());
        });

        this.panel.addOp6Listener(e -> panel.updateCenterPanel(Op6Panel));
        this.Op6Panel.addSearchListener(e -> {
            final String data = this.Op6Panel.getData();
            this.Op6Panel.updateTable(this.model.Op6_viewUserLibrary(data));
        });

        this.panel.addOp7Listener(e -> panel.updateCenterPanel(Op7Panel));
        this.Op7Panel.addSearchListener(e -> {
            final String data = this.Op7Panel.getData();
            this.Op7Panel.updateTable(this.model.Op7_searchSong(data));
        });
    }
}
