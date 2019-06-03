package com.tianee.oa.util.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class TeeCollectionSorting {
	public static List sort(Set set,Comparator comparator){
		List tmp = new ArrayList();
		tmp.addAll(set);
		
		Collections.sort(tmp, comparator);
		return tmp;
	}
	
	public static List sort(List list,Comparator comparator){
		Collections.sort(list, comparator);
		return list;
	}
}
