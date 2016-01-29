package ch.smartlink.backoffice.master.business.impl;

import ch.smartlink.backoffice.common.constant.AppConstants;
import ch.smartlink.backoffice.common.util.SessionUtil;
import ch.smartlink.backoffice.common.util.TenantUtil;
import ch.smartlink.backoffice.dao.repository.IMenuRepository;
import ch.smartlink.backoffice.master.business.MenuService;
import ch.smartlink.backoffice.master.util.BOSecurityChecker;
import ch.smartlink.backoffice.master.web.form.Menu;
import ch.smartlink.backoffice.master.web.form.MenuItem;
import ch.smartlink.core.log.LOG;
import ch.smartlink.core.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by doomphantom on 26/01/2016.
 */
public class MenuServiceImpl implements MenuService {
    private static Map<String, Menu> menuMap = new HashMap<>();
    @Autowired
    private IMenuRepository menuRepository;


    private static String SESSION_ATT_MENU = "session_att_menu";
    private static String ROLE_FORMAT = "ROLE_%s_%s_VIEW";


    @PostConstruct
    public void buildMenuMap() {
        Set<String> tenants = TenantUtil.getSupportedTenants();
        tenants.forEach(tenant -> {
            Menu menu = getMenuByTenant(tenant);
            menuMap.put(tenant, menu);
        });
    }

    @Override
    public Menu getMenuForCurrentUser() {
        Menu menu = menuMap.get(TenantUtil.getSelectedTenant());
        if (menu == null) {
            menu = menuMap.get(AppConstants.TENANT_MASTER);
        }
        List<MenuItem> resultMenuItems = new ArrayList<>();
        setAuthority(resultMenuItems, menu.getMenuItems());
        Menu resultMenu = new Menu();
        resultMenu.setMenuItems(resultMenuItems);
        return resultMenu;
    }

    public Menu getMenuByTenant(String tenantName) {
        List<ch.smartlink.backoffice.dao.entity.Menu> menus = menuRepository.findByTenantId(tenantName);
        if (menus.isEmpty()) {
            menus = menuRepository.findByTenantId(AppConstants.TENANT_MASTER);
        }
        this.validateMenu(menus);
        List<MenuItem> menuItems = new ArrayList<>();
        int order = 0;
        while (true) {
            Optional<ch.smartlink.backoffice.dao.entity.Menu> menuAtLevel1 = getMenuItemByParentAndOrder(menus, "", order);
            if (menuAtLevel1.isPresent()) {
                MenuItem menuItem = buildMenuItem(menus, menuAtLevel1.get());
                menuItems.add(menuItem);
                order++;
            } else {
                break;
            }
        }
        Menu menu = new Menu();
        menu.setMenuItems(menuItems);
        return menu;
    }

    @Override
    public void validateMenu(List<ch.smartlink.backoffice.dao.entity.Menu> menus) {
        /*TODO: a valid menu has to meet the below requirements:
                + The child menu items must belong to a existence parent menu item. (except the menu items level 1 which don't have parent)
                + All the menu item must follow the continuous order
         */
    }

    @Override
    public String getMenuDataInJson() {
        try {
            String menuInJson = (String) SessionUtil.get(SESSION_ATT_MENU);
            if (StringUtils.isBlank(menuInJson)) {
                menuInJson = JsonUtil.convertObjectToJson(this.getMenuForCurrentUser());
                SessionUtil.set(SESSION_ATT_MENU, menuInJson);
            }
            return menuInJson;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return "";
    }

    private static MenuItem buildMenuItem(List<ch.smartlink.backoffice.dao.entity.Menu> menus, ch.smartlink.backoffice.dao.entity.Menu menu) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(menu.getId());
        menuItem.setLink(menu.getLink());
        menuItem.setName(menu.getName());
        menuItem.setModuleName(menu.getModuleName());
        List<MenuItem> menuItems = buildMenuItem(menus, menuItem);
        menuItem.setItems(menuItems);
        return menuItem;
    }

    private static boolean checkAuthority(String moduleName) {
        if (StringUtils.isBlank(moduleName)) {
            return true;
        }
        return BOSecurityChecker.checkRoleInAuthorities(buildUserRole(moduleName), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }

    private static String buildUserRole(String moduleName) {
        return String.format(ROLE_FORMAT, TenantUtil.getSelectedTenant(), moduleName);
    }

    private static List<MenuItem> buildMenuItem(List<ch.smartlink.backoffice.dao.entity.Menu> menus, MenuItem menuItemParent) {
        List<MenuItem> menuItems = new ArrayList<>();
        int order = 0;
        while (true) {
            Optional<ch.smartlink.backoffice.dao.entity.Menu> menuAtLevel1 = getMenuItemByParentAndOrder(menus, menuItemParent.getId(), order);
            if (menuAtLevel1.isPresent()) {
                MenuItem menuItem = buildMenuItem(menus, menuAtLevel1.get());
                menuItems.add(menuItem);
                order++;
            } else {
                break;
            }
        }
        return menuItems;
    }


    private static Optional<ch.smartlink.backoffice.dao.entity.Menu> getMenuItemByParentAndOrder(List<ch.smartlink.backoffice.dao.entity.Menu> menus, String parent, int order) {
        return menus.stream().filter(m -> StringUtils.equals(m.getParrentId(), parent) && m.getOrder() == order).findFirst();
    }

    public void setAuthority(List<MenuItem> resultMenuItems, final List<MenuItem> menuItems) {
        menuItems.forEach(menuItem -> {
            boolean hasAuthority = checkAuthority(menuItem.getModuleName());
            if (hasAuthority) {
                MenuItem resultMenuItem = new MenuItem();
                BeanUtils.copyProperties(menuItem, resultMenuItem);
                resultMenuItems.add(resultMenuItem);
                if (!menuItem.getItems().isEmpty()) {
                    List<MenuItem> menuItemsTemp = new ArrayList<>();
                    resultMenuItem.setItems(menuItemsTemp);
                    setAuthority(menuItemsTemp, menuItem.getItems());
                }
            }
        });
    }
}
