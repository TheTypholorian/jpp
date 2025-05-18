package net.typho.jpp.plugin

import com.intellij.psi.tree.IElementType

object JPPTokenTypes {
    val KEYWORD: IElementType = JPPTokenType("KEYWORD")
    val STRING: IElementType = JPPTokenType("STRING")
    val NUMBER: IElementType = JPPTokenType("NUMBER")
    val COMMENT: IElementType = JPPTokenType("COMMENT")
    val BAD: IElementType = JPPTokenType("BAD_CHARACTER")
    val TEXT: IElementType = JPPTokenType("TEXT")
}