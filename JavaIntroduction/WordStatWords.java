import java.io.*;
import java.util.NoSuchElementException;

public class WordStatWords {
    
    public static void main(String[] args) {
        String[] words = new String[1000_000];
        int cw = 0;
        int[] counter = new int[1000_000];
        
        if (args.length < 2) {
            System.out.println("Not enough arguments");
            return;
        }
        String line;
        try {
            Scanner sc = new Scanner(args[0]);
                try {
                    while (sc.nextLine() != null) {
                        while (sc.hasNextWord()) {
                            String word = sc.nextWord();
                            boolean found = false; 
                            for (int k = 0; k < cw; k++) {
                                if (words[k].equals(word)) {
                                    counter[k]++;
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                counter[cw]++;
                                words[cw++] = word;
                            }
                        }   
                    }
                } finally { 
                    sc.close();
                }                   
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        } catch (NoSuchElementException  e) {
            System.out.println("NoSuchElementException " + e.getMessage());
        } 
        
        for (int i = 0; i < cw-1; i++) {      
            for (int j = 0; j < cw-i-1; j++) {  
                if (words[j].compareTo(words[j+1]) > 0) {  
                    line = words[j];
                    words[j] = words[j+1];
                    words[j + 1] = line;
                    int t = counter[j];
                    counter[j] = counter[j + 1];
                    counter[j + 1] = t;
                }               
            }
        }
        
        try {
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"));
            try {
                for (int r = 0; r < cw; r++) {
                    wr.write(words[r] + " " + counter[r]);
                    wr.newLine();
                }   
            } finally { 
                wr.close();
            }
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        } 
    }
}