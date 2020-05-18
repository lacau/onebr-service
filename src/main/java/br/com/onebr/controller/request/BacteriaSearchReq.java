package br.com.onebr.controller.request;

import br.com.onebr.enumeration.BacteriaType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class BacteriaSearchReq extends PageRequest {

    private static final List<Integer> EMPTY_ARRAY = new ArrayList<>();

    private static final String EMPTY_STRING = "";

    private BacteriaType bacteriaType;

    private String searchTerm;

    private List<Integer> resistomes;

    private int yearStart;

    private int yearEnd;

    private List<Integer> ids;

    public BacteriaSearchReq(Pageable pageable, Sort sort) {
        super(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm != null ? searchTerm : EMPTY_STRING;
    }

    public void setResistomes(List<Integer> resistomes) {
        this.resistomes = resistomes != null ? resistomes : EMPTY_ARRAY;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids != null ? ids : EMPTY_ARRAY;
    }

    public void setDate(Integer yearStart, Integer yearEnd) {
        this.yearStart = yearStart != null ? yearStart : 0;
        this.yearEnd = yearEnd != null ? yearEnd : 0;
    }
}
