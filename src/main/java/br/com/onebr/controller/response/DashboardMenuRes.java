package br.com.onebr.controller.response;

import br.com.onebr.enumeration.Language;
import br.com.onebr.model.dashboard.DashboardMenu;
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
public class DashboardMenuRes {

    @JsonProperty("language")
    private String language;

    @Builder.Default
    @JsonProperty("items")
    private List<DashboardMenuItem> items = new ArrayList<>();

    public void addItem(List<DashboardMenu> dashboardMenuList) {
        if (!CollectionUtils.isEmpty(dashboardMenuList)) {
            dashboardMenuList.forEach(item -> addItem(item));
        }
    }

    public void addItem(DashboardMenu dashboardMenu) {
        if (dashboardMenu != null) {
            final String value = dashboardMenu.getNameEn() != null ? dashboardMenu.getNameEn() : dashboardMenu.getNamePt();

            items.add(DashboardMenuItem.builder()
                .name(Language.PT.getCode().equals(language) ? dashboardMenu.getNamePt() : value)
                .key(dashboardMenu.getKey())
                .build());
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardMenuItem {

        @JsonProperty("name")
        public String name;

        @JsonProperty("key")
        private String key;
    }
}
