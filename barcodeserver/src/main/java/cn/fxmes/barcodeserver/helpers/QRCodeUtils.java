package cn.fxmes.barcodeserver.helpers;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


/**
 * 生成二维码的工具类
 *
 * @author Administrator
 */
public final class QRCodeUtils {
    /** 类实例（这个工具类使用了单例模式） */
    private static QRCodeUtils instance;

    /** 二位码写出工具的参数模型 */
    private Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();

    /** 生成二维码图片类型 */
    private static final String IMAGE_TYPE = "png";

    /** 填充文字所使用的画笔对象 */
    private Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, 10);

    /** 源码打印标识 */
    private boolean isPrintSourceCode = true;

    /**
     * 私有构造函数
     */
    private QRCodeUtils() {
        hints.put(EncodeHintType.CHARACTER_SET, "ISO8859-1");
    }

    /**
     * @return 类实例
     */
    public synchronized static QRCodeUtils getInstace() {
        if (instance == null) {
            instance = new QRCodeUtils();
        }
        return instance;
    }

    /**
     * @param code 源码
     * @param isPrintSourceCode 打印条码标识
     * @return 二维码字节字节流数组
     */
    public InputStream getByteStream(String code, boolean isPrintSourceCode) {
        if (code == null || "".equals(code.trim())) {
            return null;
        }
        return getByteStream(code, 100, 100, (this.isPrintSourceCode = isPrintSourceCode));
    }


    /**
     * @param code 源码
     * @param w 宽度
     * @param h 高度
     * @param isPrintSourceCode  打印条码标识
     * @return 二维码字节字节流数组
     */
    public InputStream getByteStream(String code, int w, int h, boolean isPrintSourceCode) {
        // 打印条码标识
        this.isPrintSourceCode = isPrintSourceCode;

        // 要编码的参数不能为空
        if (code == null) {
            throw new IllegalArgumentException("QRCode is null.");
        }

        // 校验宽度和高度
        if (w < 0 || h < 0) {
            throw new IllegalArgumentException("高度和宽度指定的不正确，必须是大于0的整数");
        }

        // 生成矩阵模型
        BitMatrix bitMatrix = getBitMatrix(code, w, h);

        // 缓存输出的条码对象
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;

        try {
            // 条码写出到指定流中
            writeToStream(bitMatrix, IMAGE_TYPE, code, (out = new ByteArrayOutputStream()));

            return (in = new ByteArrayInputStream(out.toByteArray()));
        } finally { // Resource Release
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    out = null;
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    in = null;
                }
            }
        }
    }

    /**
     * @param code 代码
     * @param w 宽度
     * @param h 高度
     * @return 生成矩阵模型
     */
    private BitMatrix getBitMatrix(String code, int w, int h) {
        // 写出工具类
        Writer writer = new MultiFormatWriter();

        // 生成一个矩阵模型
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = writer.encode(code, BarcodeFormat.QR_CODE, w, h, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }

    /**
     * @param matrix  二维码矩阵
     * @param code  源码
     * @return 二维码图片
     */
    private BufferedImage toBufferedImage(BitMatrix matrix, String code) {
        // 宽度 (这里增加20是为了适应源内容的字号)
        int w = matrix.getWidth() + 20;
        // 高度
        int h = matrix.getHeight();

        // 图片缓冲
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        // 获得画笔对象
        Graphics2D g = (Graphics2D)image.getGraphics();
        // 设置背景颜色
        g.setBackground(Color.WHITE);

        // 填充图片缓冲区(逐个像素点进行填充)
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                image.setRGB(x, y, adapterColor(matrix.get(x, y)));
            }
        }
        // 判断是否打印文本标识别
        if (isPrintSourceCode) {
            // 设置画笔颜色
            g.setColor(Color.BLACK);
            // 设置字体
            g.setFont(font);
            // 填充文字，0表示X轴3点，10是Y轴10点
            g.drawString(code, 6, 10);
        }
        return image;
    }

    /**
     * 写出条码对象到指定的流中
     *
     * @param matrix 条码矩形对象
     * @param format 图片格式
     * @param stream 输出流
     * @throws IOException
     */
    private void writeToStream(BitMatrix matrix, String format, String code, OutputStream stream) {
        try {
            // 写出条码对象到指定的流中
            ImageIO.write(toBufferedImage(matrix, code), format, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param isFill 指定像素点是否有元素
     * @return 填充颜色
     */
    private int adapterColor(boolean isFill) {
        // 有元素的部分用黑色填充，其余部分用白色填充
        if (isFill) {
            return Color.BLACK.getRGB();
        }
        return Color.WHITE.getRGB();
    }
}
