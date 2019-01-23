package com.ocean;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by 99512 on 2019/1/9 9:49.
 */
public class ResizeImage {

    static String strings[] = new String[]{"JPG","JPEG","PNG","BMP","PSD"};
    static List<String> suffixs = Arrays.asList(strings);

    /**
     * 对图片宽度大于deskWidth的一律生成deskWidth缩略图，高度保持等比
     * @param srcFile   源图片的file对象
     * @param destPath  目标图片的保存的绝对路径
     * @param destWidth 目标图片的宽度
     * @throws Exception
     */
    public static void saveMinPhoto(File srcFile, String destPath,int destWidth ) throws Exception {
        Image src = ImageIO.read(srcFile);
        int srcWidth = src.getWidth(null);
        if(srcWidth<=destWidth){
            //小于预定尺寸的图片不做处理
            return;
        }
        int srcHeight = src.getHeight(null);
        double srcScale = (double) srcHeight / srcWidth;
        int destHeight = (int)(destWidth*srcScale);// 缩略图高
        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src, 0, 0, destWidth, destHeight, null); //绘制缩小后的图
        FileOutputStream deskImage = new FileOutputStream(destPath); //输出到文件流
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
        encoder.encode(tag); //近JPEG编码
        deskImage.close();
    }

    /**
     * 生成PDF文件的缩略图
     * @param srcFile
     * @param destPath
     * @throws Exception
     */
    public static void saveMinPdf(File srcFile, String destPath)throws Exception{
        Document document = new Document();
        float rotation = 0f;
        document.setFile(srcFile.getAbsolutePath());
        BufferedImage img = (BufferedImage) document.getPageImage(0, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, 1);

        Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
        ImageWriter writer = (ImageWriter) iter.next();
        File outFile = new File(destPath);
        FileOutputStream out = new FileOutputStream(outFile);
        ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
        writer.setOutput(outImage);
        writer.write(new IIOImage(img, null, null));

    }

    /**
     * 遍历目录下所有文件，对其中的图片生成缩略图
     * @param destFile
     */
    public static void transAllPhoto(String destFile){
        File file = new File(destFile);
        if(!file.exists()){
            System.out.println("该路径不存在");
            return;
        }
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                transAllPhoto(f.getAbsolutePath());
            }
        }else if(file.isFile()){
            String absolutePath = file.getAbsolutePath();
            if(absolutePath.lastIndexOf(".")<0){
                return;
            }
            String prefix = absolutePath.substring(0,absolutePath.lastIndexOf("."));
            String suffix = absolutePath.substring(absolutePath.lastIndexOf(".") + 1);
            String destPath = "";
            if(suffix.equalsIgnoreCase("pdf")){
                destPath = prefix+"-slt.jpg";
                if(new File(destPath).exists()){
                    //该图片的缩略图已存在
                    return;
                }
                try {
                    ResizeImage.saveMinPdf(file,destPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(suffixs.contains(suffix.toUpperCase())){
                destPath = prefix+"-slt."+suffix;
                if(new File(destPath).exists()){
                    //该图片的缩略图已存在
                    return;
                }
                try{
                    ResizeImage.saveMinPhoto(file,destPath,400);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
