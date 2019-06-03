package com.tianee.oa.core.base.fileNetdisk.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.tianee.oa.oaconst.TeeConst;

public class TeeApacheZipUtil {

    /**
     * 设置缓冲值
     */
    static final int BUFFER = 8192;

    private static final String ALGORITHM = "PBEWithMD5AndDES";

    /**
     * 把指定目录压缩成zip文件
     * 
     * @param srcFile
     *            被压缩的文件或者文件包完全路径名称
     * @param destFile
     *            输出的压缩文件完全路径名称
     * 
     * 
     * @throws Exception
     */
    public static void doZip(String srcFile, String destFile) throws Exception {
        doZip(srcFile, destFile, null, null, 0, 0);
    }

    /**
     * 把指定目录压缩成zip文件并输出到流中
     * @param srcFile 被压缩的文件或者文件包完全路径名称
     * @param outputStream  输出到流
     * @throws Exception
     */
    public static void doZip(String srcFile, OutputStream outputStream) throws Exception {
        ZipOutputStream out = null;
        try {
            
            out = new ZipOutputStream(outputStream);
            out.setEncoding("GBK");
            doZip(srcFile, out, null, null, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (out != null) {
                out.close();
            }
        }
    }
    
    
    /**
     * 把指定目录压缩成zip文件
     * 
     * @param srcFile
     *            被压缩的文件或者文件包完全路径名称
     * @param destFile
     *            输出的压缩文件完全路径名称
     * 
     * 
     * @throws Exception
     */
    private static void doZip(String srcFile, String destFile, char[] passWord, byte[] salt, int itCnt, int mode) throws Exception {

        ZipOutputStream out = null;
        try {
            File file = new File(destFile);
            File outDir = file.getParentFile();
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new ZipOutputStream(new FileOutputStream(destFile));
            out.setEncoding("GBK");
            doZip(srcFile, out, passWord, salt, itCnt, mode);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /*
     * 把指定目录压缩成zip文件
     * 
     * @param srcFile 被压缩的文件或者文件包完全路径名称
     * 
     * @param destFile 输出的压缩文件完全路径名称
     * 
     * @throws Exception
     */
    private static void doZip(String srcFile, ZipOutputStream out, char[] passWord, byte[] salt, int itCnt, int mode) throws Exception {

        try {
            File file = new File(srcFile);
            if (!file.exists()) {
                return;
            }
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                putFile2Zip(fileList[i], out, fileList[i].getName(), passWord, salt, itCnt, mode);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 在zip输出流中增加文件
     * 
     * @param srcFile
     * @param out
     * @throws Exception
     */
    private static void putFile2Zip(File srcFile, ZipOutputStream out, String base, char[] passWord, byte[] salt, int itCnt, int mode) throws Exception {
        if (srcFile.isFile()) {
            InputStream in = null;
            try {
                ZipEntry entry = new ZipEntry(base);
                entry.setMethod(ZipEntry.DEFLATED);
                out.putNextEntry(entry);
                in = new FileInputStream(srcFile);
                if (passWord != null) {
                    // in = buildPassWordInputStream(passWord, salt, itCnt,
                    // mode, in);
                }
                byte[] buff = new byte[TeeApacheZipUtil.BUFFER];
                int readLength = 0;
                while ((readLength = in.read(buff)) > 0) {
                    out.write(buff, 0, readLength);
                    out.flush();
                }
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception ex) {
                }
            }
        } else if (srcFile.isDirectory()) {
            ZipEntry entry = new ZipEntry(base + "/");
            out.putNextEntry(entry);
            base = base.length() == 0 ? "" : base + "/";
            File[] fileList = srcFile.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                putFile2Zip(fileList[i], out, base + fileList[i].getName(), passWord, salt, itCnt, mode);
            }
        }
    }
    
    /**
     * 获取磁盘空间大小（保留2位小数）
     * 
     * @date 2012-11-13
     * @author
     * @param size
     * @return
     */
    public static String getDisckSize(long size) {
        DecimalFormat df = new DecimalFormat("####.##");
        String result = new String();
        if (size >= 0 && size < 1024) {
            result = String.valueOf(size);
            result += "B";
        } else if (size >= 1024 && size < 1024 * 1024) {
            // result = String.valueOf((float)size / 1024);
            result = df.format((double) size / 1024);
            result += "KB";
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
            // result = String.valueOf(size / 1024*1024);
            result = df.format((double) size / (1024 * 1024));
            result += "M";
        } else if (size >= 1024 * 1024 * 1024) {
            // result = String.valueOf(size / 1024*1024*1024);
            result = df.format((double) size / (1024 * 1024 * 1024));
            result += "G";
        } else {
            result = "errorData";
        }
        return result;
    }
    

    /**
     * 拷贝文件
     * 
     * @date 2014-2-16
     * @author
     * @param fromFile
     *            源文件
     * @param toFile
     *            目标文件
     * @throws Exception
     */
    public static void copyFile(String fromFile, String toFile) throws Exception {
        try {
            copyFile0(fromFile, toFile, null, false, false);
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 拷贝文件
     * 
     * @date 2014-2-16
     * @author
     * @param fromFile
     *            源文件
     * @param toFile
     *            目标文件
     * @param msrgList
     *            输出信息
     * @param isDelete
     *            拷贝后是否删除
     * @param isSetModifyTime
     *            是否设置修改时间
     * @throws Exception
     */
    private static void copyFile0(String fromFile, String toFile, List msrgList, boolean isDelete, boolean isSetModifyTime) throws Exception {
        InputStream ins = null;
        OutputStream outs = null;
        File inFile = null;
        try {
            inFile = new File(fromFile);
            File outFile = new File(toFile);
            if (!inFile.exists()) {
                return;
            }
            File outDir = outFile.getParentFile();
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            if (!outFile.canWrite()) {
                outFile.setWritable(true);
            }
            ins = new FileInputStream(inFile);
            outs = new FileOutputStream(outFile);

            // 输出消息列表
            if (msrgList != null) {
                msrgList.add("Copy " + fromFile + " to " + toFile);
            }

            byte[] buff = new byte[TeeConst.K];
            int readLength = 0;
            while ((readLength = ins.read(buff)) > 0) {
                outs.write(buff, 0, readLength);
                outs.flush();
            }
            if (isSetModifyTime) {
                outFile.setLastModified(inFile.lastModified());
            }
            if (isDelete && inFile.exists()) {
                // 输出消息列表
                if (msrgList != null) {
                    msrgList.add(fromFile + " is deleted after being copyed");
                }
                inFile.delete();
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ins != null) {
                ins.close();
            }
            if (outs != null) {
                outs.close();
            }
        }
    }
    
    
    
    
    
    

}
