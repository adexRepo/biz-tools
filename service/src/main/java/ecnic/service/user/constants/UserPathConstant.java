package ecnic.service.user.constants;

import static ecnic.service.common.constants.CommonPathConstant.CREATE;
import static ecnic.service.common.constants.CommonPathConstant.DELETE;
import static ecnic.service.common.constants.CommonPathConstant.UPDATE;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class UserPathConstant {
    
    public static final String AUTH = "/auth";
    public static final String USERS = "/users";
    
    // authentication
    public static final String AUTHN_LOGIN = AUTH + "/login";
    public static final String AUTHN_CHECK_USERNAME = AUTH + "/check-username";
    public static final String AUTHN_REFRESH_TOKEN = AUTH + "/refresh-token";
    public static final String AUTHN_LOGOUT = AUTH + "/logout";
    
    // authorization menu
    public static final String AUTHZ_MENU = AUTH + "/menus";
    public static final String AUTHZ_MENU_ACCESS = AUTH + "/menus-access";
    public static final String AUTHZ_MENU_ACCESS_CREATE = AUTHZ_MENU_ACCESS + CREATE;
    public static final String AUTHZ_MENU_ACCESS_UPDATE = AUTHZ_MENU_ACCESS + UPDATE;
    public static final String AUTHZ_MENU_ACCESS_DELETE = AUTHZ_MENU_ACCESS + DELETE;
    
    // authorization actions
    public static final String AUTHZ_ACTION = AUTH + "/actions";
    public static final String AUTHZ_ACTION_CREATE = AUTHZ_ACTION + CREATE;
    public static final String AUTHZ_ACTION_UPDATE = AUTHZ_ACTION + UPDATE;
    public static final String AUTHZ_ACTION_DELETE = AUTHZ_ACTION + DELETE;
    
}
