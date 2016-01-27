package ch.smartlink.backoffice.master.business;

import ch.smartlink.backoffice.master.web.form.Menu;

import java.util.List;

/**
 * Created by doomphantom on 26/01/2016.
 */
public interface MenuService {
    Menu getMenuForCurrentUser();

    String getMenuDataInJson();

    Menu getMenuByTenant(String tenantName);

    void validateMenu(List<ch.smartlink.backoffice.dao.entity.Menu> menus);
}
