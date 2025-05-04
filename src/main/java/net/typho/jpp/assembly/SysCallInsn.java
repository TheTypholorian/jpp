package net.typho.jpp.assembly;

import java.io.IOException;

public class SysCallInsn implements Insn {
    public static final int READ = 0;
    public static final int WRITE = 1;
    public static final int OPEN = 2;
    public static final int CLOSE = 3;
    public static final int STAT = 4;
    public static final int FSTAT = 5;
    public static final int LSTAT = 6;
    public static final int POLL = 7;
    public static final int LSEEK = 8;
    public static final int MMAP = 9;
    public static final int MPROTECT = 10;
    public static final int MUNMAP = 11;
    public static final int BRK = 12;
    public static final int RT_SIGACTION = 13;
    public static final int RT_SIGPROCMASK = 14;
    public static final int RT_SIGRETURN = 15;
    public static final int IOCTL = 16;
    public static final int PREAD64 = 17;
    public static final int PWRITE64 = 18;
    public static final int READV = 19;
    public static final int WRITEV = 20;
    public static final int ACCESS = 21;
    public static final int PIPE = 22;
    public static final int SELECT = 23;
    public static final int SCHED_YIELD = 24;
    public static final int MREMAP = 25;
    public static final int MSYNCH = 26;
    public static final int MINCORE = 27;
    public static final int MADVISE = 28;
    public static final int SHMGET = 29;
    public static final int SHMAT = 30;
    public static final int SHMCTL = 31;
    public static final int DUP = 32;
    public static final int DUP2 = 33;
    public static final int PAUSE = 34;
    public static final int NANOSLEEP = 35;
    public static final int GETITIMER = 36;
    public static final int SETTIMEOFDAY = 37;
    public static final int TIMES = 43;
    public static final int SOCKET = 41;
    public static final int CONNECT = 42;
    public static final int ACCEPT = 43;
    public static final int SENDTO = 44;
    public static final int RECVFROM = 45;
    public static final int SENDMSG = 46;
    public static final int RECVMSG = 47;
    public static final int SHUTDOWN = 48;
    public static final int BIND = 49;
    public static final int LISTEN = 50;
    public static final int GETSOCKNAME = 51;
    public static final int GETPEERNAME = 52;
    public static final int SOCKETPAIR = 53;
    public static final int SETSOCKOPT = 54;
    public static final int GETSOCKOPT = 55;
    public static final int CLONE = 56;
    public static final int FORK = 57;
    public static final int VFORK = 58;
    public static final int EXECVE = 59;
    public static final int EXIT = 60;
    public static final int WAIT4 = 61;
    public static final int KILL = 62;
    public static final int UNAME = 63;
    public static final int SEMGET = 64;
    public static final int SEMOP = 65;
    public static final int SEMCTL = 66;

    @Override
    public int bytes() {
        return 2;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        out.write(0x0F);
        out.write(0x05);
    }
}
