package br.com.onebr.config;

import br.com.onebr.service.util.RequestContextUtil;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import org.springframework.stereotype.Component;

@Component
public class CleanupServletRequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        RequestContextUtil.getInstance().setLocale(sre.getServletRequest().getLocale());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        RequestContextUtil.getInstance().clean();
    }
}
