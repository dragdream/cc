package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 入库出库单实体类
 */
@Entity
@Table(name="ZF_DOC_INOROUT")
public class DocInorout {
    // 入库出库id
    @Id
    @Column(name = "ID")
    private String id;

    // 入库/出库单
    @Column(name = "INOUT_NAME")
    private String inoutName;

    // 移交部门
    @Column(name = "MOVE_DEPARTMENT")
    private String moveDepartment;

    // 当事人
    @Column(name = "ONE_PEOPLE")
    private String onePeople;

    // 文书编号
    @Column(name = "BOOK_NUMBER")
    private String bookNumber;
    
    // 入库/出库
    @Column(name = "INOUT_NAME2")
    private String inoutName2;

    // 入库出库单编号
    @Column(name = "INOUT_NUMBER")
    private String inoutNumber;

    // 图书成品册
    @Column(name = "BOOK_CE")
    private String bookCe;

    // 图书内文散页令
    @Column(name = "BOOK_LING")
    private String bookLing;

    // 图书备注
    @Column(name = "BOOK_NOTE")
    private String bookNote;

    // 报纸成品份
    @Column(name = "PAPER_FEN")
    private String paperFen;

    // 报纸内文散页令
    @Column(name = "PAPER_LING")
    private String paperLing;

    // 报纸备注
    @Column(name = "PAPER_NOTE")
    private String paperNote;

    // 期刊成品本
    @Column(name = "PERIODICAL_BEN")
    private String periodicalBen;

    // 期刊内文散页令
    @Column(name = "PERIODICAL_LING")
    private String periodicalLing;

    // 期刊备注
    @Column(name = "PERIODICAL_NOTE")
    private String periodicalNote;

    // 光盘成品张
    @Column(name = "DISK_ZHANG")
    private String diskZhang;

    // 光盘母盘/母带张
    @Column(name = "DISK_MOTHER_ZHANG")
    private String diskMotherZhang;

    // 光盘备注
    @Column(name = "DISK_NOTE")
    private String diskNote;

    // 录音录像带成品盒
    @Column(name = "VIDEO_HE")
    private String videoHe;

    // 录音录像带母盘/母带盒
    @Column(name = "VIDEO_MOTHER_PAN")
    private String videoMotherPan;

    // 录音录像带备注
    @Column(name = "VIDEO_NOTE")
    private String videoNote;

    // 其他物品
    @Column(name = "OTHERS_GOODS")
    private String othersGoods;

    // 备注
    @Column(name = "NOTES_NOT")
    private String notesNot;

    // 送交主办人签名图片
    @Lob
    @Column(name = "MOVE_MAJOR_SIGNATURE_BASE64")
    private String moveMajorSignatureBase64;

    // 送交主办人签名值
    @Lob
    @Column(name = "MOVE_MAJOR_SIGNATURE_VALUE")
    private String moveMajorSignatureValue;

    // 送交主办人签名位置
    @Column(name = "MOVE_MAJOR_SIGNATURE_PLACE")
    private String moveMajorSignaturePlace;

    // 送交协办人签名图片
    @Lob
    @Column(name = "MOVE2_MAJOR_SIGNATURE_BASE64")
    private String move2MajorSignatureBase64;

    // 送交协办人签名值
    @Lob
    @Column(name = "MOVE2_MAJOR_SIGNATURE_VALUE")
    private String move2MajorSignatureValue;

    // 送交协办人签名位置
    @Column(name = "MOVE2_MAJOR_SIGNATURE_PLACE")
    private String move2MajorSignaturePlace;

    // 送交人日期
    @Column(name = "MOVE_DATE")
    private Date moveDate;

    // 接收主办人签名图片
    @Lob
    @Column(name = "RECIVE_MAJOR_SIGNATURE_BASE64")
    private String reciveMajorSignatureBase64;

    // 接收主办人签名值
    @Lob
    @Column(name = "RECIVE_MAJOR_SIGNATURE_VALUE")
    private String reciveMajorSignatureValue;

    // 接收主办人签名位置
    @Column(name = "RECIVE_MAJOR_SIGNATURE_PLACE")
    private String reciveMajorSignaturePlace;

