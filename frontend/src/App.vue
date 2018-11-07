<template>
  <div id="app">
    <div style="text-align: center; margin-top: 60px;">
      <h2 class="display-3" style="color: #71E4F2;">Todo? Do it now!</h2>
    </div>
    <router-view/>
    <!--     
    <Footer color="cyan lighten-4" class="font-small">
    <div class="text-center py-3 blue-grey-text">
      <container fluid>
        created by <a href="https://github.com/sexytreetrunks">Treetrunks</a>
        <br>
        referenced <a href="https://kr.vuejs.org/v2/examples/todomvc.html">TodoExample by Evan You</a> and <a href="https://blog.storyg.co/vue-js-posts/todos-tutorial">Todos-tutorial by Story's G</a>
      </container>
    </div>
    </Footer> -->
  </div>
</template>

<script>
import {Footer, Container} from 'mdbvue';

export default {
  name: 'App',
  components: {
    Container
  }
}
</script>

<style>
  .btn-pill{
    -webkit-border-radius: 16px;
    -moz-border-radius: 16px;
    border-radius: 16px;
  }
  footer {
    position:absolute;
    bottom:0;
    width:100%;
  }
</style>