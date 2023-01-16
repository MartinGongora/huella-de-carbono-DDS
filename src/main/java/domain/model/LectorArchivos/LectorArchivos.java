package domain.model.LectorArchivos;

import java.io.*;

public class LectorArchivos {

    private File file;
    private BufferedReader bufferedReader;
    private String path;

    public LectorArchivos(String path) throws FileNotFoundException {
        this.path = path;
        FileReader fileReader = abrirArchivo(this.path);
        this.bufferedReader = new BufferedReader(fileReader);
    }

    public Boolean pathArchivoValido() {
        File test = new File(path);
        return test.exists();
    }

    private FileReader abrirArchivo(String path) throws FileNotFoundException {
        this.file = new File(path).getAbsoluteFile();
        return new FileReader(file);
    }

    public Boolean existeEnArchivo(String cadena) throws IOException {
        String linea;
        while((linea = bufferedReader.readLine()) != null) {
            if(linea.equals(cadena))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
