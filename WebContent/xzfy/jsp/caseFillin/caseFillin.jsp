<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>案件填报</title>
    <link rel="stylesheet" href="../../css/common.css">
    <script src="../../js/base/jquery-1.9.1.min.js"></script>
    <script src="../../js/base/juicer-min.js"></script>
    <link rel="stylesheet" href="../../css/init1.css">
    <link rel="stylesheet" type="text/css" href="../../css/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../css/icon.css">
    <link rel="stylesheet" type="text/css" href="../../css/demo.css">
    <script type="text/javascript" src="../../js/base/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/base/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="case.css" />
    <link rel="stylesheet" href="../../css/common.css">
    <link rel="stylesheet" type="text/css" href="../../css/iconfont/iconfont.css" />

    <style>
        body {
            overflow-y: auto;
            overflow-x: hidden;
            padding-left: 10px;
            padding-right: 10px;
            background-color: #EFF0F4;
        }
        
        #d1 {
            margin-bottom: 40px;
            padding: 10px;
            background-color: #fff;
            margin-top: 10px;
        }
        
        .tab ul li {
            width: 20%;
        }
    </style>

</head>

<body onload="doInit();">
    <div class="tab">
        <ul>
            <li onclick="showpage(0);"><span class="case-tab case-tab-active">当事人信息</span></li>
            <li onclick="showpage(1);"><span class="case-tab">复议事项</span></li>
            <li onclick="showpage(2);"><span class="case-tab">立案信息</span></li>
            <li onclick="showpage(3);"><span class="case-tab">审理信息</span></li>
            <li onclick="showpage(4);"><span class="case-tab">案卷管理</span></li>
        </ul>
    </div>
    <!-- 当事人信息  -->
    <div class="content" id="d1">
        <div class="partyDiv">

            <p class="case-head-title">申请人</p>
            <div class="party-body">
                <div>
                    <label>申请人类别：</label>
                    <span><input type="radio" name="optradio" />   公民</span>
                    <span><input type="radio" name="optradio" />   法人或其他组织</span>
                    <span><input type="radio" name="optradio" />   个体工商户</span>
                </div>

                <div class="edit-table-div applicant-div">
                    <table id="applicant-table" class="edit-table"></table>
                    <p class="edit-table-add" onclick="applicant_insertRow();">添加</p>
                </div>
                <hr/>
                <!-- 用button控制添加的表格-->
                <input class="fy-btn blue-btn party-btn" type="button" value="添加申请人代理人" title="添加申请人代理人" onclick="add_agent();" />
                <div class="edit-table-div applicant-agent-div">
                    <table id="applicant-agent-table" class="edit-table"></table>
                </div>

            </div>
        </div>
    </div>

    <!-- 复议事项  -->
    <div class="content" id="d1">
        <div class="fyform">
            <table class="table-col4">
                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">案件编号：</label></td>
                    <td class="td-col4-content"><input type="text" class="fyinput" id="" placeholder="" /></td>
                    <td class="td-col4-title"><label class="inputlable">申请日期：</label></td>
                    <td class="td-col4-content">
                        <div class="fyinput">
                            <input class="easyui-datetimebox" style="width:100%;height: 100%;">
                        </div>
                    </td>

                </tr>

                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">申请事项：</label></td>
                    <td class="td-col4-content"><select class="fyselect">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            </select></td>
                    <td class="td-col4-title"><label class="inputlable">行政类别管理：</label></td>
                    <td class="td-col4-content"><select class="fyselect">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            </select></td>

                </tr>

                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">行政不作为：</label></td>
                    <td class="td-col4-content"> <span class="radio-group">
                            <input type="radio" name="radio-xz4" onclick="yesDuty();"/><label>是</label>
                            <input type="radio" name="radio-xz4" onclick="noDuty();" checked="ture"/><label>否</label>
                            </span>
                    </td>
                    <!-- 点击是 yesduty -->
                    <td class="td-col4-title yesduty"><label class="inputlable">不作为事项：</label></td>
                    <td class="td-col4-content yesduty"><input type="text" class="fyinput" id="" placeholder="" /></td>
                    <!-- 点击否 noduty -->
                    <td class="td-col4-title noduty"><label class="inputlable">行政类别管理：</label></td>
                    <td class="td-col4-content noduty"><select class="fyselect">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            </select></td>
                </tr>
                <!-- 点击否 noduty -->
                <tr class="tr-col4 noduty">
                    <td class="td-col4-title"><label class="inputlable">具体行政行为文号：</label></td>
                    <td class="td-col4-content"><input type="text" class="fyinput" id="" placeholder="" /></td>
                    <td class="td-col4-title"><label class="inputlable">具体行政行为做出日期：</label></td>
                    <td class="td-col4-content">
                        <div class="fyinput">
                            <input class="easyui-datetimebox" style="width:100%;height: 100%;">
                        </div>
                    </td>
                </tr>
                <!-- 点击否 noduty -->
                <tr class="tr-col4 noduty">
                    <td class="td-col4-title"><label class="inputlable">收到处罚决定书日期：</label></td>
                    <td class="td-col4-content">
                        <div class="fyinput">
                            <input class="easyui-datetimebox" style="width:100%;height: 100%;">
                        </div>
                    </td>
                    <td class="td-col4-title"><label class="inputlable">得知该具体行为途径：</label></td>
                    <td class="td-col4-content"><input type="text" class="fyinput" id="" placeholder="" /></td>
                </tr>

                <!-- 点击是 yesduty -->
                <tr class="tr-col4 yesduty">
                    <td class="td-col4-title"><label class="inputlable">申请人要求被申请人履行该项法定职责日期：</label></td>
                    <td class="td-col4-content">
                        <div class="fyinput">
                            <input class="easyui-datetimebox" style="width:100%;height: 100%;">
                        </div>
                    </td>
                    <td class="td-col4-title"></td>
                    <td class="td-col4-content"></td>
                </tr>
                <!--单列表单-->
                <tr>
                    <td class="td-col4-title"><label class="inputlable">具体行政行为：</label></td>
                    <td colspan="3"> <textarea class="rowinput" rows="3"></textarea></td>
                </tr>
                <tr>
                    <td class="td-col4-title"><label class="inputlable">行政复议请求：</label></td>
                    <td colspan="3"> <textarea class="rowinput" rows="3"></textarea></td>
                </tr>

                <tr>
                    <td class="td-col4-title"><label class="inputlable">行政复议请求：</label></td>
                    <td colspan="3"> <textarea class="rowinput" rows="3"></textarea></td>
                </tr>

                <!-- 按钮组 -->
                <tr>
                    <td class="td-col4-title"><label class="inputlable">行政复议前置：</label></td>
                    <td colspan="3"> <span class="radio-group">
                            <input type="radio" name="radio-xz1" /><label>是</label>
                            <input type="radio" name="radio-xz1" /><label>否</label>
                        </span>
                    </td>
                </tr>

                <tr>
                    <td class="td-col4-title"><label class="inputlable">申请举行听证会：</label></td>
                    <td colspan="3"> <span class="radio-group">
                             <input type="radio" name="radio-xz3" /><label>是</label>
                             <input type="radio" name="radio-xz3" /><label>否</label>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="td-col4-title"><label class="inputlable">申请赔偿：</label></td>
                    <td colspan="3"> <span class="radio-group">
                            <input type="radio" name="radio-xz4" onclick="showInput(this);"/><label>是</label>
                            <input type="radio" name="radio-xz4" onclick="hideInput(this);"/><label>否</label>
                        </span>
                        <span class="li-input">
                        <label class="lilable">赔偿请求类型：</label>
                        <input type="text" class="form-control" id="" placeholder="">
                        <label class="lilable">赔偿请求金额：</label>
                        <input type="text" class="form-control" id="" placeholder="">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="td-col4-title"><label class="inputlable">申请附带对规范性文件的审查：</label></td>
                    <td colspan="3"> <span class="radio-group">
                            <input type="radio" name="radio-xz5" onclick="showInput(this);"/><label>是</label>
                            <input type="radio" name="radio-xz5" onclick="hideInput(this);"/><label>否</label>
                        </span>
                        <span class="li-input">
                                <label class="lilable">规范性文件名称：</label>
                                <input type="text" class="form-control" id="" placeholder="">
                                </span>
                    </td>
                </tr>
            </table>
        </div>
    </div>


    <!-- 立案信息  -->
    <div class="content" id="d1">
        <div class="fyform">
            <table class="table-col4">
                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">受理结果：</label></td>
                    <td class="td-col4-content"><input type="text" class="fyinput" id="" placeholder="" /></td>
                    <td class="td-col4-title"><label class="inputlable">受理承办人：</label></td>
                    <td class="td-col4-content"><input type="text" class="fyinput" id="" placeholder="" /></td>
                </tr>

                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">案件编号：</label></td>
                    <td class="td-col4-content"><input type="text" class="fyinput" id="" placeholder="" /></td>
                    <td class="td-col4-title"><label class="inputlable">立案时间:</label></td>
                    <td class="td-col4-content">
                        <div class="fyinput">
                            <input class="easyui-datetimebox" style="width:100%;height: 100%;">
                        </div>
                    </td>
                </tr>

                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">立案意见：</label></td>
                    <td class="" colspan="3"><textarea class="rowinput" rows="5"></textarea></td>
                </tr>
                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">是否经过补办：</label></td>
                    <td class="" colspan="3">
                        <input type="radio" name="optradio" /><span>是</span>
                        <input type="radio" name="optradio" /><span>否</span>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <!-- 审理信息  -->
    <div class="content" id="d1">
        <div class="fyform">
            <table class="table-col4">
                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">结案类型：</label></td>
                    <td class="td-col4-content"><select class="fyselect">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            </select>
                    </td>
                    <td class="td-col4-title"><label class="inputlable">审理承办人：</label></td>
                    <td class="td-col4-content"><select class="fyselect">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            </select>
                    </td>
                </tr>

                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">结案时间:</label></td>
                    <td class="td-col4-content">
                        <div class="fyinput">
                            <input class="easyui-datetimebox" style="width:100%;height: 100%;">
                        </div>
                    </td>
                    <td class="td-col4-title"><label class="inputlable">结案法律文书文号：</label></td>
                    <td class="td-col4-content"><input type="text" class="fyinput" id="" placeholder="" /></td>
                </tr>

                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">处理理由：</label></td>
                    <td class="" colspan="3"><textarea class="rowinput" rows="6"></textarea></td>
                </tr>
                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">是否经过补办：</label></td>
                    <td class="" colspan="3">
                        <input type="radio" name="optradio" /><span>是</span>
                        <input type="radio" name="optradio" /><span>否</span>
                    </td>
                </tr>

                <tr class="tr-col4">
                    <td class="td-col4-title"><label class="inputlable">终止日期:</label></td>
                    <td class="td-col4-content">
                        <div class="fyinput">
                            <input class="easyui-datetimebox" style="width:100%;height: 100%;">
                        </div>
                    </td>
                    <td class="td-col4-title"><label class="inputlable">恢复日期:</label></td>
                    <td class="td-col4-content">
                        <div class="fyinput">
                            <input class="easyui-datetimebox" style="width:100%;height: 100%;">
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="content materialDiv" id="d1">

    </div>

    <div id="rp_btn" class="fr">
        <!-- 放左侧的按钮统一加在这 -->
        <span class="rp-btn-left"> 
           
        </span>

        <input id="back" class="fy-btn blue-btn" type="button" value="上一步" title="上一步" onclick="back();" />
        <input id="submit" class="fy-btn green-btn" type="button" value="提  交" title="提交" onclick="" />
        <input id="fill" class="fy-btn green-btn" type="button" value="填报完成" title="填报完成" onclick="" />
        <input id="forward" class="fy-btn blue-btn" type="button" value="下一步" title="下一步" onclick="forward();" />
        <input id="finish" class="fy-btn blue-btn" type="button" value="完成并继续填报" title="完成并继续填报" onclick="forward();" />
    </div>
    <script>
        $('.li-input').hide();
        $('.select-two').hide();
        $('.yesduty').hide();
        $('.noduty').show();

        function showInput(that) {

            $(that).parent().siblings('.li-input').show();
        }

        function hideInput(that) {

            $(that).parent().siblings('.li-input').hide();
        }

        function yesDuty() {
            $('.yesduty').show();
            $('.noduty').hide();
        }

        function noDuty() {
            $('.noduty').show();
            $('.yesduty').hide();
        }
        $(".apply-select").change(function() {
            var selected = $(".apply-select").val();
            if (selected == "当面接待") {
                $('.select-one').show();
                $('.select-two').hide();
            } else if (selected == "书面来件") {
                $('.select-one').hide();
                $('.select-two').show();
            }
        })

        $('.case-tab').click(function() {
            $('.case-tab').removeClass('case-tab-active');
            $(this).addClass('case-tab-active');
        })
    </script>
    <script src="case.js"></script>
    <script src="party.js"></script>
    <script src="case_fillin.js"></script>
    <script src="material.js"></script>
</body>

</html>