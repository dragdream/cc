package com.tianee.test.aopexcel.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.test.aopexcel.service.TeeExportService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.util.file.TeeAOPExcleUtil;
import com.tianee.webframe.util.file.TeeCSVUtil;

@Controller
@RequestMapping("/exportController")
public class TeeExportController extends BaseController {
	@Autowired
	private TeeExportService exportService;

	/**
	 * 导出Excle文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExcle")
	public String export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream ops = null;
		try {

			String fileName = URLEncoder.encode("test.xls", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			ops = response.getOutputStream();
			ArrayList<TeeDataRecord> dbL = exportService.getDbRecord();
			TeeAOPExcleUtil.writeExc(ops, dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ops.close();
		}
		return null;
	}

	/**
	 * 导出CSV文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportCsv")
	public String exportCsv(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("test.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

			ArrayList<TeeDataRecord> dbL = exportService.getDbRecord();
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}

	/**
	 * 导入Excle文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importExcle")
	public String importExcle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("GBK");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream is = null;
		try {

			List<MultipartFile> files = multipartRequest.getFiles("file");
			for (MultipartFile file : files) {
				if (file.isEmpty())
					continue;
				is = file.getInputStream();
				break;
				// list.add(attach);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		if (is == null) {
			return "";
		}
		ArrayList<TeeDataRecord> dbL = TeeAOPExcleUtil.readExc(is, true);
		for (int i = 0; i < dbL.size(); i++) {
			TeeDataRecord ss = dbL.get(i);
			System.out.println(ss);
		}
		return "";
	}

	/**
	 * 导入CSV文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importCsv")
	public String importCsv(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("GBK");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream is = null;
		try {

			List<MultipartFile> files = multipartRequest.getFiles("file");
			for (MultipartFile file : files) {
				if (file.isEmpty())
					continue;
				is = file.getInputStream();
				break;
				// list.add(attach);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		if (is == null) {
			return "";
		}
		ArrayList<TeeDataRecord> dbL = TeeCSVUtil.CVSReader(is, "GBK");
		for (int i = 0; i < dbL.size(); i++) {
			TeeDataRecord ss = dbL.get(i);
			System.out.println(ss);
		}
		return "";
	}

}
