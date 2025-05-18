package net.typho.jpp.plugin

import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.project.Project

class JPPSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, file: VirtualFile?): JPPSyntaxHighlighter {
        return JPPSyntaxHighlighter()
    }
}