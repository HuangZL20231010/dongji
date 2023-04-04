<template>
  <view>
    <choose_head :ismatch="ismatch" @select="handleSelect"></choose_head>
    <match v-if="page==='match'" @change_ismatch="handleIsMatch" :matchFresh="matchFresh"></match>
    <supervisor v-else-if="page==='supervisor'"></supervisor>
    <exercise_before v-else-if="page==='exercise_before'" @start-exercise="handleStartExercise"></exercise_before>
    <exercise_ing v-else-if="page==='exercise_ing'" @exerciseEnd="handleEndExercise"></exercise_ing>
    <exercise_after v-else-if="page==='exercise_after'" :scheduleItem="scheduleItem" :posterUrl="posterUrl" @startNewSport="handleStartNewSport"></exercise_after>
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
        scheduleItem: null,
        posterUrl: null,
        matchFresh:false,
      };
    },
    onPullDownRefresh(){
      console.log("[[onPullDownRefresh]]")
      if(this.page==="match"){
        console.log("正在刷新");
        this.matchFresh=true;
        const _this=this;
        setTimeout(function() {
          console.log(_this.matchFresh);
        _this.matchFresh=false;
                console.log(_this.matchFresh);
        }, 500);
      }

    },
 
    methods: {
      handleIsMatch(ismatch){
        this.ismatch=ismatch;
        this.page='supervisor';
      },
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
        const page=res.page;
        this.time=res.time;
        this.message=res.message;
        const _this=this;

wx.request({
          url: getApp().globalData.url + 'poster/makePoster',
          method:"POST",
          header: {
            'content-type': 'application/json',
            'Authorization': wx.getStorageSync('token')
          },

          data: {
            scheduleId: this.scheduleItem.id,
            sport: this.scheduleItem.sport,
            target: this.scheduleItem.target,
            time: this.time,
            message: this.message
          },

          success: function (res) {
            _this.posterUrl=res.data.data;
            _this.page=page;
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
