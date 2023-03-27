<template>
  <view>
    <view class="title">快来添加你喜欢的运动吧！</view>
    <view class="tag-container">
      <view class="tag" v-for="(value, key) in sports" :key="key" :class="{selected: value}" @click="toggleSelect(key)" :style="{backgroundColor: value ? '#E6E6FA' : '#fff', transform: value ? 'scale(1.1)' : 'scale(1)', transition: 'all 0.3s ease-in-out'}">{{sportsMap[key]}}</view>
    </view>
    <view class="save-button" @click="saveSports">保存</view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        sports: {
          "running": false,
          "skipping": false,
          "football": false,
          "basketball": false,
          "tennis": false,
          "table_tennis": false,
          "yoga": false,
          "bodybuilding": false,
          "swimming": false
        },
        sportsMap: {
          "running": "跑步",
          "skipping": "跳绳",
          "football": "足球",
          "basketball": "篮球",
          "tennis": "网球",
          "table_tennis": "乒乓球",
          "yoga": "瑜伽",
          "bodybuilding": "健身",
          "swimming": "游泳"
        }
      };
    },
    methods: {
      toggleSelect(key) {
        this.sports[key] = !this.sports[key];
      },
      
      //保存运动到数据库
      saveSports() {
        uni.request({
          url: '/saveSports',
          method: 'POST',
          data: this.sports,
          success: (res) => {
            uni.showToast({
              title: '保存成功',
              icon: 'success',
              duration: 2000,
              success: () => {
                uni.navigateBack({
                  delta: 1
                });
              }
            });
          },
          fail: (err) => {
            console.log(err);
          }
        });
      }
    },

  
    mounted() {
      uni.request({
        url: '/data',
        success: (res) => {
          this.sports = res.data;
        },
        fail: (err) => {
          console.log(err);
        }
      });
    }
  }
</script>

<style lang="scss">
  .title {
    text-align: center;
    font-size: 32rpx;
    margin-top: 100rpx;
  }
  .tag-container {
    height: 75%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    margin-top: 100rpx;
  }
  .tag {
    width: 200rpx;
    height: 80rpx;
    line-height: 80rpx;
    text-align: center;
    border-radius: 40rpx;
    margin-bottom: 50rpx;
    font-size: 28rpx;
    color: #666;
    box-shadow: 0 2rpx 5rpx rgba(0, 0, 0, 0.1);
    background-color: #fff;
    transform: scale(1);
    transition: all 0.3s ease-in-out;
    &.selected {
      background-color: #ffca3e;
      color: #666;
      transform: scale(1.1);
    }
  }
  .save-button {
    width: 200rpx;
    height: 80rpx;
    line-height: 80rpx;
    text-align: center;
    border-radius: 40rpx;
    margin: 50rpx auto 0;
    font-size: 28rpx;
    color: #fff;
    background-color: #ffca3e;
    transform: scale(1);
    transition: all 0.3s ease-in-out;
    &:hover {
      background-color: #ffad3e;
      transform: scale(1.1);
    }
  }
</style>
