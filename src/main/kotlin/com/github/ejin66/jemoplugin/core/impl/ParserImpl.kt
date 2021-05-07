package com.github.ejin66.jemoplugin.core.impl

import com.github.ejin66.jemoplugin.core.* // ktlint-disable no-wildcard-imports
import java.nio.charset.Charset

class ParserImpl : IParser {

    private var lineFeed = "\r\n"
    private var currentNode: Node? = null
    private var root: Node? = null
    private var nodes = mutableListOf<Node>()
    private var cachedMark = ""
    private var lineNumber = 0

    override fun parse(data: ByteArray): List<Node> {
        val source = data.toString(Charset.defaultCharset())
        lineFeed = readLineFeed(source)

        nodes.clear()
        root = null
        currentNode = null
        val lines = source.split(lineFeed)
        val segments = mutableListOf<List<String>>()

        var segment = mutableListOf<String>()

        lines.forEach {
            if (it.isEmpty()) {
                if (segment.isNotEmpty()) segments.add(segment)
                segment = mutableListOf("")
                return@forEach
            }

            segment.add(it)
        }
        if (segment.isNotEmpty()) segments.add(segment)

        lineNumber = 0
        return segments.mapNotNull { parseRoot(it) }
    }

    override fun lineFeed(): String {
        return lineFeed
    }

    private fun parseRoot(segment: List<String>): Node? {
        currentNode = null
        root = null

        for (line in segment) {
            lineNumber++

            val node = parseLine(line.trim(), lineNumber)
            if (root == null) root = node
        }
        return root
    }

    private fun parseLine(line: String, lineNumber: Int): Node? {
        if (line.isEmpty()) return null

        if (line.contains("}")) {
            currentNode = currentNode?.parent
            return null
        }

        if (line.contains("{")) {
            val node: Node = if (currentNode == null) {
                parseHeader()
            } else {
                val n = parseObject(line, lineNumber, false)
                currentNode!!.attach(n)
                n
            }
            currentNode = node
            return node
        }

        if (line.contains("[")) {
            val node = parseObject(line, lineNumber, true)
            currentNode?.attach(node)
            currentNode = node
            return node
        }

        if (line.contains("]")) {
            currentNode = currentNode?.parent
            return null
        }

        if (line.startsWith("//import")) {
            val node = parseImport(line)
            if (currentNode == null) {
                currentNode = node
                return node
            }

            currentNode!!.attach(node)
            return null
        }

        if (line.startsWith("//")) {
            cachedMark = line
            return null
        }

        parseLeaf(line, lineNumber).run {
            currentNode?.attach(this)
        }

        return null
    }

    private fun parseImport(line: String): Node {
        val node = ImportNode()
        val mark = ImportMark()
        mark.read(line)
        node.library = mark.library
        return node
    }

    private fun parseHeader(): Node {
        if (cachedMark.isEmpty()) {
            throw Exception("the mark of the json root is absent")
        }

        val mark = RootMark()
        mark.read(cachedMark)
        cachedMark = ""

        val root = RootNode()
        root.cls = mark.cls
        root.sup = mark.sup
        return root
    }

    private fun parseLeaf(line: String, lineNumber: Int): Node {
        val node = DataLeafNode()
        node.source = "$lineNumber, source: $line"

        if (cachedMark.isNotEmpty()) {
            val mark = LeafMark()
            mark.read(cachedMark)
            cachedMark = ""

            node.alias = mark.alias
            node.nullable = mark.nullable
        }

        node.name = readKey(line)
        val value = readValue(line)
        node.type = interType(value)
        return node
    }

    private fun parseObject(line: String, lineNumber: Int, isArray: Boolean): Node {
        val node = DataObjectNode(isArray)
        node.source = "$lineNumber, source: $line"

        if (cachedMark.isNotEmpty()) {
            val mark = ObjectMark()
            mark.read(cachedMark)
            cachedMark = ""

            node.cls = mark.cls
            node.alias = mark.alias
            node.nullable = mark.nullable
        }

        node.name = readKey(line)
        return node
    }

    private fun readKey(line: String): String {
        val result = Regex("\"(.*)\":").find(line)
        return result?.groups?.get(1)?.value ?: ""
    }

    private fun readValue(line: String): String {
        val result = Regex(".*:(.*)").find(line)
        var value = if (result == null) {
            line
        } else {
            result.groups[1]?.value ?: ""
        }
        value = value.trim()
        if (value.endsWith(",")) value = value.dropLast(1)
        return value
    }

    private fun interType(value: String): ValueType {
        if (value.startsWith("\"")) {
            return ValueType.String
        }

        if (value == "true" || value == "false") {
            return ValueType.Bool
        }

        if (value.contains(".")) {
            return ValueType.Double
        }

        if (value.toIntOrNull() != null) {
            return ValueType.Int
        }

        return ValueType.Unknown
    }

    private fun readLineFeed(source: String): String {
        var lineFeed = ""
        for (char in source) {
            if (char == '\r' || char == '\n') {
                lineFeed += char
            } else if (lineFeed == "") {
                continue
            } else {
                break
            }
        }
        return lineFeed
    }
}
