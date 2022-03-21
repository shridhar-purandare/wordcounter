package lib;

import org.springframework.stereotype.Service;

@Service
public class Translator {

    // Assuming this is third party lib to make code compile.
    public String translate(String word) {
        if(word.equals("flor")) return "flower";
        if(word.equals("blume")) return "flower";
        return word;
    }
}
