package cn.vvi.svg;

import java.util.ArrayList;
import java.util.List;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import org.w3c.dom.svg.SVGCircleElement;

public class ExportQrCode {
    private static void drawLine(int blockSize, VectorGraphics2D funcOld, int x1, int y1, int x2, int y2) {
        java.awt.Rectangle s = new java.awt.Rectangle(x1, y1, blockSize, (y2 - y1 + 1) * blockSize);
        funcOld.fill(s);
    }

    public static void fill2VectorLine(VectorGraphics2D funcOld, BitMatrix bitMatrix, int blockSize)
            throws WriterException {
        if (funcOld == null || bitMatrix == null)
            return;
        // 256 和32是有却别的,
        double width = bitMatrix.getWidth();
        double height = bitMatrix.getHeight();
        for (int x = 0; x < width; x++) {
            int theX = x * blockSize;
            List tmp = new ArrayList();
            int jsq = 0;
            int prev = -1;
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    if (prev == -1) {
                        jsq++;
                        prev = y;
                        continue;
                    }
                    if (1 == y - prev) {// 判断是否是连续的 下一个-上一个=1
                        jsq++;
                        prev = y;
                    } else {
                        tmp.add(String.format("a:%s->%s", (y - jsq), (y)));
                        drawLine(blockSize, funcOld, theX, (y - jsq), theX, y);
                        jsq = 0;
                        prev = y;
                    }
                } else {
                    if (prev >= 0) {
                        int y1 = (prev - jsq + 1);
                        int y2 = prev;
                        tmp.add(String.format("b:%s->%s", (y1), (y2)));
                        if (y1 == y2) {
                            funcOld.fillRect(theX, y1, blockSize, blockSize);
                        } else {
                            drawLine(blockSize, funcOld, theX, y1, theX, y2);
                        }
                        jsq = 0;
                        prev = -1;
                    }
                }
            }
            if (jsq > 0) {
                drawLine(blockSize, funcOld, theX, prev, theX, (prev + jsq - 1));
                tmp.add(String.format("c:%s->%s", (prev), (prev + jsq - 1)));
            }
        }
    }
}
