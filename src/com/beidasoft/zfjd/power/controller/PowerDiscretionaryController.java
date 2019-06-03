package com.beidasoft.zfjd.power.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerDiscretionary;
import com.beidasoft.zfjd.power.model.PowerDiscretionaryModel;
import com.beidasoft.zfjd.power.service.PowerDiscretionaryService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("discretionaryCtrl")
public class PowerDiscretionaryController {
    
    @Autowired
    private PowerDiscretionaryService discretionaryService;
    
    @ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(HttpServletRequest request, PowerDiscretionaryModel discretionaryModel, TeeDataGridModel dm) {
        TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
        
        List<PowerDiscretionary> discretionaries = discretionaryService.listByPage(dm, discretionaryModel);
        Long total = discretionaryService.listCount(discretionaryModel);
        
        List<PowerDiscretionaryModel> dModels = new ArrayList<PowerDiscretionaryModel>();
        PowerDiscretionaryModel dModel = null;
        
        for(int i = 0; i < discretionaries.size(); i++) {
            dModel = new PowerDiscretionaryModel();
            
            BeanUtils.copyProperties(discretionaries.get(i), dModel);
            
            dModel.setPowerId(discretionaries.get(i).getPowerDeiscretionary().getId());
            dModel.setPowerName(discretionaries.get(i).getPowerDeiscretionary().getName());
            
            dModel.setCreateDateStr(TeeDateUtil.format(discretionaries.get(i).getCreateDate(), "yyyy-MM-dd"));
            dModel.setUpdateDateStr(TeeDateUtil.format(discretionaries.get(i).getUpdateDate(), "yyyy-MM-dd"));

            dModels.add(dModel);
        }
        
        datagrid.setRows(dModels);
        datagrid.setTotal(total);
        
        return datagrid;
    }
    
    @RequestMapping("input")
    public void input(HttpServletRequest request, HttpServletResponse response) {
        try {
            String powerId = TeeStringUtil.getString(request.getParameter("id"), "");
            request.setAttribute("powerId", powerId);
            request.getRequestDispatcher("/supervise/power/power_discretionary_input.jsp").forward(request, response);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
    @RequestMapping("addOrUpdate")
    public void addOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = TeeStringUtil.getString(request.getParameter("id"), "");
            String powerId = TeeStringUtil.getString(request.getParameter("powerId"), "");
            if(id != "") {
                PowerDiscretionary discretionary = discretionaryService.getById(id);
                
                PowerDiscretionaryModel discretionaryModel = new PowerDiscretionaryModel();
                BeanUtils.copyProperties(discretionary, discretionaryModel);
                
                discretionaryModel.setPowerId(discretionary.getPowerDeiscretionary().getId());
                discretionaryModel.setCreateDateStr(TeeDateUtil.format(discretionary.getCreateDate(), "yyyy-MM-dd"));
                
                request.setAttribute("discretionary", discretionaryModel);
            } else {
                PowerDiscretionaryModel discretionaryModel = new PowerDiscretionaryModel();
                discretionaryModel.setPowerId(powerId);

                request.setAttribute("discretionary", discretionaryModel);
            }
            request.getRequestDispatcher("/supervise/power/power_discretionary_addOrUpdate.jsp").forward(request, response);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
    @ResponseBody
    @RequestMapping("saveOrUpdate")
    public TeeJson saveOrUpdate(HttpServletRequest request, PowerDiscretionaryModel discretionaryModel) {
        TeeJson result = new TeeJson();
        
        if(discretionaryModel.getId() == null || "".equals(discretionaryModel.getId())) {
            discretionaryModel.setId(UUID.randomUUID().toString());
        }
        
        PowerDiscretionary discretionary = new PowerDiscretionary();
        BeanUtils.copyProperties(discretionaryModel, discretionary);
        
        Power power = new Power();
        
        power.setId(discretionaryModel.getPowerId());
        
        discretionary.setPowerDeiscretionary(power);
        discretionary.setCreateDate(TeeDateUtil.format(discretionaryModel.getCreateDateStr(), "yyyy-MM-dd"));
        discretionary.setUpdateDate(new Date());
        
        discretionaryService.saveOrUpdate(discretionary);
        
        result.setRtState(true);
        return result;
    }
    @ResponseBody
    @RequestMapping("deleteById")
    public TeeJson deleteById(HttpServletRequest request) {
        TeeJson result = new TeeJson();
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        
        discretionaryService.deleteById(id);
        
        result.setRtState(true);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("listDiscretionaryByPowerIds")
    public TeeEasyuiDataGridJson listDiscretionaryByPowerIds(HttpServletRequest request, PowerDiscretionaryModel discretionaryModel, TeeDataGridModel dm) {
        TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
        
        List<PowerDiscretionary> discretionaries = discretionaryService.listDiscretionaryByPowerIds(dm, discretionaryModel);
        
        List<PowerDiscretionaryModel> dModels = new ArrayList<PowerDiscretionaryModel>();
        PowerDiscretionaryModel dModel = null;
        
        if (discretionaries != null && discretionaries.size() > 0) {
            for(int i = 0; i < discretionaries.size(); i++) {
                dModel = new PowerDiscretionaryModel();
                
                BeanUtils.copyProperties(discretionaries.get(i), dModel);
                
                dModel.setPowerId(discretionaries.get(i).getPowerDeiscretionary().getId());
                dModel.setPowerName(discretionaries.get(i).getPowerDeiscretionary().getName());
                
                dModel.setCreateDateStr(TeeDateUtil.format(discretionaries.get(i).getCreateDate(), "yyyy-MM-dd"));
                dModel.setUpdateDateStr(TeeDateUtil.format(discretionaries.get(i).getUpdateDate(), "yyyy-MM-dd"));

                dModels.add(dModel);
            }
            
        }
        datagrid.setRows(dModels);
        return datagrid;
    }
}
