package com.github.ejin66.jemoplugin.core

interface IParser {

    fun parse(data: ByteArray): List<Node>

    fun lineFeed(): String

}