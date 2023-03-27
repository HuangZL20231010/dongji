<template>
  <view>
    <choose_head :ismatch="ismatch" @select="handleSelect"></choose_head>
    <match v-if="page==='match'" @change_ismatch="ismatch=$event"></match>
    <supervisor v-if="page==='supervisor'"></supervisor>
    <exercise_before v-if="page==='sport'"></exercise_before>
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
        // ismatch: getApp().globalData.ismatch
        page:"sport",
        ismatch: true
      };
    },
    methods: {
      handleSelect(page) {
        console.log(page);
        this.page=page;
      }
    }
  }
</script>

<style lang="scss">

</style>
