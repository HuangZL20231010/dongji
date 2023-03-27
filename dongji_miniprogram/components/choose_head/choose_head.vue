<template>
  <view class="container" :style="{paddingTop: paddingTopNum + 'px'}">
    <view class="button-wrapper">
      <button class="match-button" :class="{selected: isMatchSelected}" @click="selectMatch">{{ismatch ? '监督' : '匹配'}}</button>
      <button class="sport-button" :class="{selected: !isMatchSelected}" @click="selectSport">运动</button>
    </view>
  </view>
</template>

<script>
  export default {
    name:"choose_head",
    props: {
      ismatch: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        paddingTopNum: wx.getSystemInfoSync().statusBarHeight+7,
        isMatchSelected: true
      };
    },
    mounted() {
      if(this.ismatch){
        this.$emit('select', 'supervisor');
      }else{
        this.$emit('select', 'match');
      }
    },
    methods: {
      selectMatch() {
        if (!this.isMatchSelected) {
          this.isMatchSelected = true;
          if(this.ismatch){
            this.$emit('select', 'supervisor');
          }else{
            this.$emit('select', 'match');
          }
        }
      },
      selectSport() {
        if (this.isMatchSelected) {
          this.isMatchSelected = false;
          this.$emit('select', 'sport');
        }
      }
    }
  }
</script>

<style scoped>
  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40%;
    height: 100%;
    border-radius: 10px;
    margin: 0 auto;
  }
  .button-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 45px;
  }
  .match-button {
    flex: 1;
    height: 100%;
    border-radius: 10px 0 0 10px;
    color: #000;
    background-color: #e6e6fa;
  }
  .sport-button {
    flex: 1;
    height: 100%;
    border-radius: 0 10px 10px 0;
    color: #000;
    background-color: #e6e6fa;
  }
  .selected {
    background-color: #8a2be2;
    color: #fff;
  }
</style>
