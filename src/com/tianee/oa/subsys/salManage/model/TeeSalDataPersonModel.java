package com.tianee.oa.subsys.salManage.model;

import java.util.Date;

import javax.persistence.Column;


public class TeeSalDataPersonModel {
	
	private int sid;//自增id
	private int userId;//上报人
	private String userName;//
	
	private int flowId;//工资流程Id
	private int accountId;//
	private String accountName;//账套名称
	
	private int salYear;//年份
	private int salMonth;//月份
	private double geshui;//个人所得税
	private double  sbBase;//社保基数
	private double  gjjBase;//公积金基数
	private String  memo;//备注

	private double  allBase;//保险基数
	
	private double  pensionBase;//养老保险
	

	private double  pensionU;//单位养老
	
	private double  pensionP;//个人养老
	
	private double  medicalBase;//医疗保险
	
	private double  medicalU;//单位医疗
	
	private double  medicalP;//个人医疗
	
	private double  fertilityBase;//生育保险
	
	private double  fertilityU;//单位生育
	
	private double  fertilityP;//个人生育
	
	private double  unemploymentBase;//失业保险
	
	private double  unemploymentU;//单位失业

	private double  unemploymentP;//个人失业
	
	private double  injuriesBase;//工伤保险
	
	private double  injuriesU;//单位工伤
	
	private double  housingBase;//住房公积金
	
	private double  housingU;//单位住房
	
	private double  housingP;//个人住房
	
	private Date  insuranceDate;//投保时间

	private int insuranceOther ;//是否投保
	
	private int isDeptInput ;//是否已经部门上报(1-是,0-否)
	
	private int isFinaInput ;//是否是财务上报(1-是,0-否)
	
	private double  injuriesP;//个人工伤
	
	private double s1;
	
	private double s2;
	
	private double s3;
	
	private double s4;
	
	private double s5;
	
	private double s6;
	
	private double s7;
	
	private double s8;

	private double s9;
	
	private double s10;
	
	private double s11;
	
	private double s12;
	
	private double s13;
	
	private double s14;
	
	private double s15;
	
	private double s16;
	
	private double s17;
	
	private double s18;
	
	private double s19;
	
	private double s20;
	
	private double s21;
	
	private double s22;
	
	private double s23;
	
	private double s24;
	
	private double s25;
	
	private double s26;
	
	private double s27;
	
	private double s28;
	
	private double s29;
	
	private double s30;

	private double s31;
	
	private double s32;
	
	private double s33;
	
	private double s34;
	
	private double s35;
	
	private double s36;
	
	private double s37;
	
	private double s38;
	
	private double s39;
	
	private double s40;
	
	private double s41;
	
	private double s42;
	
	private double s43;
	
	private double s44;
	
	private double s45;
	
	private double s46;
	
	private double s47;
	
	private double s48;
	
	private double s49;
	
	private double s50;
	
	private double s51;
	
	private double s52;
	
	private double s53;
	
	private double s54;
	
	private double s55;
	
	private double s56;
	
	private double s57;
	
	private double s58;
	
	private double s59;
	
	private double s60;

	private double s61;
	
	private double s62;
	
	private double s63;
	
	private double s64;
	
	private double s65;
	
	private double s66;
	private double s67;
	
	private double s68;
	
	private double s69;
	
	private double s70;
	
	private double s71;
	 
	private double s72;
	
	private double s73;
	
	private double s74;
	
	private double s75;
	
	private double s76;
	
	private double s77;
	
	private double s78;

	private double s79;

	private double s80;
	
	private double s81;
	
	private double s82;
	
	private double s83;
	
	private double s84;
	
	private double s85;
	
	private double s86;
	
	private double s87;
	
	private double s88;
	
	private double s89;
	
	private double s90;
	
	private double s91;
	
	private double s92;
	
	private double s93;
	
	private double s94;
	
	private double s95;
	
	private double s96;
	
	private double s97;
	
	private double s98;
	
	private double s99;
	
	private double s100;
	
