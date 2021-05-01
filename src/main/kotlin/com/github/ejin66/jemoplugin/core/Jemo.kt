package com.github.ejin66.jemoplugin.core

import com.github.ejin66.jemoplugin.core.impl.GeneratorImpl
import com.github.ejin66.jemoplugin.core.impl.ParserImpl

class Jemo(var parser: IParser, var generator: IGenerator) {

    fun start(source: ByteArray): String {
        val nodes = parser.parse(source)
        return generator.generate(nodes, parser.lineFeed())
    }

    companion object {
        fun dart(source: ByteArray): String {
            return Jemo(ParserImpl(), GeneratorImpl()).start(source)
        }
    }
}
