package com.github.mehrdaddk;

import com.github.mehrdaddk.translator.GoogleTranslator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.ui.popup.PopupFactoryImpl;

class RequestRunnable implements Runnable {
    private final GoogleTranslator mGoogleTranslator;
    private final Editor mEditor;
    private final String mQuery;

    RequestRunnable(GoogleTranslator translator, Editor editor, String query) {
        this.mEditor = editor;
        this.mQuery = query;
        this.mGoogleTranslator = translator;
    }

    @Override
    public void run() {
        String text;
        if (isFarsi(mQuery)) {
            text = mGoogleTranslator.translation(Language.FA, Language.EN, mQuery);
        } else {
            text = mGoogleTranslator.translation(Language.EN, Language.FA, mQuery);
        }
        if (text == null) {
            showPopupBalloon("خطا در ترجمه！");
        } else {
            showPopupBalloon( text);
        }
    }

    private boolean isFarsi(String strName) {
        char[] cs = strName.toCharArray();
        for (char c : cs) {
            if (isFarsi(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFarsi(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.OLD_PERSIAN
                || ub == Character.UnicodeBlock.ARABIC;
    }

    private void showPopupBalloon(final String result) {
        ApplicationManager.getApplication().invokeLater(() -> {
            mEditor.putUserData(PopupFactoryImpl.ANCHOR_POPUP_POSITION, null);
            JBPopupFactory factory = JBPopupFactory.getInstance();
            factory.createHtmlTextBalloonBuilder(result, null, new JBColor(Gray._242, Gray._0), null)
                    .createBalloon()
                    .show(factory.guessBestPopupLocation(mEditor), Balloon.Position.below);
        });
    }
}
