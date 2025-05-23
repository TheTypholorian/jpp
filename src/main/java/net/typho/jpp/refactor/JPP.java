package net.typho.jpp.refactor;

import java.util.*;

public class JPP {
    public static void main(String[] arr) {
        Map<String, String> args = new LinkedHashMap<>();
        List<String> include = new LinkedList<>();

        Iterator<String> it = Arrays.stream(arr).iterator();

        while (it.hasNext()) {
            String next = it.next();

            if (next.startsWith("--")) {
                args.put(next.substring(2), it.next());
            } else if (next.startsWith("-")) {
                args.put(next.substring(1), null);
            } else {
                include.add(next);
            }
        }

        String out = args.computeIfAbsent("out", k -> System.getProperty("user.dir"));

        switch (args.getOrDefault("mode", "compile")) {
            case "compile": {
                break;
            }
            case "decompile": {
                System.err.println("Decompilation not implemented yet");
                return;
            }
            case "assemble": {
                break;
            }
        }
    }
}
