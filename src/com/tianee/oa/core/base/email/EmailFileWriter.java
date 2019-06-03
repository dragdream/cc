package com.tianee.oa.core.base.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.util.StringUtils;
public class EmailFileWriter {

	public static void main(String[] args) {
		
	}

	// 定义发件人、收件人、SMTP服务器、用户名、密码、主题、内容等
		private String displayName;//显示名称

		private String from;//发件人的地址

		private String to;

		private String cc;

		private String bcc;

		private String server;

		private String subject;

		private String content;
		/**
		 * 用于保存发送附件的文件名的集合（<code>new String[]{文件名,显示名称}</code>）
		 */
		private Vector<String[]> attachList = new Vector<String[]>();

		private String contentType = "text/html";

		private String charset = "utf-8";

		private int port = 25;//smtp

		private Date sentDate;

		public EmailFileWriter() {

		}

		/**
		 * 用于保存发送附件的文件名的集合（<code>new String[]{文件名,显示名称}</code>）
		 */
		public void addFile(String[] filename) {
			attachList.add(filename);
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public String getCharset() {
			return charset;
		}

		public void setCharset(String charset) {
			this.charset = charset;
		}

		/**
		 * 设置SMTP服务器地址
		 */
		public void setServer(String smtpServer) {
			this.server = smtpServer;
		}

		/**
		 * 设置发件人的地址
		 */
		public void setFrom(String from) {
			this.from = from;
		}

		/**
		 * 设置显示的名称
		 */
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		/**
		 * 设置接收者
		 */
		public void setTo(String to) {
			this.to = to;
		}

		public String getCc() {
			return cc;
		}

		public void setCc(String cc) {
			this.cc = cc;
		}

		public String getBcc() {
			return bcc;
		}

		public void setBcc(String bcc) {
			this.bcc = bcc;
		}

		/**
		 * 设置主题
		 */
		public void setSubject(String subject) {
			this.subject = subject;
		}

		/**
		 * 设置主体内容
		 */
		public void setContent(String content) {
			this.content = content;
		}

		public Date getSentDate() {
			return sentDate;
		}

		public void setSentDate(Date sentDate) {
			this.sentDate = sentDate;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		/**
		 * 将email写至文件
		 * 
		 * @throws IOException
		 * @throws FileNotFoundException
		 * @throws MessagingException
		 */
		public  void writeTo(String filename) throws FileNotFoundException,
				IOException, MessagingException {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", server);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "false");

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);

			Message msg = new MimeMessage(session);
			from=MimeUtility.encodeText(from,MimeUtility.mimeCharset("utf-8"), null);
			displayName=MimeUtility.encodeText(displayName,MimeUtility.mimeCharset("utf-8"), null);
			Address from_address = new InternetAddress(from, displayName);
			msg.setFrom(from_address);

			to=MimeUtility.encodeText(to,MimeUtility.mimeCharset("utf-8"), null);
			InternetAddress[] addressTo = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			if (StringUtils.hasLength(cc)) {
				InternetAddress[] addressCc = { new InternetAddress(cc) };
				msg.setRecipients(Message.RecipientType.CC, addressCc);
			}

			if (StringUtils.hasLength(bcc)) {
				InternetAddress[] addressBcc = { new InternetAddress(bcc) };
				msg.setRecipients(Message.RecipientType.BCC, addressBcc);
			}

			msg.setSubject(subject);
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(content.toString(), getContentType() + "; charset="
					+ getCharset());
			mp.addBodyPart(mbp);
			if (!attachList.isEmpty()) {// 有附件
				for (String[] file : attachList) {
					mbp = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(file[0]); // 得到数据源
					mbp.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
					String dspName = file.length < 2 ? fds.getName() : file[1];
					mbp.setFileName(MimeUtility.encodeText(dspName, getCharset(),
							"B")); // 得到文件名同样至入BodyPart
					mp.addBodyPart(mbp);
				}
				attachList.removeAllElements();
			}
			msg.setContent(mp); // Multipart加入到信件
			msg.setSentDate(getSentDate()); // 设置信件头的发送日期
			msg.saveChanges();
			
			// 写至文件
			FileOutputStream out=new FileOutputStream(new File(filename));
			msg.writeTo(out);
			out.close();
		}
}