	private double finalPayAmount;//实发金额

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSalYear() {
		return salYear;
	}

	public void setSalYear(int salYear) {
		this.salYear = salYear;
	}

	public int getSalMonth() {
		return salMonth;
	}

	public void setSalMonth(int salMonth) {
		this.salMonth = salMonth;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public double getAllBase() {
		return allBase;
	}

	public void setAllBase(double allBase) {
		this.allBase = allBase;
	}

	public double getPensionBase() {
		return pensionBase;
	}

	public void setPensionBase(double pensionBase) {
		this.pensionBase = pensionBase;
	}

	public double getPensionU() {
		return pensionU;
	}

	public void setPensionU(double pensionU) {
		this.pensionU = pensionU;
	}

	public double getPensionP() {
		return pensionP;
	}

	public void setPensionP(double pensionP) {
		this.pensionP = pensionP;
	}

	public double getMedicalBase() {
		return medicalBase;
	}

	public void setMedicalBase(double medicalBase) {
		this.medicalBase = medicalBase;
	}

	public double getMedicalU() {
		return medicalU;
	}

	public void setMedicalU(double medicalU) {
		this.medicalU = medicalU;
	}

	public double getMedicalP() {
		return medicalP;
	}

	public void setMedicalP(double medicalP) {
		this.medicalP = medicalP;
	}

	public double getFertilityBase() {
		return fertilityBase;
	}

	public void setFertilityBase(double fertilityBase) {
		this.fertilityBase = fertilityBase;
	}

	public double getFertilityU() {
		return fertilityU;
	}

	public void setFertilityU(double fertilityU) {
		this.fertilityU = fertilityU;
	}

	public double getFertilityP() {
		return fertilityP;
	}

	public void setFertilityP(double fertilityP) {
		this.fertilityP = fertilityP;
	}

	public double getUnemploymentBase() {
		return unemploymentBase;
	}

	public void setUnemploymentBase(double unemploymentBase) {
		this.unemploymentBase = unemploymentBase;
	}

	public double getUnemploymentU() {
		return unemploymentU;
	}

	public void setUnemploymentU(double unemploymentU) {
		this.unemploymentU = unemploymentU;
	}

	public double getUnemploymentP() {
		return unemploymentP;
	}

	public void setUnemploymentP(double unemploymentP) {
		this.unemploymentP = unemploymentP;
	}

	public double getInjuriesBase() {
		return injuriesBase;
	}

	public void setInjuriesBase(double injuriesBase) {
		this.injuriesBase = injuriesBase;
	}

	public double getInjuriesU() {
		return injuriesU;
	}

	public void setInjuriesU(double injuriesU) {
		this.injuriesU = injuriesU;
	}

	public double getHousingBase() {
		return housingBase;
	}

	public void setHousingBase(double housingBase) {
		this.housingBase = housingBase;
	}

	public double getHousingU() {
		return housingU;
	}

	public void setHousingU(double housingU) {
		this.housingU = housingU;
	}

	public double getHousingP() {
		return housingP;
	}

	public void setHousingP(double housingP) {
		this.housingP = housingP;
	}

	public Date getInsuranceDate() {
		return insuranceDate;
	}

	public void setInsuranceDate(Date insuranceDate) {
		this.insuranceDate = insuranceDate;
	}

	public int getInsuranceOther() {
		return insuranceOther;
	}

	public void setInsuranceOther(int insuranceOther) {
		this.insuranceOther = insuranceOther;
	}

	public int getIsDeptInput() {
		return isDeptInput;
	}

	public void setIsDeptInput(int isDeptInput) {
		this.isDeptInput = isDeptInput;
	}

	public int getIsFinaInput() {
		return isFinaInput;
	}

	public void setIsFinaInput(int isFinaInput) {
		this.isFinaInput = isFinaInput;
	}

	public double getS1() {
		return s1;
	}

	public void setS1(double s1) {
		this.s1 = s1;
	}

	public double getS2() {
		return s2;
	}

	public void setS2(double s2) {
		this.s2 = s2;
	}

	public double getS3() {
		return s3;
	}

	public void setS3(double s3) {
		this.s3 = s3;
	}

	public double getS4() {
		return s4;
	}

	public void setS4(double s4) {
		this.s4 = s4;
	}

	public double getS5() {
		return s5;
	}

	public void setS5(double s5) {
		this.s5 = s5;
	}

	public double getS6() {
		return s6;
	}

	public void setS6(double s6) {
		this.s6 = s6;
	}

	public double getS7() {
		return s7;
	}

	public void setS7(double s7) {
		this.s7 = s7;
	}

	public double getS8() {
		return s8;
	}

	public void setS8(double s8) {
		this.s8 = s8;
	}

	public double getS9() {
		return s9;
	}

	public void setS9(double s9) {
		this.s9 = s9;
	}

	public double getS10() {
		return s10;
	}

	public void setS10(double s10) {
		this.s10 = s10;
	}

	public double getS11() {
		return s11;
	}

	public void setS11(double s11) {
		this.s11 = s11;
	}

	public double getS12() {
		return s12;
	}

	public void setS12(double s12) {
		this.s12 = s12;
	}

	public double getS13() {
		return s13;
	}

	public void setS13(double s13) {
		this.s13 = s13;
	}

	public double getS14() {
		return s14;
	}

	public void setS14(double s14) {
		this.s14 = s14;
	}

	public double getS15() {
		return s15;
	}

	public void setS15(double s15) {
		this.s15 = s15;
	}

	public double getS16() {
		return s16;
	}

	public void setS16(double s16) {
		this.s16 = s16;
	}

	public double getS17() {
		return s17;
	}

	public void setS17(double s17) {
		this.s17 = s17;
	}

	public double getS18() {
		return s18;
	}

	public void setS18(double s18) {
		this.s18 = s18;
	}

	public double getS19() {
		return s19;
	}

	public void setS19(double s19) {
		this.s19 = s19;
	}

	public double getS20() {
		return s20;
	}

	public void setS20(double s20) {
		this.s20 = s20;
	}

	public double getS21() {
		return s21;
	}

	public void setS21(double s21) {
		this.s21 = s21;
	}

	public double getS22() {
		return s22;
	}

	public void setS22(double s22) {
		this.s22 = s22;
	}

	public double getS23() {
		return s23;
	}

	public void setS23(double s23) {
		this.s23 = s23;
	}

	public double getS24() {
		return s24;
	}

	public void setS24(double s24) {
		this.s24 = s24;
	}

	public double getS25() {
		return s25;
	}

	public void setS25(double s25) {
		this.s25 = s25;
	}

	public double getS26() {
		return s26;
	}

	public void setS26(double s26) {
		this.s26 = s26;
	}

	public double getS27() {
		return s27;
	}

	public void setS27(double s27) {
		this.s27 = s27;
	}

	public double getS28() {
		return s28;
	}

	public void setS28(double s28) {
		this.s28 = s28;
	}

	public double getS29() {
		return s29;
	}

	public void setS29(double s29) {
		this.s29 = s29;
	}

	public double getS30() {
		return s30;
	}

	public void setS30(double s30) {
		this.s30 = s30;
	}

	public double getS31() {
		return s31;
	}

	public void setS31(double s31) {
		this.s31 = s31;
	}

	public double getS32() {
		return s32;
	}

	public void setS32(double s32) {
		this.s32 = s32;
	}

	public double getS33() {
		return s33;
	}

	public void setS33(double s33) {
		this.s33 = s33;
	}

	public double getS34() {
		return s34;
	}

	public void setS34(double s34) {
		this.s34 = s34;
	}

	public double getS35() {
		return s35;
	}

	public void setS35(double s35) {
		this.s35 = s35;
	}

	public double getS36() {
		return s36;
	}

	public void setS36(double s36) {
		this.s36 = s36;
	}

	public double getS37() {
		return s37;
	}

	public void setS37(double s37) {
		this.s37 = s37;
	}

	public double getS38() {
		return s38;
	}

	public void setS38(double s38) {
		this.s38 = s38;
	}

	public double getS39() {
		return s39;
	}

	public void setS39(double s39) {
		this.s39 = s39;
	}

	public double getS40() {
		return s40;
	}

	public void setS40(double s40) {
		this.s40 = s40;
	}

	public double getS41() {
		return s41;
	}

	public void setS41(double s41) {
		this.s41 = s41;
	}

	public double getS42() {
		return s42;
	}

	public void setS42(double s42) {
		this.s42 = s42;
	}

	public double getS43() {
		return s43;
	}

	public void setS43(double s43) {
		this.s43 = s43;
	}

	public double getS44() {
		return s44;
	}

	public void setS44(double s44) {
		this.s44 = s44;
	}

	public double getS45() {
		return s45;
	}

	public void setS45(double s45) {
		this.s45 = s45;
	}

	public double getS46() {
		return s46;
	}

	public void setS46(double s46) {
		this.s46 = s46;
	}

	public double getS47() {
		return s47;
	}

	public void setS47(double s47) {
		this.s47 = s47;
	}

	public double getS48() {
		return s48;
	}

	public void setS48(double s48) {
		this.s48 = s48;
	}

	public double getS49() {
		return s49;
	}

	public void setS49(double s49) {
		this.s49 = s49;
	}

	public double getS50() {
		return s50;
	}

	public void setS50(double s50) {
		this.s50 = s50;
	}

	public double getFinalPayAmount() {
		return finalPayAmount;
	}

	public void setFinalPayAmount(double finalPayAmount) {
		this.finalPayAmount = finalPayAmount;
	}

	public double getS51() {
		return s51;
	}

	public void setS51(double s51) {
		this.s51 = s51;
	}

	public double getS52() {
		return s52;
	}

	public void setS52(double s52) {
		this.s52 = s52;
	}

	public double getS53() {
		return s53;
	}

	public void setS53(double s53) {
		this.s53 = s53;
	}

	public double getS54() {
		return s54;
	}

	public void setS54(double s54) {
		this.s54 = s54;
	}

	public double getS55() {
		return s55;
	}

	public void setS55(double s55) {
		this.s55 = s55;
	}

	public double getS56() {
		return s56;
	}

	public void setS56(double s56) {
		this.s56 = s56;
	}

	public double getS57() {
		return s57;
	}

	public void setS57(double s57) {
		this.s57 = s57;
	}

	public double getS58() {
		return s58;
	}

	public void setS58(double s58) {
		this.s58 = s58;
	}

	public double getS59() {
		return s59;
	}

	public void setS59(double s59) {
		this.s59 = s59;
	}

	public double getS60() {
		return s60;
	}

	public void setS60(double s60) {
		this.s60 = s60;
	}

	public double getS61() {
		return s61;
	}

	public void setS61(double s61) {
		this.s61 = s61;
	}

	public double getS62() {
		return s62;
	}

	public void setS62(double s62) {
		this.s62 = s62;
	}

	public double getS63() {
		return s63;
	}

	public void setS63(double s63) {
		this.s63 = s63;
	}

	public double getS64() {
		return s64;
	}

	public void setS64(double s64) {
		this.s64 = s64;
	}

	public double getS65() {
		return s65;
	}

	public void setS65(double s65) {
		this.s65 = s65;
	}

	public double getS66() {
		return s66;
	}

	public void setS66(double s66) {
		this.s66 = s66;
	}

	public double getS67() {
		return s67;
	}

	public void setS67(double s67) {
		this.s67 = s67;
	}

	public double getS68() {
		return s68;
	}

	public void setS68(double s68) {
		this.s68 = s68;
	}

	public double getS69() {
		return s69;
	}

	public void setS69(double s69) {
		this.s69 = s69;
	}

	public double getS70() {
		return s70;
	}

	public void setS70(double s70) {
		this.s70 = s70;
	}

	public double getS71() {
		return s71;
	}

	public void setS71(double s71) {
		this.s71 = s71;
	}

	public double getS72() {
		return s72;
	}

	public void setS72(double s72) {
		this.s72 = s72;
	}

	public double getS73() {
		return s73;
	}

	public void setS73(double s73) {
		this.s73 = s73;
	}

	public double getS74() {
		return s74;
	}

	public void setS74(double s74) {
		this.s74 = s74;
	}

	public double getS75() {
		return s75;
	}

	public void setS75(double s75) {
		this.s75 = s75;
	}

	public double getS76() {
		return s76;
	}

	public void setS76(double s76) {
		this.s76 = s76;
	}

	public double getS77() {
		return s77;
	}

	public void setS77(double s77) {
		this.s77 = s77;
	}

	public double getS78() {
		return s78;
	}

	public void setS78(double s78) {
		this.s78 = s78;
	}

	public double getS79() {
		return s79;
	}

	public void setS79(double s79) {
		this.s79 = s79;
	}

	public double getS80() {
		return s80;
	}

	public void setS80(double s80) {
		this.s80 = s80;
	}

	public double getS81() {
		return s81;
	}

	public void setS81(double s81) {
		this.s81 = s81;
	}

	public double getS82() {
		return s82;
	}

	public void setS82(double s82) {
		this.s82 = s82;
	}

	public double getS83() {
		return s83;
	}

	public void setS83(double s83) {
		this.s83 = s83;
	}

	public double getS84() {
		return s84;
	}

	public void setS84(double s84) {
		this.s84 = s84;
	}

	public double getS85() {
		return s85;
	}

	public void setS85(double s85) {
		this.s85 = s85;
	}

	public double getS86() {
		return s86;
	}

	public void setS86(double s86) {
		this.s86 = s86;
	}

	public double getS87() {
		return s87;
	}

	public void setS87(double s87) {
		this.s87 = s87;
	}

	public double getS88() {
		return s88;
	}

	public void setS88(double s88) {
		this.s88 = s88;
	}

	public double getS89() {
		return s89;
	}

	public void setS89(double s89) {
		this.s89 = s89;
	}

	public double getS90() {
		return s90;
	}

	public void setS90(double s90) {
		this.s90 = s90;
	}

	public double getS91() {
		return s91;
	}

	public void setS91(double s91) {
		this.s91 = s91;
	}

	public double getS92() {
		return s92;
	}

	public void setS92(double s92) {
		this.s92 = s92;
	}

	public double getS93() {
		return s93;
	}

	public void setS93(double s93) {
		this.s93 = s93;
	}

	public double getS94() {
		return s94;
	}

	public void setS94(double s94) {
		this.s94 = s94;
	}

	public double getS95() {
		return s95;
	}

	public void setS95(double s95) {
		this.s95 = s95;
	}

	public double getS96() {
		return s96;
	}

	public void setS96(double s96) {
		this.s96 = s96;
	}

	public double getS97() {
		return s97;
	}

	public void setS97(double s97) {
		this.s97 = s97;
	}

	public double getS98() {
		return s98;
	}

	public void setS98(double s98) {
		this.s98 = s98;
	}

	public double getS99() {
		return s99;
	}

	public void setS99(double s99) {
		this.s99 = s99;
	}

	public double getS100() {
		return s100;
	}

	public void setS100(double s100) {
		this.s100 = s100;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public double getSbBase() {
		return sbBase;
	}

	public void setSbBase(double sbBase) {
		this.sbBase = sbBase;
	}

	public double getGjjBase() {
		return gjjBase;
	}

	public void setGjjBase(double gjjBase) {
		this.gjjBase = gjjBase;
	}

	public double getInjuriesP() {
		return injuriesP;
	}

	public void setInjuriesP(double injuriesP) {
		this.injuriesP = injuriesP;
	}

	public double getGeshui() {
		return geshui;
	}

	public void setGeshui(double geshui) {
		this.geshui = geshui;
	}
	
	
}
