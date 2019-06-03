(function(){
	window.SeniorReport=function(settings){
		this.reportId = settings.reportId;
		this.target = settings.target;
		this.showTitle = settings.showTitle;
		
		
		tools.requestJsonRs(contextPath+"/teeEreportController/renderGraph.action",{sid:this.reportId},true,function(json){
			
			if(json.rtState){
				var data=json.rtData;
				
				if(data.chartTypeDesc=="pie"){//饼状图
					$(settings.target).highcharts({
				        chart: {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false
				        },
				        title: {
				            text: settings.showTitle?settings.showTitle:''
				        },
				        tooltip: {
				            headerFormat: '{series.name}<br>',
				            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
				        },
				        plotOptions: {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: true,
				                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
				                    style: {
				                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				                    }
				                }
				            }
				        },
				        series: [{
				            type: 'pie',
				            name: data.title,
				            data: data.series
				        }]
				    });
				}else if(data.chartTypeDesc=="xy"){//双轴图
					$(settings.target).highcharts({
				        chart: {
				            zoomType: 'xy'
				        },
				        title: {
				            text: settings.showTitle?settings.showTitle:''
				        },
				        xAxis: [{
				            categories: data.categories,
				            crosshair: true
				        }],
				        yAxis: [{
				            labels: {
				                format: '{value}'
				            },
				            title: {
				                text: '主轴数值'   
				            }
				        }, {
				            
				            labels: {
				                format: '{value}'
				            },
				            title: {
				                text: '次轴数值'
				            },opposite:true
				        }],
				        
				        tooltip: {
				            shared: true
				        },
				        legend: {
				            layout: 'vertical',
				            align: 'left',
				            x: 80,
				            verticalAlign: 'top',
				            y: 55,
				            floating: true,
				            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
				        },
				        series:data.series
				        	
				    });
				}else{
					$(settings.target).highcharts({
				        chart: {
				            type: data.chartTypeDesc   //column 柱狀  bar 條形圖  pie 餅圖  line 折綫圖 
				        },
				        title: {
				            text: settings.showTitle?settings.showTitle:''
				        },
				        xAxis: {
				            categories: data.categories
				        },
				        yAxis: {
				            min: 0,
				            title: {
				                text: '统计值'
				            }
				        },
				        legend: {
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'middle',
				            borderWidth: 0
				        },
				        tooltip: {
				            headerFormat: '<span style="font-size:12px;font-weight:bold">{point.key}</span><table style="font-size:12px">',
				            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
				                '<td style="padding:0"><b>{point.y}</b></td></tr>',
				            footerFormat: '</table>',
				            shared: true,
				            useHTML: true
				        },
				        credits: {
				            enabled: false
				        },
				        plotOptions: {
				            column: {
				            	cursor: 'pointer',
				                pointPadding: 0.2,
				                borderWidth: 0,
				                events: {
				                    click: function (event) {
				                    	//curObj.clickEvent(this.name,event);
				                    }
				                }
				            },
				            bar: {
				            	cursor: 'pointer',
				                pointPadding: 0.2,
				                borderWidth: 0,
				                events: {
				                    click: function (event) {
				                    	//curObj.clickEvent(this.name,event);
				                    }
				                }
				            },
				            line: {
				            	cursor: 'pointer',
				                pointPadding: 0.2,
				                borderWidth: 0,
				                events: {
				                    click: function (event) {
				                    	//curObj.clickEvent(this.name,event);
				                    }
				                }
				            },
				            scatter: {
				            	cursor: 'pointer',
				                pointPadding: 0.2,
				                borderWidth: 0,
				                events: {
				                    click: function (event) {
				                    	//curObj.clickEvent(this.name,event);
				                    }
				                }
				            },
				            area: {
				            	cursor: 'pointer',
				                pointPadding: 0.2,
				                borderWidth: 0,
				                events: {
				                    click: function (event) {
				                    	//curObj.clickEvent(this.name,event);
				                    }
				                }
				            }
				        },
				        series: data.series
				    });
				}
				
			}	
		});
	}	
})();