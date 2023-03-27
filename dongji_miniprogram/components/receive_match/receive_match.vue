<template>
  <view class="container">
    <view class="part1">
      <image class="avatar" :src="inviter.avatarUrl"></image>
    </view>
    <view class="part2">
      <view class="nickname">{{inviter.nickname}}</view>
    </view>
    <view class="part3">
      <button class="accept-btn" @click="$emit('accept-invite', inviter)">接受</button>
      <button class="reject-btn" @click="$emit('reject-invite', inviter)">拒绝</button>
    </view>
  </view>
</template>

<script>
  export default {
    name:"receive_match",
    props: {
      inviter: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        
      };
    }
  }
</script>

<style lang="scss">
  .container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border: 1px solid #bfbdf1;
    border-radius: 10px;
    padding: 10px;
  }
  .part1{
    display:flex;
    justify-content:center;
    align-items:center;
    width:20%;
  }
  .avatar{
    border: 2px solid #f59b22; 
    border-radius:50%; 
    width:50px; 
    height:50px;
  }
  .part2{
    display:flex;
    justify-content:center;
    align-items:center;
    width:35%;
  }
  .nickname{
    width:100px;
    height:30px;
    font-family: 'Microsoft YaHei'; 
    font-weight: bold;
    color: black;
  }
  .part3{
    display:flex;
    justify-content:center;
    align-items:center;
    width:45%;
  }
  .accept-btn{
    width:60px;
    height:40px;
    border-radius:20px;
    background-color: transparent;
    border:1px solid #bfbdf1;
    color:black;
    font-size:14px;
    margin-right:10px;
  }
  .reject-btn{
    width:60px;
    height:40px;
    border-radius:20px;
    background-color: transparent;
    border:1px solid #bfbdf1;
    color:black;
    font-size:14px;
  }
</style>
