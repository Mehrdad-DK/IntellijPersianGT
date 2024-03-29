package com.github.mehrdaddk.translator;


import com.github.mehrdaddk.http.HttpParams;
import com.github.mehrdaddk.http.HttpPostParams;
import com.github.mehrdaddk.Language;
import net.sf.json.JSONArray;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public final class GoogleTranslator extends AbstractTranslator {
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    private static final String BASE_URL = "http://translate.google.cn/translate_a/single";
    private static final String SCRIPT;

    static {
        SCRIPT = "function tk(a) {"
                + "var TKK = ((function() {var a = 561666268;var b = 1526272306;return 406398 + '.' + (a + b); })());\n"
                + "function b(a, b) { for (var d = 0; d < b.length - 2; d += 3) { var c = b.charAt(d + 2), c = 'a' <= c ? c.charCodeAt(0) - 87 : Number(c), c = '+' == b.charAt(d + 1) ? a >>> c : a << c; a = '+' == b.charAt(d) ? a + c & 4294967295 : a ^ c } return a }\n"
                + "for (var e = TKK.split('.'), h = Number(e[0]) || 0, g = [], d = 0, f = 0; f < a.length; f++) {"
                + "var c = a.charCodeAt(f);"
                + "128 > c ? g[d++] = c : (2048 > c ? g[d++] = c >> 6 | 192 : (55296 == (c & 64512) && f + 1 < a.length && 56320 == (a.charCodeAt(f + 1) & 64512) ? (c = 65536 + ((c & 1023) << 10) + (a.charCodeAt(++f) & 1023), g[d++] = c >> 18 | 240, g[d++] = c >> 12 & 63 | 128) : g[d++] = c >> 12 | 224, g[d++] = c >> 6 & 63 | 128), g[d++] = c & 63 | 128)"
                + "}"
                + "a = h;"
                + "for (d = 0; d < g.length; d++) a += g[d], a = b(a, '+-a^+6');"
                + "a = b(a, '+-3^+b+-f');"
                + "a ^= Number(e[1]) || 0;"
                + "0 > a && (a = (a & 2147483647) + 2147483648);"
                + "a %= 1E6;"
                + "return a.toString() + '.' + (a ^ h)\n"
                + "}";
    }

    public GoogleTranslator() {
        langMap.put(Language.EN, "en");
        langMap.put(Language.FA, "fa");
    }

    @Override
    protected String getResponse(Language from, Language to, String query) throws Exception {
        HttpParams params = new HttpPostParams();
        String tk = tk(query);

        params.put("client", "t")
                .put("sl", langMap.get(from))
                .put("tl", langMap.get(to))
                .put("hl", "fa-IR")
                .put("dt", "at")
                .put("dt", "bd")
                .put("dt", "ex")
                .put("dt", "ld")
                .put("dt", "md")
                .put("dt", "qca")
                .put("dt", "rw")
                .put("dt", "rm")
                .put("dt", "ss")
                .put("dt", "t")
                .put("ie", "UTF-8")
                .put("oe", "UTF-8")
                .put("source", "btn")
                .put("srcrom", "1")
                .put("ssel", "0")
                .put("tsel", "0")
                .put("kc", "11")
                .put("tk", tk)
                .put("q", query);

        return params.send2String(BASE_URL);
    }

    @Override
    protected String parseString(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONArray segments = jsonArray.getJSONArray(0);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < segments.size(); i++) {
            result.append(segments.getJSONArray(i).getString(0));
            result.append("\r\n");
        }
        return new String(result);
    }

    private String tk(String val) throws Exception {
        engine.eval(SCRIPT);
        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("tk", val);
    }
}