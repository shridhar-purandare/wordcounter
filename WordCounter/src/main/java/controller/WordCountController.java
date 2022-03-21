package controller;

import lib.IWordCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/word")
public class WordCountController {

    @Autowired
    private IWordCounter wordCounter;

    @GetMapping(path="/{word}", produces = "application/json")
    public int getWordCount(@PathVariable(required = true) String word)
    {
        return wordCounter.GetWordCount(word);
    }

    @PostMapping(path="/{word}" , produces = "application/json")
    public boolean addWord(@PathVariable(required = true) String word)
    {
        return wordCounter.AddWord(word);
    }
}
