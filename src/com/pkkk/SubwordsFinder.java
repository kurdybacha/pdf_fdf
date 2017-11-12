package com.pkkk;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubwordsFinder {

    public SubwordsFinder(PDDocument doc)
    {
        _doc = doc;
    }

    public class Pos {
        public Pos(float _x, float _y)
        {
            x = _x;
            y = _y;
        }
        public float x;
        public float y;
    }

    public class FoundTerm {
        public int page;
        public Pos pos;
        public float width;
        public String lastLetter;
        public Pos lastLetterPos;
    }

    public List<FoundTerm> find(String searchTerm) throws IOException
    {
        List<FoundTerm> foundTerms = new ArrayList<>();
        for (int page = 1; page <= _doc.getNumberOfPages(); page++) {
            List<TextPositionSequence> hits = find(page, searchTerm);
            for (TextPositionSequence hit : hits) {
                TextPosition lastPosition = hit.textPositionAt(hit.length() - 1);
                FoundTerm foundTerm = new FoundTerm();
                foundTerm.page = page;
                foundTerm.pos = new Pos(hit.getX(), hit.getY());
                foundTerm.width = hit.getWidth();
                foundTerm.lastLetter = lastPosition.getUnicode();
                foundTerm.lastLetterPos = new Pos(lastPosition.getXDirAdj(), lastPosition.getYDirAdj());
                foundTerms.add(foundTerm);
            }
        }
        return foundTerms;
    }

    private List<TextPositionSequence> find(int page, String searchTerm) throws IOException
    {
        final List<TextPositionSequence> hits = new ArrayList<>();
        PDFTextStripper stripper = new PDFTextStripper()
        {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException
            {
                TextPositionSequence word = new TextPositionSequence(textPositions);
                String string = word.toString();

                int fromIndex = 0;
                int index;
                while ((index = string.indexOf(searchTerm, fromIndex)) > -1) {
                    hits.add(word.subSequence(index, index + searchTerm.length()));
                    fromIndex = index + 1;
                }
                super.writeString(text, textPositions);
            }
        };

        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(_doc);
        return hits;
    }


    private PDDocument _doc;
}
