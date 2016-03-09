/**
 * @(#)PDFWithText.java
 *
 *
 * @author Stephen H
 * @version 1.00 2011/6/21
 */

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class PDFWithText {
    public static void main(String[] args) {
        PDDocument doc = null;
        PDPage page = null;

       try{
           doc = new PDDocument();
           page = new PDPage();

           doc.addPage(page);
           PDFont font = PDType1Font.HELVETICA_BOLD;

           PDPageContentStream content = new PDPageContentStream(doc, page);
           content.beginText();
           content.setFont( font, 12 );
           content.moveTextPositionByAmount( 100, 700 );
           content.drawString("Csalam khubi che khabar felans");

           content.endText();
           content.close();
          doc.save("PDFWithText.pdf");
          doc.close();
    } catch (Exception e){
        System.out.println(e);
    }
}
}