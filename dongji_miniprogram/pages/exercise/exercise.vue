<template>
  <view>
    <choose_head :ismatch="ismatch" @select="handleSelect"></choose_head>
    <match v-if="page==='match'" @change_ismatch="ismatch=$event"></match>
    <supervisor v-else-if="page==='supervisor'"></supervisor>
    <exercise_before v-else-if="page==='exercise_before'" @start-exercise="handleStartExercise"></exercise_before>
    <exercise_ing v-else-if="page==='exercise_ing'" @exerciseEnd="handleEndExercise"></exercise_ing>
    <exercise_after v-else-if="page==='exercise_after'" :scheduleItem="scheduleItem" :message="message" @startNewSport="handleStartNewSport"></exercise_after>
  </view>
</template>

<script>
    import choose_head from "@/components/choose_head/choose_head.vue"
    import match from "@/components/match/match.vue"
    import supervisor from "@/components/supervisor/supervisor.vue"
    import exercise_before from "@/components/exercise_before/exercise_before.vue"
    import exercise_ing from "@/components/exercise_ing/exercise_ing.vue"
    import exercise_after from "@/components/exercise_after/exercise_after.vue"
export default {
      components: {
        choose_head,
        match,
        supervisor,
        exercise_before,
        exercise_ing,
        exercise_after
      },
    data() {
      return {
        ismatch: getApp().globalData.ismatch,
        page:"",
        time:'',
        message:"",
        scheduleItem: {
            startTime: "18:00",
            endTime: "19:00",
            sport: "跑步",
            target: "10公里"
          }
      };
    },
    methods: {
      handleSelect(page) {
        console.log(page);
        this.page=page;
      },
      handleStartExercise(res){
        console.log(res.page);
        this.page=res.page;
        this.scheduleItem=res.scheduleItem;
      },
      handleEndExercise(res){
        console.log(res.page);
        this.page=res.page;
        this.time=res.time;
        this.message=res.message;
        console.log(res);

wx.request({
          url: '后端接口地址',
          method: 'POST',
          data: {
            sport: this.scheduleItem.sport,
            target: this.scheduleItem.target,
            time: this.time,
            message: this.message
          },
          success: function (res) {
            console.log(res.data)
          }
        })


      },
      handleStartNewSport(res){
        console.log("[[handleStartNewSport]]");
        this.page=res;
      }
    
  }
}
</script>

<style lang="scss">

</style>
