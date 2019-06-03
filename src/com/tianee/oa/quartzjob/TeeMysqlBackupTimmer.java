package com.tianee.oa.quartzjob;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.dao.TeeMysqlBackupDao;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMysqlBackupTimmer extends TeeBaseService{
	@Autowired
	TeeMysqlBackupDao backupDao;
	public void doTimmer(){
		try {
			if(TeeSysProps.getProps()==null){
				return;
			}
			
			String dataName = TeeSysProps.getProps().getProperty("dialect");
			if(dataName.equals("mysql")){
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
				Calendar cl = Calendar.getInstance();
				String mysqlBinPath = TeeSysProps.getRootPath()+"../mysql/bin/";
				File dir = new File(mysqlBinPath);
				if(dir.exists()){
					//String mysqlBinPath = "C:\\Program Files (x86)\\MySQL\\MySQL Server 5.5\\bin\\mysqldump";
					String mysqlPassword=TeeSysProps.getProps().getProperty("db_password");
					String mysqlUser=TeeSysProps.getProps().getProperty("db_username");
					String backupName="oaop"+sf.format(cl.getTime())+".sql";
					String backupPath=TeeSysProps.getRootPath()+"../backup/";
					String mysqlBackupDir = TeeSysProps.getProps().getProperty("MYSQL_BACKUP_DIR");
					if(!TeeUtility.isNullorEmpty(mysqlBackupDir)){
						backupPath = mysqlBackupDir;
					}
					File file = new File(backupPath);
					if(!file.exists()){
						file.mkdirs();
					}
					String command = "cmd /c \""+mysqlBinPath+"mysqldump\" --opt -u "+mysqlUser+" --password="+mysqlPassword+" oaop > "+backupPath+backupName;
					Process p = Runtime.getRuntime().exec(command);
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
					String line = null;
					while((line=bufferedReader.readLine())!=null){
						System.out.println(line);
					}
					/**
					 * 将信息保存到数据库中
					 */
					Calendar cur = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String path = backupPath+backupName;
					File backupFile = new File(path);
					String sql="insert into mysql_backup(time,path)values('"+sdf.format(cur.getTime())+"','"+path+"')";
					backupDao.exectSql(sql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
