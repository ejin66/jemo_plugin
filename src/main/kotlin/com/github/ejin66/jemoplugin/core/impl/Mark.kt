package com.github.ejin66.jemoplugin.core.impl

open class Mark {

    internal var markDict: Map<String, String> = mutableMapOf()

    open fun read(mark: String) {
        markDict = parseMarkStr(mark.substring(2))
    }

    private fun parseMarkStr(mark: String): Map<String, String> {
        val result = mutableMapOf<String, String>()
        for (item in mark.split(",")) {
            val data = item.split(":")
            if (data.size != 2) continue

            val key = data[0].trim()
            val value = data[1].trim()
            result[key] = value
        }
        return result
    }

}

class ImportMark : Mark() {
    var library = ""

    override fun read(mark: String) {
        super.read(mark)
        if (markDict.containsKey("import")) {
            library = markDict["import"]!!
        }
    }
}

class RootMark: Mark() {
    var cls = ""
    var sup = ""

    override fun read(mark: String) {
        super.read(mark)
        if (markDict.containsKey("class")) {
            cls = markDict["class"]!!
        }
        if (markDict.containsKey("super")) {
            sup = markDict["super"]!!
        }
    }

}

open class LeafMark: Mark() {
    var alias = ""
    var nullable = true

    override fun read(mark: String) {
        super.read(mark)
        if (markDict.containsKey("alias")) {
            alias = markDict["alias"]!!
        }
        if (markDict.containsKey("nullable")) {
            nullable = markDict["nullable"]!! == "true"
        }
    }
}

class ObjectMark: LeafMark() {
    var cls = ""

    override fun read(mark: String) {
        super.read(mark)
        if (markDict.containsKey("class")) {
            cls = markDict["class"]!!
        }
    }

}



