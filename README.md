# pdf_fdf

Program takes words from input csv file, searches them in source pdf file 
and outputs found words with location coordinates into output csv.

## Getting Started

Clone the project, open PdfAnnotations.iml in IntelliJ, build and run.

### Prerequisites

Projects depends on openbox and opencsv but IntelliJ downloads this automagically.

### Usage

```java -jar PdfAnnotations.jar <source_pdf> <input_csv> <output_csv>```

Example:
```java -jar PdfAnnotations.jar sample.pdf input.csv output.csv```

input.csv format:
  `search_term,comments`
  
output.csv format:
  `search_term",comments,x_coordinate,y_coordinate,page_number`
  where `x_coordinate` is end of word coordinate plus 20

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

