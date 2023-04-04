<template>
  <view>
    <view class="title">请添加（选择）你的个性标签吧~</view>
    <view class="label-container">
      <view class="label-item" v-for="(label, index) in labels" :key="index" :class="{ active: label.active }"
        @click="toggleLabelActive(label)">{{ label.name }}</view>
    </view>
    <view class="add-save-label">
      <button class="add-label" @click="addCustomLabel">添加自定义标签</button>
      <button class="save-label" @click="saveLabels">保存</button>
    </view>
    <view v-if="activeLabels.length > 9" style="color: red;">最多只能选择9个标签</view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      labels: []
    };
  },
  computed: {
    activeLabels() {
      return this.labels.filter(label => label.active);
    }
  },
  methods: {
    toggleLabelActive(label) {
      if (this.activeLabels.length >= 9 && !label.active) {
        wx.showToast({
          title: '最多只能选择9个标签',
          icon: 'none'
        });
        return;
      }
      label.active = !label.active;
    },
    addCustomLabel() {
      wx.showModal({
        title: '添加自定义标签',
        editable: true,
        success: (res) => {
          if (res.confirm && res.content.trim() !== '') {
            let newLabel = { name: res.content, active: true };
            this.labels.push(newLabel);
          }
        }
      })
    },

    //保存个性标签
    saveLabels() {
      wx.showLoading({
        title: 'loading...',
        mask: true
      })
      let activeLabels = this.labels.filter(label => label.active);
      wx.request({
        url: getApp().globalData.url + 'user/savePersonalityLabel',
        method: 'POST',
        header: {
          'content-type': 'application/json',
          'Authorization': wx.getStorageSync('token')
        },
        data: activeLabels,
        success: (res) => {
          wx.hideLoading()
          if (res.data.success) {
            wx.showToast({
              title: '保存成功',
              icon: 'success',
              duration: 2000,
            })
          }
          // let pages = getCurrentPages(); //获取小程序页面栈
          // let beforePage = pages[pages.length - 2]; //获取上个页面的实例对象
          // beforePage.getUserBaseInfo(); //触发上个页面自定义的getUserBaseInfo方法
          wx.navigateBack({
            delta: 1
          })
        }
      })
    }
  },
  onLoad(options) {
    //从App中解析并获取labels
    this.labels = getApp().globalData.userBaseInfo.labels;
    console.log("[[lebels]]");
    console.log(this.labels);



    // this.labels = [
    //     {name: '健身', active: false},
    //     {name: '旅游', active: false},
    //     {name: '美食', active: false},
    //     {name: '音乐', active: false},
    //     {name: '电影', active: false},
    //     {name: '阅读', active: false},
    //     {name: '游戏', active: false},
    //     {name: '摄影', active: false},
    //     {name: '编程', active: false}
    //   ]
  }
}
</script>

<style lang="scss">
.title {
  text-align: center;
  font-size: 32rpx;
  margin-top: 40rpx;
}

.label-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin-top: 40rpx;
}

.label-item {
  width: 30%;
  height: 80rpx;
  line-height: 80rpx;
  text-align: center;
  background-color: #f5f5f5;
  border-radius: 40rpx;
  margin-bottom: 40rpx;
  font-size: 28rpx;
  color: #000;
  box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease-in-out;
}

.label-item.active {
  background-color: #E6E6FA;
  color: #000;
  box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.2);
  transform: scale(1.1);
}

.add-save-label {
  display: flex;
  justify-content: space-between;
  margin-top: 40rpx;
}

.add-label {
  background-color: #E6E6FA;
  color: #000;
  border: none;
  border-radius: 40rpx;
  padding: 20rpx 40rpx;
  font-size: 30rpx;
  box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease-in-out;
  width: 45%;
}

.add-label:hover {
  transform: scale(1.1);
}

.save-label {
  background-color: #E6E6FA;
  color: #000;
  border: none;
  border-radius: 40rpx;
  padding: 20rpx 40rpx;
  font-size: 30rpx;
  box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease-in-out;
  width: 45%;
}

.save-label:hover {
  transform: scale(1.1);
}</style>
