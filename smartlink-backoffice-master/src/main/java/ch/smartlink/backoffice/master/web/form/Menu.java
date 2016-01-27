package ch.smartlink.backoffice.master.web.form;

import java.io.Serializable;
import java.util.List;

/**
 * Created by doomphantom on 26/01/2016.
 */
public class Menu implements Serializable {
    List<MenuItem> menuItems;

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
