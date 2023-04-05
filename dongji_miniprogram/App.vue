<script>
	export default {
		onLaunch() {
		    // 展示本地存储能力
		    const logs = wx.getStorageSync('logs') || []
		    logs.unshift(Date.now())
		    wx.setStorageSync('logss', logs)
        
        
		    // 登录
		    wx.login({
		      success: res => {
		          // 获取到用户的 code 之后：res.code
		        console.log("用户的code:" + res.code);
		          // 可以传给后台，再经过解析获取用户的 openid
		        wx.request({
		          method:'POST',
		          url: getApp().globalData.url +'user/login',
		          data:{
		            code:res.code
		          },
		          header: {
		            "Content-Type": "application/x-www-form-urlencoded"
		            },
		          success: res => {
		              // 获取到用户的 token
		              console.log("token : " + res.data.data.token);
		 
		              // 将token保存为公共数据（多页面使用->全局globalData）
		              this.globalData.token = res.data.data.token
                  let ismatch=res.data.data.ismatch
                  if(ismatch==="1")this.globalData.ismatch=true;
                  else if(ismatch==="0")this.globalData.ismatch=false;
                  
		              // 将token保存在数据缓存中（下次无需重新获取token）
                  wx.setStorageSync('token', res.data.data.token)
		              // wx.setStorage({
		              //   key:'token',
		              //   data:res.data.data.token
		              // })
		          }
		          
		        })
		      }
		    });
		  },
		  
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
    globalData: {
      userInfo: null,
	  // url:'http://8.130.92.141:8081/',
	  // url:'https://soulwinter.site/',
	  url:'https://localhost:8081/',
      token:null,
	  sport:'exercise_before',
	  ismatch: false
    }
    
	}
</script>

<style>
	/*每个页面公共css */
  ::-webkit-scrollbar {
  
  
    display:none;
    width:0;
    height:0;
    color:transparent;
  }
  
</style>
