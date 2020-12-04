package MessagingSystem;

import java.util.HashMap;

public class ProfanityCreatorScript {
    String filepath;
    private ReadProfanities rp = new ReadProfanities("phase2/src/MessagingSystem/profanityList.csv");
    private StoreProfanitiesList sp;

    public ProfanityCreatorScript(String filepath) {
        this.filepath = filepath;
        this.sp = new StoreProfanitiesList(filepath);
    }

    public void runScript() {
        HashMap<String, String> pl = rp.getProfanityList();
        sp.storeProfanities(pl);
    }
}
