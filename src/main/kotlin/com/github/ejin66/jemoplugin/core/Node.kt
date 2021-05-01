package com.github.ejin66.jemoplugin.core

open class Node {
    var name: String = ""
    var type: ValueType = ValueType.Unknown
    var source: String = ""
    var parent: Node? = null

    open fun attach(node: Node) {}
}

open class LeafNode : Node()

class ImportNode : Node() {
    var library: String = ""
    var child: Node? = null

    override fun attach(node: Node) {
        child = node
    }
}

open class MultiNode : Node() {
    var children = mutableListOf<Node>()

    override fun attach(node: Node) {
        node.parent = this
        children.add(node)
    }
}

class RootNode : MultiNode() {
    var cls = ""
    var sup = ""
}

class DataLeafNode : LeafNode() {
    var alias = ""
    var nullable = true
}

class DataObjectNode(isArray: Boolean) : MultiNode() {

    init {
        type = if (isArray) {
            ValueType.Array
        } else {
            ValueType.Object
        }
    }

    var cls = ""
    var alias = ""
    var nullable = true

}
