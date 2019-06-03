package com.tianee.thirdparty.newcapec.quartzjob;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysCustomerProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TeeDeptTimer { // 定时任务类
	@Autowired
	private TeeDeptService deptService;

	@SuppressWarnings("unchecked")
	public void deptTimer() throws Exception {
		Connection conn = null;
		Connection conn1 = null;
		try {
			TeeDataSource dataSource = new TeeDataSource(); // 链接数据库直接写在定时任务里面
			dataSource.setPassWord(TeeSysCustomerProps.getString("MYSQL_PWD"));
			dataSource.setUserName(TeeSysCustomerProps.getString("MYSQL_NAME"));
			dataSource.setDriverClass(TeeSysCustomerProps
					.getString("DRIVER_CLASS"));
			dataSource.setUrl(TeeSysCustomerProps.getString("MYSQL_URL"));

			// 获取连接
			conn = TeeDbUtility.getConnection(dataSource);

			// 获取数据
			DbUtils dbUtils = new DbUtils(conn);

			TeeDataSource dataSource1 = new TeeDataSource(); // 链接数据库直接写在定时任务里面
																// (外部数据库连接)
			dataSource1
					.setPassWord(TeeSysCustomerProps.getString("MYSQL_PWD1"));
			dataSource1.setUserName(TeeSysCustomerProps
					.getString("MYSQL_NAME1"));
			dataSource1.setDriverClass(TeeSysCustomerProps
					.getString("DRIVER_CLASS1"));
			dataSource1.setUrl(TeeSysCustomerProps.getString("MYSQL_URL1"));
			// 获取连接
			conn1 = TeeDbUtility.getConnection(dataSource1);
			DbUtils dbUtils1 = new DbUtils(conn1);
			// 内库查询
			List<Map> renyList = dbUtils
					.queryToMapList("select * from department ");
			String cs = "";// 取参数
			String cs1 = "";// 取参数1
			String cs2 = "";
			String cs3 = "";
			if (renyList.size() == 0) {
				cs2 = "0";
			} else {

				for (Map mapu : renyList) {
					if (mapu.get("unique_id") != ""
							&& mapu.get("unique_id") != null) {
						cs += TeeStringUtil.getString(mapu.get("unique_id"))
								+ ",";
					} else {
						cs += "0" + ",";
					}
				}
				cs2 = cs.substring(0, cs.length() - 1);
			}

			// 外库查询数据
			List<Map> upwkList = dbUtils1
					.queryToMapList("select * from t_mid_zzjg ");
			if (upwkList.size() == 0) {
				cs3 = "0";
			} else {
				for (Map wc : upwkList) {
					if (wc.get("DWH") != "" && wc.get("DWH") != null) {
						cs1 += TeeStringUtil.getString(wc.get("DWH")) + ",";
					} else {
						cs1 += "0" + ",";
					}
				}
				cs3 = (String) cs1.subSequence(0, cs1.length() - 1);
			}
			// 外库查询数据
			@SuppressWarnings("rawtypes")
			List<Map> wklist = dbUtils1
					.queryToMapList("select * from t_mid_zzjg where DWH not in ("
							+ cs2 + ")" + "order by DWH asc");
			// 内库查询数据
			List<Map> nklist = dbUtils
					.queryToMapList("select * from department where UNIQUE_ID not in ("
							+ cs3 + ")" + "order by UNIQUE_ID  asc ");

			// 需要insert的数据
			List<String> l1 = new ArrayList();

			// 需要delete的数据
			List<String> l2 = new ArrayList();
			// 需要update的数据
			List<String> l3 = new ArrayList();

			// 从对方表里面找出咱表里没有的数据，并加到l1里面
			for (Map wk : wklist) {
				// 取出对方表里面的工号
				String wkId = TeeStringUtil.getString(wk.get("DWH"));
				boolean hasExists = false;
				for (Map nk : nklist) {
					// 取出咱表里面的uniqueId
					String nkId = TeeStringUtil.getString(nk.get("unique_id"));
					if (nklist.equals(nkId)) {
						hasExists = true;
						continue;
					}
				}

				if (!hasExists) {
					l1.add(wkId);
				}
			}

			TeeDepartmentModel model = new TeeDepartmentModel();
			for (String aa : l1) {
				List<Map> wkk = dbUtils1
						.queryToMapList("select * from t_mid_zzjg where t_mid_zzjg.DWH ="
								+ aa);
				List<Map> list = dbUtils
						.queryToMapList("select * from department where department.uuid="
								+ aa);// 根据当前 aa 数据查出department表里的数据
				for (Map w : wkk) {
					model.setUniqueId(String.valueOf(w.get("DWH")));// 取uuid
					model.setDeptName(String.valueOf(w.get("DWMC")));// 部门名称；
					if(w.get("LSDWH")==null){
	    				model.setDeptParentId(0);
					} else {
						model.setDeptParentId(Integer.parseInt(String.valueOf(w
								.get("LSDWH"))));
					}
					model.setDeptNo(11);
					model.setDeptType(1);
					// 如果list有值就是之前添加到 然后找到数据进行删除
					for (Map p : list) {
						if (p != null) {
							model.setUuid(Integer.parseInt(String.valueOf(p
									.get("uuid"))));
							deptService.deletedep(model);
						}
					}
//					deptService.addDeptService(model);// 添加借口
					// 修改条件uniqueid不等于null&&"" 就修改
					dbUtils.executeUpdate("update department set department.uuid=department.unique_id where unique_id!='' ");
					conn.commit();
					// 替换对应的dept_full_id
					int originalUuid = model.getUuid();
					String deptFullId = model.getDeptFullId();
					deptFullId = deptFullId.replace(
							String.valueOf(originalUuid),
							String.valueOf(model.getUniqueId()));
					dbUtils.executeUpdate(
							"update department set department.dept_full_id=? where department.uuid="
									+ model.getUniqueId(),
							new Object[] { deptFullId });
					conn.commit();
				}
			}

			// 删除
			for (Map nk : nklist) {
				String nId = TeeStringUtil.getString(nk.get("unique_id"));
				l2.add(nId);
			}
			for (String sc : l2) {
				if (sc != null && sc != "") {
					model.setUuid(Integer.parseInt(sc));
					deptService.deletedep(model);
				}
			}

			// 修改
			for (Map upwk : upwkList) {
				if (!TeeUtility.isNullorEmpty(upwk)) {
					String upwkId = TeeStringUtil.getString(upwk.get("DWH"));
					l3.add(upwkId);
				}

			}
			for (String xg : l3) {
				List<Map> wkkk = dbUtils1
						.queryToMapList("select * from t_mid_zzjg where t_mid_zzjg.DWH ="
								+ xg);
				for (Map w : wkkk) {
					model.setUniqueId(String.valueOf(w.get("DWH")));// 取uuid
					model.setDeptName(String.valueOf(w.get("DWMC")));// 部门名称；
					model.setDeptFunc(String.valueOf(w.get("DWMC")));
					if(w.get("LSDWH")==null){
	    				model.setDeptParentId(0);
					} else {
						model.setDeptParentId(Integer.parseInt(String.valueOf(w
								.get("LSDWH"))));
					}

					deptService.upDept(model);// 添加借口

				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			conn.close();
			conn1.close();
		}
	}
}
