
var myScroll,
pullDownEl, pullDownOffset,
pullUpEl, pullUpOffset,
generatedCount = 0;

/**
 * 加载数据
 */
function loaded() {

	myScroll = null;
	pullDownEl = null;
	pullDownOffset = 0;
	pullUpOffset = 0;
	pullUpEl = null;
	pullDownEl = document.getElementById('pullDown');//往上拉获取最新数据.
	pullDownOffset = pullDownEl.offsetHeight;//获取高度
	pullUpEl = document.getElementById('pullUp');//往下拉获取更多数据	
	pullUpOffset = pullUpEl.offsetHeight;//获取高度
	myScroll = new iScroll('wrapper', {
		useTransition: true,
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('loading')) {//上方下拉获取数据后，触发事件
				pullDownEl.className = '';
				
				pullDownEl.querySelector('.pullDownLabel').innerHTML =  ten_lang.pda["msg_10"];
				loadData(1);//加载数据完成隐藏
			} else if (pullUpEl.className.match('loading')) {//下方下拉获取数据后，触发事件
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = ten_lang.pda["msg_8"];
				loadData(1);//加载数据完成隐藏
			}
		},
		onScrollMove: function () {
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = ten_lang.pda["msg_6"];
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '获取最新...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = ten_lang.pda["msg_6"];
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '获取更多...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			
			if (pullDownEl.className.match('flip')) {//上面下拉，获取最新
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = ten_lang.pda["msg_2"];	
				loadData(0);//加载数据
				pullDownAction();	// Execute custom function (ajax call?)加载数据
			} else if (pullUpEl.className.match('flip')) {//上面下来，获取之前历史记录
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = ten_lang.pda["msg_2"];		
				loadData(0);//加载数据
				pullUpAction();	// Execute custom function (ajax call?)
	
			}
		}
	});
	document.getElementById('wrapper').style.left = '0';
	loadData(1);//加载数据完成隐藏  
	
	 
	//setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 10);
}

document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);