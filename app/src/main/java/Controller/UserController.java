package Controller;

import java.util.Objects;

import Model.UserModel;
import View.UserPanel;

public class UserController {
    
    private final UserModel model;
    private final UserPanel panel;

    public UserController (final UserModel model, final UserPanel panel){
        Objects.requireNonNull(model);
        Objects.requireNonNull(panel);
        this.model = model;
        this.panel = panel;
    }
}
