package com.nus.utils;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {

    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
