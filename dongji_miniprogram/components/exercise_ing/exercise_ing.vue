<template>
  <view>
    <view class="timeLine">已运动时长：{{time}}</view>
    <button class="EndExercise" @click="endExercise">结束运动</button>
    <button class="takePhoto" @click="takePicture">
      <div class="iconWrapper">
        <image src="/static/照相机.png" class="photoImages" mode="aspectFit"></image>
      </div>
      <span>记录此刻</span>
    </button>
    <view class="poster">
      <view>结束运动后</view>
      <view>即可生成报告</view>
    </view>
  </view>
</template>

<script>
  export default {
    name: "exercise_ing",
    data() {
      return {
        time: '00:00',
        count: 0,
        timer: null,
        scope_camera:false,
        scopewritePhotosAlbum:false,
      };
    },
    mounted() {
      //计时器功能
      this.timer = setInterval(() => {
        let minutes = Math.floor(this.count / 60);
        let seconds = this.count % 60;
        this.time = `${minutes < 10 ? '0' : ''}${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
        this.count++;
      }, 1000);
    },
    beforeDestroy() {
      clearInterval(this.timer);
    },
    methods: {
      async authorizePermissions() {
        const _this = this;
        return new Promise(async (resolve, reject) => {
          try {
            const res = await wx.getSetting();
      
            if (!res.authSetting['scope.camera']) {
              console.log("正在授权camera");
              await wx.authorize({ scope: 'scope.camera' });
              _this.scope_camera = true;
            } else {
              _this.scope_camera = true;
            }
      
            if (!res.authSetting['scope.writePhotosAlbum']) {
              console.log("正在授权writePhotosAlbum");
              await wx.authorize({ scope: 'scope.writePhotosAlbum' });
              _this.scopewritePhotosAlbum = true;
            } else {
              _this.scopewritePhotosAlbum = true;
            }
      
            if (_this.scopewritePhotosAlbum && _this.scope_camera) {
              resolve(true);
            }
          } catch (error) {
            reject(error);
          }
        });
      },


      //点击了拍照按钮
     async takePicture() {
       console.log("点击了拍照按钮");
       try {
         await this.authorizePermissions();
         this.takeAndSave();
       } catch (error) {
         wx.showToast({
           title: error.message || '授权失败',
           icon: 'none',
           duration: 2000,
         });
       }
     },


      //执行拍照和保存
      takeAndSave() {
        wx.chooseImage({
          count: 1,
          sizeType: ['original', 'compressed'],
          sourceType: ['camera'],
          success: function(res) {
            const tempFilePaths = res.tempFilePaths
            wx.saveImageToPhotosAlbum({
              filePath: tempFilePaths[0],
              success(res) {
                wx.showToast({
                  title: '保存成功',
                  icon: 'success',
                  duration: 2000
                })
              },
              fail(res) {
                wx.showToast({
                  title: '保存失败',
                  icon: 'none',
                  duration: 2000
                })
              }
            })
          }
        })
      },

      //点击结束运动按钮
      endExercise() {
        const _this = this;
        wx.showModal({
          title: '结束运动',
          content: '是否结束运动？',
          success(res) {
            
            if (res.confirm) {
              clearInterval(_this.timer);
              wx.showModal({
                title: '请留下你的运动感言吧~',
                editable: true,
                placeholderText: '请输入留言',
                success(res) {
                   var message ="";
                  if (res.confirm) {
                    //获取留言
                    message = res.content;                
                  }
                  getApp().globalData.sport="exercise_after";
                  _this.$emit('exerciseEnd', {
                    
                    time: _this.time,
                    message: message,
                    page:"exercise_after"
                  });
                  
                }
              })
            }
          }
        })
      },

    }
  }
</script>

<style lang="scss">
  .timeLine {
    width: 100%;
    height: 100rpx;
    line-height: 100rpx;
    text-align: center;
    font-size: 45rpx;
    color: #26244d;
    margin-top: 20rpx;
    margin-bottom: 20rpx;
  }

  .EndExercise {
    width: 350rpx;
    height: 350rpx;
    border-radius: 50%;
    background-color: #d1cff5;
    color: #807be4;
    font-size: 60rpx;
    text-align: center;
    line-height: 350rpx;
    margin: 0x auto;
  }

  .takePhoto {
    width: 350rpx;
    border: 1px solid purple;
    border-radius: 40px;
    font-size: 40rpx;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
    margin-top: 50rpx;
  }

  .iconWrapper {
    width: 70rpx;
    height: 70rpx;
    margin-right: 10rpx;
    display: flex;
    align-items: center;
  }

  .photoImages {
    width: 100%;
    height: 100%;
  }

  .poster {
    width: 90%;
    height: 200rpx;
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    border-radius: 120rpx 120rpx 0 0;
    border: 2px solid #736ecd;
    border-bottom: none;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    font-size: 40rpx;
    text-align: center;
  }

  .poster view:first-child {
    margin-bottom: 10rpx;
  }
</style>
