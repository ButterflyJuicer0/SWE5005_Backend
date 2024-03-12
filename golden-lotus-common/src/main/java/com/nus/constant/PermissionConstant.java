package com.nus.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionConstant {

    public static final List<String> USER_PERMISSIONS =
            new ArrayList<>(Arrays.asList("logout",
                    "add address", "modify address", "delete address",
                    "submit order",
                    "search",
                    "add to cart", "remove from cart", "clear cart"));

    public static final List<String> CHEF_PERMISSIONS =
            new ArrayList<>(Arrays.asList("manage dish", "manage order"));

    public static final List<String> ADMIN_PERMISSIONS =
            new ArrayList<>(Arrays.asList("add new chef", "modify chef information", "enable or disable account"));
}
