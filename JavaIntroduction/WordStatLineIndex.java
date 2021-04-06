import java.io.*;
import java.util.*;

public class WordStatLineIndex {
    
    public static boolean isPartOfWord(char p) {
        return Character.isLetter(p) || Character.getType(p) == Character.DASH_PUNCTUATION || p== '\'';
    }
    
    public static void adder(String word, int cl, int cw, Map<String, List<int[]>> words) {
        List<int[]> emp = words.getOrDefault(word, new ArrayList<int[]>());
        emp.add(new int[]{cl, cw});
        words.put(word, emp);
    }

    
    public static void main(String[] args) {        
        if (args.length < 2) {
            System.out.println("not enough arguments");
            return;
        }
  
        
        NavigableMap<String, List<int[]>> words = new TreeMap<>();

        try {
            int cl = 0;
            Scanner sc = new Scanner(args[0]);
            try {
                while (sc.nextLine() != null) {
                    cl++;
                    int cw = 0;
                    while (sc.hasNextWord()) {
                        String word = sc.nextWord();
                        cw++;
                        adder(word, cl, cw, words);
                    }       
                }
            } finally { 
                sc.close();
            }                   
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException" + e.getMessage());
        } catch (NoSuchElementException  e) {
            System.out.println("NoSuchElementException " + e.getMessage());
        } 

        try{ 
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"));
            try{
                for (NavigableMap.Entry<String, List<int[]>> pam : words.entrySet()) { 
                    wr.write(pam.getKey() + " " + pam.getValue().size());
                    for (int[] pos : pam.getValue()) {
                        wr.write(" " + pos[0] + ":" + pos[1]);
                    }
                    wr.newLine();
                }   
            }finally { 
                wr.close();
            }
        }catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        } 
    }
}