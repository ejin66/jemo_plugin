package com.github.ejin66.jemoplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.io.File

class JemoAction() : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        var jsonDir = "${project.basePath}/lib/model/json"
        var geneDir = "${project.basePath}/lib/generate/model"

        if (!findJson(jsonDir, geneDir)) return

        jsonDir = "${project.basePath}/lib/src/model/json"
        geneDir = "${project.basePath}/lib/src/generate/model"

        findJson(jsonDir, geneDir)
    }

    private fun findJson(jsonDirPath: String, geneDirPath: String): Boolean {
        val jsonDir = File(jsonDirPath)
        if (!jsonDir.exists()) return false

        val geneDir = File(geneDirPath)
        if (geneDir.exists()) geneDir.delete()

        try {
            jsonDir.list()?.forEach {
                ConvertHelper.convert(geneDirPath, "$jsonDir/$it")
            }
        } catch (e: Exception) {
            e.message?.run {
                Messages.showErrorDialog(this, "JEMO")
            }
        }
        return true
    }
}
