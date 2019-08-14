package com.steamyao.newcode.interceptor;

import com.steamyao.newcode.dao.LoginTicketDOMapper;
import com.steamyao.newcode.dao.UserDOMapper;
import com.steamyao.newcode.dataobject.LoginTicketDO;
import com.steamyao.newcode.dataobject.UserDO;
import com.steamyao.newcode.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TicketInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginTicketDOMapper loginTicketDOMapper;
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        String ticket = null;
        // 在request的cookie里找到ticket的值
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        // 找到有效ticket的user并放进ThreadLocal里，可供后续使用
        if (ticket != null) {
            LoginTicketDO loginTicket = loginTicketDOMapper.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }
            UserDO user = userDOMapper.selectByPrimaryKey(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        if (modelAndView != null && hostHolder.getUser() != null) {
            // 在渲染之前把"user"加进view里
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // 视图渲染完成之后，资源清理
        hostHolder.clear();
    }
}