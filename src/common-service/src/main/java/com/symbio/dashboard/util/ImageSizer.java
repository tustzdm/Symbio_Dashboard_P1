package com.symbio.dashboard.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.util.Base64;
import java.util.List;

public class ImageSizer {
    public static final MediaTracker tracker =
            new MediaTracker(new Component() {
                private static final long serialVersionUID = 1234162663955668507L;
            });

    /**
     * @param originalFile
     * @param resizedFile
     * @param width
     * @param format       jpg, png, gif()
     * @throws IOException
     */
    public static void resize(File originalFile, File resizedFile, int width, String format)
            throws IOException {
        if (format != null && "gif".equals(format.toLowerCase())) {
            resize(originalFile, resizedFile, width, 1);
            return;
        }
        FileInputStream fis = new FileInputStream(originalFile);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int readLength = -1;
        int bufferSize = 1024;
        byte bytes[] = new byte[bufferSize];
        while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
            byteStream.write(bytes, 0, readLength);
        }
        byte[] in = byteStream.toByteArray();
        fis.close();
        byteStream.close();

        Image inputImage = Toolkit.getDefaultToolkit().createImage(in);
        waitForImage(inputImage);
        int imageWidth = inputImage.getWidth(null);
        if (imageWidth < 1)
            throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
        int imageHeight = inputImage.getHeight(null);
        if (imageHeight < 1)
            throw new IllegalArgumentException("image height " + imageHeight + " is out of range");

