package net.typho.jpp.assembly;

import java.io.IOException;

public class LEA64Insn implements Insn {
    public Register64 dst, base, index;
    public int scale, disp;

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

        if (needsSib()) {
            size += 1;
        }

        if (isRipRelative() || hasDisp()) {
            size += 4;
        }

        return size;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        int rex = 0x48;

        if ((dst.ordinal() & 8) != 0) {
            rex |= 0x04;
        }

        if (index != null && (index.ordinal() & 8) != 0) {
            rex |= 0x02;
        }

        if (base != null && (base.ordinal() & 8) != 0) {
            rex |= 0x01;
        }

        out.write(rex);
        out.write(0x8D);

        int mod, rm;

        if (isRipRelative()) {
            mod = 0; rm = 5;
        } else if (needsSib()) {
            if (disp == 0) {
                mod = 0;
            } else {
                mod = 2;
            }

            rm = 4;
        } else {
            if (disp == 0) {
                mod = 0;
            } else if (disp >= -128 && disp <= 127) {
                mod = 1;
            } else {
                mod = 2;
            }

            assert base != null;
            rm = base.ordinal() & 0x7;
        }

        out.write((mod << 6) | ((dst.ordinal() & 0x7) << 3) | rm);

        if (needsSib()) {
            int ss = switch (scale) {
                case 1 -> 0;
                case 2 -> 1;
                case 4 -> 2;
                case 8 -> 3;
                default -> throw new IllegalArgumentException("Invalid scale " + scale);
            };
            out.write((ss << 6) | ((index == null ? 4 : index.ordinal() & 0x7) << 3) | (base == null ? 5 : base.ordinal() & 0x7));
        }

        if (isRipRelative() || hasDisp()) {
            out.ints(disp);
        }
    }

    protected boolean isRipRelative() {
        return base == null;
    }

    protected boolean hasDisp() {
        return disp != 0;
    }

    protected boolean needsSib() {
        return !isRipRelative() && index != null;
    }
}
