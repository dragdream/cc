package com.tianee.oa.core.general.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


import com.tianee.oa.core.general.bean.TeePortlet;
import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.model.TeeSmsModel;

import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class TeePortletDao extends TeeBaseDao<TeePortlet>{
	
	public List<TeePortlet> getPortletList(){
		
		List<TeePortlet> list = find("from TeePortlet portlet where portlet.viewType = 1 order by portlet.sortNo", null);
		
		return list;
		
	}
	
	public List<TeePortlet> getPortletListAll(){
		
		List<TeePortlet> list = find("from TeePortlet portlet order by portlet.sortNo", null);
		
		return list;
		
	}

}
