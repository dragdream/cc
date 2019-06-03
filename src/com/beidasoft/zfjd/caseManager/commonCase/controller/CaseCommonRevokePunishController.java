/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.controller 
 * @author: songff   
 * @date: 2019年1月23日 上午10:51:14 
 */
package com.beidasoft.zfjd.caseManager.commonCase.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonRevokePunishModel;
import com.tianee.webframe.util.str.TeeStringUtil;

/**   
* 2019 
* @ClassName: CaseCommonRevokePunishController.java
* @Description: 该类的功能描述
*
* @author: songff
* @date: 2019年1月23日 上午10:51:14 
*
*/
@Controller
@RequestMapping("caseCommonRevokePunishCtrl")
public class CaseCommonRevokePunishController {

    @RequestMapping("/commonCaseAddRevokePunish")
    public void commonCaseAddRevoke(HttpServletRequest request, HttpServletResponse response, CaseCommonRevokePunishModel revokePunishModel) throws Exception{
        try {
            if(request.getParameter("editFlag") != null && !"".equals(request.getParameter("editFlag"))) {
                String editFlag = TeeStringUtil.getString(request.getParameter("editFlag"),"");
                request.setAttribute("editFlag", editFlag);
            }
            if(request.getParameter("isNext") != null && !"".equals(request.getParameter("isNext"))) {
                String isNext = TeeStringUtil.getString(request.getParameter("isNext"),"");
                request.setAttribute("isNext", isNext);
            } 
            if(request.getParameter("caseId") != null && !"".equals(request.getParameter("caseId"))) {
                String caseId = TeeStringUtil.getString(request.getParameter("caseId"),"");
                request.setAttribute("caseId", caseId);
            } 
            request.setAttribute("revokePunish", revokePunishModel);
            request.getRequestDispatcher("/supervise/caseManager/commonCase/common_case_addRevokePunish.jsp").forward(request, response);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
