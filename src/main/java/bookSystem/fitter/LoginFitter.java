package bookSystem.fitter;

import bookSystem.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@Component
@WebFilter(urlPatterns = "/*")
public class LoginFitter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String url = req.getRequestURL().toString();
        log.info("请求的url{}",url);
        if(url.contains("login")){
            filterChain.doFilter(servletRequest,servletResponse);
            log.info("登录操作");
            return;
        }

        if (HttpMethod.OPTIONS.toString().equals(req.getMethod())) {
            System.out.println("OPTIONS请求，放行");

            // 设置CORS响应头
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

            // 设置状态码为200
            resp.setStatus(HttpServletResponse.SC_OK);

            return; // 不调用filterChain，直接返回
        }

        String jwt = req.getHeader("authorization");
        log.info(jwt);

        if (!StringUtils.hasLength(jwt)){
            resp.setContentType("application/json");
            resp.getWriter().write("{\"redirect\": \"http://localhost:8081/#/login\"}");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        try {
            JwtUtils.analysisJwt(jwt);
        }catch (Exception e){
            e.printStackTrace();
            resp.setContentType("application/json");
            resp.getWriter().write("{\"redirect\": \"http://localhost:8081/#/login\"}");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        log.info("合法");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
