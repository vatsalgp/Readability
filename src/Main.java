package src;

import java.io.File;

class Main {
    public static void main(final String[] args) {
        if (args.length == 0)
            System.out.println("ERROR: No File Path added");
        else
            for (String arg : args)
                readFile(new File(arg));
    }

    private static void readFile(File file) {
        if (file.isFile())
            new Score(file);
        else if (file.isDirectory())
            for (File child : file.listFiles())
                readFile(child);
        else
            System.out.println("Cannot open " + file.getName());
    }
}