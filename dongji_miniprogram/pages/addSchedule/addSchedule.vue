<template>
  <view>
    <view>
      <view class="section">
        <view class="section-title">运动项目</view>
        <radio-group bindchange="onSportChange">
          <label class="radio" v-for="(item, index) in sports" :key="index" >
            <radio :value="item" :checked="item === sport" style="font-size:28rpx;">{{item}}</radio>
          </label>
        </radio-group>
      </view>
    
    
    
      <view class="section">
        <view class="section-title">时间段</view>
        <view class="time-picker-group">
          <view class="time-picker">
            <text class="time-picker-label">开始时间：</text>
            <picker mode="time" :value="startTime" start="00:00" end="23:59" bindchange="onStartTimeChange">
              <text class="time-picker-value" v-if="startTime === ''" style="color: #999;">选择时间</text>
              <text class="time-picker-value" v-else>{{startTime}}</text>
            </picker>
          </view>
          <view class="time-picker">
            <text class="time-picker-label">结束时间：</text>
            <picker mode="time" :value="endTime" start="00:00" end="23:59" bindchange="onEndTimeChange">
              <text class="time-picker-value" v-if="endTime === ''" style="color: #999;">选择时间</text>
              <text class="time-picker-value" v-else>{{endTime}}</text>
            </picker>
          </view>
        </view>
      </view>
    
    
    
      <view class="section">
        <view class="section-title">重复</view>
        <picker mode="selector" :range="repeatOptions" bindchange="onRepeatChange">
          <view class="picker">
            <text v-if="repeat === ''" style="color: #999;">请点击选择</text>
            <text v-else>{{repeat}}</text>
          </view>
        </picker>
      </view>
    
     <view class="section" >
        <view class="section-title">目标</view>
        <input class="input-box" type="text" placeholder="请输入你的目标" :value="target" bindinput="onTargetChange"/>
      </view>
     
        <button class="add-button" @click="post_Schedule" >添加计划</button>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        sports: ['跑步', '跳绳', '足球', '篮球', '网球', '乒乓球', '瑜伽', '健身', '游泳'],
        sport: '',
        startTime: '',
        endTime: '',
        repeatOptions: ['不重复', '未来一周'],
        repeat: '',
        target: ''
      };
    },
    onLoad(options) {
      this.selectedDate = new Date(options.date);
      console.log("[Date]:"+this.selectedDate);
    },
      onSportChange(event) {
        this.sport = event.detail.value;
      },
  
      onStartTimeChange(event) {
        this.startTime = event.detail.value;
      },
      onEndTimeChange(event) {
        this.endTime = event.detail.value;
      },
      onRepeatChange(event) {
        this.repeat = this.repeatOptions[event.detail.value];
      },
      onTargetChange(event) {
        this.target = event.detail.value;
      },
    methods: {


      //点击按钮后将数据发送到后端，并返回上一级
      post_Schedule(e) {
        console.log("[[post_Schedule]]");
        console.log("sport"+this.sport);
        if (this.sport === '' || this.startTime === '' || this.endTime === '' || this.repeat === '' || this.target === '') {
          wx.showToast({
            title: '请填写完整信息',
            icon: 'none'
          });
          return;
        }
        let repeatValue = '';
        if (this.repeat === '不重复') {
          repeatValue = '1';
        } else if (this.repeat === '未来一周') {
          repeatValue = '2';
        }
        const data = {
          date: this.selectedDate,
          sport: this.sport,
          startTime: this.startTime,
          endTime: this.endTime,
          repeat: repeatValue,
          target: this.target
        };
        
        console.log(data);
        wx.showLoading({
          title: '加载中',
        })
        wx.request({
          url: getApp().globalData.url + 'schedule/saveSchedule',
          method: 'POST',
          header: {
            'content-type': 'application/json',
            'Authorization': wx.getStorageSync('token')
          },
          data: data,
          success(res) {
            console.log(res);
            let pages = getCurrentPages();
            let prevPage = pages[pages.length - 2];//上一个页面
            //直接调用上一个页面的setData()方法，把数据存到上一个页面中去
            prevPage.setData({
            	 isRefresh: true 
            })
            wx.hideLoading();
            wx.navigateBack({
              delta: 1
            })
          },
          fail(err) {
            console.log(err);
            wx.hideLoading();
             wx.showToast({
            title: '服务器出错啦',
            icon: 'none'
          });
          }
        });
      }
    }
  }
</script>

<style lang="scss">
  .section {
    margin-top: 20rpx;
    padding: 20rpx;
    background-color: #fff;
    border: 1px solid #d3adf7;
    padding: 10rpx;
    width: 86%;
    margin-left: 7%;
    border-radius: 10rpx;
  }

  .section-title {
    font-size: 32rpx;
    font-weight: bold;
    margin-bottom: 20rpx;
  }

  .radio {
    display: flex;
    align-items: center;
    margin-bottom: 20rpx;
    margin-left: 50rpx;
  }

  .picker {
    display: flex;
    align-items: center;
    margin-bottom: 20rpx;
  }

  .picker text {
    font-size: 28rpx;
    margin-right: 10rpx;
  }

  .time-picker-group {
    width: 90%;
    display: flex;
    justify-content: space-between;
    margin-bottom: 20rpx;

  }

  .time-picker {
    display: flex;
    align-items: center;
  }

  .time-picker-label {
    font-size: 28rpx;
    margin-right: 10rpx;
  }

  .time-picker-value {
    font-size: 28rpx;

  }

  .input-box {
    width: 89%;
    height: 80rpx;
    border-bottom: 1px solid #d3adf7;
    font-size: 28rpx;
    margin-top: 20rpx;
  }

  .add-button {
    width: 40%;
    height: 80rpx;
    background-color: #d3adf7;
    font-size: 28rpx;
    margin-top: 20rpx;
    margin-bottom: 20rpx;
    text-align: center;
    border-radius: 15rpx; 
    color: #000; 
  }
</style>
