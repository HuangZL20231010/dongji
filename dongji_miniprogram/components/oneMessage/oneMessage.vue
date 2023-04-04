<template>
  <view class="container">
    <view class="head">{{message.title}}</view>
    <view class="message">
      <image class="poster-left" :src="message.posterUrl"  mode="heightFix" v-if="message.type === 'poster'&&message.sender==='partner'" @click="previewImage"></image>
      <image class="poster-right" :src="message.posterUrl" mode="heightFix" v-else-if="message.type === 'poster'&&message.sender==='me'" @click="previewImage"></image>
      <view class="text-left" v-else-if="message.type === 'text'&&message.sender==='partner'">
        <view class="triangle-left"></view>
        <span class="rotationtiao-left">{{message.content}}</span>
      </view>
      <view class="text-right" v-else-if="message.type === 'text'&&message.sender==='me'">
        <view class="triangle-right"></view>
        <span class="rotationtiao-right">{{message.content}}</span>
      </view>
    </view>
  </view>
</template>

<script>
  export default {
    name:"oneMessage",
    props: {
      message: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        
      };
    },
    methods: {
        previewImage() {
          wx.previewImage({
            urls: [this.message.posterUrl],
          })
        }
      }
  }
</script>

<style lang="scss">
.container{
  display: flex;
  flex-direction: column;

  margin-top: 20rpx;
  margin-bottom: 20rpx;
  //底边虚线
  // border-bottom: 1rpx dashed #999;
}

.head{
  font-size: 30rpx;
  color: #999;
  //居中
  text-align: center;
  margin-bottom: 20rpx;
}

.poster-left{
  margin-left: 20rpx;
  width: auto;
  height: 350rpx;
  //圆角矩形
  border-radius: 20rpx;
}
.poster-right{
 
  float: right;
  width: auto;
  height: 350rpx;
  //圆角矩形
  border-radius: 20rpx;
  margin-right: 10px;
  //蓝色边框
  border: 1rpx solid rgba(240, 139, 252, 0.79);
}

.text-left{
  width: 100%;
		float: left;
		position: relative;
		padding: 0 0 0 5px;

}

.triangle-left{
		width:0;
		height:0;
		border-top:7px solid transparent;
		border-bottom:7px solid transparent;
		border-right:7px solid #e2a7ee;
		position: absolute;
		left: 0;
		top: 6px;
	}
	.rotationtiao-left{
		float: left;
		padding: 10px;
		background-color: #e2a7ee;
		font-size: 14px;
    //圆角
    border-radius: 20rpx;
    //字体大小
    font-size: 35rpx;
	}

.text-right{
  width: 100%;
		float: left;
		position: relative;
		padding: 0 5px 0 0;
    // background-color: aqua;
  }

  .triangle-right{
		width:0;
		height:0;
		border-top:7px solid transparent;
		border-bottom:7px solid transparent;
		border-left:7px solid #e2a7ee;
		position: absolute;
		right: 5px;
		top: 6px;

    // border-top:20px solid transparent;
    // border-bottom:20px solid transparent;
    // border-left:20px solid red;
	}
	.rotationtiao-right{
		float: right;
    margin-right: 5px;
		padding: 10px;
		background-color: #e2a7ee;
		font-size: 14px;
		border-radius: 2px;
    //圆角
    border-radius: 20rpx;
    //字体大小
    font-size: 35rpx;
	}

</style>
