package net.typho.jpp.plugin;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class PopupDialogAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent event) {
        // Using the event, evaluate the context,
        // and enable or disable the action.
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        System.out.println("test");
        // Using the event, implement an action.
        // For example, create and show a dialog.
    }

    // Override getActionUpdateThread() when you target 2022.3 or later!

}