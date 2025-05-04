package net.typho.jpp.assembly;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ASMOutputStream extends OutputStream {
    public void bytes(byte... b) throws IOException {
        for (byte v : b) {
            write(v);
        }
    }

    public void shorts(short... s) throws IOException {
        for (short v : s) {
            write(v);
            write(v >> 8);
        }
    }

    public void ints(int... i) throws IOException {
        for (int v : i) {
            write(v);
            write(v >> 8);
            write(v >> 16);
            write(v >> 24);
        }
    }

    public void longs(long... l) throws IOException {
        for (long v : l) {
            write((byte) v);
            write((byte) (v >> 8));
            write((byte) (v >> 16));
            write((byte) (v >> 24));
            write((byte) (v >> 32));
            write((byte) (v >> 40));
            write((byte) (v >> 48));
            write((byte) (v >> 56));
        }
    }
}
