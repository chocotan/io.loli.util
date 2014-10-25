package io.loli.util.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public class ThumbnailUtil {
    public final static int SMALL_SIZE = 150;
    public final static int MIDDLE_SIZE = 300;
    public final static int LARGE_SIZE = 600;

    /**
     * 按照比例缩放成小图片
     * 
     * @param is 该图片输入流
     * @param format 图片的格式
     * @return 转换后的图片的输出流
     * @throws IOException 当文件读取、写入错误时抛出此异常
     */
    public static ByteArrayOutputStream resizeSmall(InputStream is, String format) throws IOException {
        BufferedImage image = ImageIO.read(is);
        return toOutputStream(resize(image, SMALL_SIZE, SMALL_SIZE, format), format);
    }

    /**
     * 按照比例缩放图片，如果提供的最大宽高大于原图片宽高，则不缩放
     * 
     * @param origin 需要转换的图片
     * @param maxWidth 缩放的最大宽度
     * @param maxHeight 缩放的最大高度
     * @param format 该图片的格式，如jpg, png, gif等等；gif图片只截取第一帧
     * @return 转换后的图片
     */
    public static BufferedImage resize(BufferedImage origin, int maxWidth, int maxHeight, String format) {
        double thumbRatio = (double) maxWidth / (double) maxHeight;
        int imageWidth = origin.getWidth();
        int imageHeight = origin.getHeight();
        double imageRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < imageRatio) {
            maxHeight = (int) (maxWidth / imageRatio);
        } else {
            maxWidth = (int) (maxHeight * imageRatio);
        }

        if (imageWidth < maxWidth && imageHeight < maxHeight) {
            maxWidth = imageWidth;
            maxHeight = imageHeight;
        } else if (imageWidth < maxWidth)
            maxWidth = imageWidth;
        else if (imageHeight < maxHeight)
            maxHeight = imageHeight;

        Image img = origin.getScaledInstance(maxWidth, maxHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bufImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufImg.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bufImg;
    }

    /**
     * 将图片转为输出流
     * 
     * @param image 需要转换的图片
     * @param format 图片格式
     * @return 输出流
     * @throws IOException 当文件读取、写入错误时抛出此异常
     */
    public static ByteArrayOutputStream toOutputStream(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, format, os);
        } finally {
            os.close();
        }
        return os;
    }

    /**
     * 按照比例缩放成中等大小图片
     * 
     * @param is 该图片输入流
     * @param format 图片的格式
     * @return 转换后的图片的输出流
     * @throws IOException 当文件读取、写入错误时抛出此异常
     */
    public static ByteArrayOutputStream resizeMiddle(InputStream is, String format) throws IOException {
        BufferedImage image = ImageIO.read(is);
        return toOutputStream(resize(image, MIDDLE_SIZE, MIDDLE_SIZE, format), format);
    }

    /**
     * 按照比例缩放成大图片
     * 
     * @param is 该图片输入流
     * @param format 图片的格式
     * @return 转换后的图片的输出流
     * @throws IOException 当文件读取、写入错误时抛出此异常
     */
    public static ByteArrayOutputStream resizeBig(InputStream is, String format) throws IOException {
        BufferedImage image = ImageIO.read(is);
        return toOutputStream(resize(image, LARGE_SIZE, LARGE_SIZE, format), format);
    }

    /**
     * 裁剪图片
     * 
     * @param image 需要裁剪的图片
     * @param format 该图片的格式
     * @param x 起始横坐标
     * @param y 起始纵坐标
     * @param width 裁剪部分宽度
     * @param height 裁剪部分高度
     * @return 裁剪后的图片
     */
    public static BufferedImage cut(BufferedImage image, String format, int x, int y, int width, int height) {
        BufferedImage img = image.getSubimage(x, y, width, height);
        return img;
    }

    /**
     * 将图片在正中裁剪为正方形
     * 
     * @param is 该图片的输入流
     * @param format 该图片的格式
     * @return 裁剪后的图片的输入流
     * @throws IOException 当文件读取、写入错误时抛出此异常
     */
    public static ByteArrayOutputStream cutSqure(InputStream is, String format) throws IOException {
        BufferedImage image = ImageIO.read(is);
        int w = image.getWidth();
        int h = image.getHeight();
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        if (w > h) {
            x = (w - h) / 2;
            y = 0;
            width = h;
            height = h;
        } else {
            x = 0;
            y = (h - w) / 2;
            width = w;
            height = w;
        }

        return toOutputStream(cut(image, format, x, y, width, height), format);
    }

    /**
     * 将图片缩放为小图片并裁剪为正方形
     * 
     * @param is 该图片的输入流
     * @param format 该图片的格式
     * @return 裁剪后的图片的输入流
     * @throws IOException 当文件读取、写入错误时抛出此异常
     */
    public static ByteArrayOutputStream cutSqureWithResizeSmall(InputStream is, String format) throws IOException {
        return cutSqureWithResize(is, SMALL_SIZE, SMALL_SIZE, format);
    }

    /**
     * 将图片缩放为指定大小并裁剪为正方形
     * 
     * @param is 该图片的输入流
     * @param maxWidth 缩放的最大宽度
     * @param maxHeight 缩放的最大高度
     * @param format 该图片的格式，如jpg, png, gif等等；gif图片只截取第一帧
     * @return 转换后的图片的输出流
     * @throws IOException 当文件读取、写入错误时抛出此异常
     */
    public static ByteArrayOutputStream cutSqureWithResize(InputStream is, int maxWidth, int maxHeight, String format)
        throws IOException {
        BufferedImage image = ImageIO.read(is);

        double thumbRatio = (double) maxWidth / (double) maxHeight;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double imageRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < imageRatio) {
            maxHeight = (int) (maxWidth / imageRatio);
        } else {
            maxWidth = (int) (maxHeight * imageRatio);
        }

        if (imageWidth < maxWidth && imageHeight < maxHeight) {
            maxWidth = imageWidth;
            maxHeight = imageHeight;
        } else if (imageWidth < maxWidth)
            maxWidth = imageWidth;
        else if (imageHeight < maxHeight)
            maxHeight = imageHeight;
        int w = maxWidth;
        int h = maxHeight;
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        if (w > h) {
            x = (w - h) / 2;
            y = 0;
            width = h;
            height = h;
        } else {
            x = 0;
            y = (h - w) / 2;
            width = w;
            height = w;
        }
        Image img = image.getScaledInstance(maxWidth, maxHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufImg.createGraphics();
        g.drawImage(img, -x, -y, null);
        g.dispose();
        return toOutputStream(bufImg, format);
    }

    public static void main(String[] args) throws IOException {
        Files.write(
            new File("/tmp/test.png").toPath(),
            ((ByteArrayOutputStream) cutSqure(new FileInputStream(
                "/home/choco/images/weibo/79c1fb50gw1eizrrfj5wdj20qu18bn2q (2).jpg"), "jpg")).toByteArray());
    }

}
