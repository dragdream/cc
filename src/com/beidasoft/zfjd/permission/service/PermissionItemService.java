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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.permission.bean.PermissionItem;
import com.beidasoft.zfjd.permission.bean.PermissionItemGist;
import com.beidasoft.zfjd.permission.bean.PermissionItemPower;
import com.beidasoft.zfjd.permission.dao.PermissionItemDao;
import com.beidasoft.zfjd.permission.dao.PermissionItemGistDao;
import com.beidasoft.zfjd.permission.dao.PermissionItemPowerDao;
import com.beidasoft.zfjd.permission.model.PermissionItemModel;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * @ClassName: PermissionItemService.java
 * @Description: 许可事项SERVICE层
 *
 * @author: mixue
 * @date: 2019年2月26日 下午8:26:58
 */
@Service
public class PermissionItemService {

    @Autowired
    private PermissionItemDao permissionItemDao;

    @Autowired
    private TblDepartmentDao departmentDao;

    @Autowired
    private PermissionItemGistDao permissionItemGistDao;

    @Autowired
    private PermissionItemPowerDao permissionItemPowerDao;

    /**
     * 
     * @Function: findListByPage
     * @Description: 许可事项分页查询
     *
     * @param dataGridModel
     * @param permissionItemModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午9:00:54
     */
    public List<PermissionItemModel> findListByPage(TeeDataGridModel dataGridModel,
            PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        List<PermissionItemModel> permissionItemModels = new ArrayList<>();
        List<PermissionItem> permissionItems = permissionItemDao.findListByPage(dataGridModel.getFirstResult(),
                dataGridModel.getRows(), permissionItemModel);
        for (PermissionItem permissionItem : permissionItems) {
            PermissionItemModel sModel = copyAllProperties(permissionItem);
            permissionItemModels.add(sModel);
        }
        return permissionItemModels;
    }

    /**
     * 
     * @Function: findListCountByPage
     * @Description: 许可事项查询总条数
     *
     * @param permissionItemModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午9:01:12
     */
    public Long findListCountByPage(PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        return permissionItemDao.findListCountByPage(permissionItemModel);
    }

    /**
     * 
     * @Function: getPermissionItemById
     * @Description: 通过ID获取单个许可事项
     *
     * @param id
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午9:01:50
     */
    public PermissionItemModel getPermissionItemById(String id) {
        // TODO Auto-generated method stub
        PermissionItem permissionItem = permissionItemDao.get(id);
        PermissionItemModel permissionItemModel = new PermissionItemModel();
        if (permissionItem != null)
            permissionItemModel = copyAllProperties(permissionItem);
        return permissionItemModel;
    }

