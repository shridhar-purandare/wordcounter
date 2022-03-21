package lib;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * WordCounter contract implementation.
 */
@Service
public final class WordCounter implements IWordCounter {
    private final HashMap<String, Integer> wordCountMap;
    private final Translator translator;
    ReadWriteLock lock = new ReentrantReadWriteLock();

    public WordCounter(Translator translator) {
        this.wordCountMap = new HashMap<String, Integer>();
        this.translator = translator;
    }

    @Override
    public boolean AddWord(String word) {
        boolean addStatus = false;
        if(isStringOnlyAlphabet(word)) {
            this.lock.writeLock().lock();
            try {
                String englishWord = translator.translate(word).toLowerCase();

                if (wordCountMap.containsKey(englishWord)) {
                    Integer count = wordCountMap.get(englishWord);
                    wordCountMap.put(englishWord, ++count);
                } else {
                    wordCountMap.put(englishWord, 1);
                }
            } finally {
                this.lock.writeLock().unlock();
            }
            addStatus = true;
        }

        return addStatus;
    }


    @Override
    public int GetWordCount(String word) {
        int wordCount = 0;

        if(isStringOnlyAlphabet(word)) {
            String englishWord = translator.translate(word).toLowerCase();
            this.lock.readLock().lock();
            try {
                if (wordCountMap.containsKey(englishWord)) {
                    return wordCountMap.get(englishWord);
                }
            } finally {
                this.lock.readLock().unlock();
            }
        }

        return wordCount;
    }

    private boolean isStringOnlyAlphabet(String str)
    {
        return ((str != null) && (!str.equals(""))
                && (str.matches("^[a-zA-Z]*$")));
    }
}
