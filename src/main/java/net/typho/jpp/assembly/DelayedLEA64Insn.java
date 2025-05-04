package net.typho.jpp.assembly;

import java.io.IOException;

public class DelayedLEA64Insn extends LEA64Insn {
    public Assembler asm;
    public int memAddress;

    public DelayedLEA64Insn(Register64 dst, Register64 base, Register64 index, int scale, int disp, Assembler asm, int memAddress) {
        super(dst, base, index, scale, disp);
        this.asm = asm;
        this.memAddress = memAddress;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        disp += asm.codeBytes() - (before + bytes()) + memAddress;
        super.write(before, out);
    }

    @Override
    protected boolean hasDisp() {
        return true;
    }
}
