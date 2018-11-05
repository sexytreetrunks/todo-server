<template>
  <div id="app">
    <div style="text-align: center; margin-top: 60px;">
      <h2 class="display-3" style="color: #71E4F2;">Todo List</h2>
    </div>
    <router-view/>
    <footer class="d-flex fixed-bottom">
      <div class="container d-flex align-items-center justify-content-center">
        <div class="container font-weight-light">
          created by <a href="https://github.com/sexytreetrunks">Treetrunks</a>
          <br>
          referenced <a href="https://kr.vuejs.org/v2/examples/todomvc.html">TodoExample by Evan You</a> and <a href="https://blog.storyg.co/vue-js-posts/todos-tutorial">Todos-tutorial by Story's G</a>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  footer {
    background-color: #f5f5f5;
    height: 60px;
    line-height: 20px;
    font-size: 13px;
    text-align: center;
  }
</style>