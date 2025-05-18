package net.typho.jpp.plugin

import com.intellij.lang.Language

object JPPLanguage : Language("J++") {
    private fun readResolve(): Any = JPPLanguage
}