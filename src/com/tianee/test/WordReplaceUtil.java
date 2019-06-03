package com.tianee.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordReplaceUtil {
    public static Map<String, String> params = new HashMap<String, String>();
    public static void main(String[] args) {
        String readPath = "d:\\1.docx";
        String writePath = "d:\\2.doc";
        initParams();
            try {
                XWPFDocument doc = generateWord(params,readPath);
//                replaceFooterAndHeader(doc);
                doc.write(new FileOutputStream(new File(writePath)));
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        System.out.println("End");
    }
    //获取文件类型
    public static String getFileSufix(String fileName){
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }
    //初始化替换数据
    private static void initParams() {
        params.put("${招标机构名称}", "中国石油华北分公司北京集团");
        params.put("${招标机构名称英文}", "WCFGKEBS");
        params.put("${类型}", "中标成功");
        params.put("${通知书编号}", "0924");
        params.put("${中标公司名称}", "英伟达");
        params.put("${项目名称}", "处理器");
        params.put("${标段名称}", "CLQ01");
        params.put("${项目编号}", "Apple01");
        params.put("${标段编号}", "KL003");
        params.put("${金额}", "13000");
        params.put("${招标机构名称", "易招标");
        params.put("${招标公司名称}", "德玛西亚");
        params.put("${日期}", DateFormat.getDateInstance().format(new Date()));
    }
    
    public static void replaceFooterAndHeader(XWPFDocument doc){
        List<XWPFParagraph> footers = doc.getHeaderFooterPolicy().getDefaultFooter().getParagraphs();
        List<XWPFParagraph> headers = doc.getHeaderFooterPolicy().getDefaultHeader().getParagraphs();
        //处理页脚
        for (XWPFParagraph paragraph : footers) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                if(StringUtils.isNotEmpty(text)){
                    for(Entry<String, String> entry : params.entrySet()){
                        String key = entry.getKey();
                        if(text.indexOf(key) != -1){
                            Object value = entry.getValue();
                            if(value instanceof String){
                                text = text.replace(key, value.toString());
                                run.setText(text,0);
                            }
                        }
                    }
                }
            }
        }
        //处理页眉
        for (XWPFParagraph paragraph : headers) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                if(StringUtils.isNotEmpty(text)){
                    for(Entry<String, String> entry : params.entrySet()){
                        String key = entry.getKey();
                        if(text.indexOf(key) != -1){
                            Object value = entry.getValue();
                            if(value instanceof String){
                                text = text.replace(key, value.toString());
                                run.setText(text,0);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static XWPFDocument generateWord(Map<String, String> param, String filePath) {
        XWPFDocument doc = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(filePath);
            doc = new XWPFDocument(pack);
            if (param != null && param.size() > 0) {
//                //处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);
                //处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                            processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
    
    public static void processParagraphs(List<XWPFParagraph> paragraphList,Map<String, String> param,XWPFDocument doc){
        if(paragraphList != null && paragraphList.size() > 0){
            for(XWPFParagraph paragraph:paragraphList){
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if(text != null){
                        for (Entry<String, String> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if(text.indexOf(key) != -1){
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                    run.setText(text,0);
                                }
                            }
                        }
//                        if(isSetText){
//                            run.setText(text,0);
//                        }
                    }
                }
            }
        }
    }
}
