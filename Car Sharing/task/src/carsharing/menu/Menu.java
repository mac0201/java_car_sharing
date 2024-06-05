package carsharing.menu;

import java.util.List;

public interface Menu {

    MenuResponse launch();
    List<String> getOptions();

}
