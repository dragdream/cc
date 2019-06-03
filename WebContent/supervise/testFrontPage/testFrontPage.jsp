<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/coercion_direct_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
<script src="echarts.min.js"></script>
<script src="testFrontPage.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/testFrontPage/iconfont/iconfont.css" />

<title>行政强制案件填报</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>


#page1{height:200px;width:100%;margin-bottom:20px;}
#page2{height:90px;width:100%;display:table;margin-bottom:10px; }
#page3{width:100%;}
#page1 div{
height:100%;
display:inline-block;
vertical-align: -webkit-baseline-middle;
}
#page3 div{height:100%;display:inline-block;}
#page2 ul {height:100%;display:table-row;} 
#page2 ul li{
display: table-cell;
border: 1px solid #d1d1d1;
height: 100%;
text-align: center;
border-right: 1px solid #fff;
}
#page2 ul li:last-child{border-right:1px solid #d1d1d1;}

#page1 .pageleft{
    display:inline-block;
    height:100%;
    float:left;
    width:calc(100% - 280px);
}
.warn{
border:1px solid #d1d1d1;
border-radius:5px;
width:52%;
float:left;
}
.notice{
border:1px solid #d1d1d1;
border-radius:5px;
width:calc(48% - 10px);
float:right;
}
.enter{
border:1px solid #d1d1d1;
border-radius:5px;
width:270px;
float:right;
}
.data{
width:calc(100% - 240px);
border:1px solid #d1d1d1;border-radius:5px;
}
.submitted{
width:230px;
float:right;
border:1px solid #d1d1d1;border-radius:5px;
}
.moheader{
    width:100%;
    height:30px;
    display:inline-block;
    border-bottom:1px solid #e8e8e8;
    background:#fbfbfb;
}
.moheader i{
    background:#0fbbb5;
    height:20px;
    width:3px;
    display:inline-block;
    margin:0 5px 0 10px;
    vertical-align: middle;
}
.moheader span{
    display:inline-block;
    height:30px;
    line-height:30px;
    vertical-align:middle;
    font-size: 14px;
    color: #2d4c98;
}
#page2 ul li:hover{
    background-color: #E8F3FB;
    border: 1px solid #eee;
    transform: scale(1.05);
    box-shadow: 0 0 50px rgba(0,0,0,.08);
    z-index:101;
}
#page2 ul li:hover >i{
    color: #4EABEF;
}
#page2 li{
    cursor:pointer;
}
#page2 .num{
    display: inline-block;
    
    width: 100px;
    line-height: 62px;
    font-size: 24px;
    font-weight: bolder;
}
#page2 .iconfont{
    display: block;
    height: 20%;
    width: 100px;
    line-height: 20px;
    font-size: 16px;
    margin: 0 auto;
    margin-top: -14px;
}
#barDiv{
    width:100%;
    text-align:center;
}
.warnBar{
    
    display: inline-block;
    height: 170px;
    padding:15px;
    width:100%;
}
.warnBar li{
    display: inline-block;
    width: 100%;
    height: 40px;
    line-height:40px;
    background: #f5f5f5;
    margin-bottom: 10px;
    border-radius:5px;
    color:#2da7fe;
}
.warnBar li:last-child{
    margin-bottom:0px;
}
#warnData{
    float:right;
    margin-right:20px;
}
#warnType{
        margin: 0 5px;
        vertical-align: middle;
}
#warnContent{
    color:#000000;
    display: inline-block;
    width: 65%;
    vertical-align: middle;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}
.enterTb{
    width: 100%;
    margin-top: 5px;
    height: 160px;
    border-collapse: collapse;
    border: none;
    /* padding: 18px; */
}

.enterTb tr {height:50%;width:100%;}
.enterTb td {text-align: center;cursor:pointer;}
.entericon{
    display:inline-block;
    width:40px;
    height:40px;
    background-size: contain;
}
.enterTb td span{
    display:inline-block;
    width:100%;
    margin-top:5px;
}
.noticeUl{    
    list-style-type: square;
    height: 170px;
    /* margin: 4px 10px; */
    padding: 10px;

    }
.noticeUl li{
    list-style-type: square;
    margin-left: 15px;
    height: 25px;
   /*text-indent: -5px;*/
    line-height:25px;
    cursor:pointer;
}
.noticeUl a{
    color: #126dba;
    text-decoration: none;
    font-family: "Microsoft YaHei";
}
.noticeUl .noticetop a{
    color:red;
}
.noticeUl li a{
    display: inline-block;
    width: 73%;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    vertical-align:middle;
}
.noticeDate{
    width: 25%;
    text-align:right;
    display: inline-block;
    /* height: 23px; */
    vertical-align: middle;
    /* float: right; */
}
.submitted table{    
    width: 90%;
    margin: 5px auto;
}
.subDate{
    width:35%;
}
.subCity{
    width:20%;
}
.subData{
    width: 45%;
    font-weight: bolder;
    color: #6989ff;
    text-align: right;
    font-size: 13px;
}

