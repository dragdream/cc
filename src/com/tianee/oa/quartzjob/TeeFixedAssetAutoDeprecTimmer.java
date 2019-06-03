package com.tianee.oa.quartzjob;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.oa.core.base.fixedAssets.service.TeeFixedAssetsInfoService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;

@Service
public class TeeFixedAssetAutoDeprecTimmer extends TeeBaseService{
	@Autowired
	private TeeFixedAssetsInfoService assetsInfoService;
	
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		List<TeeFixedAssetsInfo> list = simpleDaoSupport.find("from TeeFixedAssetsInfo", null);
		//逐步折旧
		for(TeeFixedAssetsInfo fixedAsset:list){
			assetsInfoService.depreciate(fixedAsset, null);
		}
	}
}
