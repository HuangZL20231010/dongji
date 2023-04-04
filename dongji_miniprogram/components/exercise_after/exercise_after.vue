<template>
  <view class="parent">
    <view class="poster" :animation="move" :style="{ backgroundImage: `url(${posterUrl})`, height: height }">

      <!-- <view class="project-date">
        <view class="project">{{ scheduleItem.sport }}</view>
        <view class="date">
          <view class="day">{{ day }}</view>
          <view class="yearAndMonth">{{ yearAndMonth }}</view>
        </view>
      </view>


      <view class="target">完成目标：{{ scheduleItem.target }}</view>


      <view class="message" v-if="message === ''">留言：{{ defaultMessages[Math.floor(Math.random() *
        defaultMessages.length)] }}</view>
      <view class="message" v-else>留言：{{ message }}</view> -->

    </view>


    <view class="btns" :hidden="btnsHide">
      <view class="btn">
        <view class="btn-text" @click="savePosterToAlbum">保存到相册</view>
      </view>
      <view class="btn" @click="startNewSport">
        <view class="btn-text">开始新运动</view>
      </view>
    </view>

    <view class="posterBefore" :hidden="posterBeforeHide">
      <view>结束运动后</view>
      <view>即可生成报告</view>
    </view>
  </view>
</template>

<script>

const app = getApp();
const { pxToRpx } = app.globalData;

export default {
  name: "exercise_after",
  data() {
    return {
      posterBeforeHide: false,
      btnsHide: true,
      move: {},
      day: null,
      yearAndMonth: "",
      defaultMessages: [
        "运动使我快乐！",
        "我运动，我健康",
      ],
      posterCanvas: null,
      height: wx.getSystemInfoSync().windowWidth*0.9*610/510+'px',
    };
  },
  props: {
    posterUrl: {
      type: String,
      default: ""
    },
    scheduleItem: {
      type: Object,
      default: {}
    }
  },
  mounted() {
    console.log("[[mounted]]")
    console.log(this.height)
    this.setDate();
    // const poster = uni.createSelectorQuery().select(".poster");
    setTimeout(() => {
      // 隐藏 posterBefore 元素
      this.posterBeforeHide = true;
      this.movefun();
    }, 1800);
  },
  methods: {
    movefun() {
      console.log("[[正在执行动画]]")
      const move = uni.createAnimation({
        duration: 2000,
        timingFunction: 'ease',
        delay: 0,
        transformOrigin: '50% 50% 0'
      });
      move.opacity(1).translateY('-80vh').step();
      // move.opacity(1).translateY('0').step({ duration: 0 });
      this.move = move.export();
      setTimeout(() => {
        // 显示按钮
        this.btnsHide = false;
      }, 1900);

    },
    setDate() {
      const date = new Date();
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      this.yearAndMonth = `${year}.${month}`;
      this.day = day;
      console.log(`${year}.${month}`);
      console.log(this.day);
    },
    startNewSport() {
      //设置globalData的sport为exercise_before
      getApp().globalData.sport = "exercise_before";
      this.$emit("startNewSport", 'exercise_before');
    },
    savePosterToAlbum() {
      wx.showLoading({
        title: '保存中...',
      });
      wx.getSetting({
        success: (res) => {
          if (!res.authSetting['scope.writePhotosAlbum']) {
            wx.authorize({
              scope: 'scope.writePhotosAlbum',
              success: () => {
                // 已经授权，可以保存图片
                this.saveToAlbum();
              },
              fail: () => {
                wx.showModal({
                  title: '保存失败',
                  content: '请开启保存图片权限',
                  showCancel: false,
                  success: () => {
                    wx.openSetting({
                      success: (res) => {
                        if (res.authSetting['scope.writePhotosAlbum']) {
                          // 用户已经开启保存图片权限，可以保存图片
                          this.saveToAlbum();
                        }
                      },
                    });
                  },
                });
              },
            });
          } else {
            // 用户已经授权，可以保存图片
            this.saveToAlbum();
          }
        },
      });
    },

    saveToAlbum(){
      wx.downloadFile({
        url: this.posterUrl,
        success: (res) => {
          if (res.statusCode === 200) {
            wx.saveImageToPhotosAlbum({
              filePath: res.tempFilePath,
              success: () => {
                wx.hideLoading();
                wx.showToast({
                  title: '保存成功',
                  icon: 'success',
                  duration: 2000,
                });
              },
              fail: (err) => {
                wx.hideLoading();
                wx.showModal({
                  title: '保存失败',
                  content: '请检查相册权限设置',
                  showCancel: false,
                });
              },
            });
          }
        },
        fail: () => {
          wx.hideLoading();
          wx.showToast({
            title: '下载失败',
            icon: 'none',
            duration: 2000,
          });
        },
      });
    }

  }

}

