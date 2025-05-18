package net.typho.jpp.plugin

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class JPPFileType : LanguageFileType(JPPLanguage) {
    companion object {
        @JvmField
        val INSTANCE: JPPFileType = JPPFileType()
    }

    override fun getName(): String {
        return "J++"
    }

    override fun getDescription(): String {
        return "Java and C++ merged together: J++"
    }

    override fun getDefaultExtension(): String {
        return "jpp"
    }

    override fun getIcon(): Icon {
        TODO("Not yet implemented")
    }
}