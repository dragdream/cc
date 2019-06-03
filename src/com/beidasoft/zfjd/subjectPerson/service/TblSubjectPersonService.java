package com.beidasoft.zfjd.subjectPerson.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.subjectPerson.bean.TblSubjectPerson;
import com.beidasoft.zfjd.subjectPerson.dao.TblSubjectPersonDao;
import com.beidasoft.zfjd.subjectPerson.model.TblSubjectPersonModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 主体人员关联表SERVICE类
 */
@Service
public class TblSubjectPersonService extends TeeBaseService {

    @Autowired
    private TblSubjectPersonDao SubjectPersonDao;
    
    /**
	 * 根据人员id查询
	 * @param personId
	 * @return
	 */
	public TblSubjectPerson getByPersonId (String personId){
		return SubjectPersonDao.getByPersonId(personId);
	}
    
    /**
     * 保存主体人员关联表数据
     *
     * @param beanInfo
     */
    public void save(TblSubjectPersonModel tblSubjectPersonModel) {
    	TblSubjectPerson subjectPerson = new TblSubjectPerson();
    	BeanUtils.copyProperties(tblSubjectPersonModel,subjectPerson);
    	SubjectPersonDao.save(subjectPerson);
    }

    /*
	 * 根据分页进行查询
	 */
	public List<TblSubjectPerson> listByPage(int firstResult, int rows) {
		return SubjectPersonDao.listByPage(firstResult, rows, null);
	}

	public List<TblSubjectPerson> listByPage(int firstResult, int rows, TblSubjectPersonModel queryModel) {
		return SubjectPersonDao.listByPage(firstResult, rows, queryModel);

	}
	
	public long getTotal() {
		return SubjectPersonDao.getTotal();
	}

	public long getTotal(TblSubjectPersonModel queryModel) {
		return SubjectPersonDao.getTotal(queryModel);
	}

    
    /**
     * 获取主体人员关联表数据
     *
     * @param id
     * @return     */
    public TblSubjectPerson getById(String id) {

        return SubjectPersonDao.get(id);
    }
}
