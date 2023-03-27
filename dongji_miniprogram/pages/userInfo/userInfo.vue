<template>
  <view>
    <view class="item" @click="editNickname">
      <view class="label">昵称</view>
      <input class="content" v-model="nickname" placeholder="请输入" />
    </view>
    <view class="item" @click="editGender">
      <view class="label">性别</view>
      <view class="content">
        {{gender || '请选择'}}
      </view>
    </view>
    <view class="item" @click="editSchool">
      <view class="label">学校</view>
      <input class="content" v-model="school" placeholder="请输入" />
    </view>
    <view class="item" @click="editCollege">
      <view class="label">学院</view>
      <input class="content" v-model="college" placeholder="请输入" />
    </view>
    <view class="item" @click="showDatePicker">
      <view class="label">生日</view>
      <view class="content">
        <picker mode="date" @change="editBirthday" :value="birthday">{{birthday || '请选择'}}</picker>
      </view>
    </view>
    <view class="item" @click="editDescription">
      <view class="label">个性描述</view>
      <input class="content" v-model="description" placeholder="请输入" maxlength="16" />
      <view v-if="description.length > 16" style="color: red;">个性描述不能超过16个字</view>
    </view>
    <view class="item" style="display: flex; justify-content: center;">
      <button @click="saveUserInfo">保存</button>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        gender: '',
        birthday: '',
        nickname: '',
        school: '',
        college: '',
        description: ''
      };
    },
    methods: {
      editNickname() {
        // code to edit nickname
      },
      editGender() {
        // code to edit gender
        let genderList = ['男', '女'];
        wx.showActionSheet({
          itemList: genderList,
          success: (res) => {
            this.gender = genderList[res.tapIndex];
          },
          fail: (res) => {
            console.log(res.errMsg);
          }
        });
      },
      editSchool() {
        // code to edit school
      },
      editCollege() {
        // code to edit college
      },
      editBirthday(e) {
        this.birthday = e.detail.value;
      },
      editDescription() {
        // code to edit description
      },
      saveUserInfo() {
        // code to save user info
        wx.request({
          //TODO 填写后端的url
          url: 'url',
          method: 'POST',
          header: {
            'Authorization': getApp().globalData.token
          },
          data: {
            gender: this.gender,
            birthday: this.birthday,
            nickname: this.nickname,
            school: this.school,
            college: this.college,
            description: this.description
          },
          success: (res) => {
            if (res.statusCode === 200) {
              wx.showToast({
                title: '保存成功',
                icon: 'success',
                duration: 2000,
                complete: () => {
                  let pages = getCurrentPages(); //获取小程序页面栈
                  let beforePage = pages[pages.length - 2]; //获取上个页面的实例对象
                  beforePage.go_update(); //触发上个页面自定义的go_update方法
                  wx.navigateBack({ //返回上一页  
                    delta: 1
                  })
                }
              });
            }
          },
          fail: (res) => {
            console.log(res.errMsg);
          }
        });
      }
    },
    onLoad(options) {
      const userBaseInfo = JSON.parse(options.userBaseInfo);
      this.nickname = userBaseInfo.nickname;
      this.gender = userBaseInfo.gender;
      this.birthday = userBaseInfo.birthday;
      this.school = userBaseInfo.school;
      this.college = userBaseInfo.college;
      this.description = userBaseInfo.description;
    }
  }
</script>

<style lang="scss">
  .item {
    display: flex;
    align-items: center;
    height: 80rpx;
    padding: 0 30rpx;
    border-bottom: 1rpx solid #e5e5e5;

    .label {
      font-size: 28rpx;
      color: #333333;
      width: 200rpx;
    }

    .content {
      flex: 1;
      cursor: pointer;
    }
  }
</style>