</script>

<style lang="scss" >
.posterBefore {
  width: 90%;
  height: 200rpx;
  position: fixed;
  bottom: 0;
  left: 5%;
  border-radius: 120rpx 120rpx 0 0;
  border: 2px solid #736ecd;
  border-bottom: none;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-size: 40rpx;
  text-align: center;

  animation: down 2s ease forwards;
}

@keyframes down {
  0% {
    opacity: 1;
    transform: translateY(0);
  }

  100% {
    opacity: 0;
    transform: translateY(200rpx);
  }
}


// .project-date {
//   display: flex;
//   justify-content: space-between;
// }

.parent {
  position: relative;
  height: 88vh;
}

.poster {
  // background-image: url('../../static/logo.png');
  //background-image设为posterUrl
  // background-image: url('https://huangzelin.oss-cn-beijing.aliyuncs.com/dongji/images/poster/42bc70f232bfe3843e4b8463ccdcb20.jpg');
  background-size: cover;
  position: absolute;
  margin-top: 90vh;
  left: 5%;
  width: 90%;
  // height: 800rpx;
  //根据屏幕宽度自适应高度
  
  // height: 403px;
  border-radius: 120rpx;
  border: 2px solid #736ecd;
  font-size: 40rpx;
  display: flex;
  justify-content: center;
  flex-direction: column;
  /* 从上到下排布 */
}

// .project-date {
//   width: 100%;
//   // background-color: #736ecd;
//   display: flex;
//   height: 40%;
//   padding-top: 50rpx;
//   flex: 1;
//   /* 左右排布 */
// }

// .project {
//   width: 60%;
//   height: 100%;
//   // background-color: #da4848;
//   text-align: center;
//   line-height: 100%;
//   font-size: 60rpx;
//   //垂直居中
//   display: flex;
//   justify-content: center;
//   align-items: center;
// }

// .date {
//   width: 30%;
//   height: 100%;
//   // background-color: #7e5656;
//   text-align: center;
//   line-height: 100%;
//   //实线边框
//   border: 2px solid #736ecd;
//   //圆角
//   border-radius: 10rpx;
// }

// .day {

//   width: 100%;
//   height: 70%;
//   display: flex;
//   justify-content: center;
//   align-items: center;
//   font-size: 80rpx;
// }

// .yearAndMonth {
//   //上边框
//   border-top: 1px solid #736ecd;
//   display: flex;
//   justify-content: center;
//   align-items: center;
// }


// .target {
//   width: 100%;
//   height: 20%;
//   // background-color: #ca9353;
//   font-size: 40rpx;
//   //垂直居中，水平不居中
//   display: flex;
//   justify-content: flex-start;
//   align-items: center;
//   padding-left: 40rpx;
// }

// .message {
//   width: 90%;
//   height: 40%;
//   // background-color: #736ecd;
//   //垂直居中，水平不居中
//   display: flex;
//   justify-content: flex-start;
//   align-items: center;
//   padding-left: 40rpx;
// }

.btns {
  position: absolute;
  bottom: 3vh;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.btn {
  margin: 0 20px;
  background-color: #8a2be2;
  border-radius: 10px;
  padding: 10px 20px;
}

.btn-text {
  color: #fff;
  font-size: 40rpx;
}
</style>