        // Create output image.
        int height = -1;
        double scaleW = (double) imageWidth / (double) width;
        double scaleY = (double) imageHeight / (double) height;
        if (scaleW >= 0 && scaleY >= 0) {
            if (scaleW > scaleY) {
                height = -1;
            } else {
                width = -1;
            }
        }
        Image outputImage = inputImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        checkImage(outputImage);
        encode(new FileOutputStream(resizedFile), outputImage, format);
    }

    public static void resize(byte[] bytes, File resizedFile, int width, String format)
            throws IOException {
        if (format != null && "gif".equals(format.toLowerCase())) {
            resize(bytes, resizedFile, width, 1);
            return;
        }
        Image inputImage = Toolkit.getDefaultToolkit().createImage(bytes);
        waitForImage(inputImage);
        int imageWidth = inputImage.getWidth(null);
        if (imageWidth < 1)
            throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
        int imageHeight = inputImage.getHeight(null);
        if (imageHeight < 1)
            throw new IllegalArgumentException("image height " + imageHeight + " is out of range");

        // Create output image.
        int height = -1;
        double scaleW = (double) imageWidth / (double) width;
        double scaleY = (double) imageHeight / (double) height;
        if (scaleW >= 0 && scaleY >= 0) {
            if (scaleW > scaleY) {
                height = -1;
            } else {
                width = -1;
            }
        }
        Image outputImage = inputImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        checkImage(outputImage);
        encode(new FileOutputStream(resizedFile), outputImage, format);
    }

    public static void resizeWH(
            File originalFile, File resizedFile, int width, String format, int height)
            throws IOException {
        if (format != null && "gif".equals(format.toLowerCase())) {
            resizeH(originalFile, resizedFile, width, 1, height);
            return;
        }
        FileInputStream fis = new FileInputStream(originalFile);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int readLength = -1;
        int bufferSize = 1024;
        byte bytes[] = new byte[bufferSize];
        while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
            byteStream.write(bytes, 0, readLength);
        }
        byte[] in = byteStream.toByteArray();
        fis.close();
        byteStream.close();

        Image inputImage = Toolkit.getDefaultToolkit().createImage(in);
        waitForImage(inputImage);
        int imageWidth = inputImage.getWidth(null);
        if (imageWidth < 1)
            throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
        int imageHeight = inputImage.getHeight(null);
        if (imageHeight < 1)
            throw new IllegalArgumentException("image height " + imageHeight + " is out of range");

        // Create output image.
        double scaleW = (double) imageWidth / (double) width;
        double scaleY = (double) imageHeight / (double) height;
        if (scaleW >= 0 && scaleY >= 0) {
            if (scaleW > scaleY) {
                height = -1;
            } else {
                width = -1;
            }
        }
        Image outputImage = inputImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        checkImage(outputImage);
        encode(new FileOutputStream(resizedFile), outputImage, format);
    }

    /**
     * Checks the given image for valid width and height.
     */
    private static void checkImage(Image image) {
        waitForImage(image);
        int imageWidth = image.getWidth(null);
        if (imageWidth < 1)
            throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
        int imageHeight = image.getHeight(null);
        if (imageHeight < 1)
            throw new IllegalArgumentException("image height " + imageHeight + " is out of range");
    }

    /**
     * Waits for given image to load. Use before querying image height/width/colors.
     */
    private static void waitForImage(Image image) {
        try {
            tracker.addImage(image, 0);
            tracker.waitForID(0);
            tracker.removeImage(image, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encodes the given image at the given quality to the output stream.
     */
    private static void encode(OutputStream outputStream, Image outputImage, String format)
            throws IOException {
        int outputWidth = outputImage.getWidth(null);
        if (outputWidth < 1)
            throw new IllegalArgumentException("output image width " + outputWidth + " is out of range");
        int outputHeight = outputImage.getHeight(null);
        if (outputHeight < 1)
            throw new IllegalArgumentException(
                    "output image height " + outputHeight + " is out of range");

        // Get a buffered image from the image.
        BufferedImage bi = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D biContext = bi.createGraphics();
        biContext.drawImage(outputImage, 0, 0, null);
        ImageIO.write(bi, format, outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private static void resizeH(
            File originalFile, File resizedFile, int newWidth, float quality, int height)
            throws IOException {
        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;
        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, height, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance(height, newWidth, Image.SCALE_SMOOTH);
        }
        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();
        // Create the buffered image.
        BufferedImage bufferedImage =
                new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();
        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();
        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {
                0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0
        };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);
        writeImage(bufferedImage, resizedFile, quality);
    }

    /**
     * gif
     *
     * @param originalFile
     * @param resizedFile
     * @param newWidth
     * @param quality
     * @throws IOException
     */
    private static void resize(File originalFile, File resizedFile, int newWidth, float quality)
            throws IOException {
        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;
        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
        if (iWidth > iHeight) {
            resizedImage =
                    i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage =
                    i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
        }
        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();
        // Create the buffered image.
        BufferedImage bufferedImage =
                new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();
        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();
        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {
                0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0
        };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);
        writeImage(bufferedImage, resizedFile, quality);
    }

    @SuppressWarnings("unused")
    private static void resize(byte[] bytes, File resizedFile, int newWidth, float quality)
            throws IOException {
        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }
        ImageIcon ii = new ImageIcon(bytes);
        Image i = ii.getImage();
        Image resizedImage = null;
        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
        if (iWidth > iHeight) {
            resizedImage =
                    i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage =
                    i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
        }
        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();
        // Create the buffered image.
        BufferedImage bufferedImage =
                new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();
        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();
        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {
                0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0
        };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);
        writeImage(bufferedImage, resizedFile, quality);
    }

    private static void writeImage(BufferedImage image, File outputFile, float quality)
            throws IOException {
        FileOutputStream out = new FileOutputStream(outputFile);
        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(quality);
        final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        writer.setOutput(out);
        writer.write(null, new IIOImage(image, null, null), jpegParams);
    }

    public static void drawRect(InputStream input, File outFile, String file, List<Rect> rect)
            throws Exception {
        Image image = ImageIO.read(input);
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        // g.setColor(Color.RED);
        // g.setColor(new Color(255, 99, 132));
        g.setStroke(new BasicStroke(2));
        for (Rect r : rect) {
            g.setColor(r.getColor());
            g.drawRect(r.x, r.y, r.width, r.height);
        }
        g.dispose();
        ImageIO.write(bufferedImage, FilenameUtils.getExtension(file), outFile);
    }

    public static byte[] File2byte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void writeByteArrayToFile(String path, byte[] byteArray) {
        try {
            FileUtils.writeByteArrayToFile(new File(path), byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeCanvasBytesToPng(String path, String data) {
        try {
            String strImgData = data;
            if (strImgData.startsWith("data:image/png;base64")) {
                strImgData = strImgData.substring("data:image/png;base64,".length());
            }
            byte[] decodedString = Base64.getDecoder().decode(strImgData.getBytes("UTF-8"));
            writeByteArrayToFile(path, decodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rect {
        int x;
        int y;
        int width;
        int height;
        Color color;

        public Rect() {
        }

        public Rect(int x, int y, int width, int height) {
            super();
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public Rect(int x, int y, int width, int height, Color color) {
            super();
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            ImageSizer is = new ImageSizer();
            byte[] arrayByte = new byte[200];
            arrayByte = File2byte(new File("/home/shawn/new.txt"));
            String strImgData = new String(arrayByte);

            writeCanvasBytesToPng("/home/shawn/cavans.png", strImgData);

            if (strImgData.startsWith("data:image/png")) {
                strImgData = strImgData.substring("data:image/png;base64,".length());
            }
            byte[] decodedString = Base64.getDecoder().decode(strImgData.getBytes("UTF-8"));
            is.writeByteArrayToFile("/home/shawn/test.png", decodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
