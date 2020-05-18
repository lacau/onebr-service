package br.com.onebr.enumeration;

import lombok.Getter;

@Getter
public enum Scope {

    MENU(false),
    FOOTER(false),
    HOME(false),
    TEAM(false),
    CONTRIBUTOR(false),
    PUBLICATION(false),
    CONTACT(false),
    RESTRICTED_DASHBOARD(true),
    RESTRICTED_EXPERIMENT(true),
    RESTRICTED_PUBLICATION(true),
    RESTRICTED_USER(true);

    boolean restricted;

    Scope(boolean restricted) {
        this.restricted = restricted;
    }
}
