<template>
  <div class="home">
    <button @click="callNativeFunc()">Submit</button>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component({})
export default class Home extends Vue {
  public callNativeFunc() {
    alert(JSON.stringify({key: "test", msg: "hoge"}))
  }
}
</script>