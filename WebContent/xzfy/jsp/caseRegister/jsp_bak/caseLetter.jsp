<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../../css/init1.css">
    <script src="../../js/base/jquery.1.11.3.min.js"></script>
    <title>Document</title>
    <style>
        .main-left {
            width: 100px;
            display: inline-block;
        }

        .main-right {
            width: 90%;
            height: 95%;
            display: inline-block;
            vertical-align: top;
        }

        .lanky-tab {
            width: 73px;
            height: 35px;
            margin-top: 14px;
            border: solid 1px burlywood;
        }

        .head {
            width: 100%;
            height: 51px;
            line-height: 51px;
            font-size: 18px;
            background-color: #F7F7F7;
        }

        .content {
            width: 100%;
        }
        .lanky-star {
            color: red;
            font-weight: bold;
        }
        .lanky-tr {
            height: 35px;
            font-size: 18px;
            color: #343434;
            background-color: #F7F7F7;
        }

        .lanky-th {
            line-height: 35px;
            font-size: 18px;
            color: #3376C3;
        }

        .lanky-item {
            width: 10%;
            margin: 0 auto;
            text-indent: 10px;
            vertical-align: middle;
            text-align: right;
        }

        .lanky-info {
            width: 15%;
            text-align: left;
            border: 1px solid #cfcfcf;
            background-color: #F7F7F7;
        }
        .lanky-p {
            height: 70px;
        }
        

        table {
            width: 100%;
        }

        table td,
        table th {
            border: none;
        }

        table {
            /* border-top: 1px solid #cfcfcf;
            border-left: 1px solid #cfcfcf; */
            border: 1px solid #cfcfcf;
            border-collapse: separate;
            border-spacing: 0px 10px;
        }

        table td {
            height: 30px;
            line-height: 30px;
            font-size: 15px;
            text-indent: 5px;
        }
        p{
            word-wrap:break-word;
            overflow:auto
        }
    </style>
</head>

<body onload="doInit();">
    <div class="main-left">
        <ul>
            <li class="lanky-tab">登记信息</li>
            <li class="lanky-tab"></li>
            <li class="lanky-tab"></li>
        </ul>
    </div>
    <div class="main-right">
        <div class="head">
            头部内容头部内容头部内容头部内容头部内容头部内容头部内容头部内容头部内容头部内容
        </div>
        <div class="content reg-info">
            <table>
                <tr class="lanky-tr">
                    <th class="lanky-th" colspan="8">来件/接待信息</th>
                </tr>
                <tr>
                    <td class="lanky-item">申请方式：</td>
                    <td class="lanky-info">11111111111</td>
                    <td class="lanky-item">接待日期：</td>
                    <td class="lanky-info"></td>
                    <td class="lanky-item">接待人名称：</td>
                    <td class="lanky-info"></td>
                    <td class="lanky-item">第二接待人：</td>
                    <td class="lanky-info"></td>
                </tr>
                <tr>
                    <td class="lanky-item">接待地点：</td>
                    <td class="lanky-info" colspan="7">广东省从化市”的中国“男性”身分证字</td>
                </tr>
                <tr>
                    <td class="lanky-item">复议请求：</td>
                    <td class="lanky-info">11111111111</td>
                    <td class="lanky-item">处理结果：</td>
                    <td class="lanky-info"></td>
                    <td class="lanky-item">是否接受材料：</td>
                    <td class="lanky-info">是</td>
                    <td class="lanky-item"></td>
                    <td class="lanky-info"></td>
                </tr>
                <tr>
                    <td class="lanky-item">被接待人信息：</td>
                    <td class="lanky-info" colspan="7"><p class="lanky-p">广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字</p></td>
                </tr>
                <tr>
                    <td class="lanky-item">接待情况：</td>
                    <td class="lanky-info" colspan="7"><p class="lanky-p">广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字</p></td>
                </tr>
                <tr>
                    <td class="lanky-item">材料信息：</td>
                    <td class="lanky-info" colspan="7"><p class="lanky-p">广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字广东省从化市”的中国“男性”身分证字</p></td>
                </tr>
            </table>
            
        </div>
        <div class="content">1</div>
        <div class="content">2</div>
    </div>

    <script type="text/javascript" src="../../js/caseTrial/case_trial.js"></script>
</body>

</html>