package br.com.onebr.service.util;

import java.util.Locale;

public class RequestContextUtil {

    private static final ThreadLocal<RequestContext> REQUEST_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    private static final RequestContextUtil INSTANCE;

    private RequestContextUtil() {
    }

    static {
        INSTANCE = new RequestContextUtil();
    }

    public static RequestContextUtil getInstance() {
        return INSTANCE;
    }

    public void setLocale(Locale locale) {
        REQUEST_CONTEXT_THREAD_LOCAL.set(RequestContext.builder().locale(locale == null ? Locale.US : locale).build());
    }

    public void clean() {
        REQUEST_CONTEXT_THREAD_LOCAL.remove();
    }

    public Locale getLocale() {
        return REQUEST_CONTEXT_THREAD_LOCAL.get().getLocale();
    }
}
