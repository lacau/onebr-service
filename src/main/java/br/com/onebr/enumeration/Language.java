package br.com.onebr.enumeration;

import lombok.Getter;

@Getter
public enum Language {

    PT("pt"),
    EN("en");

    String code;

    Language(String code) {
        this.code = code;
    }
}
