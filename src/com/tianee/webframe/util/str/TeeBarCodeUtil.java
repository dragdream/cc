package com.tianee.webframe.util.str;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code93Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;

import sun.misc.BASE64Encoder;


public class TeeBarCodeUtil {
	
	/**
	 * 
	 * @param format 格式  分为code128  和  code93
	 * @param strBarCode 将要生成条形码的字符串
	 * @param dimension  条形码的密集度  0-1
	 * @param barheight  条形码高度
	 * @param isShowText 是否显示文本
	 * @return
	 */
	public static String generateBarCode128(String format,String strBarCode,String dimension,String barheight,boolean isShowText) {  
        try {  
            ByteArrayOutputStream outputStream = null;  
            BufferedImage bi = null;  
            //int len = strBarCode.length();  
            JBarcode productBarcode =null;
            if(("code128").equals(format)){
            	productBarcode= new JBarcode(Code128Encoder.getInstance(),  
                      WidthCodedPainter.getInstance(),  
                      EAN13TextPainter.getInstance());
            }else{
            	productBarcode = new JBarcode(Code93Encoder.getInstance(),  
                         WidthCodedPainter.getInstance(),  
                         EAN13TextPainter.getInstance());
            }
         
            
   
            // 尺寸，面积，大小 密集程度  
            productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());  
            // 高度 10.0 = 1cm 默认1.5cm  
            productBarcode.setBarHeight(Double.valueOf(barheight).doubleValue());  
            // 宽度  
            productBarcode.setWideRatio(Double.valueOf(30).doubleValue());  
//          是否显示字体  
            productBarcode.setShowText(isShowText);  
//          显示字体样式  
            productBarcode.setTextPainter(BaseLineTextPainter.getInstance());   
   
            // 生成二维码  
            bi = productBarcode.createBarcode(strBarCode);  
              
            outputStream = new ByteArrayOutputStream();  
            ImageIO.write(bi, "jpg", outputStream);  
            BASE64Encoder encoder = new BASE64Encoder();  
           //System.err.println(encoder.encode(outputStream.toByteArray()));  
  
                return encoder.encode(outputStream.toByteArray());  
            } catch (Exception e) {  
                e.printStackTrace();  
                return "encodeError";  
        }  
    }  
}
