<template>
  <view class="container">
    <view class="part1">
      <image class="avatar" :src="invitee.avatarUrl"></image>
    </view>
    <view class="part2">
      <view class="nickname">{{invitee.nickname}}</view>
    </view>
    <view class="part3">
      <button class="invite-btn" :disabled="invitee.success" @click="invitee.success ? null : $emit('send-invite', invitee)">{{invitee.success ? '已发送' : '发送组队邀请'}}</button>
    </view>
  </view>
</template>

<script>
  export default {
    name:"send_match",
    props: {
      invitee: {
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
  .invite-btn{
    width:120px;
    height:40px;
    border-radius:20px;
    background-color: transparent;
    border:1px solid #bfbdf1;
    color:black;
    font-size:14px;
  }
</style>
