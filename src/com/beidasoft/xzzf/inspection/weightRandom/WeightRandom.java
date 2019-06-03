package com.beidasoft.xzzf.inspection.weightRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tianee.webframe.httpmodel.TeeJson;

public class WeightRandom {
	static List<WeightCategory> categorys = new ArrayList<WeightCategory>();
	private static Random random = new Random();
	public static void initData(List<WeightCategory> categoryList) {  
		categorys = categoryList;
    }
	/**
	 * 根据权重随机选取数组中的一个对象
	 * @param categoryList
	 * @return
	 */
	public static TeeJson randomObject(List<WeightCategory> categoryList) {
		TeeJson json = new TeeJson();
		initData(categoryList);
        Integer weightSum = 0;  
        for (WeightCategory wc : categorys) {  
            weightSum += wc.getWeight();  
        }  
        if (weightSum <= 0) {  
	        json.setRtMsg("Error: weightSum=" + weightSum.toString());  
			return json;  
		}  
		Integer n = random.nextInt(weightSum); // n in [0, weightSum)  
		Integer m = 0;  
		for (WeightCategory wc : categorys) {  
		     if (m <= n && n < m + wc.getWeight()) {  
		    	 json.setRtData(wc.getCategory());
		    	 json.setRtState(true);
		         return json;  
		     }  
		     m += wc.getWeight();  
		}
		return json;
   }  
}