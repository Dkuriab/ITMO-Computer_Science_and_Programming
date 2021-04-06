package md2html;

import java.util.*;
import java.io.*;
import java.lang.*;

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

    private static BufferedReader input;
    private static BufferedWriter output;



    public static StringBuilder chapter(StringBuilder chap, int position) throws IOException {
        StringBuilder chapter = new StringBuilder();
        if (position == 0) {
            output.write("<p>");
            chapter.append("</p>\n");
        }
        else {
            output.write("<h" + position + ">");
            chapter.append("</h").append(position).append(">\n");
        }
        return chapter;
    }



    public static StringBuilder mark(StringBuilder text, String mark, int position) {
        StringBuilder done = new StringBuilder();
        char symbol = mark.charAt(0);

        while (position < text.length()) { 

            if (text.charAt(position) == symbol && !Character.isWhitespace(text.charAt(position - 1))) {
                return done.insert(0, REPLACESTART.get(mark)).append(REPLACEEND.get(mark));
            }

            if (SIMBOLS.contains(text.charAt(position) + "") && !Character.isWhitespace(text.charAt(position + 1))) {
                if (text.charAt(position) == text.charAt(position + 1)) {
                    done.append(mark(text, text.substring(position, position + 1), position + 2));
                } else {
                    done.append(mark(text, text.charAt(position) + "", position));
                }
            } else if (Character.isWhitespace(text.charAt(position + 1))) {
                done.append(text.charAt(position + 1));
                position++;
            }

            if (text.charAt(position) == '<' || text.charAt(position) == '>' || text.charAt(position) == '&') {
                done.append(REPLACESPEC.get((text.charAt(position) + "")));
                position++;
                continue;
            }

            if (text.charAt(position) == '\\') {                        
                position++;
                done.append(text.charAt(position++));
                continue;
            }  

            done.append(text.charAt(position++));
        }
        return done;
    
    }
    
    public static void main(String[] args) {
        
        try{
            input = new BufferedReader(new InputStreamReader (new FileInputStream(new File(args[0])), "utf-8"));
            try {
                output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File (args[1])), "utf-8"));
                try {
                    String line = "";
                    while (line != null) {

                        int position = 0;
                        StringBuilder chap = new StringBuilder();
                        
                        line = input.readLine();
                        while (line.isEmpty()) {
                            line = input.readLine();
                        }

                        if (line.charAt(0) == '#') { 
                            while (position < line.length() && line.charAt(position) == '#') {
                                position++;
                            }
                            position = (line.charAt(position) == ' ' ? position : 0);    
                        }

                        while(line != null && line.length() != 0) {
                            chap.append(line);
                            line = input.readLine();
                        }

                        output.write(chapter(chap, position).toString()); 

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