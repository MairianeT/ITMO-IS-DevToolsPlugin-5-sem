package com.mairianet.demos.myplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import kotlin.Suppress;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RandomIntArrayPlugin extends AnAction {

    @Suppress(names = "ReturnCount")
    public void actionPerformed(@NotNull AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        assert editor != null;
        var project = event.getData(PlatformDataKeys.PROJECT);
        assert project != null;

        StringBuilder data = new StringBuilder("{");

        var input = JOptionPane.showInputDialog("Enter Length");
        var length = Integer.parseInt(input);
        input = JOptionPane.showInputDialog("Enter min value");
        var min = Integer.parseInt(input);
        input = JOptionPane.showInputDialog("Enter max value");
        var max = Integer.parseInt(input);

        for (int i = 0; i < length; i++) {
            var tmp = (int)(Math.random() * (max + 1 - min) + min);
            data.append(tmp);
            if (i < length - 1) {
                data.append(", ");
            } else {
                data.append("}");
            }
        }

        Runnable r = ()-> EditorModificationUtil.insertStringAtCaret(editor, data.toString());

        WriteCommandAction.runWriteCommandAction(project, r);
    }

    @Override
    public boolean isDumbAware() {
        return super.isDumbAware();
    }
}
