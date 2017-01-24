package Converter;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * create by Intellij IDEA
 * Author: Al-assad
 * E-mail: yulinying@1994.com
 * Github: https://github.com/Al-assad
 * Date: 2017/1/23 21:02
 * Description: 字符画转化器核心类
 *
 */
public class Converter {

    //实例保存在静态域中，构造方私有，只向外部提供的静态调用
    private static Converter converter;

    private VBox pane;   //绘制字符的面板
    private Image srcImage;    //图片源路径
    private char paintChar;    //字符画使用字符
    private boolean backgroundColor;  //是否启用背景
    private int fontSize;     //字体大小
    private int fontHeight;  //字体高度像素
    private int fontWidth;    //字体宽度像素
    private int xCount;  // X,Y轴上字符的个数
    private int yCount;

    private TextFlow textflow;
    private int line ;

    /**构造方法：完成实例数据域赋值，创建源素材图片对象*/
    private Converter(String srcPath,char paintChar ,int fontSize,boolean backgroundColor){
        this.paintChar = paintChar;
        initinalize(srcPath,fontSize,backgroundColor);
    }

    private void initinalize(String srcPath,int fontSize,boolean backgroundColor){
        this.fontSize = fontSize;
        this.backgroundColor = backgroundColor;

        srcImage = new Image(srcPath);
        FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(new java.awt.Font(null,java.awt.Font.PLAIN,fontSize));
        this.fontHeight = fm.getHeight();
        this.fontWidth = fm.charWidth(paintChar);

        //获取X,Y轴上字符的个数
        xCount = ((int)srcImage.getWidth()) / fontWidth;
        yCount = ((int)srcImage.getHeight()) / fontHeight;
    }

    /**外部调用方法:转化图片，使用指定字符填充(参数：源图片路径，字符素材，字符大小，是否启用背景)*/
    public static Converter convertWithChar(String srcPath,char paintChar ,int fontSize,boolean backgroundColor){
        converter = new Converter("file:"+srcPath,paintChar,fontSize,backgroundColor);
        converter.convertSrcImage();

        return converter;

    }
    /** @TODO
     * 外部调用方法：转化图片，使用指定文字文件填充（如代码文件）（参数：原图片路径，素材文件路径，字符大小，是启用背景）**/
    private static Converter convertWithFile(String srcPath,String materialPath,int fontSize,boolean backgroundColor){
        return null;
    }

    /**外部调用方法：储存为图片（参数：储存路径）*/
    public void save(String saveFilePath){
        Scene scene = new Scene(pane,converter.getTextflow().getPrefWidth(),converter.getTextflow().getPrefHeight()*converter.getLine());
        //获取场景的快照，并将其写入文件
         WritableImage writeableImage = scene.snapshot(null);
        File outFile = new File(saveFilePath);
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(writeableImage,null),"png",outFile);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**实例转化图片对象，遍历像素，向TextFlow写入相应RGB值的Text*/
    private void convertSrcImage(){

        //创建Pane对象,并设置尺寸
        pane = new VBox();
        pane.setAlignment(Pos.CENTER);

        //计算背景颜色的RBG
        int totalR = 0;
        int totalG = 0;
        int totalB = 0;

        //遍历像素点，打印字符:

        PixelReader reader = srcImage.getPixelReader();
        for(int y=0;y<yCount*fontHeight;y+=fontHeight){
            //每行创建一个TextFlow对象，读取图片与单位字体同等像素大小的区域，计算其平均RGB，向TextFlow写入该颜色值的Text
            textflow = new TextFlow();
            textflow.setTextAlignment(TextAlignment.CENTER);
            for(int x=0;x<xCount*fontWidth;x+=fontWidth){
                Color avgColor = getRectAvgColor(reader,x,y,fontWidth,fontHeight);

                //累加总RGB值
                totalR += (int)Math.floor(avgColor.getRed()*255);
                totalG += (int)Math.floor(avgColor.getGreen()*255);
                totalB += (int)Math.floor(avgColor.getBlue()*255);

                //创建Text对象并装载入TextFlow
                Text text = new Text(paintChar+"");
                text.setFont(Font.font(fontSize));
                text.setFill(avgColor);
                textflow.getChildren().add(text);

            }
            //每行TextFlow填充Text结束后，将其装载到VBox
            pane.getChildren().add(textflow);
            line++;

        }
        //设置背景颜色
        if(backgroundColor) {
            int totalCount = xCount * yCount;
            String colorValue = "#" + Integer.toHexString(totalR / totalCount) + Integer.toHexString(totalG / totalCount)
                    + Integer.toHexString(totalB / totalCount);
            pane.setStyle("-fx-background-color:" + colorValue);
        }


    }

    //私有方法：获取图像矩形区域RGB的平均值；
    private static Color getRectAvgColor(PixelReader reader,int iniX,int iniY,int width,int height){
        int rVal=0;
        int gVal=0;
        int bVal=0;
        Color colorTemp;
        int count = width * height;
        for(int y=iniY;y<iniY+height;y++){
            for(int x=iniX;x<iniX+width;x++){
                colorTemp = reader.getColor(x,y);
                rVal += (int)Math.floor(colorTemp.getRed()*255);
                gVal += (int)Math.floor(colorTemp.getGreen()*255);
                bVal += (int)Math.floor(colorTemp.getBlue()*255);
            }
        }
        return Color.rgb(rVal/count,gVal/count,bVal/count);
    }


    public VBox getPane(){
        return this.pane;
    }
    private TextFlow getTextflow(){
        return this.textflow;
    }
    private int getLine(){
        return this.line;
    }




}
