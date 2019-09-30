package org.mjaworski.backend.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpUtils {
    public static void buildResponse(HttpServletResponse res,
                                     String body, int responseCode) throws IOException {
        res.setStatus(responseCode);

        res.addHeader("Content-type", "application/json;charset=UTF-8");
        res.getWriter().write(body);
        res.getWriter().flush();
        res.getWriter().close();
    }
}
