package com.pkkk;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.TextPosition;

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
        for (int page = 1; page <= _doc.getNumberOfPages(); page++)
        {
            List<TextPositionSequence> hits = new SubwordsFinder(_doc).find(page, searchTerm);
            for (TextPositionSequence hit : hits)
            {
                TextPosition lastPosition = hit.textPositionAt(hit.length() - 1);
                System.out.printf("  Page %s at %s, %s with width %s and last letter '%s' at %s, %s\n",
                        page, hit.getX(), hit.getY(), hit.getWidth(),
                        lastPosition.getUnicode(), lastPosition.getXDirAdj(), lastPosition.getYDirAdj());
            }
        }
    }

    private PDDocument _doc;
}
