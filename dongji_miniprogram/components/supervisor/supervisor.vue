<template>
  <view class="container">
    <view class="number-area">
      
      <view class="left-area">
        <image class="avatar" :src="partner.avatarUrl"></image>
      </view>
      
      
      <view class="middle-area">
        <view class="text-area">
          <text style="margin-bottom: 10px;">一起运动 {{togetherDay}} 天</text>
          <text style="margin-top: 10px;">积分已达{{integral}}分</text>
        </view>
      </view>
      
      
      <view class="right-area">
        <image class="avatar" :src="me.avatarUrl"></image>
      </view>
    </view>
    
    
    
    <view class="message-area">
      <oneMessage v-for="(message, index) in messages" :key="index" :message="message"></oneMessage>
    </view>
  </view>
</template>

<script>
  import oneMessage from "@/components/oneMessage/oneMessage.vue"
  export default {
    name:"supervisor",
    components: {
      oneMessage
    },
    data() {
      return {
        partner: {
          avatarUrl: ''
        },
        me: {
          avatarUrl: ''
        },
        togetherDay: 21,
        integral: 250,
        messages: [{
          type: "poster",
          sender: "me",
          head: "2023-1-3 19:30",
          posterUrl: "https://img.yzcdn.cn/vant/apple-1.jpg",
          content: null
        },{
          type: "text",
          sender: "partner",
          head:"达到了200分，解锁信息",
          posterUrl: null,
          content: "快乐宅男"
        },{
          type: "poster",
          sender: "partner",
          head: "2023-1-3 19:30",
          posterUrl: "https://img.yzcdn.cn/vant/apple-1.jpg",
          content: null
        },{
          type: "text",
          sender: "me",
          head:"达到了200分，解锁信息",
          posterUrl: null,
          content: "热爱运动：篮球"
        }]
      };
    },
    
    //获取组队信息
    mounted() {
      uni.request({
        url: '/api/getData',
        success: (res) => {
          this.partner = res.partner;
          this.partner.avatarUrl = `url(${this.partner.avatarUrl})`;
          this.me = res.me;
          this.me.avatarUrl = `url(${this.me.avatarUrl})`;
          this.togetherDay = res.togetherDay;
          this.integral = res.integral;
          this.messages = res.messages;
        }
      })
    }
  }
</script>

<style lang="scss">
  .container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
    // background-color: #a5a432;
  }
  .number-area {
    
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 360rpx;
    .left-area, .right-area {
      width: 30%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      .avatar {
        width: 180rpx;
        height: 180rpx;
        border-radius: 50%;
        border: 2px dashed #a29bfe;
      }
    }
    .middle-area {
      width: 40%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      .text-area {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        text-align: center;
        font-size: 14px;
        color: #666;
        text-shadow: 1px 1px 1px #fff;
      }
    }
  }

  .message-area{
    width: 90%;
    height: 750rpx;
    margin: 0 auto;
    //浅紫色背景，紫色边框
    background-color: #edd7ff50;
    border: 1px solid #a29bfe;
    //圆角边框
    border-radius: 10px;
    //变成一个滚动条,初始化时都处于最底部
    overflow: auto;
    margin-bottom: 30rpx;
}
  
  
</style>
