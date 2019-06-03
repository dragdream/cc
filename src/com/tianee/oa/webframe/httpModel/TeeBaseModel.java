package com.tianee.oa.webframe.httpModel;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * BaseModel是所有的model 父类 
 * Model 是前台向后台传递参数 时候 以模型的方式接收参数
 * 也可以不用此模型 大家依据实际情况，看看使用哪种为好 不固定写法 
 * 主要目的： 快捷 方便 简单 易用
 * @author 赵鹏
 *封装一下 前台的参数模型
 *主要目的是用来传递参数
 *这是我们用spring 框架的优势 
 *学习封装一下 
 *所有的model 都可以从 BaseModel继承
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TeeBaseModel implements java.io.Serializable{

}
