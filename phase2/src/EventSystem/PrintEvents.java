package EventSystem;

import java.io.*;
import java.awt.Desktop;

public class PrintEvents {
    private String filepath;

    public PrintEvents(){ this.filepath = System.getProperty("user.dir");}

    public void print(StringBuilder doc) {

        try {
            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Events List</title></head>");
            html.append("<body>" + doc + "<body></html>");
            WriteToFile(html.toString(),"Events.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteToFile(String fileContents, String fileName) throws IOException {
        String tempFile = filepath + File.separator + fileName;
        File file = new File(tempFile);

        if (file.exists()) {
            try {
                File newFileName = new File(filepath + File.separator + "backup_" + fileName);
                file.renameTo(newFileName);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(fileContents);
        writer.close();

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(file.toURI());
        }
    }
}