<template>
  <view class="parent">
    <view class="poster" :animation="move">

      <view class="project-date">
        <view class="project">{{ scheduleItem.sport }}</view>
        <view class="date">
          <view class="day">{{ day }}</view>
          <view class="yearAndMonth">{{ yearAndMonth }}</view>
        </view>
      </view>


      <view class="target">完成目标：{{ scheduleItem.target }}</view>


      <view class="message" v-if="message === ''">留言：{{ defaultMessages[Math.floor(Math.random() *
        defaultMessages.length)] }}</view>
      <view class="message" v-else>留言：{{ message }}</view>

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

    <!-- <canvas id="posterCanvas" canvas-id="posterCanvas" style="width: 100%; height: 100%; position: fixed; top: -9999px;"></canvas> -->

  </view>
</template>

<script>
import html2canvas from 'html2canvas';
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
        "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试 "
      ],
      posterUrl: "",
      posterCanvas: null,
    };
  },
  props: {
    message: {
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







//     //保存图片到相册
//     savePosterToAlbum() {
//       console.log("[[savePosterToAlbum]]");
//       const _this=this;
//       this.$nextTick(() => {
//         const query = uni.createSelectorQuery().in(this);
//         query.selectViewport().scrollOffset();
//         query.select('.poster').fields({
//           dataset: true,
//           size: true,
//           scrollOffset: true,
//           properties: ['scrollX', 'scrollY'],
//           computedStyle: ['margin', 'backgroundColor'],
//           context: true
//         }, (res) => {
//           console.log(res);

//           const { width, height } = res;
//           // let canvas = uni.createCanvasContext('posterCanvas', this.$scope);
//           uni.createSelectorQuery().in(this).select('#posterCanvas')
//   .fields({ node: true, size: true })
//   .exec((res) => {
//       let textCanvas = res[0].node;  // 重点1
 
//       canvas = textCanvas.getContext('2d');  // 重点2
 
//       /**至此，textCanvas，textCtx已经成功获取到，下面代码为绘图测试代码可根据自己需要修改**/
 
//       canvas.clearRect(0,0,width, height)
//       canvas.beginPath();
 
//       const bg = textCanvas.createImage();
//       bg.src =  'https://huangzelin.oss-cn-beijing.aliyuncs.com/dongji/images/poster/42bc70f232bfe3843e4b8463ccdcb20.jpg';
//       bg.onload = () => {
//         canvas.drawImage(bg, 0, 0, width, height)
//       }
      
//           // 绘制背景色
//           canvas.setFillStyle('white');
//           canvas.fillRect(0, 0, width, height);
          
//           // 绘制项目
//           this.drawText(canvas, this.scheduleItem.sport, width * 0.1, height * 0.25, 60 * pxToRpx, '#000');

//           // 绘制日期
//           this.drawText(canvas, this.day, width * 0.7, height * 0.25, 80 * pxToRpx, '#000');
//           this.drawText(canvas, this.yearAndMonth, width * 0.7, height * 0.35, 40 * pxToRpx, '#000');

//           // 绘制目标
//           this.drawText(canvas, `完成目标：${this.scheduleItem.target}`, width * 0.1, height * 0.55, 40 * pxToRpx, '#000');

//           // 绘制留言
//           const displayMessage = this.message ? this.message : this.defaultMessages[Math.floor(Math.random() * this.defaultMessages.length)];
//           this.drawText(canvas, `留言：${displayMessage}`, width * 0.1, height * 0.8, 40 * pxToRpx, '#000');

//           this.canvase=canvas;
//           setTimeout(() => {
//   console.log("正在绘图");
//   this.canvase.draw(true, () => {
//     console.log("绘制成功");
//     uni.canvasToTempFilePath({
//       canvas: _this.canvase,
//       success: (result) => {
//         console.log("转成路径成功啦！");
//         const tempFilePath = result.tempFilePath;
//         console.log(tempFilePath);

//         // 保存图片到手机相册
//         uni.saveImageToPhotosAlbum({
//           filePath: tempFilePath,
//           success() {
//             uni.showToast({
//               title: '保存成功',
//               icon: 'success',
//               duration: 2000
//             });
//           },
//           fail() {
//             uni.showToast({
//               title: '保存失败',
//               icon: 'none',
//               duration: 2000
//             });
//           }
//         });
//       },
//       fail: (err) => {
//         console.error("canvasToTempFilePath失败", err);
//       }
//     });
//   });
// }, 1000);

// })


// //           const bgImageUrl = 'https://huangzelin.oss-cn-beijing.aliyuncs.com/dongji/images/poster/42bc70f232bfe3843e4b8463ccdcb20.jpg';
// //           this.downloadImage(bgImageUrl).then((tempFilePath) => {
// //   // 绘制背景图
// //   // canvas.drawImage(tempFilePath, 0, 0, width, height);


// //           // 绘制背景色
// //           canvas.setFillStyle('white');
// //           canvas.fillRect(0, 0, width, height);
          
// //           // 绘制项目
// //           this.drawText(canvas, this.scheduleItem.sport, width * 0.1, height * 0.25, 60 * pxToRpx, '#000');

// //           // 绘制日期
// //           this.drawText(canvas, this.day, width * 0.7, height * 0.25, 80 * pxToRpx, '#000');
// //           this.drawText(canvas, this.yearAndMonth, width * 0.7, height * 0.35, 40 * pxToRpx, '#000');

// //           // 绘制目标
// //           this.drawText(canvas, `完成目标：${this.scheduleItem.target}`, width * 0.1, height * 0.55, 40 * pxToRpx, '#000');

// //           // 绘制留言
// //           const displayMessage = this.message ? this.message : this.defaultMessages[Math.floor(Math.random() * this.defaultMessages.length)];
// //           this.drawText(canvas, `留言：${displayMessage}`, width * 0.1, height * 0.8, 40 * pxToRpx, '#000');

// //           this.canvase=canvas;
// //           setTimeout(() => {
// //   console.log("正在绘图");
// //   this.canvase.draw(true, () => {
// //     console.log("绘制成功");
// //     uni.canvasToTempFilePath({
// //       canvas: _this.canvase,
// //       success: (result) => {
// //         console.log("转成路径成功啦！");
// //         const tempFilePath = result.tempFilePath;
// //         console.log(tempFilePath);

// //         // 保存图片到手机相册
// //         uni.saveImageToPhotosAlbum({
// //           filePath: tempFilePath,
// //           success() {
// //             uni.showToast({
// //               title: '保存成功',
// //               icon: 'success',
// //               duration: 2000
// //             });
// //           },
// //           fail() {
// //             uni.showToast({
// //               title: '保存失败',
// //               icon: 'none',
// //               duration: 2000
// //             });
// //           }
// //         });
// //       },
// //       fail: (err) => {
// //         console.error("canvasToTempFilePath失败", err);
// //       }
// //     });
// //   });
// // }, 1000);



// //       })
// //       .catch((error) => {
// //         console.error('Failed to load image:', error);
// //       });

//         }).exec();
//       });
//     },

//     drawText(canvas, text, x, y, fontSize, color, textAlign = 'left') {
//       canvas.setFontSize(fontSize);
//       canvas.setFillStyle(color);
//       canvas.setTextAlign(textAlign);
//       canvas.fillText(text, x, y);
//     },


//     preloadImage(url) {
//   return new Promise((resolve, reject) => {
//     uni.getImageInfo({
//       src: url,
//       success: (res) => {
//         resolve(res.path);
//       },
//       fail: (error) => {
//         reject(error);
//       },
//     });
//   });
// }
// ,
// downloadImage(url) {
//   return new Promise((resolve, reject) => {
//     uni.downloadFile({
//       url,
//       success: (res) => {
//         if (res.statusCode === 200) {
//           console.log('downloadImage success', res)
//           resolve(res.tempFilePath);
//         } else {
//           reject(new Error(`Failed to download image, status code: ${res.statusCode}`));
//         }
//       },
//       fail: (error) => {
//         reject(error);
//       },
//     });
//   });
// }






  }

}

