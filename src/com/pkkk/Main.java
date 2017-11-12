package com.pkkk;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 3)
            showUsageAndExit();

        PDDocument pdf = PDDocument.load(new File(args[0]));
        CSVReader  csvReader = new CSVReader(new FileReader(args[1]));
        CSVWriter csvWriter = new CSVWriter(new FileWriter(args[2]));
        String[] line;
        int lineNo = 1;
        while((line = csvReader.readNext()) != null) {

            if (line.length != 2)
                throw new IOException("Invalid number of columns in file " + args[1] + ", line " + lineNo);

            lineNo++;
            String searchTerm = line[0];
            String comment = line[1];
            List<SubwordsFinder.FoundTerm> foundTerms = new SubwordsFinder(pdf).find(searchTerm);
            for (SubwordsFinder.FoundTerm foundTerm : foundTerms) {

                csvWriter.writeNext(new String[] {
                        searchTerm,
                        comment,
                        String.valueOf(foundTerm.pos.x + foundTerm.width + 20),
                        String.valueOf(foundTerm.pos.y),
                        String.valueOf(foundTerm.page)
                });

            }
        }

        csvWriter.close();
        csvReader.close();
        pdf.close();

    }

    private static void showUsageAndExit()
    {
        String message = "Usage: java -jar PdfAnnotations.jar <source_pdf> <source_csv> <output_csv>\n"
                + "\nExample:\n"
                + "  java -jar PdfAnnotations.jar sample.pdf sample.csv output.csv";

        System.err.println(message);
        System.exit(1);
    }
}
