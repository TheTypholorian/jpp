package net.typho.jpp;

import java.util.Iterator;

public final class Literal {
    public static String parseString(String s) {
        if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
            s = s.substring(1, s.length() - 1);
        } else {
            return null;
        }

        if (!s.contains("\\")) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = s.chars().iterator();

        while (it.hasNext()) {
            int c = it.next();

            if (c == '\\') {
                if (!it.hasNext()) {
                    return sb.toString();
                }

                switch ((int) it.next()) {
                    case 'b': {
                        sb.append('\b');
                        break;
                    }
                    case 't': {
                        sb.append('\t');
                        break;
                    }
                    case 'n': {
                        sb.append('\n');
                        break;
                    }
                    case 'f': {
                        sb.append('\f');
                        break;
                    }
                    case 'r': {
                        sb.append('\r');
                        break;
                    }
                    case '"': {
                        sb.append('"');
                        break;
                    }
                    case '\'': {
                        sb.append('\'');
                        break;
                    }
                    case '\\': {
                        sb.append('\\');
                        break;
                    }
                    case 'u': {
                        StringBuilder hex = new StringBuilder();

                        h:
                        while (it.hasNext() && hex.length() != 4) {
                            char next = (char) (int) it.next();

                            switch (Character.toLowerCase(next)) {
                                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f': {
                                    hex.append(next);
                                    break;
                                }
                                default: {
                                    break h;
                                }
                            }
                        }

                        if (!hex.isEmpty()) {
                            sb.append((char) Integer.parseInt(hex.toString(), 16));
                        }
                        break;
                    }
                    default: {
                        StringBuilder octal = new StringBuilder();

                        o:
                        while (it.hasNext() && octal.length() != 3) {
                            char next = (char) (int) it.next();

                            switch (next) {
                                case '0', '1', '2', '3', '4', '5', '6', '7': {
                                    octal.append(next);
                                    break;
                                }
                                default: {
                                    break o;
                                }
                            }
                        }

                        if (!octal.isEmpty()) {
                            sb.append((char) Integer.parseInt(octal.toString(), 8));
                        }
                        break;
                    }
                }
            } else {
                sb.append((char) c);
            }
        }

        return sb.toString();
    }

    public static long parseInt(String s) {
        if (s == null || s.isEmpty()) {
            throw new NumberFormatException("Bad input");
        }

        s = s.replaceAll("_", "");

        char last = s.charAt(s.length() - 1);

        if (last == 'l' || last == 'L') {
            s = s.substring(0, s.length() - 1);
        }

        char c = s.charAt(0);

        if (c == '0') {
            if (s.length() == 1) {
                return 0;
            }

            return switch (Character.toLowerCase(s.charAt(1))) {
                case 'x' -> Long.parseLong(s.substring(2), 16);
                case 'b' -> Long.parseLong(s.substring(2), 2);
                default -> Long.parseLong(s.substring(1));
            };
        }

        return Long.parseLong(s);
    }

    public static double parseDecimal(String s) {
        return Double.parseDouble(s);
    }
}
