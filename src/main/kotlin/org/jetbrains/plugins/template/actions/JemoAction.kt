package org.jetbrains.plugins.template.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.io.*

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

        jsonDir.listFiles { file -> file.name.endsWith(".json") }?.forEach {
            val geneName = "$geneDirPath/${it.name.dropLast(5)}.dart"

            val geneFile = File(geneName)

            if (!geneFile.parentFile.exists()) {
                geneFile.parentFile.mkdirs()
            }

            val output = FileOutputStream(geneFile)
            val input = FileInputStream(it)

            try {
                val source = BufferedInputStream(input).readBytes()
                val convertData = convert(source) ?: return@forEach
                output.write(convertData)
                output.flush()
            } finally {
                output.close()
                input.close()
            }
        }

        return true
    }

    private fun convert(data: ByteArray): ByteArray? {
        return data
    }

}