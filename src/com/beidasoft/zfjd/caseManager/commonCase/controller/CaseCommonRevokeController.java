/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.controller 
 * @author: songff   
 * @date: 2019年1月22日 下午2:18:34 
 */
package com.beidasoft.zfjd.caseManager.commonCase.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonRevokeModel;
import com.tianee.webframe.util.str.TeeStringUtil;

/**   
* 2019 
* @ClassName: CaseCommonCRevokeController.java
* @Description: 该类的功能描述
*
* @author: songff
* @date: 2019年1月22日 下午2:18:34 
*
*/
@Controller
@RequestMapping("caseCommonRevokeCtrl")
public class CaseCommonRevokeController {
    
    /**
     * 
    * @Function: commonCaseAddRevoke()
    * @Description: 跳转撤销立案页面
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月22日 下午2:20:33 
    *
     */
    @RequestMapping("/commonCaseAddRevoke")
    public void commonCaseAddRevoke(HttpServletRequest request, HttpServletResponse response, CaseCommonRevokeModel revokeModel) throws Exception{
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
            request.setAttribute("revoke", revokeModel);
            request.getRequestDispatcher("/supervise/caseManager/commonCase/common_case_addRevoke.jsp").forward(request, response);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
