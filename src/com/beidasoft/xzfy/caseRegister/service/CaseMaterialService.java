package com.beidasoft.xzfy.caseRegister.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.beidasoft.xzfy.caseRegister.bean.FyMaterial;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyMaterialDao;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.MaterialReq;

@Service
public class CaseMaterialService {

    @Autowired
    private CaseFyMaterialDao caseFyMaterialDao;

    /**
     * 案件材料
     * 
     * @param reviewMattersReq
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional
    public void addCaseMaterial(MaterialReq materialReq) throws IllegalAccessException,
            InvocationTargetException {

        // 根据案件ID和案件材料类型编码删除案件材料
        caseFyMaterialDao.deleteByList(materialReq.getCaseId(), materialReq.getCaseTypeCode());
        // 案件材料信息入库
        caseFyMaterialDao.addFyMaterial(materialReq.getListFyMaterial());
    }

    /**
     * 案件材料查询
     * 
     * @param string
     * 
     * @return
     */
    public List<FyMaterial> selectCaseMaterial(String caseId) {
        // 案件登记
        String caseTypeCode = null;
        List<FyMaterial> listFyMaterial = caseFyMaterialDao.findCaseMaterial(caseId, caseTypeCode);
        return listFyMaterial;
    }
}
