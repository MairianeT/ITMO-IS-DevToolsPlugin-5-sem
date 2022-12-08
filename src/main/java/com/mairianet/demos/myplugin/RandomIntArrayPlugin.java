package com.mairianet.demos.myplugin;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.ui.DialogPanel;
import com.intellij.openapi.ui.DoNotAskOption;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.messages.MessagesService;
import kotlin.Suppress;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RandomIntArrayPlugin extends AnAction {

    public static final NotificationGroup GROUP_DISPLAY_ID_INFO =
            new NotificationGroup("My notification group",
                    NotificationDisplayType.BALLOON, true);
    @Suppress(names = "ReturnCount")
    public void actionPerformed(@NotNull AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        assert editor != null;
        var project = event.getData(PlatformDataKeys.PROJECT);
        assert project != null;

        StringBuilder data = new StringBuilder("{");

        var input = JOptionPane.showInputDialog("Enter Length");
        if (!StringUtils.isNumeric(input)){
            Notification notification = GROUP_DISPLAY_ID_INFO.createNotification("You must enter the length as an positive integer!", NotificationType.ERROR);
            Notifications.Bus.notify(notification, project);
            return;
        }
        var length = Integer.parseInt(input);

        input = JOptionPane.showInputDialog("Enter min value");
        if (!StringUtils.isNumeric(input)){
            if(input.charAt(0) == '-'){
                var num = String.copyValueOf(input.toCharArray(), 1, input.length() - 1);
                if (!StringUtils.isNumeric(num)){
                    Notification notification = GROUP_DISPLAY_ID_INFO.createNotification("You must enter min as an integer value!", NotificationType.ERROR);
                    Notifications.Bus.notify(notification, project);
                    return;
                }
            }
            else {
                Notification notification = GROUP_DISPLAY_ID_INFO.createNotification("You must enter min as an integer value!", NotificationType.ERROR);
                Notifications.Bus.notify(notification, project);
                return;
            }
        }
        var min = Integer.parseInt(input);

        input = JOptionPane.showInputDialog("Enter max value");
        if(input.charAt(0) == '-'){
            var num = String.copyValueOf(input.toCharArray(), 1, input.length() - 1);
            if (!StringUtils.isNumeric(num)){
                Notification notification = GROUP_DISPLAY_ID_INFO.createNotification("You must enter max as an integer value!", NotificationType.ERROR);
                Notifications.Bus.notify(notification, project);
                return;
            }
        }
        else {
            Notification notification = GROUP_DISPLAY_ID_INFO.createNotification("You must enter max as an integer value!", NotificationType.ERROR);
            Notifications.Bus.notify(notification, project);
            return;
        }
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
