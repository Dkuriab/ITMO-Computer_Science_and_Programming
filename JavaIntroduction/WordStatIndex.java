import java.io.*;
import java.util.*;

public class WordStatIndex {
    
    public static boolean isPartOfWord(char p) {
        if (Character.isLetter(p) || Character.getType(p) == Character.DASH_PUNCTUATION || p== '\'') {
            return true;    
        }
        return false;
    }
    
    public static void adder(String word, int cw, Map<String, List<Integer>> words) {
        List<Integer> emp = words.getOrDefault(word, new ArrayList<Integer>());
        emp.add(cw);
        words.put(word, emp);
    }
    
    public static void main(String[] args) {
        
        Map<String, List<Integer>> words = new LinkedHashMap<>();
        int cw = 0;
        
        if (args.length < 2) {
            System.out.println("not enough arguments");
            return;
        }
        try {
            BufferedReader re = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8"));
            String line;
            try{
                while ((line = re.readLine()) != null) {
                    int i = 0;
                    while (i < line.length()) {
                        if (isPartOfWord(line.charAt(i))) {
                            int start = i;
                            while ((i < line.length()) && isPartOfWord(line.charAt(i))) {
                                i++;
                            }
                            cw++;
                            String word = line.substring(start, i).toLowerCase();
                            adder(word, cw, words);
                        }       
                        i++;
                    }
                }
            } finally { 
                re.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        }
        

        try{ 
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"));
            try{
                for (Map.Entry<String, List<Integer>> pam : words.entrySet()) { 
                    wr.write(pam.getKey());
                    wr.write(" " + pam.getValue().size());
                    for (int value : pam.getValue()) {
                        wr.write(" " + value);
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