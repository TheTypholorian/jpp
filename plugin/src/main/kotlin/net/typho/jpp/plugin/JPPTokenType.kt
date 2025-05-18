package net.typho.jpp.plugin

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class JPPTokenType(@NonNls debugName: String) : IElementType(debugName, JPPLanguage)