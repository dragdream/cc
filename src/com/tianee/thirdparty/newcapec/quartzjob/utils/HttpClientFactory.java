package com.tianee.thirdparty.newcapec.quartzjob.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * HttpClient工具类
 * 
 * @describe:
 * 
 * @author aobao
 * 
 * @version V1.0
 * 
 * @date:Dec 4, 2012
 * 
 */
public class HttpClientFactory {
	public static final String ERROR = "ERROR";

	private static HttpClientFactory inst = null;

	private HttpClient client = null;

	private int timeout = 2000;

	private HttpClientFactory() {
		MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
		manager.getParams().setConnectionTimeout(timeout);
		client = new HttpClient(manager);
		client.getParams().setHttpElementCharset("UTF-8");
		client.getParams().setCredentialCharset("UTF-8");
		client.getParams().setContentCharset("UTF-8");
	}

	public synchronized static HttpClientFactory getInstance() {
		if (inst == null) {
			inst = new HttpClientFactory();
		}
		return inst;
	}

	public int goExeGetStatus(String url) {
		int statusCode = HttpStatus.SC_BAD_REQUEST;

		if (url == null || url.isEmpty()) {
			return statusCode;
		}

		// Create a method instance.
		GetMethod method = new GetMethod(url);

		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));

		try {
			statusCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return statusCode;
	}

	public String goExeGet(String url, int time) {

		String result = "";
		if (url == null || url.isEmpty()) {
			return result;
		}

		// Create a method instance.
		GetMethod method = new GetMethod(url);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, time);

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
				return ERROR;
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();
			if (responseBody == null || responseBody.length == 0) {
				return result;
			}

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			String value = new String(responseBody, "UTF-8");
			return value;

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return result;
	}

	public String goExeGet(String url) {
		return goExeGet(url, timeout);
	}

	public String goExePost(String url) {
		String result = "";
		if (url == null || url.isEmpty()) {
			return result;
		}

		// Create a method instance.
		PostMethod method = new PostMethod(url);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
				return ERROR;
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();
			if (responseBody == null || responseBody.length == 0) {
				return result;
			}

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			String value = new String(responseBody, "UTF-8");
			return value;

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return result;
	}

	public String goExePost(String url, Map<String, String> params) {
		String result = "";
		if (url == null || url.isEmpty()) {
			return result;
		}

		// Create a method instance.
		PostMethod method = new PostMethod(url);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		// 参数名值对
		List<NameValuePair> kvList = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			String value = params.get(key);
			NameValuePair kv = new NameValuePair(key, value);
			kvList.add(kv);
		}

		method.setRequestBody(kvList.toArray(new NameValuePair[] {}));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
				return ERROR;
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();
			if (responseBody == null || responseBody.length == 0) {
				return result;
			}

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			String value = new String(responseBody, "UTF-8");
			return value;

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return result;
	}

	public int goExePostStatus(String url, Map<String, String> params) {
		int statusCode = HttpStatus.SC_BAD_REQUEST;

		if (url == null || url.isEmpty()) {
			return statusCode;
		}

		// Create a method instance.
		PostMethod method = new PostMethod(url);

		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		// 参数名值对
		List<NameValuePair> kvList = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			String value = params.get(key);
			NameValuePair kv = new NameValuePair(key, value);
			kvList.add(kv);
		}

		method.setRequestBody(kvList.toArray(new NameValuePair[] {}));

		try {
			statusCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return statusCode;
	}

	public String goExePostFile(String host, File file, String id) {
		String result = null;

		PostMethod method = new PostMethod(host);
		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000);

		int status = 0;
		try {
			Part[] parts = { new FilePart("imgFile", file),
					new StringPart("id", id) };
			method.setRequestEntity(new MultipartRequestEntity(parts, method
					.getParams()));

			status = client.executeMethod(method);

			// TODO
			if (status == HttpStatus.SC_OK
					|| status == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 获取响应结果,解析反馈回来的URL地址
				Header locatoin = method.getResponseHeader("Location");
				if (locatoin != null) {
					String info = method.getResponseHeader("Location")
							.getValue();
					String[] sub = info.split("http:");
					if (sub.length >= 3) {
						result = "http:"
								+ sub[2].substring(0, sub[2].indexOf("\""));
						result = result.replace("\\u003d", "=");
					}
					//System.out.println("result:" + result);
				}
			} else {
				//System.out.println("上传图片出错,返回状态码是" + status);
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}

		return result;
	}

	/**
	 * 文件下载
	 * 
	 * @param downloadedFile
	 *            下载路径
	 * @param downloadUrl
	 *            文件存放的url路径
	 */
	public int downloadFile(File downloadedFile, String downloadUrl,
			Map<String, String> params) {
		int statusCode = HttpStatus.SC_BAD_REQUEST;

		if (downloadUrl == null || downloadUrl.isEmpty()) {
			return statusCode;
		}

		// Create a method instance.
		PostMethod method = new PostMethod(downloadUrl);
		FileOutputStream out = null;
		InputStream stream = null;

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		// 参数名值对
		List<NameValuePair> kvList = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			String value = params.get(key);
			NameValuePair kv = new NameValuePair(key, value);
			kvList.add(kv);
		}

		method.setRequestBody(kvList.toArray(new NameValuePair[] {}));

		try {
			// Execute the method.
			statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
				return statusCode;
			}

			stream = method.getResponseBodyAsStream();
			// long size = method.getResponseContentLength();
			// ByteFormat formater = new ByteFormat();

			downloadedFile.getParentFile().mkdirs();

			out = new FileOutputStream(downloadedFile);
			copy(stream, out);

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();

			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return statusCode;
	}

	private void copy(final InputStream in, final OutputStream out) {
		try {
			while (true) {

				final byte[] buffer = new byte[4096];
				int bytesRead = in.read(buffer);
				if (bytesRead < 0) {
					return;
				}
				out.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
		}
	}

}
