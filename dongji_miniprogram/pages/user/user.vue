<template>

  <view class="my-class">
    <view class="user-info">
      <image class="user-avatar" :src="userBaseInfo.avatarUrl" mode="aspectFill"></image>
      <view class="user-info-right">
        <text class="user-nickname">{{userBaseInfo.nickname}}</text>
        <text class="user-description">{{userBaseInfo.description}}</text>
      </view>
    </view>
  
  <view class="personalTag">
    <view class="personalTag-title">个性标签</view>
    <view class="personalTag-container">
      <view class="personalTag-rect-container">
        <block v-for="(item, index) in userBaseInfo.labels.slice(0,6)" :key="index">
          <view v-if="item.active" class="personalTag-label" :style="{backgroundColor: item.color}">{{item.name}}</view>
        </block>
      </view>
      <view class="personalTag-button-container" @tap="goToPersonalityLabel">
        <view class="personalTag-button-text">去</view>
        <view class="personalTag-button-text">修</view>
        <view class="personalTag-button-text">改</view>
      </view>
    </view>
  </view>
  
  
  <view class="selectedPhotos">
    <view class="selectedPhotos-title">精选照片</view>
    <view class="selectedPhotos-row">
      <view class="selectedPhotos-left">
        <image class="selectedPhotos-image" :src="userBaseInfo.photo1url" mode="aspectFill" @tap="previewImage(userBaseInfo.photo1url)"></image>
        <image class="selectedPhotos-image" :src="userBaseInfo.photo2url" mode="aspectFill" @tap="previewImage(userBaseInfo.photo1url)"></image>
      </view>
      <view class="selectedPhotos-right" @tap="choosePhoto">
        <view class="personalTag-button-text">去</view>
        <view class="personalTag-button-text">修</view>
        <view class="personalTag-button-text">改</view>
      </view>
    </view>
  </view>
  
    <view class="user-button">
      <button @tap="tapUserInfo">个人信息</button>
      <button @tap="tapInterest">兴趣</button>
      <button>积分</button>
    </view>
  
  </view>
</template>

  
<script>
  export default {
    data() {
      return {
        userBaseInfo:{
          nickname:"",
          description:"",
          avatarUrl:"",
          labels: [
            // {name: '标签1', color: 'red', active: true},
            // {name: '标签2', color: 'blue', active: false},
            // {name: '标签3', color: 'green', active: true},
            // {name: '标签4', color: 'yellow', active: true},
            // {name: '标签5', color: 'purple', active: false},
            // {name: '标签6', color: 'orange', active: true},
            // {name: '标签7', color: 'pink', active: true},
            // {name: '标签8', color: 'black', active: false},
            // {name: '标签9', color: 'grey', active: true},
            // {name: '标签10', color: 'brown', active: true},
          ],
          photo1url:"",
          photo2url:"",
          gender: '',
          birthday: '',
          nickname: '',
          school: '',
          college: ''
        },
      };
    }, 
    
   onShow(){
    console.log("onshow")
      // this.userBaseInfo='';
      this.getUserBaseInfo();
   },
    // mounted() {
    //   this.getUserBaseInfo();
    // },
    methods:{
       //获取用户基本资料
      getUserBaseInfo(){
        console.log("[[正在获取用户基本资料]]")
        let _this=this;
        wx.request({
          url: getApp().globalData.url +'user/getUserInfo',
          method: 'GET',
          header: {
            'content-type': 'application/json',
            'Authorization': wx.getStorageSync('token')
          },
          success: (res) => {
            _this.userBaseInfo=res.data.data;
            // _this.userBaseInfo.nickname = res.data.data.nickname;
            // _this.userBaseInfo.description = res.data.data.description;
            // _this.userBaseInfo.avatarUrl = res.data.data.avatarUrl;
            // _this.userBaseInfo.labels = res.data.data.labels;
            // _this.userBaseInfo.photo1url = res.data.data.photo1url;
            // _this.userBaseInfo.photo2url = res.data.data.photo2url;
          },
          fail: (err) => {
            console.log(err);
          }
        })
      },
      //点击“基本资料”跳转
      tapUserInfo(){
        getApp().globalData.userBaseInfo = this.userBaseInfo;
        wx.navigateTo({
          url: '/pages/userInfo/userInfo'
        })
      },
      
      //点击“兴趣”跳转
      tapInterest(){
        wx.navigateTo({
          url: '/pages/interestChoose/interestChoose'
        })
      },
      
      //点击“去修改”用户标签 跳转
      goToPersonalityLabel(){
        getApp().globalData.userBaseInfo = this.userBaseInfo;
        wx.navigateTo({
          url: '/pages/personality_label/personality_label'
        })
      },
      
      //点击“修改”图片
      choosePhoto(){
        let _this=this
        wx.chooseMedia({
          count: 1,
          mediaType: ['image'],
          sourceType: ['album', 'camera'],
          success: function (res) {
            const tempFilePaths = res.tempFiles[0].tempFilePath
            //如果第一张不为空，就放到第二张

            if(_this.userBaseInfo.photo1url===""){
              console.log("放在第一张了");
              _this.userBaseInfo.photo1url=tempFilePaths
              _this.uploadPicture(tempFilePaths,1)
            }else{
              console.log("放在第二张了");
              _this.userBaseInfo.photo2url=tempFilePaths
              _this.uploadPicture(tempFilePaths,2)

            }
          }
        })
      },
      
      //上传图片
      uploadPicture(tempFilePaths,number){
        console.log("图片地址："+tempFilePaths);
        const _this=this;
        wx.uploadFile({
          url: getApp().globalData.url + 'user/uploadPhoto',
          filePath: tempFilePaths,
          name: 'file',
          header: {Authorization:wx.getStorageSync('token')} ,
          formData: {
            'number': number,
          },
          success: function (res){
            if(number===1){
              _this.userBaseInfo.photo1url=tempFilePaths
            }else{
              _this.userBaseInfo.photo2url=tempFilePaths
            
            }
            wx.showToast({
              title: '添加成功',
              icon: 'success',
              duration: 2000
            })
          },
          fail: function (res){
            wx.showToast({
              title: '添加失败',
              icon: 'none',
              duration: 2000
            })
          }
        })
      },
      //放大图片
      previewImage(url) {
        console.log(url)
          wx.previewImage({
            current: url, // 当前显示图片的链接
            urls: [this.userBaseInfo.photo1url, this.userBaseInfo.photo2url] // 需要预览的图片链接列表
          });
        }
    }
  }
