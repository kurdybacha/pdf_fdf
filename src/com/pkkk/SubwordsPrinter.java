package com.pkkk;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;

public class SubwordsPrinter {

    public SubwordsPrinter(PDDocument doc)
    {
        _doc = doc;
    }


    void print(String searchTerm) throws IOException
    {
        System.out.printf("* Looking for '%s'\n", searchTerm);
        List<SubwordsFinder.FoundTerm> foundTerms = new SubwordsFinder(_doc).find(searchTerm);
        for (SubwordsFinder.FoundTerm foundTerm : foundTerms) {
                System.out.printf("  Page %s at %s, %s with width %s and last letter '%s' at %s, %s\n",
                        foundTerm.page, foundTerm.pos.x, foundTerm.pos.y, foundTerm.width,
                        foundTerm.lastLetter, foundTerm.lastLetterPos.x, foundTerm.lastLetterPos.y);
        }
    }

    private PDDocument _doc;
}
