package br.com.onebr;

import br.com.onebr.security.TokenUtil;
import br.com.onebr.service.S3SynchronizationService;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OneBrJobs {

    private static final int INITIAL_DELAY = 5 * 1000; // 5 seconds

    private static final int INTERVAL = 1 * 60 * 60 * 1000; // 1 hour

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private S3SynchronizationService s3SynchronizationService;

    @Scheduled(initialDelay = INITIAL_DELAY, fixedDelay = INTERVAL)
    public void synchronizeS3Images() {
        s3SynchronizationService.synchronizeImages();
    }

    @Scheduled(initialDelay = INITIAL_DELAY, fixedDelay = INTERVAL)
    public void cleanInvalidJwtTokensFromCache() {
        log.info("message=Starting cleaning revoked jwt token cache.");
        for (Iterator<String> it = TokenUtil.REVOKED_TOKENS.iterator(); it.hasNext(); ) {
            final String next = it.next();
            if (tokenUtil.isTokenExpired(next)) {
                it.remove();
                log.info("message=Token revoked. token={}", next);
            }
        }
        log.info("message=End cleaning revoked jwt token cache.");
    }
}
