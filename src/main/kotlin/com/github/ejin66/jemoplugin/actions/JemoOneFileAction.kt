package com.github.ejin66.jemoplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class JemoOneFileAction: AnAction() {

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

        ConvertHelper.convert(geneDirPath, file.path)
    }

    override fun update(e: AnActionEvent) {
        val file: VirtualFile? = e.dataContext.getData(PlatformDataKeys.VIRTUAL_FILE)
        print(file?.extension)
        if (file == null || file.extension != "json") {
            templatePresentation.isEnabledAndVisible = false
            return
        }
        templatePresentation.isEnabledAndVisible = true
    }
}