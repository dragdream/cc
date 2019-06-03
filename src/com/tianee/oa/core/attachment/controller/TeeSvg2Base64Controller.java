package com.tianee.oa.core.attachment.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/svg2base64")
public class TeeSvg2Base64Controller {
	
	@ResponseBody
	@RequestMapping("/convert")
	public TeeJson convert(String svg) throws IOException, TranscoderException{
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtData(convertToPngBase64(svg));
		return json;
	}
	
	public static void main(String[] args) throws IOException, TranscoderException {
		System.out.println(convertToPngBase64("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"459\" height=\"186\"><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 1 111 c 0.05 -0.09 1.73 -3.86 3 -5 c 1.76 -1.58 4.61 -3.32 7 -4 c 4.19 -1.2 9.29 -1.06 14 -2 c 7.19 -1.44 13.78 -3.96 21 -5 c 35.04 -5.05 68.37 -8.02 104 -13 c 11.07 -1.55 21.06 -3.69 32 -6 c 6.93 -1.46 13.36 -2.99 20 -5 c 4.48 -1.36 8.77 -3.1 13 -5 c 2.45 -1.1 6.41 -2.17 7 -4 c 1.53 -4.77 1.61 -14.67 1 -21 c -0.31 -3.24 -1.79 -8.08 -4 -10 c -4.4 -3.82 -12.45 -6.93 -19 -10 c -4.25 -1.99 -8.55 -3.63 -13 -5 c -4.26 -1.31 -11.08 -4.15 -13 -3 c -1.75 1.05 -1.86 7.97 -2 12 c -0.52 14.58 0.26 28.93 0 44 c -0.09 5.15 -0.14 10.13 -1 15 c -1.12 6.36 -3.17 17.17 -5 19 c -0.93 0.93 -5.42 -4.97 -8 -6 c -1.81 -0.72 -5.14 -0.86 -7 0 c -9.17 4.21 -20.12 10.83 -30 17 c -6.36 3.97 -12.13 8.34 -18 13 c -3.93 3.12 -11.23 9.19 -11 10 l 13 -3\"/><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 162 86 c 0.11 -0.02 3.98 -0.91 6 -1 c 5.63 -0.24 12.03 -1.03 17 0 c 3.92 0.81 8.52 3.41 12 6 c 9.19 6.84 19.83 16.08 27 23 c 0.83 0.8 1.12 2.82 1 4 c -0.18 1.81 -0.86 4.98 -2 6 c -1.32 1.17 -7 2 -7 2\"/><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 160 113 c 0.4 0 15.58 -0.27 23 0 c 1.33 0.05 4 0.38 4 1 c 0 1.35 -2.06 6.57 -4 8 c -3.5 2.58 -10.08 3.76 -15 6 c -2.46 1.12 -4.61 3.28 -7 4 c -3.89 1.17 -11.69 0.09 -13 2 c -1.41 2.05 0.89 9.56 2 14 c 0.84 3.38 3.08 6.61 4 10 c 1.04 3.83 1.82 8.11 2 12 c 0.15 3.22 0.07 8.47 -1 10 c -0.64 0.91 -4.44 0.69 -6 0 c -3.75 -1.67 -9.23 -5.05 -12 -8 c -1.64 -1.75 -2.59 -5.38 -3 -8 c -0.53 -3.37 -1.25 -8.91 0 -11 c 1.12 -1.87 6 -3.12 9 -4 c 2.45 -0.72 5.29 -0.86 8 -1 c 4.03 -0.2 7.98 0.29 12 0 c 5.41 -0.39 10.58 -1.82 16 -2 c 14.45 -0.49 43 0 43 0\"/><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 248 79 c 0.04 0.32 1.79 11.89 2 18 c 0.47 13.84 -0.37 39.84 0 41 c 0.2 0.65 4.55 -14.57 6 -22 c 1.21 -6.23 0.98 -12.69 2 -19 c 0.66 -4.08 1.88 -7.89 3 -12 c 0.95 -3.47 1.71 -7.2 3 -10 c 0.52 -1.14 2.28 -3.18 3 -3 c 1.2 0.3 3.96 3.14 5 5 c 1.73 3.07 2.99 7.23 4 11 c 1.32 4.92 2.66 9.99 3 15 c 0.65 9.49 0.91 19.59 0 29 c -0.73 7.64 -5 23 -5 23\"/><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 279 103 c 0.03 0.1 2.13 4.3 2 6 c -0.16 2.04 -1.54 5.3 -3 7 c -2.18 2.54 -9 7 -9 7\"/><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 310 10 c 0.44 0.1 16.7 3.57 25 6 c 5.51 1.61 10.65 4.3 16 6 c 1.9 0.61 4.59 0.3 6 1 c 0.83 0.42 2 2.13 2 3 c 0 1.3 -0.82 4.44 -2 5 c -3.84 1.82 -11.75 2.33 -17 4 c -1.75 0.56 -3.34 2.69 -5 3 c -3.13 0.59 -11.3 -0.12 -11 0 c 0.94 0.37 23.24 5.59 34 9 c 2.47 0.78 6.43 2.73 7 4 c 0.41 0.92 -1.73 4.3 -3 5 c -1.76 0.96 -5.36 0.41 -8 1 c -6.43 1.43 -12.57 4.05 -19 5 c -11.5 1.7 -24.76 1.65 -35 3 c -1.05 0.14 -3.21 1.99 -3 2 c 1.07 0.07 11.78 -0.9 18 -1 c 37.24 -0.59 73.87 1.86 108 -1 c 11.59 -0.97 35 -11 35 -11\"/><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 399 1 c -0.23 0.53 -7.98 20.3 -13 30 c -4.69 9.06 -10.22 17.78 -16 26 c -2.81 4 -6.35 7.56 -10 11 c -7.83 7.37 -15.49 14.31 -24 21 c -10.52 8.26 -32 23 -32 23\"/><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 357 68 c 0.17 0.07 7.16 2.21 10 4 c 3.17 2 5.72 5.62 9 8 c 13.9 10.08 28.91 19.25 42 29 c 2 1.49 3.21 4.21 5 6 l 4 3\"/><path stroke-linejoin=\"round\" stroke-linecap=\"round\" stroke-width=\"5\" stroke=\"darkblue\" fill=\"none\" d=\"M 361 115 c 0.4 0 15.32 -0.26 23 0 c 2.36 0.08 5.34 0.26 7 1 c 0.84 0.38 2.12 2.11 2 3 c -0.42 3.12 -2.88 7.9 -4 12 c -0.9 3.31 -1 6.84 -2 10 c -0.97 3.06 -2.92 5.89 -4 9 c -1.6 4.6 -2.3 9.42 -4 14 c -2.67 7.19 -5.29 17.61 -9 21 c -2.28 2.08 -10.3 1.13 -14 0 c -3.01 -0.92 -6.76 -4.41 -9 -7 c -1.76 -2.03 -3.58 -5.47 -4 -8 c -0.47 -2.85 -0.1 -8.27 1 -10 c 0.67 -1.05 3.98 -0.94 6 -1 c 9.94 -0.28 30 0 30 0\"/></svg>"));
	}
	
	
	/**
     * 将svg字符串转换为png
     * 
     * @param svgCode svg代码
     * @param pngFilePath 保存的路径
     * @throws TranscoderException svg代码异常
     * @throws IOException io错误
     */
    public static String convertToPngBase64(String svgCode) throws IOException,
            TranscoderException {
    	
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            convertToPng(svgCode, outputStream);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray()).replace("\n", "");
    }

    /**
     * 将svgCode转换成png文件，直接输出到流中
     * 
     * @param svgCode svg代码
     * @param outputStream 输出流
     * @throws TranscoderException 异常
     * @throws IOException io异常
     */
    public static void convertToPng(String svgCode, OutputStream outputStream)
            throws TranscoderException, IOException {
        try {
            byte[] bytes = svgCode.getBytes("utf-8");
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
            TranscoderOutput output = new TranscoderOutput(outputStream);
            t.transcode(input, output);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    
}
