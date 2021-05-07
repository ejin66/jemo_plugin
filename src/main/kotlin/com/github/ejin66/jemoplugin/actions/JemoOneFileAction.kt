package com.github.ejin66.jemoplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class JemoOneFileAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val file: VirtualFile = e.dataContext.getData(PlatformDataKeys.VIRTUAL_FILE) ?: return
        val project = e.project ?: return

        val geneDirPath = if (file.path.contains("src")) {
            "${project.basePath}/lib/src/generate/model"
        } else {
            "${project.basePath}/lib/generate/model"
        }

        val geneDir = File(geneDirPath)
        if (geneDir.exists()) geneDir.delete()

        try {
            ConvertHelper.convert(geneDirPath, file.path)
        } catch (e: Exception) {
            e.printStackTrace()
            val error = e.message ?: return
            Messages.showErrorDialog(error, "JEMO")
        }
    }

    override fun update(e: AnActionEvent) {
        val file: VirtualFile? = e.dataContext.getData(PlatformDataKeys.VIRTUAL_FILE)
        print(file?.extension)
        if (file == null || file.isDirectory || file.extension != "json") {
            e.presentation.isEnabledAndVisible = false
            return
        }

        e.presentation.isEnabledAndVisible = true
    }
}
