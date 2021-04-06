package md2html;

import java.util.*;
import java.io.*;

public class Md2Html {

    private static final Map<String, String> REPLACESTART = Map.of(
        "**", "<strong>", 
        "--", "<s>", 
        "_", "<em>", 
        "`", "<code>", 
        "*", "<em>", 
        "__", "<strong>", 
        "++", "<u>"
    );

    private static final Map<String, String> REPLACEEND = Map.of(
        "**", "</strong>",
        "--", "</s>",
        "_", "</em>",
        "`", "</code>",
        "*", "</em>",
        "__", "</strong>",
        "++", "</u>"
    );
    private static final Map<String, String> REPLACESPEC = Map.of(
        "<", "&lt;",
        ">", "&gt;", 
        "&", "&amp;"
    );
    private static final Set<String> SIMBOLS = Set.of("*", "-", "_", "`", "+");
    private static final Set<String> DOUBLESIMBOLS = Set.of("-", "+");

    private static BufferedReader input;
    private static BufferedWriter output;
    public static int position = 0;

    public static StringBuilder chapter(StringBuilder chapter) {
        String tag = position == 0 ? "p" : "h" + position;
        return mark(chapter, null).insert(0, "<" + tag + ">").append("</").append(tag).append(">");
    }

    public static StringBuilder mark(StringBuilder text, String mark) {
        StringBuilder done = new StringBuilder();

        while (position < text.length()) {
            if (mark != null && text.charAt(position) == mark.charAt(0) && !Character.isWhitespace(text.charAt(position - 1))) {
                return done.insert(0, REPLACESTART.get(mark)).append(REPLACEEND.get(mark));
            } else if (REPLACESPEC.containsKey(text.charAt(position) + "")) {
                done.append(REPLACESPEC.get((text.charAt(position) + "")));
                position++;
            } else if (text.charAt(position) == '\\') {                        
                position++;
                done.append(text.charAt(position++));
            } else if (SIMBOLS.contains(text.charAt(position) + "") && !Character.isWhitespace(text.charAt(position + 1))) {
                if (text.charAt(position) == text.charAt(position + 1)) {
                    position += 2;
                    done.append(mark(text, text.substring(position - 2, position)));
                    position += 2;
                } else if (!DOUBLESIMBOLS.contains(text.charAt(position) + "")){
                    done.append(mark(text, text.charAt(position++) + ""));
                    position++;  
                } else {
                    done.append(text.charAt(position++));
                }
            } else {
                done.append(text.charAt(position++));
            }
        }
        return done;
    }
    
    public static void main(String[] args) {
        try {
            input = new BufferedReader(new InputStreamReader (new FileInputStream(new File(args[0])), "utf-8"));
            try {
                output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File (args[1])), "utf-8"));
                try {
                    
                    String line = input.readLine();
                    while (line != null) {
                        StringBuilder chapter = new StringBuilder();
                        position = 0;
                        while (line.isEmpty()) {
                            line = input.readLine();
                        }

                        if (line.charAt(0) == '#') { 
                            while (position < line.length() && line.charAt(position) == '#') {
                                position++;
                            }
                            position = line.charAt(position) == ' ' ? position : 0;    
                        }

                        while (line != null && !line.isEmpty()) {
                            chapter.append(line).append("\n");
                            line = input.readLine();
                        }
                        chapter.setLength(chapter.length() - 1);
                        output.write(chapter(chapter).toString()); 
                    }
                } finally {
                    output.close();
                } 
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}