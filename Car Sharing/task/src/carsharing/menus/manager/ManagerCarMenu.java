package carsharing.menus.manager;

import carsharing.menus.customer.CarMenu;
import carsharing.menus.Menu;
import carsharing.service.CompanyCarService;

public class ManagerCarMenu extends CarMenu {

    public ManagerCarMenu(Menu parent, CompanyCarService service) {
        super(parent, service);
    }

    @Override
    public void launch() {

    }


}
