package br.com.onebr.controller.response;

import br.com.onebr.enumeration.Language;
import br.com.onebr.model.dashboard.DashboardOptions;
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
public class DashboardOptionsRes {

    @JsonProperty("language")
    private String language;

    @Builder.Default
    @JsonProperty("items")
    private List<DashboardOptionsItem> items = new ArrayList<>();

    public void addItem(List<DashboardOptions> dashboardOptionsList) {
        if (!CollectionUtils.isEmpty(dashboardOptionsList)) {
            dashboardOptionsList.forEach(item -> addItem(item));
        }
    }

    public void addItem(DashboardOptions dashboardOptions) {
        if (dashboardOptions != null) {
            final String value = dashboardOptions.getNameEn() != null ? dashboardOptions.getNameEn() : dashboardOptions.getNamePt();

            items.add(DashboardOptionsItem.builder()
                .name(Language.PT.getCode().equals(language) ? dashboardOptions.getNamePt() : value)
                .key(dashboardOptions.getKey())
                .build());
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardOptionsItem {

        @JsonProperty("name")
        public String name;

        @JsonProperty("key")
        private String key;
    }
}