    // 接收协办人签名图片
    @Lob
    @Column(name = "RECIVE2_MAJOR_SIGNATURE_BASE64")
    private String recive2MajorSignatureBase64;

    // 接收协办人签名值
    @Lob
    @Column(name = "RECIVE2_MAJOR_SIGNATURE_VALUE")
    private String recive2MajorSignatureValue;

    // 接收协办人签名位置
    @Column(name = "RECIVE2_MAJOR_SIGNATURE_PLACE")
    private String recive2MajorSignaturePlace;

    // 接收人日期
    @Column(name = "RECIVE_DATE")
    private Date reciveDate;

    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法环节id
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 创建人id
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    // 创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 变更人id
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    // 变更人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    // 变更时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    // 执法办案唯一id
    @Column(name = "BASE_ID")
    private String baseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInoutName() {
        return inoutName;
    }

    public void setInoutName(String inoutName) {
        this.inoutName = inoutName;
    }

    public String getMoveDepartment() {
        return moveDepartment;
    }

    public void setMoveDepartment(String moveDepartment) {
        this.moveDepartment = moveDepartment;
    }

    public String getOnePeople() {
        return onePeople;
    }

    public void setOnePeople(String onePeople) {
        this.onePeople = onePeople;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getInoutNumber() {
        return inoutNumber;
    }

    public void setInoutNumber(String inoutNumber) {
        this.inoutNumber = inoutNumber;
    }


    public String getBookNote() {
        return bookNote;
    }

    public void setBookNote(String bookNote) {
        this.bookNote = bookNote;
    }

    public String getPaperNote() {
        return paperNote;
    }

    public void setPaperNote(String paperNote) {
        this.paperNote = paperNote;
    }

    public String getInoutName2() {
		return inoutName2;
	}

	public void setInoutName2(String inoutName2) {
		this.inoutName2 = inoutName2;
	}

	public String getPeriodicalNote() {
        return periodicalNote;
    }

    public void setPeriodicalNote(String periodicalNote) {
        this.periodicalNote = periodicalNote;
    }

    public String getDiskNote() {
        return diskNote;
    }

    public void setDiskNote(String diskNote) {
        this.diskNote = diskNote;
    }

    public String getVideoNote() {
        return videoNote;
    }

    public void setVideoNote(String videoNote) {
        this.videoNote = videoNote;
    }

    public String getOthersGoods() {
        return othersGoods;
    }

    public void setOthersGoods(String othersGoods) {
        this.othersGoods = othersGoods;
    }

    public String getNotesNot() {
        return notesNot;
    }

    public void setNotesNot(String notesNot) {
        this.notesNot = notesNot;
    }

    public String getMoveMajorSignatureBase64() {
        return moveMajorSignatureBase64;
    }

    public void setMoveMajorSignatureBase64(String moveMajorSignatureBase64) {
        this.moveMajorSignatureBase64 = moveMajorSignatureBase64;
    }

    public String getMoveMajorSignatureValue() {
        return moveMajorSignatureValue;
    }

    public void setMoveMajorSignatureValue(String moveMajorSignatureValue) {
        this.moveMajorSignatureValue = moveMajorSignatureValue;
    }

    public String getMoveMajorSignaturePlace() {
        return moveMajorSignaturePlace;
    }

    public void setMoveMajorSignaturePlace(String moveMajorSignaturePlace) {
        this.moveMajorSignaturePlace = moveMajorSignaturePlace;
    }

    public String getMove2MajorSignatureBase64() {
        return move2MajorSignatureBase64;
    }

    public void setMove2MajorSignatureBase64(String move2MajorSignatureBase64) {
        this.move2MajorSignatureBase64 = move2MajorSignatureBase64;
    }

    public String getMove2MajorSignatureValue() {
        return move2MajorSignatureValue;
    }

    public void setMove2MajorSignatureValue(String move2MajorSignatureValue) {
        this.move2MajorSignatureValue = move2MajorSignatureValue;
    }

    public String getMove2MajorSignaturePlace() {
        return move2MajorSignaturePlace;
    }

    public void setMove2MajorSignaturePlace(String move2MajorSignaturePlace) {
        this.move2MajorSignaturePlace = move2MajorSignaturePlace;
    }

    public Date getMoveDate() {
        return moveDate;
    }

    public void setMoveDate(Date moveDate) {
        this.moveDate = moveDate;
    }

    public String getReciveMajorSignatureBase64() {
        return reciveMajorSignatureBase64;
    }

    public void setReciveMajorSignatureBase64(String reciveMajorSignatureBase64) {
        this.reciveMajorSignatureBase64 = reciveMajorSignatureBase64;
    }

    public String getReciveMajorSignatureValue() {
        return reciveMajorSignatureValue;
    }

    public void setReciveMajorSignatureValue(String reciveMajorSignatureValue) {
        this.reciveMajorSignatureValue = reciveMajorSignatureValue;
    }

    public String getReciveMajorSignaturePlace() {
        return reciveMajorSignaturePlace;
    }

    public void setReciveMajorSignaturePlace(String reciveMajorSignaturePlace) {
        this.reciveMajorSignaturePlace = reciveMajorSignaturePlace;
    }

    public String getRecive2MajorSignatureBase64() {
        return recive2MajorSignatureBase64;
    }

    public void setRecive2MajorSignatureBase64(String recive2MajorSignatureBase64) {
        this.recive2MajorSignatureBase64 = recive2MajorSignatureBase64;
    }

    public String getRecive2MajorSignatureValue() {
        return recive2MajorSignatureValue;
    }

    public void setRecive2MajorSignatureValue(String recive2MajorSignatureValue) {
        this.recive2MajorSignatureValue = recive2MajorSignatureValue;
    }

    public String getRecive2MajorSignaturePlace() {
        return recive2MajorSignaturePlace;
    }

    public void setRecive2MajorSignaturePlace(String recive2MajorSignaturePlace) {
        this.recive2MajorSignaturePlace = recive2MajorSignaturePlace;
    }

    public String getEnclosureAddress() {
        return enclosureAddress;
    }

    public void setEnclosureAddress(String enclosureAddress) {
        this.enclosureAddress = enclosureAddress;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getBookCe() {
		return bookCe;
	}

	public void setBookCe(String bookCe) {
		this.bookCe = bookCe;
	}

	public String getBookLing() {
		return bookLing;
	}

	public void setBookLing(String bookLing) {
		this.bookLing = bookLing;
	}

	public String getPaperFen() {
		return paperFen;
	}

	public void setPaperFen(String paperFen) {
		this.paperFen = paperFen;
	}

	public String getPaperLing() {
		return paperLing;
	}

	public void setPaperLing(String paperLing) {
		this.paperLing = paperLing;
	}

	public String getPeriodicalBen() {
		return periodicalBen;
	}

	public void setPeriodicalBen(String periodicalBen) {
		this.periodicalBen = periodicalBen;
	}

	public String getPeriodicalLing() {
		return periodicalLing;
	}

	public void setPeriodicalLing(String periodicalLing) {
		this.periodicalLing = periodicalLing;
	}

	public String getDiskZhang() {
		return diskZhang;
	}

	public void setDiskZhang(String diskZhang) {
		this.diskZhang = diskZhang;
	}

	public String getDiskMotherZhang() {
		return diskMotherZhang;
	}

	public void setDiskMotherZhang(String diskMotherZhang) {
		this.diskMotherZhang = diskMotherZhang;
	}

	public String getVideoHe() {
		return videoHe;
	}

	public void setVideoHe(String videoHe) {
		this.videoHe = videoHe;
	}

	public String getVideoMotherPan() {
		return videoMotherPan;
	}

	public void setVideoMotherPan(String videoMotherPan) {
		this.videoMotherPan = videoMotherPan;
	}

	public Date getReciveDate() {
		return reciveDate;
	}

	public void setReciveDate(Date reciveDate) {
		this.reciveDate = reciveDate;
	}

	public String getLawLinkId() {
		return lawLinkId;
	}

	public void setLawLinkId(String lawLinkId) {
		this.lawLinkId = lawLinkId;
	}

	public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

}
