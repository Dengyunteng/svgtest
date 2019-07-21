package cn.vvi.svg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import de.erichseifert.vectorgraphics2d.SVGGraphics2D;

public class Test1 {
    //直接复制代码就可生成,所要引入的pom在后面
    public static void main(String[] args) throws WriterException, FileNotFoundException {
        double point_x = 0;
        double point_y = 0;
        final int blockSize = 1;
        SVGGraphics2D funcOld = new SVGGraphics2D(point_x, point_y, 300 * blockSize, 300 * blockSize);
        ExportQrCode.fill2VectorLine(funcOld, GetBitMatrix("https://www.baidu.com/", 300, ErrorCorrectionLevel.M), blockSize);
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
