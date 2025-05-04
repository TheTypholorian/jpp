package net.typho.jpp.assembly;

import java.io.IOException;

public interface Insn {
    int bytes();

    void write(int before, ASMOutputStream out) throws IOException;
}
