package com.beidasoft.xzfy.caseArchive.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.base.exception.ValidateException;
import com.beidasoft.xzfy.caseAcceptence.service.CaseAcceptService;
import com.beidasoft.xzfy.caseArchive.model.request.CaseArchiveCommitRequest;
import com.beidasoft.xzfy.caseArchive.model.request.CaseArchiveSaveRequest;
import com.beidasoft.xzfy.caseArchive.service.CaseArchiveService;
import com.beidasoft.xzfy.caseFile.bean.CaseFile;
import com.beidasoft.xzfy.caseFile.dao.CaseFileDao;
import com.beidasoft.xzfy.common.controller.FyCommonController;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import common.Logger;

/**
 * 案件归档
 * 
 * @author fyj
 * 
 */
@Controller
@RequestMapping("/xzfy/caseArchive")
public class CaseArchiveController extends FyBaseController {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private final Logger log = Logger.getLogger(CaseArchiveController.class);

    @Autowired
    private CaseArchiveService archiveService;

    // 案件受理service
    @Autowired
    private CaseAcceptService service;

    @Autowired
    private CaseFileDao caseFileDao;

    /**
     * 案件归档资料保存
     * 
     * @param req
     * @return
     * @throws ValidateException
     */
    @RequestMapping("/caseMaterialSave")
    @ResponseBody
    public TeeJson caseMaterialSave(CaseArchiveSaveRequest req) {

        log.info("[xzfy - CaseArchiveController - caseMaterialSave] enter controller.");
        TeeJson json = new TeeJson();
        try {
            // 参数校验
            req.validate();

            // 校验案件是否存在
            Boolean b = service.isExitCase(req.getCaseId());
            if (!b) {
                json.setRtState(false);
                json.setRtMsg("案件不存在");
            }

            // 案件材料保存
            archiveService.caseMaterialSave(req);

            // 设置返回参数
            json.setRtState(true);
            json.setRtMsg("请求成功");

        } catch (ValidateException e) {

            log.info("[xzfy - CaseArchiveController - caseMaterialSave] error=" + e);
            json.setRtState(false);
            json.setRtMsg("校验失败");
        } catch (Exception e) {

            log.info("[xzfy - CaseArchiveController - caseMaterialSave] error=" + e);
            json.setRtState(false);
            json.setRtMsg("请求失败");
        } finally {

            log.info("[xzfy - CaseArchiveController - caseMaterialSave] controller end.");
        }
        return json;
    }

    /**
     * 案件归档完成
     * 
     * @param req
     * @return
     */
    @RequestMapping("/caseArchiveCommit")
    @ResponseBody
    public TeeJson caseArchiveCommit(CaseArchiveCommitRequest req, HttpServletRequest request) {

        log.info("[xzfy - CaseArchiveController - caseMaterialSave] enter controller.");
        TeeJson json = new TeeJson();
        TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
        try {
            // 参数校验
            req.validate();

            // 1、修改案件状态
            archiveService.caseArchiveCommit(req, getRequest());

            // 2、形成归档目录 3、文件打包成压缩文件
            archive(req, loginUser);
            // 设置返回参数
            json.setRtState(true);
            json.setRtMsg("请求成功");

        } catch (ValidateException e) {

            log.info("[xzfy - CaseArchiveController - caseMaterialSave] error=" + e);
            json.setRtState(false);
            json.setRtMsg("校验失败");
        } catch (Exception e) {

            log.info("[xzfy - CaseArchiveController - caseMaterialSave] error=" + e);
            json.setRtState(false);
            json.setRtMsg("请求失败");
        } finally {

            log.info("[xzfy - CaseArchiveController - caseMaterialSave] controller end.");
            // 调用方法形成归档目录、打包形成zip文件

        }
        return json;
    }

    /**
     * Description:归档管理 1、修改案件状态 2、形成归档目录 3、文件打包成压缩文件
     * 
     * @author zhangchengkun
     * @version 0.1 2019年5月7日
     * @param request
     * @return TeeJson
     * @throws IOException
     */
    public TeeJson archive(CaseArchiveCommitRequest req, TeePerson loginUser) throws IOException {
        TeeJson json = new TeeJson();
        // 案件id
        String caseId = req.getCaseId();
        // 前台获取文件urls
        String urls = req.getUrls();
        // 前台选中文件临时存放地址
        String tempPath =
            TeeSysProps.getProps().getProperty("FILE_UPLOAD_URL") + "temp" + File.separator
                + System.currentTimeMillis() + File.separator;
        // 生成文书，并返回文书路径
        String legalUrl = "";// TODO 有待调用文书生成方法，返回文书URL
        // 将文书URL追加到urls中
        urls += legalUrl;
        // 转化为数组
        String[] arrayUrl = urls.split(",");
        // 复制所有文件到指定文件夹下，为打包做准备
        for (String url : arrayUrl) {
            FyCommonController.copyFile(url, tempPath);// 由controller统一捕获异常
        }
        Calendar rightNow = Calendar.getInstance();
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH) + 1; // 第一个月从0开始，所以得到月份＋1
        Integer day = rightNow.get(Calendar.DAY_OF_MONTH);
        String fileName = StringUtils.getUUId();
        // 文件真实名称
        String realFileName = "归档文件" + year + month + day + System.currentTimeMillis() + ".zip";
        // 文件存储路径(不含文件名称)
        String uploadedFileUrl =
            TeeSysProps.getProps().getProperty("FY_LEGAL_DOC") + "zip" + File.separator + year + File.separator + month
                + File.separator + day + File.separator;
        // 文件存储全路径(含文件名称)
        String uploadedFileFullUrl = uploadedFileUrl + fileName + ".zip";
        if (!new File(uploadedFileUrl).exists()) {
            new File(uploadedFileUrl).mkdirs();
        }
        // 打包文件
        TeeJson jsonFile = FyCommonController.fileToZip(tempPath, uploadedFileUrl, fileName);
        // 存储文件信息至数据库
        CaseFile fileInfo = new CaseFile();
        fileInfo.setId(StringUtils.getUUId());
        fileInfo.setCaseId(caseId);
        fileInfo.setFileName(realFileName);
        fileInfo.setFileType(Const.FILETYPE.ARCHIVE_NAME);
        fileInfo.setFileTypeCode(Const.FILETYPE.ARCHIVE_CODE);
        fileInfo.setSize(Integer.parseInt(jsonFile.getRtData().toString()));
        fileInfo.setLink("归档环节");
        fileInfo.setStoragePath(uploadedFileFullUrl);
        StringUtils.setAddInfo(fileInfo, loginUser);
        caseFileDao.save(fileInfo);

        return json;
    }
}
