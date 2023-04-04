<template>
  <view>
    <view class="date"><b>{{today}}</b></view>
    <div class="scroll-container">
      <div class="scroll-item-container" style="display: flex; flex-direction: row;">
        <view class="scroll-item" v-for="(item, index) in scrollItems" :key="index" style="width: 400rpx;" @click="handlClickItem(item,index)">
          <view class="week">{{item.week}}</view>
          <view class="day">{{item.day}}</view>
          <view class="dot" :class="{active: item.active}"></view>
        </view>
      </div>
    </div>
    <schedule_item v-for="(item, index) in scheduleItems" :key="index" :scheduleItem="item" fromPage="schedule"></schedule_item>
    <view class="add-btn" @click="handleAddSchedule">添加计划</view>
  </view>
</template>


<script>
  import schedule_item from "@/components/schedule_item/schedule_item.vue"
  export default {
    components: {
      schedule_item
    },
    data() {
      return {
        today: "",
        scrollItems: [],
        scheduleItems: [
          //假数据
          // {
          //   startTime: "18:00",
          //   endTime: "19:00",
          //   sport: "跑步",
          //   target: "10公里"
          // },
          // {
          //   startTime: "18:00",
          //   endTime: "19:00",
          //   sport: "跑步",
          //   target: "10公里"
          // }
        ],
        selectedDate: null,
        isRefresh:false
      }
    },
    
    
    //页面挂载后 获取当天时间，初始化 滚动条数据
    mounted() {
      this.getToday()
      this.initScrollItems()
    },
    
    onShow(){
      let pages = getCurrentPages();
      console.log("[[onshow]]")
      console.log(this.isRefresh);
      		let currPage = pages[pages.length - 1];
      		if(currPage.data.isRefresh){
      			// 重新获取数据
      			this.getScheduleData(this.selectedDate)//获取列表数据
      			// 每一次需要清除，否则会参数会缓存
              	currPage.__data__.isRefresh=false
      		}
    },

    onPullDownRefresh() {
  console.log('Pull down to refresh');
  // Do something to refresh the data
      this.getScheduleData(this.selectedDate);
  wx.stopPullDownRefresh();
},
    
    methods: {
      //获取当前日期
      getToday() {
        const date = new Date()
        const year = date.getFullYear()
        const month = date.getMonth() + 1
        const day = date.getDate()
        this.today = `${year}/${month}/${day}`
      },
      
      //初始化滚动条数据
      initScrollItems() {

        var weeks = ['日', '一', '二', '三', '四', '五', '六'];
        let startDate = new Date();
        let endDate = new Date();

        endDate.setDate(startDate.getDate() + 15);
        startDate.setDate(startDate.getDate() - 2)
        while ((endDate.getTime() - startDate.getTime()) > 0) {
          let month = (startDate.getMonth() + 1).toString().length === 1 ? "0" + (startDate.getMonth() + 1).toString() :
            (
              startDate.getMonth() + 1);
          let day = startDate.getDate().toString().length === 1 ? "0" + startDate.getDate() : startDate.getDate();
          // let week = weeks[startDate.getDay()];
          // dateList.push(month + "-" + day + "-周" + week);

          this.scrollItems.push({
            week: weeks[startDate.getDay()],
            day: month + "-" + day,
            active: false
          })
          startDate.setDate(startDate.getDate() + 1);
        }
        let nowDate=new Date();
        this.showDot(2);
        this.selectedDate = nowDate;
        //最后要向后端获取当前日期对应的计划
        setTimeout(() => {
          this.getScheduleData(nowDate);
        }, 1800);

      },
      
      //处理日期点击事件
      handlClickItem(item,index) {
        console.log(item);
        this.showDot(index);
        
        //当出现跨年的情况可能会出问题
        const date = new Date();
        const year = date.getFullYear();
        const month = item.day.split("-")[0];
        const day = item.day.split("-")[1];
        const dateString = `${year}-${month}-${day}`;
        const dateObject = new Date(dateString);
        // console.log("day2:"+day);
      
        //TODO 找出item所对应的Date类 item-》day->(day-现在的时间)->endDate.setDate(startDate.getDate() + 15);
        // let dataObject=new Data();
        // const day = item.day.split("-")[1];
        // dataObject.setDate()
        
        this.selectedDate = dateObject;
        // send dateObject to backend
        this.getScheduleData(dateObject);
      },
      
      //向后端发送请求，请求日期对应的计划
      getScheduleData(date) {
        console.log(date);
        // make get request to backend to retrieve schedule data
        uni.request({
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
            this.scheduleItems = res.data.data
          },
          fail: (err) => {
            console.log(err)
          }
        })
      },
      
      
      //控制dot的出现
      showDot(index) {
        this.scrollItems.forEach((item, i) => {
          if (i === index) {
            item.active = true;
          } else {
            item.active = false;
          }
        })
        
      },
      
      //处理添加计划按钮点击事件
      handleAddSchedule() {
        if (this.selectedDate) {
          //跳转到addSchedule界面
          uni.navigateTo({
            url: `/pages/addSchedule/addSchedule?date=${this.selectedDate}`
          })
        } else {
          console.log("Please select a date first.")
        }
      }
    }
  }
</script>



<style lang="scss">
  .date {
    font-size: rpx;
    margin: 20rpx 0;
    text-align: left;
    margin-left: 40rpx;

  }

  .scroll-container {
    width: 100vw;
    overflow-x: scroll;
    white-space: nowrap;
  }

  .scroll-item-container {
    display: inline-block;
  }

  .week {
    font-size: 36rpx;
    color: #666666;
  }

  .day {
    font-size: 28rpx;
    color: #333333;
  }

  .dot {
    width: 20rpx;
    height: 20rpx;
    border-radius: 50%;
    // background-color: #8A2BE2;
    margin-top: 10rpx;
  }

  .dot.active {
    background-color: #8A2BE2;
  }

  .add-btn {
    width: 400rpx;
    height: 80rpx;
    background-color: #8A2BE2;
    border-radius: 40rpx;
    color: #FFFFFF;
    font-size: 32rpx;
    text-align: center;
    line-height: 80rpx;
    margin: 0 auto;
    margin-top: 40rpx;
  }

  .scroll-item {
    // width: 90rpx;
    height: 130rpx;
    margin-right: 20rpx;
    border-radius: 10rpx;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    // border: 1px solid #8A2BE2;
    margin-left: 45rpx;
  }
</style>
