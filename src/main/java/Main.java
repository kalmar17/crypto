import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedImage img = ImageIO.read(new File("/Users/ekaterina/Downloads/LAND.BMP"));
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        String s = "hello";
        byte[] bytes = s.getBytes();

        BitSet arrayBitSet = BitSet.valueOf(bytes);
        int k = 0;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color color = new Color(img.getRGB(i, j));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                int newR = 0;
                int newG = 0;
                int newB = 0;
                if (k >= bytes.length * 8) {
                    newR = r;
                    newG = g;
                    newB = b;
                } else if (k < bytes.length * 8 - 3) {
                    newR = newColorRGB(r, arrayBitSet.get(k));
                    newG = newColorRGB(g, arrayBitSet.get(k + 1));
                    newB = newColorRGB(b, arrayBitSet.get(k + 2));
                    k += 3;
                } else {
                    newR = newColorRGB(r, arrayBitSet.get(k));
                    k++;
                    if (k < bytes.length)
                        newG = newColorRGB(g, arrayBitSet.get(k));
                    else
                        newG = g;
                    k++;
                    if (k < bytes.length)
                        newB = newColorRGB(b, arrayBitSet.get(k));
                    else
                        newB = b;
                    k++;
                }

                Color newColor = new Color(newR, newG, newB);

                // И устанавливаем этот цвет в текущий пиксель результирующего изображения
                result.setRGB(i, j, newColor.getRGB());
            }

        }
        File output = new File("LAND.BMP");
        ImageIO.write(result, "BMP", output);

        BufferedImage imgNew = ImageIO.read(new File("/Users/ekaterina/IdeaProjects/Lab5Crypto/LAND.BMP"));
        StringBuffer sBuffer = new StringBuffer();

        for (int i = 0; i < imgNew.getWidth(); i++) {
            for (int j = 0; j < imgNew.getHeight(); j++) {
                Color color = new Color(imgNew.getRGB(i, j));

                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                sBuffer.append(r & 1);
                sBuffer.append(g & 1);
                sBuffer.append(b & 1);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < sBuffer.length(); i += 8) {
            char c = Character.forDigit((Integer.valueOf(sBuffer.substring(i, i + 7), 10)), 10);
            stringBuffer.append(c);
        }
        System.out.println(stringBuffer.toString());
    }

    public static int flipBit(int value) {
        return value ^ 1;
    }

    static int newColorRGB(int col, boolean bool) {
        if (((col & 1) == 1 && !bool) || ((col & 1) == 0 && bool))
            return flipBit(col);
        else
            return col;
    }

}
