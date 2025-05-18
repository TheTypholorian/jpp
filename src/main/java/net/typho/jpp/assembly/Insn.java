package net.typho.jpp.assembly;

import net.typho.jpp.tree.ClassNode;

import java.io.IOException;

public interface Insn {
    int bytes();

    void write(int before, ASMOutputStream out) throws IOException;

    default void post(byte[] b, int i, ClassNode node) {
    }
}
