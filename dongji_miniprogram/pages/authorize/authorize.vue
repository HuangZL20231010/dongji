<template>
  <view>
    <view wx:if="{{canIUse}}">
        <view class='header'>
            <image src='/static/logo.png'></image>
        </view>
    
        <view class='content'>
            <view>申请获取以下权限</view>
            <text>获得你的公开信息(昵称，头像等)</text>
        </view>
    
        <button class='bottom' type='primary' open-type="getUserInfo" lang="zh_CN" bindgetuserinfo="bindGetUserInfo">
            授权登录
        </button>
    </view>
    
    <view wx:else>请升级微信版本</view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        //判断小程序的API，回调，参数，组件等是否在当前版本可用。
                canIUse: uni.canIUse('button.open-type.getUserInfo')
      };
    },
    methods:{
      bindGetUserInfo(e) {//点击的“拒绝”或者“允许
             if (e.detail.userInfo) {//点击了“允许”按钮，
                 var that = this;
                 requestUrl.requestUrl({//将用户信息传给后台数据库
                     url: "/xxxx/xxxx",
                     params: {
                         token: wx.getStorageSync('token'),
                         // openId: globalOpenId,//用户的唯一标识
                         nick: e.detail.userInfo.nickName,//微信昵称
                         avatarUrl: e.detail.userInfo.avatarUrl,//微信头像
                         province: e.detail.userInfo.province,//用户注册的省
                         city: e.detail.userInfo.city//用户注册的市
                     }
                 }).then((data) => {
                 // 这一步我设置的是当进入tabBar页面（除了首页)获取授权后会停留在当前界面；而进入到某个详情页面也就是除了tabBar页面授权之后会返回上一页。
                     let pages = getCurrentPages();
                     if (pages.length) {
                         if (pages.length == 1) {
                             wx.switchTab({
                                 url: '../my/my', // 个人中心页面为my，名字随便起
                             })
                         }else {
                             wx.navigateBack({
                                 delta: 1,
                             })
                         }
                     }
                 }).catch((errorMsg) => {
                     console.log(errorMsg)
                 })
                 wx.setStorageSync('userInfo', e.detail.userInfo) // 存储用户信息
             } else {
                 wx.showModal({
                     title: '警告',
                     content: '您点击了拒绝授权，将无法进入小程序，请授权之后再进入!!!',
                     showCancel: false,
                     confirmText: '返回授权',
                     success: function (res) {
                         if (res.confirm) {
                         }
                     }
                 })
             }
         }
          
    }
     
  }
</script>

<style lang="scss">
.header {
    margin: 90rpx 0 90rpx 50rpx;
    text-align: center;
    width: 650rpx;
    height: 300rpx;
    line-height: 450rpx;
}

.header image {
    width: 200rpx;
    height: 200rpx;
    border-radius: 100rpx;
}

.content {
    margin-left: 50rpx;
    margin-bottom: 90rpx;
}

.content text {
    display: block;
    color: #9d9d9d;
    margin-top: 40rpx;
}

.bottom {
    border-radius: 80rpx;
    margin: 70rpx 50rpx;
    font-size: 35rpx;
}


</style>
