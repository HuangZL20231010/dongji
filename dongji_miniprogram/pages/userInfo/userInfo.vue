<template>
  <view>
   <view class="avatar-item" >
      <view class="label">头像</view>
      <button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar" 
      :style="{backgroundImage: 'url(' + avatarUrl + ')'}">
        <!-- <image class="avatar" :src="avatarUrl" mode="aspectFill"></image> -->
      </button> 
    </view>
    <view class="item" @click="editNickname">
      <view class="label">昵称</view>
      <input class="content" v-model="nickname" placeholder="请输入" />
    </view>
    <view class="item" @click="editGender">
      <view class="label">性别</view>
      <view class="content" style="height: 100%;">{{gender || '请选择'}}</view>
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
    <view class="item" style="height: 100rpx; display: flex; justify-content: center; align-items: center;">
      <button class="saveButton" @click="saveUserInfo">保存</button>
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
        description: '',
        avatarUrl:''
,
        avatarIsChanged: false,
      };
    },
    methods: {
      onChooseAvatar(e){
        console.log("onChooseAvatar")
        const { avatarUrl } =  e.detail
        this.avatarUrl = avatarUrl;
        this.avatarIsChanged = true;
      },
      
      editNickname() {
        // code to edit nickname
      },
      editGender() {
        // code to edit gender
        console.log("[[editGender]]")
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
        //先上传头像
        if(this.avatarIsChanged){
          wx.uploadFile({
            //TODO 填写后端的url
            url: getApp().globalData.url + 'user/uploadAvatar',
            filePath: this.avatarUrl,
            name: 'file',
            header: {
              'content-type': 'multipart/form-data',
              'Authorization':wx.getStorageSync('token')
            },
            success: (res) => {
              if (res.statusCode === 200) {
                this.avatarUrl = JSON.parse(res.data).data;
              }
            },
            fail: (res) => {
              console.log(res.errMsg);
            }
          })
        }
        

        //上传其他信息
        wx.request({
          //TODO 填写后端的url
          // url: 'http://localhost:8081/user/saveUserInfo',
          url: getApp().globalData.url + 'user/saveUserInfo',
          method: 'POST',
          header: {
            'content-type': 'application/json',
            'Authorization':wx.getStorageSync('token')
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
                  // let pages = getCurrentPages(); //获取小程序页面栈
                  // let beforePage = pages[pages.length - 2]; //获取上个页面的实例对象
                  // beforePage.getUserBaseInfo(); //触发上个页面自定义的getUserBaseInfo方法
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
      const userBaseInfo = getApp().globalData.userBaseInfo;
      console.log(userBaseInfo);
      this.nickname = userBaseInfo.nickname;
      this.gender = userBaseInfo.gender;
      this.birthday = userBaseInfo.birthday;
      this.school = userBaseInfo.school;
      this.college = userBaseInfo.college;
      this.description = userBaseInfo.description;
      this.avatarUrl=userBaseInfo.avatarUrl;
    }
  }
</script>

<style lang="scss">
  .item {
    display: flex;
    align-items: center;
    height: 100rpx;
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
            display: flex;
      align-items: center;
    }
  }

  .saveButton {
    // font-size: 30rpx;
    height: 80rpx;
    width: 200rpx;
    border-radius: 30rpx; 
    background-color: #4d7fff; 
    color: #fff;
    //垂直居中，水平居中
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .avatar-item{
    display: flex;
    align-items: center;
    height: 100rpx;
    padding: 0 30rpx;
    border-bottom: 1rpx solid #e5e5e5;

    .label {
      font-size: 28rpx;
      color: #333333;
      width: 200rpx;
    }

    .avatar-wrapper{
  width: 100rpx;
  height: 100%;
  border-radius: 50%;
  overflow: hidden;  // 取消注释
  margin-right: 20rpx;
  background-color: aqua;
      background-size: cover;
      background-repeat: no-repeat;
      background-position: center;
  
    .avatar{
      background-color: #4d7fff;
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }


  }


</style>
