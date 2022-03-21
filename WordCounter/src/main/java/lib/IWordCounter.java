package lib;

/**
 * Word Counter Library Contract
 */
public interface IWordCounter {
    /**
     *
     * @param word - word to add to the counter
     * @return true or false based on whether this word can be added counter or not.
     * If word is non alphabetic then it can not be added. Word is case insensitive.
     */
    boolean AddWord(String word);

    /**
     *
     * @param word - word to get count of
     * @return will return count of times word is added or 0 in cases where it's not added or not a valid word
     */
    int GetWordCount(String word);
}