    /**
     * 
     * @Function: copyAllProperties
     * @Description: 事项信息处理
     *
     * @param permissionItem
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午9:02:05
     */
    private PermissionItemModel copyAllProperties(PermissionItem permissionItem) {
        // TODO Auto-generated method stub
        PermissionItemModel permissionItemModel = new PermissionItemModel();
        BeanUtils.copyProperties(permissionItem, permissionItemModel);
        // 从代码表获取办件类型
        if (permissionItem.getXkBjlx() != null) {
            permissionItemModel.setXkBjlxValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("XK_BJLX", permissionItem.getXkBjlx()));
        }
        // 从代码表获取事项结果类型
        if (permissionItem.getXkSxjglx() != null) {
            permissionItemModel.setXkSxjglxValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("XK_SXJGLX", permissionItem.getXkSxjglx()));
        }
        // 从代码表获取申请人主体类型
        if (permissionItem.getXkXdrType() != null) {
            permissionItemModel.setXkXdrTypeValue(TeeSysCodeManager.getChildSysCodeNameCodeNo("XK_XDR_TYPE", permissionItem.getXkXdrType()));
        }
        // 获取许可部门
        if (permissionItem.getXkGsdw() != null) {
            TblDepartmentInfo departmentInfo = departmentDao.get(permissionItem.getXkGsdw());
            permissionItemModel.setXkGsdwName("");
            if (departmentInfo != null)
                permissionItemModel.setXkGsdwName(departmentInfo.getName());
        }
        // 许可职权处理
        if (permissionItem.getPermissionItemPowers() != null && permissionItem.getPermissionItemPowers().size() > 0) {
            StringBuffer powerLists = new StringBuffer("");
            for (int i = 0; i < permissionItem.getPermissionItemPowers().size(); i++) {
                powerLists.append(permissionItem.getPermissionItemPowers().get(i).getPowerId() + ",");
            }
            permissionItemModel.setPowerJsonStr(powerLists.substring(0, powerLists.length() - 1));
        }

        // 设定依据处理
        if (permissionItem.getPermissionItemGists() != null && permissionItem.getPermissionItemGists().size() > 0) {
            StringBuffer gistLists = new StringBuffer("");
            for (int i = 0; i < permissionItem.getPermissionItemGists().size(); i++) {
                gistLists.append(permissionItem.getPermissionItemGists().get(i).getGistId() + ",");
            }
            permissionItemModel.setGistJsonStr(gistLists.substring(0, gistLists.length() - 1));
        }
        return permissionItemModel;
    }

    /**
     * 
     * @Function: updateOrDeleteById
     * @Description: 修改、删除
     *
     * @param permissionItemModel
     *
     * @author: mixue
     * @date: 2019年2月27日 上午10:16:42
     */
    public void updateOrDeleteById(PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        Map<String, Object> columns = new HashMap<String, Object>();
        Date date = new Date();
        if (!TeeUtility.isNullorEmpty(permissionItemModel.getIsDelete())) {
            columns.put("IS_DELETE", permissionItemModel.getIsDelete()); // 删除状态
            columns.put("DELETE_TIME", date); // 删除时间
            columns.put("UPDATE_TIME", date); // 更新时间
            columns.put("UPDATE_PERSON_ID", permissionItemModel.getUpdatePersonId());
            columns.put("UPDATE_PERSON_NAME", permissionItemModel.getUpdatePersonName());
            permissionItemDao.updateOrDeleteById(columns, permissionItemModel.getId());
        } else {
            // 事项编码
            columns.put("XK_SXBM", permissionItemModel.getXkSxbm());
            // 事项名称
            columns.put("XK_SXMC", permissionItemModel.getXkSxmc());
            // 大项名称
            columns.put("XK_DXMC", permissionItemModel.getXkDxmc());
            // 归属单位
            columns.put("XK_GSDW", permissionItemModel.getXkGsdw());
            // 办件类型
            columns.put("XK_BJLX", permissionItemModel.getXkBjlx());
            // 法定期限
            columns.put("XK_FDQX", permissionItemModel.getXkFdqx());
            // 承诺期限
            columns.put("XK_CNQX", permissionItemModel.getXkCnqx());
            // 受理标准
            columns.put("XK_SLBZ", permissionItemModel.getXkSlbz());
            // 审批流程
            columns.put("XK_SPLC", permissionItemModel.getXkSplc());
            // 审查内容及标准
            columns.put("XK_SCNRBZ", permissionItemModel.getXkScnrbz());
            // 事项结果类型
            columns.put("XK_SXJGLX", permissionItemModel.getXkSxjglx());
            // 申请人主体类型
            columns.put("XK_XDR_TYPE", permissionItemModel.getXkXdrType());

            columns.put("UPDATE_TIME", date); // 更新时间
            columns.put("UPDATE_PERSON_ID", permissionItemModel.getUpdatePersonId());
            columns.put("UPDATE_PERSON_NAME", permissionItemModel.getUpdatePersonName());
            permissionItemDao.updateOrDeleteById(columns, permissionItemModel.getId());

            PermissionItem permissionItem = new PermissionItem();
            BeanUtils.copyProperties(permissionItemModel, permissionItem);
            // 许可职权处理
            if (permissionItemModel.getPowerList() != null && permissionItemModel.getPowerList().size() > 0) {
                List<PermissionItemPower> permissionItemPowers = new ArrayList<>();
                PermissionItemPower permissionItemPower = null;
                for (int i = 0; i < permissionItemModel.getPowerList().size(); i++) {
                    permissionItemPower = new PermissionItemPower();
                    BeanUtils.copyProperties(permissionItemModel.getPowerList().get(i), permissionItemPower);
                    permissionItemPower.setPermissionItem(permissionItem);
                    permissionItemPowers.add(permissionItemPower);
                }
                permissionItem.setPermissionItemPowers(permissionItemPowers);
            }

            // 设定依据处理
            if (permissionItemModel.getGistList() != null && permissionItemModel.getGistList().size() > 0) {
                List<PermissionItemGist> permissionItemGists = new ArrayList<>();
                PermissionItemGist permissionItemGist = null;
                for (int i = 0; i < permissionItemModel.getGistList().size(); i++) {
                    permissionItemGist = new PermissionItemGist();
                    BeanUtils.copyProperties(permissionItemModel.getGistList().get(i), permissionItemGist);
                    permissionItemGist.setPermissionItem(permissionItem);
                    permissionItemGists.add(permissionItemGist);
                }
                permissionItem.setPermissionItemGists(permissionItemGists);
            }

            permissionItemPowerDao.deletePermissionItemPower(permissionItem.getId());
            permissionItemPowerDao.savePermissionItemPowers(permissionItem.getPermissionItemPowers());

            permissionItemGistDao.deletePermissionItemGist(permissionItem.getId());
            permissionItemGistDao.savePermissionItemGists(permissionItem.getPermissionItemGists());
        }
    }

    /**
     * 
     * @Function: savePermissionItem
     * @Description: 新增
     *
     * @param permissionItemModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月27日 上午10:16:54
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public TeeJson savePermissionItem(PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        TeeJson json = new TeeJson();
        try {
            permissionItemModel.setId(UUID.randomUUID().toString());
            permissionItemModel.setIsDelete(0);
            permissionItemModel.setCreateTime(new Date());
            PermissionItem permissionItem = new PermissionItem();
            BeanUtils.copyProperties(permissionItemModel, permissionItem);
            // 许可职权处理
            if (permissionItemModel.getPowerList() != null && permissionItemModel.getPowerList().size() > 0) {
                List<PermissionItemPower> permissionItemPowers = new ArrayList<>();
                PermissionItemPower permissionItemPower = null;
                for (int i = 0; i < permissionItemModel.getPowerList().size(); i++) {
                    permissionItemPower = new PermissionItemPower();
                    BeanUtils.copyProperties(permissionItemModel.getPowerList().get(i), permissionItemPower);
                    permissionItemPower.setPermissionItem(permissionItem);
                    permissionItemPowers.add(permissionItemPower);
                }
                permissionItem.setPermissionItemPowers(permissionItemPowers);
            }

            // 设定依据处理
            if (permissionItemModel.getGistList() != null && permissionItemModel.getGistList().size() > 0) {
                List<PermissionItemGist> permissionItemGists = new ArrayList<>();
                PermissionItemGist permissionItemGist = null;
                for (int i = 0; i < permissionItemModel.getGistList().size(); i++) {
                    permissionItemGist = new PermissionItemGist();
                    BeanUtils.copyProperties(permissionItemModel.getGistList().get(i), permissionItemGist);
                    permissionItemGist.setPermissionItem(permissionItem);
                    permissionItemGists.add(permissionItemGist);
                }
                permissionItem.setPermissionItemGists(permissionItemGists);
            }

            permissionItemDao.save(permissionItem);

            permissionItemPowerDao.savePermissionItemPowers(permissionItem.getPermissionItemPowers());

            permissionItemGistDao.savePermissionItemGists(permissionItem.getPermissionItemGists());
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
     * @Function: findPermissionItem
     * @Description: 模糊查询许可事项
     *
     * @param q
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 下午3:22:25
     */
    public List<PermissionItem> findPermissionItem(String q) {
        // TODO Auto-generated method stub
        List<PermissionItem> permissionItems = null;
        permissionItems = permissionItemDao.findPermissionItem(q);
        return permissionItems;
    }

    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 许可事项分页查询（带数据权限）
     *
     * @param dataGridModel
     * @param permissionItemModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 下午3:33:14
     */
    public List<PermissionItemModel> findListByPageRoles(TeeDataGridModel dataGridModel,
            PermissionItemModel permissionItemModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        List<PermissionItemModel> permissionItemModels = new ArrayList<>();
        List<PermissionItem> permissionItems = permissionItemDao.findListByPageRoles(dataGridModel.getFirstResult(),
                dataGridModel.getRows(), permissionItemModel, orgCtrlInfoModel);
        for (PermissionItem permissionItem : permissionItems) {
            PermissionItemModel pModel = copyAllProperties(permissionItem);
            permissionItemModels.add(pModel);
        }
        return permissionItemModels;
    }

    /**
     * 
     * @Function: findListCountByPageRoles
     * @Description: 许可事项查询总条数(√)
     *
     * @param permissionItemModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 上午11:41:22
     */
    public Long findListCountByPageRoles(PermissionItemModel permissionItemModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        return permissionItemDao.findListCountByPageRoles(permissionItemModel, orgCtrlInfoModel);
    }

    /**
     * 
     * @Function: getPermissionItemByOneself
     * @Description: 查询本单位许可事项
     *
     * @param permissionItemModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 上午11:41:49
     */
    public List<PermissionItemModel> getPermissionItemByOneself(PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        List<PermissionItemModel> permissionItemModels = new ArrayList<>();
        List<PermissionItem> permissionItems = permissionItemDao.getPermissionItemByOneself(permissionItemModel);
        for (PermissionItem permissionItem : permissionItems) {
            PermissionItemModel sModel = copyAllProperties(permissionItem);
            permissionItemModels.add(sModel);
        }
        return permissionItemModels;
    }

}
