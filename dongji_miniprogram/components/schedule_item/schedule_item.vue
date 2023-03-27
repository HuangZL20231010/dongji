<template>
  <view class="container">
    <view class="left">
      <view class="time" style="margin-top: 10px;">{{scheduleItem.startTime}}</view>
      <view class="time" >~</view>
      <view class="time" style="margin-bottom: 10px;">{{scheduleItem.endTime}}</view>
    </view>
    <view class="right" style="margin-left: 30px;">
      <view class="top" style="margin-top: 10px;">{{scheduleItem.sport}}</view>
      <view class="bottom" style="margin-bottom: 15px;">目标：{{scheduleItem.target}}</view>
    </view>
    <view class="delete" style="margin: auto;">×</view>
  </view>
</template>

<script>
  export default {
    name:"schedule_item",
    props: {
      scheduleItem: {
        type: Object,
        required: true,
        default: () => ({})
      }
    }
  }
</script>

<style lang="scss">
  .container{
    display: flex;
    justify-content: center;
    width: 90%;
    height: 100px;
    border-radius: 10px;
    padding: 10px;
    margin: 20px auto;
    border-width: 2px;
    border-style: solid;
    border-color: #d8bfd8;
  }
  .left{
    width: 25%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    border-right: 2px dashed #d8bfd8;
  }
  .time{
    font-size: 14px;
    color: #666666;
    text-align:center;
  }
  .right{
    width: 70%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
  }
  .top{
    font-size: 20px;
    color: #333333;
    text-align:center;
    font-weight: bold;
  }
  .bottom{
    font-size: 14px;
    color: #666666;
    text-align:center;
  }
  .delete{
    width: 5%;
    font-size: 20px;
    color: #666666;
    cursor: pointer;
  }
</style>
