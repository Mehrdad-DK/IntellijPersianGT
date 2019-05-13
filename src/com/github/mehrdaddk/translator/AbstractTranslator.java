package com.github.mehrdaddk.translator;

import com.github.mehrdaddk.Language;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTranslator implements Translator {
    Map<Language, String> langMap = new HashMap<>();

    @Override
    public final String translation(Language from, Language to, String query) {
        String response = "";
        try {
            response = getResponse(from, to, query);
            return parseString(response);
        } catch (Exception e) {
            return response;
        }
    }

    protected abstract String getResponse(Language from, Language to, String query) throws Exception;

    protected abstract String parseString(String jsonString);
}
