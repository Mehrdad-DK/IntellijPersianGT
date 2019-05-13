package com.github.mehrdaddk.translator;

import com.github.mehrdaddk.Language;

public interface Translator {

    String translation(Language from, Language to, String query);

}
