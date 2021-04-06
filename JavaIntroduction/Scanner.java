import java.io.*;
import java.util.*;

public class Scanner {
    private String line;
    private BufferedReader re;
    private int point = 0;
    
// It throws IOException, NoSuchElementException 
    
    public Scanner(InputStream in) throws IOException {
        try {
            re = new BufferedReader(new InputStreamReader(in, "utf8"));
        } catch (IOException e) {
            throw new IOException ("Can't open stream: " + e.getMessage());
        }       
    }
    
    public Scanner(String arg) throws IOException {
        try {
            re = new BufferedReader(new InputStreamReader(new FileInputStream(arg), "utf8"));
        } catch (IOException e) {
            new IOException ("Can't research file: " + e.getMessage());
        }       
    }
    
    public String nextLine() throws IOException {
        try {
            line = re.readLine();
            if (line != null) {
                line += " ";
                point = 0;    
            }
            return line;
        } catch (IOException e) {
            throw new IOException ("At nextLine " + e.getMessage());
        }
    }
    
    public String getLine() {
        return line;
    }  

    public boolean hasNextInt() {
        if (line == null || line.isEmpty()) {
            return false;
        }

        nextPoint();
        return point != line.length();   
    }    

    public int nextInt() throws NoSuchElementException {
        if (!hasNextInt()) {
            throw new NoSuchElementException("Can't research next int ");
        }
        nextPoint();
        int start = point;
        while (point < line.length() && !Character.isWhitespace(line.charAt(point))) {
            point++;
        }
        return Integer.parseInt(line.substring(start, point));
    }
    
    public boolean hasNextWord() {
        if (line == null) {
            return false;
        }
        nextPointW();
        if (point == line.length()) {
            return false;
        } 
        return true;
    }
    
    public String nextWord() throws NoSuchElementException {
        if (!hasNextWord()) {
            throw new NoSuchElementException ("Can't research next word ");
        }
        nextPointW();
        int start = point;
        while ((point < line.length()) && isPartOfWord(line.charAt(point))) {
            point++;
        }
        String word = line.substring(start, point).toLowerCase();  
        return word;
    }

    public void close() throws IOException {
        re.close();
    }

    public void nextPoint() {
        while (point < line.length() && Character.isWhitespace(line.charAt(point))) {
            point++;          
        }
    }

    public void nextPointW() {
        while (point < line.length() && !isPartOfWord(line.charAt(point))) {
            point++;
        }
    }

    private static boolean isPartOfWord(char p) {
        return Character.isLetter(p) || Character.getType(p) == Character.DASH_PUNCTUATION || p == '\'';
    }    
}   
