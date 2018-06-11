package com.bingo.util;

import com.bingo.model.Param;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BarCodeUtils {


    static String FILE_PATH;

    @Value("${com.bingo.printbarcode.codeImagePath}")
    public void setFILE_PATH(String codeImagePath) {
        FILE_PATH = codeImagePath;
    }

    static int columns = 4;
    static int rows = 17;

    static int codeWidth = 200; //条形码长度
    static int codeHeight = 60; //条形码宽度

    static int paddingTop = 20; //上边距
    static int paddingBottom = 20; //下边距
    static int paddingLeft = 20; //左边距
    static int paddingRight = 20; //右边距

    static int spaceColumn = 10;  // 列间距
    static int spaceRow = 10; //行间距

    static{

    }

    public static String generateCode(Param param) {
        File dir = new File(FILE_PATH);
        if(!dir.exists()){
            dir.mkdir();
        }

        int code = param.getStartNumber();
        int length = param.getLength();
        if(length < (code+"").length()){
            length = (code+"").length();
        }
        columns = param.getColumns();
        rows = param.getRows();

        codeWidth = param.getCodeWidth(); //条形码长度
        codeHeight = param.getCodeHeight(); //条形码宽度

        paddingTop = param.getPaddingTop(); //上边距
        paddingBottom = param.getPaddingBottom(); //下边距
        paddingLeft = param.getPaddingLeft(); //左边距
        paddingRight = param.getPaddingRight(); //右边距

        spaceColumn = param.getSpaceColumn();  // 列间距
        spaceRow = param.getSpaceRow(); //行间距

        List<String> barCodeFileNames = new ArrayList<>();
        //需要生成的一维码code
        for(int i = 0; i < columns *rows; i++) {
            String barCode = String.format("%0" + length + "d", code + i);
            barCodeFileNames.add(generateImg(barCode));
        }
        System.out.println(barCodeFileNames);
        String fileName = System.currentTimeMillis() + ".png";

        String codeName = FILE_PATH + fileName ;

        //讲多个一维码合成到一个图片上
        boolean result = mergeImg(barCodeFileNames, codeName);

        System.out.println(result);
        return fileName;
    }





    /**
     * 合并图片
     * @param barCodeFileNames
     * @param imgName
     * @return
     */
    public static boolean mergeImg(List<String> barCodeFileNames,String imgName) {
        //条形码文件数量
        int size = barCodeFileNames.size();
        File[] barCodeFiles = new File[size];
        //条形码图片数组
        BufferedImage[] bufferedImages = new BufferedImage[size];
        for(int i = 0;i < size;i++) {
            try {
                barCodeFiles[i] = new File(barCodeFileNames.get(i));
                //根据设置的条形码长度和宽度改变大小并初始化到条形码图片数组
                bufferedImages[i] = resizeImage(ImageIO.read(barCodeFiles[i]), codeWidth, codeHeight);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        //单个图片宽度
        int singleImgWidth = bufferedImages[0].getWidth();
        //单个图片高度
        int singleImgHeight = bufferedImages[0].getHeight();

        //合并后的大图片宽度
        int combinImgWidth = singleImgWidth * columns + paddingLeft + paddingRight + spaceColumn * (columns -1);
        //合并后的大图片高度
        int combinImgHeight = singleImgHeight * rows + paddingTop + paddingBottom + spaceRow * (rows-1);
        System.out.println("width:"+combinImgWidth);
        System.out.println("height:"+combinImgHeight);
        try {
            //合成图
            BufferedImage combinedImg = new BufferedImage(combinImgWidth, combinImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D) combinedImg.getGraphics();
            //设置背景色为白色，默认为黑色
            g2.setBackground(Color.WHITE);
            g2.clearRect(0, 0, combinImgWidth, combinImgHeight);

            for(int r=0;r<rows;r++){
                for(int c = 0; c< columns; c++){
                    int startX = paddingLeft + c * (spaceColumn + singleImgWidth);
                    int startY = paddingTop + r * (singleImgHeight + spaceRow);
                    int[] imageRGB = new int[singleImgWidth * singleImgHeight];
                    imageRGB = bufferedImages[r * columns + c].getRGB(0, 0, singleImgWidth, singleImgHeight,imageRGB, 0, singleImgWidth);
                    //讲单个图片的RGB值写入合成图中
                    combinedImg.setRGB(startX, startY, singleImgWidth, singleImgHeight, imageRGB, 0, singleImgWidth);
                }
            }

            File outFile = new File(imgName);

            ImageIO.write(combinedImg, "png", outFile);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //清理单个图片
        for(int i = 0;i<size;i++) {
            barCodeFiles[i].delete();
        }

        return true;
    }


    /**
     * 生成二维码
     * @param barcode
     * @return
     */
    public static String generateImg(String barcode) {
        OutputStream out = null;
        String fileName ;
        try {
            Code128Bean bean = new Code128Bean();
            final int dpi = 150;
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
//            // 宽度
//            bean.setWideFactor(4);
            // 高度
            bean.setHeight(10);
            // 条形码左右两边是否留空白，默认为true
            bean.doQuietZone(true);
            // 设置条码号字体的大小
            bean.setFontSize(2);
            // 设置条码号显示的位置
            bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
            // 获取文件的保存位置
            fileName = FILE_PATH + barcode + ".png";
            File outputFile = new File(fileName);
            out = new FileOutputStream(outputFile);
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/png", dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            bean.generateBarcode(canvas, barcode);
            canvas.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return fileName;
    }



    public static BufferedImage resizeImage(BufferedImage srcImg, int width, int height) {
        double wr,hr;
        BufferedImage buffImg = null;
//        wr = width;
//        hr = wr/srcImg.getWidth()*srcImg.getHeight();
//        int height =  (int)hr;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                0, null);
        return buffImg;

    }

    public static void main(String[] args) {


        int code = 1234567;
        generateImg(code-1 +"");
        List<String> barCodeFileNames = new ArrayList<>();
        //需要生成的一维码code
        for(int i = 0; i < columns *rows; i++) {
            String barCode = (code + i) + "";
            barCodeFileNames.add(generateImg(barCode));
        }
        System.out.println(barCodeFileNames);
        //讲多个一维码合成到一个图片上
        boolean result = mergeImg(barCodeFileNames, FILE_PATH+Thread.currentThread().getName() + System.currentTimeMillis() + ".png");

        System.out.println(result);

    }
}
