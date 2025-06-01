import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportPDF {

    public static void export(List<Barang> barangList, String filename) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // === Nama Perusahaan ===
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph header = new Paragraph("PT. Wira Works", headerFont);
            header.setAlignment(Element.ALIGN_LEFT);
            document.add(header);

            // === Garis Pembatas ===
            LineSeparator ls = new LineSeparator();
            document.add(new Chunk(ls));

            // === Judul Laporan ===
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("LAPORAN BARANG", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(10);
            title.setSpacingAfter(20);
            document.add(title);

            // === Tanggal Cetak ===
            Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            String tanggalCetak = new SimpleDateFormat("dd MMMM yyyy").format(new Date());
            Paragraph tanggal = new Paragraph("Dicetak pada: " + tanggalCetak, smallFont);
            tanggal.setAlignment(Element.ALIGN_RIGHT);
            tanggal.setSpacingAfter(10);
            document.add(tanggal);

            // === Tabel ===
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 1, 3, 4, 3 });

            Font headerTableFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            BaseColor headerColor = new BaseColor(220, 220, 220);

            // Header
            String[] headers = { "ID", "Tanggal", "Nama Barang", "Harga" };
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerTableFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Isi Tabel
            for (Barang b : barangList) {
                table.addCell(String.valueOf(b.getId()));
                table.addCell(b.getTanggal().toString());
                table.addCell(b.getNamaBarang());
                table.addCell(String.format("Rp %,d", b.getHarga()));
            }

            document.add(table);

            // === Ruang Kosong sebelum tanda tangan ===
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // === Tanda Tangan ===
            Paragraph ttd = new Paragraph(
                    "Mengetahui,\n\n\n\n" +
                            "Anak Agung Ngurah Aditya Wirayudha\n" +
                            "(_________________)",
                    smallFont);

            ttd.setAlignment(Element.ALIGN_RIGHT);
            document.add(ttd);

            document.close();
            System.out.println("✅ Laporan berhasil diexport ke " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
