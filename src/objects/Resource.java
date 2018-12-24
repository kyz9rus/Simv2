package objects;

import java.io.File;

public class Resource {
    private File file;

    public Resource(String fileName){
        file = new File(fileName);
    }

    public File getFile() {
        return file;
    }
}
