package com.github.ejin66.jemoplugin.actions

import com.github.ejin66.jemoplugin.core.Jemo
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ConvertHelper {

    companion object {

        fun convert(geneDirPath: String, sourcePath: String) {
            val inputFile = File(sourcePath)

            val geneName = "$geneDirPath/${inputFile.name.dropLast(5)}.dart"

            val geneFile = File(geneName)

            if (!geneFile.parentFile.exists()) {
                geneFile.parentFile.mkdirs()
            }

            val output = FileOutputStream(geneFile)
            val input = FileInputStream(inputFile)

            try {
                val source = BufferedInputStream(input).readBytes()
                val convertData = transform(source) ?: return
                output.write(convertData)
                output.flush()
            } finally {
                output.close()
                input.close()
            }
        }

        private fun transform(data: ByteArray): ByteArray? {
            return Jemo.dart(data).toByteArray()
        }
    }

}