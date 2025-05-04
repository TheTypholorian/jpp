package net.typho.jpp.assembly;

import java.io.IOException;

public class LEA64Insn implements Insn {
    public final Register64 dst, base, index;
    public final int scale, disp;

    public LEA64Insn(Register64 dst, Register64 base, Register64 index, int scale, int disp) {
        this.dst = dst;
        this.base = base;
        this.index = index;
        this.scale = scale;
        this.disp = disp;
    }

    @Override
    public int bytes() {
        int size = 3;
        if (needsSib()) size += 1;
        if (isRipRelative() || hasDisp()) size += 4;
        return size;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
// 1) REX prefix: 0100W R X B
        int rex = 0x40 | 0x08; // W=1
        int dstCode = dst.ordinal();
        if ((dstCode & 8) != 0) rex |= 0x04; // R
        if (index != null && (index.ordinal() & 8) != 0) rex |= 0x02; // X
        if (base  != null && (base.ordinal()  & 8) != 0) rex |= 0x01; // B
        out.write(rex);
// 2) opcode
        out.write(0x8D);
// 3) ModR/M
        int mod, rm;
        if (isRipRelative()) {
            mod = 0; rm = 5;
        } else if (needsSib()) {
            if (disp == 0) mod = 0;
            else mod = 2; // use disp32 for simplicity
            rm = 4;
        } else {
// simple [base + disp]
            if (disp == 0) mod = 0;
            else if (disp >= -128 && disp <= 127) mod = 1;
            else mod = 2;
            rm = base.ordinal() & 0x7;
        }
        int reg = dstCode & 0x7;
        out.write((mod << 6) | (reg << 3) | rm);
// 4) SIB if needed
        if (needsSib()) {
            int ss;
            switch (scale) {
                case 1: ss = 0; break;
                case 2: ss = 1; break;
                case 4: ss = 2; break;
                case 8: ss = 3; break;
                default: throw new IllegalArgumentException("Invalid scale: " + scale);
            }
            int idx = index == null ? 4 : index.ordinal() & 0x7;
            int bas = base  == null ? 5 : base.ordinal()  & 0x7;
            out.write((ss << 6) | (idx << 3) | bas);
        }
// 5) Displacement or RIP-relative disp32
        if (isRipRelative() || hasDisp()) {
            out.ints(disp);
        }
    }

    private boolean isRipRelative() {
        return base == null;
    }
    private boolean hasDisp() {
        return disp != 0;
    }
    private boolean needsSib() {
        return !isRipRelative() && index != null;
    }
}
