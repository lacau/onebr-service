package br.com.onebr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StService {

    private static final String ST_CACHE = "st";

    private static final String PREFIX = "ST%d";

    private static final int MIN = 1;

    private static final int MAX = 10000;

    @Cacheable(value = ST_CACHE, key = "#root.methodName")
    public List<String> findAll() {
        final ArrayList<String> sts = new ArrayList<>();
        IntStream.range(MIN, MAX).forEach(i -> sts.add(String.format(PREFIX, i)));

        return sts;
    }
}
