package fr.enssat.dayoff_manager;

import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebFilter(
        filterName = "AuthFilter",
        urlPatterns = {"/*"}
)
public class AuthFilter extends HttpFilter {

    private static final Map<String, List<EmployeeType>> AUTH_MAP = new HashMap<>();
    private static final boolean ALLOW_DEBUG_ENDPOINTS = true;

    static {
        AUTH_MAP.put("change-password", Arrays.asList(EmployeeType.CLASSIC, EmployeeType.BOSS, EmployeeType.RH, EmployeeType.RH_ADMIN));
        AUTH_MAP.put("logout", Arrays.asList(EmployeeType.CLASSIC, EmployeeType.BOSS, EmployeeType.RH, EmployeeType.RH_ADMIN));
        AUTH_MAP.put("dayoff-timeline", Arrays.asList(EmployeeType.BOSS, EmployeeType.RH, EmployeeType.RH_ADMIN));
        AUTH_MAP.put("stat-overview", Arrays.asList(EmployeeType.RH, EmployeeType.RH_ADMIN));
        AUTH_MAP.put("default", Arrays.asList(EmployeeType.CLASSIC, EmployeeType.BOSS, EmployeeType.RH, EmployeeType.RH_ADMIN));

        AUTH_MAP.put("dayofftype-delete", Collections.singletonList(EmployeeType.RH_ADMIN));
        AUTH_MAP.put("dayofftype-add-edit", Collections.singletonList(EmployeeType.RH_ADMIN));
        AUTH_MAP.put("dayofftype-list", Collections.singletonList(EmployeeType.RH_ADMIN));

        AUTH_MAP.put("employees-delete", Collections.singletonList(EmployeeType.RH_ADMIN));
        AUTH_MAP.put("employees-add-edit", Collections.singletonList(EmployeeType.RH_ADMIN));
        AUTH_MAP.put("employees-list", Collections.singletonList(EmployeeType.RH_ADMIN));

        AUTH_MAP.put("rh-dayoff-edit", Arrays.asList(EmployeeType.RH, EmployeeType.RH_ADMIN));
        AUTH_MAP.put("rh-dayoff-list", Arrays.asList(EmployeeType.RH, EmployeeType.RH_ADMIN));
        AUTH_MAP.put("manage-my-dayoffs", Arrays.asList(EmployeeType.CLASSIC, EmployeeType.BOSS, EmployeeType.RH, EmployeeType.RH_ADMIN));


    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Employee user = (Employee) req.getSession().getAttribute(Constants.LOGGED_USER_SESSION_ATTRIBUTE_NAME);
        String endpoint = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1, req.getRequestURI().length());
        req.getSession().setAttribute(Constants.CURRENT_ENDPOINT_SESSION_ATTRIBUTE_NAME, endpoint);
        if (endpoint.equals("login") || req.getRequestURI().contains("includes")) {
            chain.doFilter(req, res);
            return;
        }

        if (ALLOW_DEBUG_ENDPOINTS && endpoint.equals("showDBContents")) {
            chain.doFilter(req, res);
            return;
        }

        if (user == null) {
            res.sendRedirect("login");
        } else {
            List<EmployeeType> allowedTypes = AUTH_MAP.get(endpoint);
            if (allowedTypes != null) {
                if (allowedTypes.contains(user.getType())) {
                    chain.doFilter(req, res);
                } else {
                    res.sendError(404);
                }
            } else {
                res.sendError(404);
            }
        }
    }
}
