package net.typho.jpp.testing;

public class Testing {
    public static native void main() {
        loadstat rsi "Hello World!\n"

        68 0D 00 00 00 // push 13
        56 // push rsi
        invoke print

        48 B8 80 00 00 00 00 00 00 10 // mov rax, 0x1000_0000_0000_0080
        50 // push rax
        50 // push rax (rax * 2)
        invoke add2

        50 // push rax
        invoke exit
    }

    public static native long add2(long a, long b) alloc 0 {
        48 8B 45 10 // mov rax, [rbp+16]
        48 8B 55 18 // mov rdx, [rbp+24]
        48 01 D0 // add rax, rdx
    }

    public static native void print(long ptr, int len) alloc 0 {
        48 8B 75 10 // mov rsi, [rbp+16]
        BF 01 00 00 00 // mov edi, 1
        48 8B 55 18 // mov edx, [rbp+24]
        B8 01 00 00 00 // mov eax, 1
        0F 05 // syscall
    }

    public static native void exit(int code) {
        48 8B 7D 10 // mov rdi, [rbp+16]
        B8 3C 00 00 00 // mov eax, 60 (exit)
        0F 05 // syscall
    }
}