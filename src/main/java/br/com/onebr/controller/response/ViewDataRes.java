package br.com.onebr.controller.response;

import br.com.onebr.enumeration.Language;
import br.com.onebr.enumeration.Scope;
import br.com.onebr.model.config.ViewData;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewDataRes {

    @JsonProperty("scope")
    private Scope scope;

    @JsonProperty("language")
    private String language;

    @Builder.Default
    @JsonProperty("items")
    private List<ViewDataItem> items = new ArrayList<>();

    public void addItem(List<ViewData> viewData) {
        if (!CollectionUtils.isEmpty(viewData)) {
            viewData.forEach(item -> addItem(item));
        }
    }

    public void addItem(ViewData viewData) {
        if (viewData != null) {
            final String value = viewData.getContentEn() != null ? viewData.getContentEn() : viewData.getContentPt();

            items.add(ViewDataItem.builder()
                .key(viewData.getKey())
                .value(Language.PT.getCode().equals(language) ? viewData.getContentPt() : value)
                .build());
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewDataItem {

        @JsonProperty("key")
        public String key;

        @JsonProperty("value")
        public String value;
    }
}
