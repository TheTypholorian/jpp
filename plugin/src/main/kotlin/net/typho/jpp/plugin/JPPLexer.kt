package net.typho.jpp.plugin

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.Nullable

class JPPLexer : LexerBase() {
    private var buf: CharSequence = ""
    private var bufEnd: Int = 0
    private var tokStart: Int = 0
    private var tokEnd: Int = 0
    private var tokType: IElementType? = null

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        buf = buffer
        bufEnd = endOffset
        tokStart = startOffset
        tokEnd = endOffset
        tokType = JPPTokenTypes.TEXT
    }

    override fun getState(): Int {
        return 0
    }

    @Nullable
    override fun getTokenType(): IElementType? {
        return tokType
    }

    override fun getTokenStart(): Int {
        return tokStart
    }

    override fun getTokenEnd(): Int {
        return tokEnd
    }

    override fun advance() {
        tokType = null
    }

    override fun getBufferSequence(): CharSequence {
        return buf
    }

    override fun getBufferEnd(): Int {
        return bufEnd
    }
}