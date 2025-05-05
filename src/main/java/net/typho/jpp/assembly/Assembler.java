package net.typho.jpp.assembly;

import net.typho.jpp.lexical.LexicalIterator;
import net.typho.jpp.parsing.DefaultParser;
import net.typho.jpp.parsing.Parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Assembler implements Parser {
    private final List<Insn> instructions = new LinkedList<>();
    private final List<byte[]> data = new LinkedList<>();
    private int off = 0;

    public final DefaultParser parent;

    public Assembler(DefaultParser parent) {
        this.parent = parent;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        List<String> path = new LinkedList<>();
        List<List<String>> args = new LinkedList<>();
        path.add(token);

        while (true) {
            String next = it.next();

            if (next.equals(";")) {
                break;
            }

            switch (next) {
                case ".": {
                    path.add(it.next());
                    break;
                }
                case "(": {
                    List<String> a = new LinkedList<>();

                    while (true) {
                        next = it.next();

                        if (next.equals(")")) {
                            break;
                        }

                        if (!next.equals(",")) {
                            a.add(next);
                        }
                    }

                    args.add(a);

                    break;
                }
            }
        }

        if (path.getFirst().equals("Console")) {
            if (path.get(1).startsWith("Print")) {
                String text = args.getFirst().getFirst();
                text = text.substring(1, text.length() - 1);

                if (path.get(1).equals("Println")) {
                    text += '\n';
                }

                parent.asm.add(new StaticPrintInsn(text));
            }
        }
    }

    public void add(Insn insn) {
        instructions.add(insn);
    }

    public int data(byte[] data) {
        int r = off;

        off += data.length;
        this.data.add(data);

        return r;
    }

    public int codeBytes() {
        int b = 0;

        for (Insn insn : instructions) {
            b += insn.bytes();
        }

        return b;
    }

    public int dataBytes() {
        return off;
    }

    public int totalBytes() {
        return codeBytes() + dataBytes();
    }

    public void write(ASMOutputStream out) throws IOException {
        for (byte[] d : data) {
            out.write(d);
        }

        int before = 0;

        for (Insn insn : instructions) {
            insn.write(before, out);
            before += insn.bytes();
        }
    }

    public byte[] write() throws IOException {
        byte[] bytes = new byte[totalBytes()];

        write(new ASMOutputStream() {
            int i = 0;

            @Override
            public void write(int b) {
                bytes[i++] = (byte) (b & 0xFF);
            }
        });

        return bytes;
    }
}
