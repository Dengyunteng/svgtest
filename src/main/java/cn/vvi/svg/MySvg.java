package cn.vvi.svg;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import de.erichseifert.vectorgraphics2d.SVGGraphics2D;
import de.erichseifert.vectorgraphics2d.VectorGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Hashtable;

public class MySvg {
    private static void drawLine(int blockSize, SVGGraphics2D funcOld, int x1, int y1, int x2, int y2) {
//        java.awt.Rectangle s = new java.awt.Rectangle(x1, y1, x2, y2);
//        funcOld.fill(s);
        funcOld.setPaint(Color.red);
        Stroke s = new BasicStroke();
        funcOld.drawLine(0, 0, 300, 0);
        funcOld.drawLine(0, 0, 0, 600);
        funcOld.drawLine(300, 0, 300, 600);
        funcOld.drawLine(0, 600, 300, 600);
        funcOld.drawRoundRect(0, 0, 150, 150, 30, 30);
        Shape circle = new Ellipse2D.Double(0, 0, 50, 50);
        funcOld.setPaint(Color.red);
        funcOld.fill(circle);
        funcOld.translate(60, 0);
        funcOld.setPaint(Color.green);
        funcOld.fill(circle);
        funcOld.translate(60, 0);
        funcOld.setPaint(Color.blue);
        funcOld.fill(circle);

    }
    public static void main(String[] args) throws WriterException, FileNotFoundException {
        double point_x = 0;
        double point_y = 0;
        final int blockSize = 1;
        SVGGraphics2D funcOld = new SVGGraphics2D(point_x, point_y, 300 * blockSize, 600 * blockSize);
//        ExportQrCode.fill2VectorLine(funcOld, GetBitMatrix("https://www.baidu.com/", 300, ErrorCorrectionLevel.M), blockSize);
        drawLine(1, funcOld, 0, 0, 150, 150);
        File file = new File("2.svg");
        PrintStream psFile = new PrintStream(file);
        psFile.append(funcOld.toString());
        psFile.close();
    }

    public static BitMatrix GetBitMatrix(String content, int size, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        size = (size <= 0) ? 100 : size;
        BitMatrix bitMatrix = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1); // 控制码图白边
        hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel); // 容错率
        bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        return bitMatrix;
    }
}
