package Controller;

import View.AdminFrame;
import View.UserFrame;
import View.View;

public class MainControllerImpl implements MainController{
    
    private final View adminFrame;
    private final View userFrame;

    public MainControllerImpl() {
        this.adminFrame = new AdminFrame();
        this.userFrame = new UserFrame();
    }

    @Override
    public void openAdminView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'openAdminView'");
    }

    @Override
    public void openUserView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'openUserView'");
    }
}
