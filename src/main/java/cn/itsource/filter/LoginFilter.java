package cn.itsource.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        HttpServletRequest request=(HttpServletRequest) arg0;
        HttpSession session=request.getSession();
        String path=request.getRequestURI();
        if (path.indexOf("/login")>-1){
            filterChain.doFilter(request,httpServletResponse);
        }else {
            if (session.getAttribute("Account") == null) {
                httpServletResponse.sendRedirect("/login.html");
            } else {
                filterChain.doFilter(request, httpServletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
