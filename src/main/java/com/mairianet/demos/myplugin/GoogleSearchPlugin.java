package com.mairianet.demos.myplugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleSearchPlugin extends AnAction{
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        assert editor != null;
        String selectionText = editor.getSelectionModel().getSelectedText();
        assert selectionText != null;
        String encode = URLEncoder.encode(selectionText, StandardCharsets.UTF_8);
        String url = String.format("https://www.google.com/search?q=%s", encode);
        BrowserUtil.browse(url);
    }

    @Override
    public boolean isDumbAware() {
        return super.isDumbAware();
    }
}
