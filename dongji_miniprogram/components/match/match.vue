<template>
  <view class="match-container">
    <view class="match-avatar-container" style="width:20%">
      <view v-if="interests[0]" class="match-avatar1">{{interests[0]}}</view>
    </view>

    <view class="match-avatar-container" style="width:60%;">
      <button class="match-button" @click="getInvitees">{{loading ? '匹配中...' : '匹配'}}</button>
    </view>

    <view class="match-avatar-container" style="width:20%;display:flex;flex-direction:column;justify-content:space-between;">
      <view v-if="interests[1]" class="match-avatar2">{{interests[1]}}</view>
      <view v-if="interests[2]" class="match-avatar3">{{interests[2]}}</view>
    </view>
  </view>

  <view v-if="invitees.length > 0" class="match-title">匹配到的人:</view>
  <view>
    <send_match v-for="(invitee,index) in invitees" :key="index" :invitee="invitee" @send-invite="sendInvite(invitee)"></send_match>
    <!-- <view v-if="invitees.length === 0">暂无匹配用户，请稍后尝试~</view> -->
  </view>
  <view v-if="inviters.length > 0" class="match-title" >收到的邀请:</view>
  <view>
    <receive_match v-for="(inviter,index) in inviters" :key="index" :inviter="inviter" @accept-invite="acceptInvite(inviter)" @reject-invite="rejectInvite(inviter)"></receive_match>
  </view>
</template>

<script>
  import send_match from "@/components/send_match/send_match.vue"
  import receive_match from "@/components/receive_match/receive_match.vue"
  export default {
    name:"match",
    components:{
      send_match,
      receive_match
    },
    data() {
      return {
        interests: [],
        invitees: [
          {
            avatarUrl: "../../static/logo.png",
            nickname: "John",
            inviteeId: 1,
            success: true
          },

          {
            avatarUrl: "../../static/logo.png",
            nickname: "Jane",
            inviteeId: 2,
            success: false
          }
        ],
        inviters: [
          {
            avatarUrl: "../../static/logo.png",
            nickname: "John",
            inviterId: 1
          }
        ],
        loading: false
      };
    },
    mounted() {
      // this.$emit('success', true);
      this.getInviters();
      this.getInterests();
    },
    methods: {
      getInvitees() {
        console.log("[[getInvitees]]")
        this.loading = true;
        wx.request({
          url: '/invitees',
          success: (response) => {
            setTimeout(() => {
              this.invitees = response.data;
              this.loading = false;
            }, 1000);
          },
          fail: (error) => {
            console.log(error);
            this.loading = false;
          }
        })
      },
      getInviters(){
        console.log("[[getInviters]]")
        this.loading = true;
        wx.request({
          url: '/inviters',
          success: (response) => {
            this.inviters = response.data;
            this.loading = false;
          },
          fail: (error) => {
            console.log(error);
            this.loading = false;
          }
        })
      },

      //获取兴趣
      getInterests(){
        console.log("[[getInterests]]")
        wx.request({
          url: '/interests',
          success: (response) => {
            this.interests = response.data.slice(0,3);
          },
          fail: (error) => {
            console.log(error);
          }
        })
      },

      //发送邀请
      sendInvite(invitee){
        console.log("[[sendInvite]]")
        wx.request({
          url: '/send-invite',
          method: 'POST',
          data: {
            inviteeId:invitee.inviteeId
          },
          success: (response) => {
            invitee.success = true;
          },
          fail: (error) => {
            console.log(error);
          }
        })
      },

      //接收邀请
      acceptInvite(inviter){
        console.log("[[acceptInvite]]")
        wx.request({
          url: '/inviterId',
          method: 'POST',
          data: {
            inviterId:inviter.inviterId
          },
          success: (response) => {
            console.log(response);
            wx.showToast({
              title: '匹配成功',
              icon: 'success',
              duration: 2000
            })
            this.$emit('change_ismatch', ismatch);
          },
          fail: (error) => {
            console.log(error);
          }
        })
      },

      //拒绝邀请
      rejectInvite(inviter){
        console.log("[[rejectInvite]]")
        wx.request({
          url: '/inviterId',
          method: 'POST',
          data: {
            inviterId:inviter.inviterId
          },
          success: (response) => {
            console.log(response);
            wx.showToast({
              title: '拒绝成功',
              icon: 'success',
              duration: 2000
            })
            this.getInviters();
          },
          fail: (error) => {
            console.log(error);
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  .match-container {
    height: 600rpx;
    display: flex;
    flex-direction: row;
  }

  .match-avatar-container {
    display: flex;
    justify-content: center;
    align-items: center;
    
  }

  .match-avatar1 {
    width: 120rpx;
    height: 120rpx;
    border-radius: 50%;
    background-color: transparent;
    border: 1px solid #bfbcf1;
    display: flex;
    justify-content: center;
    align-items: center;
    animation: moveUpDown1 5s ease-in-out infinite;
  }

  @keyframes moveUpDown1 {
    0% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-20px);
    }
    100% {
      transform: translateY(0);
    }
  }

.match-avatar2 {
    width: 140rpx;
    height: 140rpx;
    border-radius: 50%;
    background-color: transparent;
    border: 1px solid #bfbcf1;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: -100rpx;
    animation: moveUpDown2 6s ease-in-out infinite;
  }

  @keyframes moveUpDown2 {
    0% {
      transform: translateY(0) translateX(0);
    }
    50% {
      transform: translateY(20px) translateX(10px);
    }
    100% {
      transform: translateY(0) translateX(0);
    }
  }
.match-avatar3 {
    width: 120rpx;
    height: 120rpx;
    border-radius: 50%;
    background-color: transparent;
    border: 1px solid #bfbcf1;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: -70rpx;
    animation: moveUpDown3 7s ease-in-out infinite;
  }

  @keyframes moveUpDown3 {
    0% {
      transform: translateY(0) scale(1);
    }
    50% {
      transform: translateY(-20px) scale(1.2);
    }
    100% {
      transform: translateY(0) scale(1);
    }
  }
  .match-button{
    height: 400rpx;
    width: 400rpx;
    border-radius: 50%;
    font-size: 48px;
    // font-weight: bold;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: transparent;
    border: 3px solid #807be4;
  }

  .match-title {
    width: 30%;
    font-size: 20px;
    font-weight: bold;
    margin-top: 20px;
    border-radius: 10px;
    border-top: 2px solid #bfbcf1;
    border-left: 2px solid #bfbcf1;
    border-right: 2px solid #bfbcf1;
    padding: 5px;
    text-align: center;
  }
</style>
