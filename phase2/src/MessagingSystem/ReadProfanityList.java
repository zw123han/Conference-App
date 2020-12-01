package MessagingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ReadProfanityList {
    private String filepath;

    public ReadProfanityList(String filepath){
        this.filepath = filepath;
    }

    public HashMap<String, String> getProfanityList(){
        HashMap<String, String> profanities = new HashMap<>();
        try{
            File file = new File(filepath);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                String word = reader.nextLine();
                word = word.substring(0, word.length() - 1);
                profanities.put(parseProfanity(word), word);
            }
        }catch (FileNotFoundException e){
            System.out.println("File Not Found.");
        }
        return profanities;
    }

    // NOTE: keys of hashmap should be Regex! For example:
    // "fuck" = "(?i)f *u *c *k" to ignore case and whitespace
    // Use parseProfanity to do the conversion automatically when initializing profanities.
    private String parseProfanity(String profanity) {
        StringBuilder result = new StringBuilder("(?i)");
        String[] bad = profanity.split("");
        for (String letter : bad) {
            result.append(letter)
                    .append(" *");
        }
        return result.substring(0, result.length()-2);
    }
}
