package com.gft.management.utils;

import com.gft.management.models.Projeto;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class NotasPDFExporter {
    private List<Projeto> listProjetos;

    public NotasPDFExporter(List<Projeto> listProjetos) {
        this.listProjetos = listProjetos;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.decode("#213E7F"));
        cell.setPadding(5);

        Font fonte = FontFactory.getFont(FontFactory.HELVETICA);
        fonte.setColor(Color.WHITE);

        List<String> tableHeaders = Arrays.asList("Projeto ID", "Starter", "Nota", "Módulo", "Etapa");
        tableHeaders.forEach(header -> {
            cell.setPhrase(new Phrase(header, fonte));
            table.addCell(cell);
        });
    }

    private void writeTableData(PdfPTable table) {
        for (Projeto projeto : listProjetos) {

            table.addCell(String.valueOf(projeto.getId()));
            table.addCell(projeto.getStarter().getNome());

            PdfPCell notaCell = new PdfPCell(new Phrase(String.valueOf(projeto.getNota())));
            mudarCorDaCellBaseadoNaNota(projeto, notaCell);
            table.addCell(notaCell);
            table.addCell(projeto.getModulo().getNome());
            table.addCell(String.valueOf(projeto.getEtapa().getNome()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.decode("#213E7F"));
        inserirLogoNoPDF(document);

        Paragraph titulo = new Paragraph("Lista de projetos por nota", font);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titulo);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }

    private void mudarCorDaCellBaseadoNaNota(Projeto projeto, PdfPCell notaCell) {
        if(projeto.getNota() < 50){
            notaCell.setBackgroundColor(Color.RED);
        } else if(projeto.getNota() >= 50 && projeto.getNota() < 69) {
            notaCell.setBackgroundColor(Color.ORANGE);
        } else {
            notaCell.setBackgroundColor(Color.GREEN);
        }
    }

    private void inserirLogoNoPDF(Document document) throws IOException {
        Image logo = Image.getInstance("src/main/resources/static/images/logo.jpg");
        logo.scaleAbsolute(90,90);
        logo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(logo);
    }
}
