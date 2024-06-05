package carsharing.menu.menus;

import carsharing.menu.Menu;
import carsharing.menu.MenuOptions;
import carsharing.menu.MenuResponse;
import carsharing.menu.MenuUtils;

import java.util.List;

public class ManagerMenu implements Menu {

    private final Menu parent;

    public ManagerMenu(Menu parent) {
        this.parent = parent;
    }

    @Override
    public MenuResponse launch() {
        System.out.println("=== MANAGER MENU ===");
        MenuUtils.printNumberedOptionsList(getOptions());
        return new MenuResponse(parent);
    }

    @Override
    public List<String> getOptions() {
        return MenuOptions.MANAGER.getOptions();
    }
}
