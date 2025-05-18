package net.typho.jpp.plugin

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.swing.Icon
import java.util.HashMap

class JPPColorSettingsPage : ColorSettingsPage {
    companion object {
        val DESCRIPTORS: Array<AttributesDescriptor> = arrayOf(
            AttributesDescriptor("Keyword", TextAttributesKey.createTextAttributesKey("MYLANG_KEYWORD")),
            AttributesDescriptor("String", TextAttributesKey.createTextAttributesKey("MYLANG_STRING")),
            AttributesDescriptor("Number", TextAttributesKey.createTextAttributesKey("MYLANG_NUMBER")),
            AttributesDescriptor("Comment", TextAttributesKey.createTextAttributesKey("MYLANG_COMMENT")),
            AttributesDescriptor("Bad Character", TextAttributesKey.createTextAttributesKey("MYLANG_BAD_CHAR"))
        )
    }

    @Nullable
    override fun getIcon(): Icon? {
        return null
    }

    @NotNull
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    @NotNull
    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    @NotNull
    override fun getDisplayName(): String {
        return "J++"
    }

    @Nullable
    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? {
        return HashMap<String, TextAttributesKey>()
    }

    @NotNull
    override fun getHighlighter(): SyntaxHighlighter {
        return JPPSyntaxHighlighter()
        /*
        return arrayOf(
            TextAttributesKey.createTextAttributesKey("MYLANG_KEYWORD"),
            TextAttributesKey.createTextAttributesKey("MYLANG_STRING"),
            TextAttributesKey.createTextAttributesKey("MYLANG_NUMBER"),
            TextAttributesKey.createTextAttributesKey("MYLANG_COMMENT"),
            TextAttributesKey.createTextAttributesKey("MYLANG_BAD_CHAR")
        )
         */
    }

    @Nullable
    override fun getDemoText(): @NonNls String {
        return "keyword1 \"string1\" 1234 //comment"
    }
}