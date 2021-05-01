package com.github.ejin66.jemoplugin.core

interface IGenerator {

    fun generate(nodes: List<Node>, lineFeed: String): String

}