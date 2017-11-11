package com.pkkk;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.io.File;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 2)
            throw new IOException("Invalid number of arguments");

        PDDocument doc = PDDocument.load(new File(args[0]));

        SubwordsPrinter printer = new SubwordsPrinter(doc);
        printer.print(args[1]);

        doc.close();

    }
}
