package com.tianee.oa.subsys.zhidao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianee.oa.subsys.zhidao.service.TeeZhiDaoLabelService;

@Controller
@RequestMapping("/zhiDaoLabelController")
public class TeeZhiDaoLabelController {
	@Autowired
	private TeeZhiDaoLabelService zhiDaoLabelService;
}
