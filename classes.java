package net.typho.jpp.testing;

public class Testing {
    public static native void main() {
        loadstat rsi "This is a fairly long string that I am writing to attempt to overflow the single byte jump instruction and test if strings longer than 256 bytes work fine with loadstat instruction for native methods\n"

        68 c8 00 00 00 // push 200
        56 // push rsi
        invoke print

        68 80 00 00 00 // push 128
        68 80 00 00 00 // push 128

        invoke add2

        89 C7 // mov edi, rax

        //bf 0f 00 00 00 // mov edi, 69
        b8 3c 00 00 00 // mov eax, 60 (exit)
        0f 05 // syscall
    }

    public static native void add2(int a, int b) alloc 16 {
        48 8b 45 10 // mov rax, [rbp+16]
        48 8b 55 18 // mov rdx, [rbp+24]
        48 01 d0 // add rax, rdx
    }

    public static native void print(long ptr, int len) alloc 12 {
        48 8b 75 10 // mov rsi, [rbp+16]
        bf 01 00 00 00 // mov edi, 1
        48 8b 55 18 // mov edx, [rbp+24]
        b8 01 00 00 00 // mov eax, 1
        0f 05 // syscall
    }
}