</style>
</head>
<body  onload="doInit();" style="overflow:hidden;padding: 5px; background-color: #fff;">
    <div id="page1">
     <div class="pageleft">
      <div class="warn">
          <p class="moheader">
          <i></i>
          <span>工作提醒</span>
          </p>
          <ul class="warnBar">
          </ul>
      </div>
      <div class="notice">
          <p class="moheader">
          <i></i>
          <span>通知公告</span>
          </p>
          <ul class="noticeUl" >
             
          </ul>
      </div>
      </div>
      <div class="enter">
          <p class="moheader">
             <i></i>
             <span>快捷入口</span>
          </p>
          <table class="enterTb">
             <tr>
               <td>
                 <i class="entericon" style="background-image:url('<%=contextPath%>/common/zt_webframe/imgs/front/enter1.png');"></i>
                 <span>执法主体查询</span>
               </td>
               <td>
                 <i class="entericon" style="background-image:url('<%=contextPath%>/common/zt_webframe/imgs/front/enter2.png');"></i>
                 <span>执法人员查询</span>
               </td>
               <td>
                 <i class="entericon" style="background-image:url('<%=contextPath%>/common/zt_webframe/imgs/front/enter3.png');"></i>
                 <span>行政职权查询</span>
               </td>
             </tr>
             <tr>
               <td>
                 <i class="entericon" style="background-image:url('<%=contextPath%>/common/zt_webframe/imgs/front/enter4.png');"></i>
                 <span>简易案件维护</span>
               </td>
               <td>
                 <i class="entericon" style="background-image:url('<%=contextPath%>/common/zt_webframe/imgs/front/enter5.png');"></i>
                 <span>一般案件维护</span>
               </td>
               <td>
                 <i class="entericon" style="background-image:url('<%=contextPath%>/common/zt_webframe/imgs/front/enter6.png');"></i>
                 <span>&nbsp;</span>
               </td>
             </tr>
          </table>
      </div>  
    </div>

   

    <div id="page2">
    <ul>
    <li><span class="num" style="color:#ff845b">1253</span><i class="iconfont icon-uEAD-oc-iconfont-gl">&nbsp;执法主体</i></li>
    <li><span class="num" style="color:#20bcec">87</span><i class="iconfont icon-jianchazhifa">&nbsp;执法人员</i></li>
    <li><span class="num" style="color:#20bcec">568</span><i class="iconfont icon-qingdan">&nbsp;权力清单</i></li>
    <li><span class="num" style="color:#ff845b">523</span><i class="iconfont icon-anjian">&nbsp;监督机关</i></li></li>
    <li><span class="num" style="color:#20bcec">387</span><i class="iconfont icon-law">&nbsp;监督人员</i></li></li>
    <li><span class="num" style="color:#20bcec">326</span><i class="iconfont icon-flag">&nbsp;法律法规</i></li></li>
    </ul>
    </div>


    <div id="page3">
      <div class="data">
          <p class="moheader">
          <i></i>
          <span>各省数据报送情况</span>
          </p>
          <div id="barDiv" ></div>
      </div>
      <div class="submitted">
          <p class="moheader">
          <i></i>
          <span>最新报送情况</span>
          </p>
          <table>
             <tr>
                <td class="subDate">2019.4.18</td>
                <td class="subCity">北京市</td>
                <td class="subData">5362168</td>
             </tr>   
             <tr>
                <td class="subDate">2019.4.18</td>
                <td class="subCity">北京市</td>
                <td class="subData">5362168</td>
             </tr> 
             <tr>
                <td class="subDate">2019.4.18</td>
                <td class="subCity">北京市</td>
                <td class="subData">5362168</td>
             </tr>        
          </table>
      </div>
    </div>

<script  type="text/javascript">
    //page3高度自适应
    var page3height=$('body').height()-$('#page1').outerHeight(true)-$('#page2').outerHeight(true);
    $('#page3').css('height',page3height);
   
    //图表部分
       var barheight=page3height-30;
       $('#barDiv').css('height',barheight);

       var myChart = echarts.init(document.getElementById('barDiv')); 
       var option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            extraCssText:'width:100px;height:50px;'  //设置提示框的大小
        }, 
        grid: {
            left: '1%',
            right: '1%',
            bottom: '3%',
            
            width:'98%',
            height:'80%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['北京', '天津', '河北', '山西', '内蒙古', '辽宁', '吉林', '天津', '河北', '山西', '内蒙古', '辽宁', '吉林', '天津', '河北', '山西', '内蒙古', '辽宁', '吉林', '天津', '河北', '山西', '内蒙古', '辽宁', '吉林', '河北', '山西', '内蒙古', '辽宁', '吉林'],
                axisTick: {
                    alignWithLabel: true
                },

                axisLabel:{  
                        interval:0 ,  
                        formatter:function(val){  
                        return val.split("").join("\n");  
                        }  
                }

            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLine: {show: false}, // 控制y轴线是否显示
                splitLine: {show: true}, // 控制网格线是否显示
                axisTick: {show: false}  // 去除y轴上的刻度线
            }
        ],
        series : [
            {
                type:'bar',
                barWidth: '70%',
                data:[10, 52, 200, 334, 390, 330, 220, 52, 200, 334, 390, 330, 220, 52, 200, 334, 390, 330, 220, 52, 200, 334, 390, 330, 220, 200, 334, 390, 330, 220]
            }
        ]
    };

    

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

    window.onresize = function(){
        myChart.resize(); 
    }
</script>
</body>
</html>