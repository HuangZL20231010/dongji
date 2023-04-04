<template>
  <view>
    <view class="schedule">
      <text class="title">{{scheduleItems.length === 0 ? '今日计划已经完成啦，快去添加计划吧~' : '今日未完成计划:'}}</text>
      
      <view v-for="(scheduleItem, index) in scheduleItems" :key="index" @click="changeChoose(scheduleItem)">
        <schedule_item  :scheduleItem="scheduleItem" fromPage="exercise" ></schedule_item>
      </view>
    
    </view>

    <view class="buttonArea">
      <button class="ExerciseButton" @click="startExercise">开始运动</button>
    </view>
  </view>
</template>

<script>
  import schedule_item from '../schedule_item/schedule_item.vue'
  export default {
    name:"exercise_ing",
    data() {
      return {
        scheduleItems: [
          // {
          //   startTime: "18:00",
          //   endTime: "19:00",
          //   sport: "跑步",
          //   target: "10公里",
          //   isChoose: true
          // },
          // {
          //   startTime: "18:00",
          //   endTime: "19:00",
          //   sport: "跑步",
          //   target: "10公里",
          //   isChoose: false
          // }
        ]
      };
    },
    mounted() {
     this.getSchedules();
    },
    onShow() {
     this.getSchedules();
    },
    components: {
      schedule_item
    },
    methods:{
      getSchedules(){
        const _this = this;
        let date=new Date();
        //微信小程序的请求方式
        wx.request({
          
          url: getApp().globalData.url + 'schedule/getSchedules',
            method:"POST",
            header: {
              'content-type': 'application/json',
              'Authorization': wx.getStorageSync('token')
            },
            data:{
              'date':date.toISOString().slice(0,10)
            },
          success: (res) => {
            
            _this.scheduleItems = res.data.data;
            //将得到的数组中，增加一个字段isChoose，用来记录是否被选中，初始值为false
            console.log(_this.scheduleItems)
            _this.scheduleItems.forEach((item)=>{
              item.isChoose = false;
            })
          },
          fail: (err) => {
            console.log(err);
          }
        });
      },
      
      changeChoose(scheduleItem){
        console.log("[[changeChoose]]");
        //记录当前点击的scheduleItem的isChoose的值
        const isChoose = scheduleItem.isChoose;

        this.scheduleItems.forEach((item)=>{
          if(item.isChoose){
            item.isChoose = false;
          }
        })
        //如果isChoose为true，就变为false，如果为false，就变为true
        scheduleItem.isChoose = !isChoose;
      },
      
      startExercise(){
        console.log("[[startExercise]]");
        //判断是否选择了项目
        if(!this.scheduleItems.some((item)=>{
          return item.isChoose;
        })){
          wx.showToast({
            title: '请先选择项目',
            icon: 'none',
            duration: 2000
          })
          return;
        }
        //将globalData中的sport改成exercise_ing;
        getApp().globalData.sport = "exercise_ing";
        this.$emit('start-exercise', {
          page:'exercise_ing',
          scheduleItem:this.scheduleItems.find((item)=>{
            return item.isChoose;
          })});
      },

     
    }
  }
</script>

<style>
  .title {
    font-size: 18px;
    text-align: left;
    margin:20rpx;
  }
  .buttonArea {
    display: flex;
    justify-content: center;
    margin-top: 20rpx;
    height: 350rpx;
  }
  .ExerciseButton{
    width: 350rpx;
    height: 350rpx;
    border-radius: 50%;
    background-color: #d1cff5;
    color: #807be4;
    font-size: 60rpx;
    text-align: center;
    line-height: 350rpx;
    margin: 0 auto;
    transition: all 0.5s ease-in-out;
  }
  .ExerciseButton.clicked{
    transform: scale(0.8);
  }
  .schedule_item.fade-out{
    animation: fade-out 1s ease-in-out forwards;
  }
  @keyframes fade-out{
    from{
      opacity: 1;
      transform: translateY(0);
    }
    to{
      opacity: 0;
      transform: translateY(-100px);
    }
  }

</style>
