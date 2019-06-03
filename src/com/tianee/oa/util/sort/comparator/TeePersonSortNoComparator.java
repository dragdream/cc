package com.tianee.oa.util.sort.comparator;

import java.util.Comparator;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 人员排序对比
 * @author kakalion
 *
 */
public class TeePersonSortNoComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		TeePerson p1 = (TeePerson) o1;
		TeePerson p2 = (TeePerson) o2;
		if(p1.getUserNo()>p2.getUserNo()){
			return 1;
		}else if(p1.getUserNo()<p2.getUserNo()){
			return -1;
		}else{
			return 0;
		}
	}

}
