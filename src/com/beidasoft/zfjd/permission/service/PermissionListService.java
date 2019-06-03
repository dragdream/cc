package com.beidasoft.zfjd.permission.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.permission.bean.PermissionItem;
import com.beidasoft.zfjd.permission.bean.PermissionList;
import com.beidasoft.zfjd.permission.dao.PermissionItemDao;
import com.beidasoft.zfjd.permission.dao.PermissionListDao;
import com.beidasoft.zfjd.permission.model.PermissionListModel;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * @ClassName: PermissionListService.java
 * @Description: 许可清单SERVICE层
 *
 * @author: mixue
 * @date: 2019年2月21日 下午3:48:01
 */
@Service
public class PermissionListService extends TeeBaseService {

    @Autowired
    private PermissionListDao permissionListDao;
    
    @Autowired
    private PermissionItemDao permissionItemDao;
    
    @Autowired
    private TblDepartmentDao departmentDao;
    
    /**
     * 
     * @Function: findListByPage
     * @Description: 许可清单分页查询
     *
     * @param dataGridModel
     * @param permissionListModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月21日 下午6:16:45
     */
    public List<PermissionListModel> findListByPage(TeeDataGridModel dataGridModel,
            PermissionListModel permissionListModel) {
        // TODO Auto-generated method stub
        List<PermissionListModel> permissionListModels = new ArrayList<>();
        List<PermissionList> permissionLists = permissionListDao.findListByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(), permissionListModel);
        for (PermissionList permissionList : permissionLists) {
            PermissionListModel sModel = copyAllProperties(permissionList);
            permissionListModels.add(sModel);
        }
        return permissionListModels;
    }

    /**
     * 
     * @Function: findListCountByPage
     * @Description: 许可清单查询总条数
     *
     * @param permissionListModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月21日 下午6:18:18
     */
    public Long findListCountByPage(PermissionListModel permissionListModel) {
        // TODO Auto-generated method stub
        return permissionListDao.findListCountByPage(permissionListModel);
    }

    /**
     * 
     * @Function: copyAllProperties
     * @Description: 许可信息处理
     *
     * @param permissionList
     * @return
     *
     * @author: mixue
     * @date: 2019年2月21日 下午6:22:03
     */
    private PermissionListModel copyAllProperties(PermissionList permissionList) {
        // TODO Auto-generated method stub
        PermissionListModel permissionListModel = new PermissionListModel();
        BeanUtils.copyProperties(permissionList, permissionListModel);
        // 从代码表获取行政许可相对人类型
        if(permissionList.getXkXdrType() != null){
            permissionListModel.setXkXdrTypeValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("XK_XDR_TYPE", permissionList.getXkXdrType()));
        }
        // 从代码表获取许可类别
        if(permissionList.getXkXklb() != null){
            permissionListModel.setXkXklbValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("XK_XKLB", permissionList.getXkXklb()));
        }
        // 从代码表获取自然人证件类型
        if(permissionList.getXkXdrZjlx() != null){
            permissionListModel.setXkXdrZjlxValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("XK_XDR_ZJLX", permissionList.getXkXdrZjlx()));
        }
        // 获取许可部门
        if(permissionList.getXkXkjg() != null){
            TblDepartmentInfo departmentInfo = departmentDao.get(permissionList.getXkXkjg());
            permissionListModel.setXkXkjgName("");
            if(departmentInfo != null)
                permissionListModel.setXkXkjgName(departmentInfo.getName());
        }
        // 获取许可事项
        if(permissionList.getXkItemId() != null){
            PermissionItem permissionItem = permissionItemDao.get(permissionList.getXkItemId());
            permissionListModel.setXkItemName("");
            if(permissionItem != null){
                permissionListModel.setXkItemName(permissionItem.getXkSxmc());
                permissionListModel.setXkItemId(permissionItem.getId());
                permissionListModel.setXkItemBm(permissionItem.getXkSxbm());
            }
        }
        //将许可决定日期转化为格式化的字符串
        if (permissionList.getXkJdrq() != null) {
            permissionListModel.setXkJdrqStr(TeeDateUtil.format(permissionList.getXkJdrq(), "yyyy-MM-dd"));    
        }else {
            permissionListModel.setXkJdrqStr("");
        }
        //将有效期起始日期转化为格式化的字符串
        if (permissionList.getXkYxqz() != null) {
            permissionListModel.setXkYxqzStr(TeeDateUtil.format(permissionList.getXkYxqz(), "yyyy-MM-dd"));    
        }else {
            permissionListModel.setXkYxqzStr("");
        }
        //将有效期终止日期转化为格式化的字符串
        if (permissionList.getXkYxqzi() != null) {
            permissionListModel.setXkYxqziStr(TeeDateUtil.format(permissionList.getXkYxqzi(), "yyyy-MM-dd"));    
        }else {
            permissionListModel.setXkYxqziStr("");
        }
        return permissionListModel;
    }

    /**
     * 
     * @Function: savePermissionList
     * @Description: 许可清单插入
     *
     * @param permissionListModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月22日 下午4:59:29
     */
    public TeeJson savePermissionList(PermissionListModel permissionListModel) {
        // TODO Auto-generated method stub
        TeeJson json = new TeeJson();
        try {
            permissionListModel.setId(UUID.randomUUID().toString());
            permissionListModel.setXkJdrq(TeeDateUtil.format(permissionListModel.getXkJdrqStr(),"yyyy-MM-dd"));
            permissionListModel.setXkYxqz(TeeDateUtil.format(permissionListModel.getXkYxqzStr(),"yyyy-MM-dd"));
            permissionListModel.setXkYxqzi(TeeDateUtil.format(permissionListModel.getXkYxqziStr(),"yyyy-MM-dd"));
            permissionListModel.setIsDelete(0);
            permissionListModel.setCreateTime(new Date());
            permissionListModel.setXkXdrType(permissionListModel.getXkXdrTypeValue());
            PermissionList permissionList = new PermissionList();
            BeanUtils.copyProperties(permissionListModel, permissionList);
            permissionListDao.save(permissionList);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            json.setRtState(false);
        }
        return json;
    }

    /**
     * 
     * @Function: updateOrDeleteById
     * @Description: 状态删除、修改
     *
     * @param permissionListModel
     *
     * @author: mixue
     * @date: 2019年2月25日 上午9:15:26
     */
    public void updateOrDeleteById(PermissionListModel permissionListModel) {
        // TODO Auto-generated method stub
        Map<String, Object> columns = new HashMap<String, Object>();
        Date date = new Date();
        if (!TeeUtility.isNullorEmpty(permissionListModel.getIsDelete())) {
            columns.put("IS_DELETE", permissionListModel.getIsDelete()); //删除状态
            columns.put("DELETE_TIME", date); //删除时间
        }else{
            // 许可决定日期
            columns.put("XK_JDRQ", TeeDateUtil.format(permissionListModel.getXkJdrqStr(),"yyyy-MM-dd"));
            // 有效期自
            columns.put("XK_YXQZ", TeeDateUtil.format(permissionListModel.getXkYxqzStr(),"yyyy-MM-dd"));
            // 有效期至
            columns.put("XK_YXQZI", TeeDateUtil.format(permissionListModel.getXkYxqziStr(),"yyyy-MM-dd"));
            // 行政相对人名称
            columns.put("XK_XDR_MC", permissionListModel.getXkXdrMc());
            // 行政相对人类型
            columns.put("XK_XDR_TYPE", permissionListModel.getXkXdrTypeValue());
            // 行政许可决定文书名称
            columns.put("XK_XKWS", permissionListModel.getXkXkws());
            // 行政许可决定文书号
            columns.put("XK_WSH", permissionListModel.getXkWsh());
            // 许可类别
            columns.put("XK_XKLB", permissionListModel.getXkXklb());
            // 许可证书名称
            columns.put("XK_XKZS", permissionListModel.getXkXkzs());
            // 许可编号
            columns.put("XK_XKBH", permissionListModel.getXkXkbh());
            // 许可内容
            columns.put("XK_NR", permissionListModel.getXkNr());
            // 许可部门
            columns.put("XK_XKJG", permissionListModel.getXkXkjg());
            // 许可部门统一社会信用代码
            columns.put("XK_XKJGDM", permissionListModel.getXkXkjgdm());
            // 当前状态
            columns.put("XK_ZT", permissionListModel.getXkZt());
            // 许可事项
            columns.put("XK_ITEM_ID", permissionListModel.getXkItemId());
            // 事项编码
            columns.put("XK_ITEM_BM", permissionListModel.getXkItemBm());
            if("1".equals(permissionListModel.getXkXdrTypeValue())){
                // 自然人证件类型
                columns.put("XK_XDR_ZJLX", permissionListModel.getXkXdrZjlx());
                // 证件号码
                columns.put("XK_XDR_ZJHM", permissionListModel.getXkXdrZjhm());
                // 统一社会信用代码
                columns.put("XK_XDR_SHXYM", "");
                // 工商注册号
                columns.put("XK_XDR_GSZC", "");
                // 组织机构代码
                columns.put("XK_XDR_ZZJG", "");
                // 税务登记号
                columns.put("XK_XDR_SWDJ", "");
                // 事业单位证书号
                columns.put("XK_XDR_SYDW", "");
                // 社会组织登记证号
                columns.put("XK_XDR_SHZZ", "");
                // 法定代表人
                columns.put("XK_FRDB", "");
                // 法定代表人身份证号
                columns.put("XK_FR_SFZH", "");
            }else if("2".equals(permissionListModel.getXkXdrTypeValue()) || "3".equals(permissionListModel.getXkXdrTypeValue()) || "4".equals(permissionListModel.getXkXdrType())){
                // 自然人证件类型
                columns.put("XK_XDR_ZJLX", "");
                // 证件号码
                columns.put("XK_XDR_ZJHM", "");
                // 统一社会信用代码
                columns.put("XK_XDR_SHXYM", permissionListModel.getXkXdrShxym());
                // 工商注册号
                columns.put("XK_XDR_GSZC", permissionListModel.getXkXdrGszc());
                // 组织机构代码
                columns.put("XK_XDR_ZZJG", permissionListModel.getXkXdrZzjg());
                // 税务登记号
                columns.put("XK_XDR_SWDJ", permissionListModel.getXkXdrSwdj());
                // 事业单位证书号
                columns.put("XK_XDR_SYDW", permissionListModel.getXkXdrSydw());
                // 社会组织登记证号
                columns.put("XK_XDR_SHZZ", permissionListModel.getXkXdrShzz());
                // 法定代表人
                columns.put("XK_FRDB", permissionListModel.getXkFrdb());
                // 法定代表人身份证号
                columns.put("XK_FR_SFZH", permissionListModel.getXkFrSfzh());
            }
            if("4".equals(permissionListModel.getXkXdrTypeValue())){
                // 事业单位证书号
                columns.put("XK_XDR_SYDW", "");
                // 社会组织登记证号
                columns.put("XK_XDR_SHZZ", "");
            }
            
        }
        columns.put("UPDATE_TIME", date); //更新时间
        columns.put("UPDATE_PERSON_ID", permissionListModel.getUpdatePersonId());
        columns.put("UPDATE_PERSON_NAME", permissionListModel.getUpdatePersonName());
        permissionListDao.updateOrDeleteById(columns, permissionListModel.getId());
    }

    /**
     * 
     * @Function: getPermissionListById
     * @Description: 通过ID获取单个许可清单
     *
     * @param id
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 上午1:16:35
     */
    public PermissionListModel getPermissionListById(String id) {
        // TODO Auto-generated method stub
        PermissionList permissionList = permissionListDao.get(id);
        PermissionListModel permissionListModel = new PermissionListModel();
        permissionListModel = copyAllProperties(permissionList);
        return permissionListModel;
    }

    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 许可清单分页查询（带数据权限）
     *
     * @param dataGridModel
     * @param permissionListModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 上午11:46:28
     */
    public List<PermissionListModel> findListByPageRoles(TeeDataGridModel dataGridModel,
            PermissionListModel permissionListModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        List<PermissionListModel> permissionListModels = new ArrayList<>();
        List<PermissionList> permissionLists = permissionListDao.findListByPageRoles(dataGridModel.getFirstResult(),
                dataGridModel.getRows(), permissionListModel, orgCtrlInfoModel);
        for (PermissionList permissionList : permissionLists) {
            PermissionListModel pModel = copyAllProperties(permissionList);
            permissionListModels.add(pModel);
        }
        return permissionListModels;
    }

    public Long findListCountByPageRoles(PermissionListModel permissionListModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        return permissionListDao.findListCountByPageRoles(permissionListModel, orgCtrlInfoModel);
    }

}