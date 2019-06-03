var optionSQRLX = {
	title : {
		text : '申请人类型分析',
		subtext : '',
		x : 'center'
	},
	toolbox : {
		show : true,
		feature : {
			dataView : {
				readOnly : false
			},
			restore : {},
			saveAsImage : {}
		}
	},
	tooltip : {
		trigger : 'item',
		formatter : "{a} <br/>{b} : {c} ({d}%)"
	},
	legend : {
		orient : 'vertical',
		left : 'left',
		data : [ '公民', '法人或其他组织', '个体工商户' ]
	},
	color:['#3781BD', '#91BB5E','#00ADC7'],
	series : [ {
		name : '',
		type : 'pie',
		radius : '55%',
		center : [ '50%', '60%' ],
		data : [ {
			value : 100,
			name : '公民'
		}, {
			value : 50,
			name : '法人或其他组织'
		}, {
			value : 80,
			name : '个体工商户'
		} ],
		itemStyle : {
			emphasis : {
				shadowBlur : 10,
				shadowOffsetX : 0,
				shadowColor : 'rgba(0, 0, 0, 0.5)'
			}
		}
	} ]
};
var optionBSQRLX = {
		title : {
			text : '被申请人类型分析',
			subtext : '',
			x : 'center'
		},
		toolbox : {
			show : true,
			feature : {
				dataView : {
					readOnly : false
				},
				restore : {},
				saveAsImage : {}
			}
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			left : 'left',
			data : [ '市级政府', '市级政府的部门', '区级政府','区级政府的部门','其他' ]
		},
		color:['#3781BD', '#3781BD','#00ADC7','#224F74','#597537'],
		series : [ {
			name : '',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [ {
				value : 100,
				name : '市级政府'
			}, {
				value : 50,
				name : '市级政府的部门'
			}, {
				value : 80,
				name : '区级政府'
			}, {
				value : 88,
				name : '区级政府的部门'
			}, {
				value : 10,
				name : '其他'
			} ],
			itemStyle : {
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				}
			}
		} ]
	};
function switchCharts(option){
	$("#tablediv_sqr").hide();
	$("#tablediv_bsqr").hide();
	$("#main").show();
	myChart.setOption(option);
}
function showTable(tablediv){
	$("#tablediv_sqr").hide();
	$("#tablediv_bsqr").hide();
	$("#main").hide();
	$("#"+tablediv).show();
}