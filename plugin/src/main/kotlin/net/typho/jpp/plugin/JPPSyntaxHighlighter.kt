package net.typho.jpp.plugin

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NotNull

class JPPSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val KEYWORD_KEYS: Array<TextAttributesKey> = arrayOf(TextAttributesKey.createTextAttributesKey("MYLANG_KEYWORD"))
        val STRING_KEYS: Array<TextAttributesKey> = arrayOf(TextAttributesKey.createTextAttributesKey("MYLANG_STRING"))
        val NUMBER_KEYS: Array<TextAttributesKey> = arrayOf(TextAttributesKey.createTextAttributesKey("MYLANG_NUMBER"))
        val COMMENT_KEYS: Array<TextAttributesKey> = arrayOf(TextAttributesKey.createTextAttributesKey("MYLANG_COMMENT"))
        val BAD_KEYS: Array<TextAttributesKey> = arrayOf(TextAttributesKey.createTextAttributesKey("MYLANG_BAD_CHAR"))
        val TEXT_KEYS: Array<TextAttributesKey> = arrayOf(TextAttributesKey.createTextAttributesKey("MYLANG_TEXT"))
        val ATTRS: MutableMap<IElementType, Array<TextAttributesKey>> = HashMap<IElementType, Array<TextAttributesKey>>().apply {
            put(JPPTokenTypes.KEYWORD, KEYWORD_KEYS)
            put(JPPTokenTypes.STRING, STRING_KEYS)
            put(JPPTokenTypes.NUMBER, NUMBER_KEYS)
            put(JPPTokenTypes.COMMENT, COMMENT_KEYS)
            put(JPPTokenTypes.BAD, BAD_KEYS)
            put(JPPTokenTypes.TEXT, TEXT_KEYS)
        }
    }

    override fun getHighlightingLexer(): Lexer {
        return JPPLexer()
    }

    override fun getTokenHighlights(@NotNull tokenType: IElementType): Array<TextAttributesKey> {
        return ATTRS[tokenType] ?: return TEXT_KEYS
    }
}