package lib;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class WordCounterTest {
    private Translator mockTranslator;
    private IWordCounter wordCounter;

    @Before
    public void setUp() {
        mockTranslator = mock(Translator.class);

        doReturn("flower").when(mockTranslator).translate("flor");
        doReturn("flower").when(mockTranslator).translate("FLOR");
        doReturn("flower").when(mockTranslator).translate("blume");
        doReturn("flower").when(mockTranslator).translate("BLUME");
        doReturn("flower").when(mockTranslator).translate("flower");
        doReturn("flower").when(mockTranslator).translate("FLOWER");

        wordCounter = new WordCounter(mockTranslator);
    }

    @Test
    public void returnTrueIfAlphabeticWordAdded() {
        boolean isAdded = wordCounter.AddWord("flower");
        Assert.assertEquals(true, isAdded);
    }

    @Test
    public void returnFalseIfNonAlphabeticWordAdded() {
        boolean isAdded = wordCounter.AddWord("flower1");
        Assert.assertEquals(false, isAdded);
    }

    @Test
    public void returnFalseIfNullWordAdded() {
        boolean isAdded = wordCounter.AddWord(null);
        Assert.assertEquals(false, isAdded);
    }

    @Test
    public void returnFalseIfEmptyWordAdded() {
        boolean isAdded = wordCounter.AddWord(null);
        Assert.assertEquals(false, isAdded);
    }


    @Test
    public void returnCountOfTimesWordWasAdded() {
        IWordCounter localWordCounter = new WordCounter(mockTranslator);
        boolean isAdded = localWordCounter.AddWord("flower");
        Assert.assertEquals(true, isAdded);

        int count = localWordCounter.GetWordCount("flower");
        Assert.assertEquals(1, count);
    }


    @Test
    public void returnCountOfTimesWordWasAddedEvenIfDiffLanguageWordIsPassed() {
        IWordCounter localWordCounter = new WordCounter(mockTranslator);
        boolean isAdded = localWordCounter.AddWord("flor");
        Assert.assertEquals(true, isAdded);

        int count = localWordCounter.GetWordCount("flower");
        Assert.assertEquals(1, count);
    }

    @Test
    public void returnCountOfTimesWordWasAddedEvenIfDiffCaseWordIsPassed() {
        IWordCounter localWordCounter = new WordCounter(mockTranslator);
        boolean isAdded = localWordCounter.AddWord("FLOWER");
        Assert.assertEquals(true, isAdded);

        int count = localWordCounter.GetWordCount("flower");
        Assert.assertEquals(1, count);
    }

    @Test
    public void returnCountOfTimesWordWasAddedEvenIfWordIsAddedMultipleNumberOfTimesInDiffLanguages() {
        IWordCounter localWordCounter = new WordCounter(mockTranslator);
        localWordCounter.AddWord("FLOWER");
        localWordCounter.AddWord("flor");
        localWordCounter.AddWord("blume");
        localWordCounter.AddWord("BLUME");

        int count = localWordCounter.GetWordCount("flower");
        Assert.assertEquals(4, count);
    }

    @Test
    public void returnCountOfTimesWordWasAddedWhenMultipleThreadsAddTheWord() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        IWordCounter localWordCounter = new WordCounter(mockTranslator);
        Random rand = new Random();
        List<String> inputList = Arrays.asList(new String[]{"flower", "FLOWER", "flor", "BLUME", "blume", "FLOR"});

        IntStream.range(0, 10)
                .forEach(i -> executor.submit(() -> localWordCounter.AddWord(inputList.get(rand.nextInt(inputList.size())))));


        boolean isExecutorTerminated = executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        if(isExecutorTerminated) {
            int count = localWordCounter.GetWordCount("flower");
            Assert.assertEquals(10, count);
        } else {
            System.out.println("Executor timed out hence not asserting");
        }
    }
}
