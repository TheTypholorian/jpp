package net.typho.jpp.testing;

public class Testing {
    public static void main() {
        print("Hello World!\n", 13);
        exit(69); //add2(0x80, 0x80)
    }

    public static native long add2(long a, long b) alloc 0 {
        48 8B 45 10 // mov rax, [rbp+16]
        48 8B 55 18 // mov rdx, [rbp+24]
        48 01 D0 // add rax, rdx
    }

    public static native void print(byte* ptr, int len) alloc 0 {
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