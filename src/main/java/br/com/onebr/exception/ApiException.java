package br.com.onebr.exception;

import br.com.onebr.config.ApplicationContextUtils;
import br.com.onebr.service.util.RequestContextUtil;
import java.util.Locale;
import lombok.Getter;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ApiException extends ResponseStatusException {

    private static MessageSourceAccessor messageSourceAccessor;

    private String[] args;

    private Locale locale;

    public ApiException(String message, HttpStatus status, String... args) {
        super(status, message);
        this.args = args;
        this.locale = RequestContextUtil.getInstance().getLocale();
        if (messageSourceAccessor == null) {
            messageSourceAccessor = ApplicationContextUtils.getBean(MessageSourceAccessor.class);
        }
    }

    @Override
    public String getReason() {
        String reason = super.getReason();
        if (!StringUtils.isEmpty(reason)) {
            if (args != null) {
                return messageSourceAccessor.getMessage(reason, args, locale);
            }

            return messageSourceAccessor.getMessage(reason, locale);
        }
        return getStatus().getReasonPhrase();
    }
}
