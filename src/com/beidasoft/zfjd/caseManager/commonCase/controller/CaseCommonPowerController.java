/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.controller 
 * @author: songff   
 * @date: 2019年1月18日 上午9:08:58 
 */
package com.beidasoft.zfjd.caseManager.commonCase.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianee.webframe.util.str.TeeStringUtil;

/**   
* 2019 
* @ClassName: CaseCommonPowerController.java
* @Description: 该类的功能描述
*
* @author: songff
* @date: 2019年1月18日 上午9:08:58 
*
*/
@Controller
@RequestMapping("caseCommonPowerCtrl")
public class CaseCommonPowerController {

    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = LogManager.getLogger(CaseCommonPowerController.class);
    
    /**
     * 
    * @Function: commonCaseAddPower()
    * @Description: 选择违法行为弹框
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月15日 上午11:39:12 
    *
     */
    @RequestMapping("/commonCaseAddPower")
    public void commonCaseAddPower(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            if(request.getParameter("actSubject") != null && !"".equals(request.getParameter("actSubject"))) {
                String actSubject = TeeStringUtil.getString(request.getParameter("actSubject"),"");
                request.setAttribute("actSubject", actSubject);
            } 
            if(request.getParameter("powerType") != null && !"".equals(request.getParameter("powerType"))) {
                String powerType = TeeStringUtil.getString(request.getParameter("powerType"),"");
                request.setAttribute("powerType", powerType);
            } 
            request.getRequestDispatcher("/supervise/caseManager/commonCase/common_case_addPower.jsp").forward(request, response);
        }catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
}
