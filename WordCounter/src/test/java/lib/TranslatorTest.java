package lib;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TranslatorTest {
    private Translator translator;

    @Before
    public void setUp() {
        translator = new Translator();
    }

    @Test
    public void returnSameWordIfPassedEnglish() {
        String translatedWord = translator.translate("flower");
        Assert.assertEquals("flower", translatedWord);
    }

    @Test
    public void returnTranslationIfNonEnglishWordIsPassed() {
        String translatedWord = translator.translate("flor");
        Assert.assertEquals("flower", translatedWord);
    }
}