</script>

<style lang="scss">
.my-class{
  //渐变背景
  background: linear-gradient(120deg, #ffeeff -10%, #ffffff 100%);
}

.user-info {
  /* background-color: aqua; */
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  height: 250rpx;
  border-bottom: 2px solid rgb(241, 203, 241);
}

.user-avatar {
  // background-color: blue;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-right: 20px;
  margin-left: 20px;
  border: 2px solid lavender;
}

.user-info-right {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.user-nickname {
  font-size: 30px;
  font-weight: bold;
  margin-bottom: 5px;
}

.user-description {
  font-size: 14px;
  color: #666;
  margin-bottom: 20px;
}

.user-button{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin-top: 20px;

}

.user-button > button:not(:last-child) {
margin-right: 10px;
}

.user-button button {
  // background-color: white;
  
  color: rgb(88, 88, 238);
  border-radius: 10px;
  padding: 10px;
  font-size: 16px;
  font-weight: bold;
  //浅紫色实线边框
  border: 1px solid rgb(88, 88, 238);
  width: 100px;
}




.personalTag {
  /* background-color: brown; */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 20rpx;
  height: 320rpx;
}

.personalTag-title {
  font-size: 40rpx;
  font-weight: bold;
  margin-bottom: 5px;
 margin-left: 0; /* 将左外边距设置为 0 */
width: 80%;
}


.personalTag-container {

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 95%;
  margin-left: auto;
  margin-right: auto;
  border: 2px solid #cb63ff;
  border-radius: 10px;
  height: 150px;
 box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3); /* 添加阴影效果 */
}


.personalTag-rect-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
  position: relative;
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  // background-color: #666;
}

.personalTag-button-container{
  width: 10%;
  // background-color: #666;
  border: none;
  color: black;
  font-size: 16px;
  text-align: center;
}

.personalTag-label {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 30px;
  border-radius: 15px;
  margin: 10px;
  color: white;
  font-size: 14px;
  font-weight: bold;
  text-align: center;
}


.personalTag-button {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 30px;
  border-radius: 15px;
  margin: 10px;
  color: white;
  font-size: 14px;
  font-weight: bold;
  text-align: center;
  background-color: transparent;
  border: 2px solid white;
  writing-mode: vertical-rl;
}










.selectedPhotos {
  /* background-color:blueviolet; */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  height: 400rpx;
}

.selectedPhotos-title {
  font-size: 40rpx;
  font-weight: bold;
  margin-bottom: 5px;
 margin-left: 0; /* 将左外边距设置为 0 */
width: 80%;
}


.selectedPhotos-row {
  /* background-color: brown; */
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-content: center;
  width: 95%;
  height: 300rpx;
  border: 2px solid #cb63ff;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3); /* 添加阴影效果 */
}

.selectedPhotos-left {
  /* background-color: chartreuse; */
  width: 90%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}


.selectedPhotos-right {
  width: 10%;
  /* background-color: chocolate; */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.selectedPhotos-button {
  background-color: transparent;
  border: none;
  color: black;
  font-size: 16px;
  text-align: center;
  margin-top: 10px;
}
.selectedPhotos-image{
  height: 250rpx;
  margin: 5rpx;
}
</style>
