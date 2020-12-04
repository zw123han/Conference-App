package MessagingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ReadProfanities {
    private String filepath;

    public ReadProfanities(String filepath){
        this.filepath = filepath;
    }

    public HashMap<String, String> getProfanityList(){
        HashMap<String, String> profanities = new HashMap<>();
        try{
            File file = new File(filepath);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                String profanity = line.substring(0, line.indexOf(" "));
                String replacement = line.substring(line.indexOf(" ")+1, line.length()-1);
                profanities.put(parseProfanity(profanity), replacement);
            }
        }catch (FileNotFoundException e){
            System.out.println("File Not Found.");
        }
        return profanities;
    }

    //private String repeatStar(int times) {
    //    StringBuilder result = new StringBuilder();
    //    for (int i = 0; i < times; i++) {
    //        result.append("*");
    //    }
    //    return result.toString();
    //}

    // NOTE: keys of hashmap should be Regex! For example:
    // "fuck" = "(?i)f[\\s]*u[\\s]*c[\\s]*k" to ignore case and whitespace
    // Use parseProfanity to do the conversion automatically when initializing profanities.
    private String parseProfanity(String profanity) {
        StringBuilder result = new StringBuilder("(?i)");
        String[] bad = profanity.split("");
        for (String letter : bad) {
            result.append(letter)
                    .append("[\\s]*");
        }
        return result.substring(0, result.length()-3);
    }
}