</script>

<style lang="scss">
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
  background-image: url('https://huangzelin.oss-cn-beijing.aliyuncs.com/dongji/images/poster/42bc70f232bfe3843e4b8463ccdcb20.jpg');
  background-size: cover;
  position: absolute;
  margin-top: 90vh;
  left: 5%;
  width: 90%;
  // height: 800rpx;
  height: 65vh;
  border-radius: 120rpx;
  border: 2px solid #736ecd;
  font-size: 40rpx;
  display: flex;
  justify-content: center;
  flex-direction: column;
  /* 从上到下排布 */
}

.project-date {
  width: 100%;
  // background-color: #736ecd;
  display: flex;
  height: 40%;
  padding-top: 50rpx;
  flex: 1;
  /* 左右排布 */
}

.project {
  width: 60%;
  height: 100%;
  // background-color: #da4848;
  text-align: center;
  line-height: 100%;
  font-size: 60rpx;
  //垂直居中
  display: flex;
  justify-content: center;
  align-items: center;
}

.date {
  width: 30%;
  height: 100%;
  // background-color: #7e5656;
  text-align: center;
  line-height: 100%;
  //实线边框
  border: 2px solid #736ecd;
  //圆角
  border-radius: 10rpx;
}

.day {

  width: 100%;
  height: 70%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 80rpx;
}

.yearAndMonth {
  //上边框
  border-top: 1px solid #736ecd;
  display: flex;
  justify-content: center;
  align-items: center;
}


.target {
  width: 100%;
  height: 20%;
  // background-color: #ca9353;
  font-size: 40rpx;
  //垂直居中，水平不居中
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding-left: 40rpx;
}

.message {
  width: 90%;
  height: 40%;
  // background-color: #736ecd;
  //垂直居中，水平不居中
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding-left: 40rpx;
}

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
