package com.tianee.oa.mobile.workflow.comparator;

import java.util.Comparator;

import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;

public class TeeFormItemComparator implements Comparator<TeeFormItem>{

	@Override
	public int compare(TeeFormItem o1, TeeFormItem o2) {
		// TODO Auto-generated method stub
		if(o1.getSortNo()>o2.getSortNo()){
			return 1;
		}else if(o1.getSortNo()<o2.getSortNo()){
			return -1;
		}
		return 0;
	}
	
}
