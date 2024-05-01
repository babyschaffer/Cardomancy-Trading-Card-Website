<template>
  <div id="app">
    <div id="nav" class="position-fixed top-0 start-0 w-100 z-1">
      <nav-bar />
    </div>
    <router-view class="page-view z-0" />
  </div>
</template>
<script>
import NavBar from "./components/NavBar.vue";


export default {
  name: "app",
  components: {
    NavBar,
  },
};
</script>

<style>
html,
body {
  width: 100vw;
  height: 100vh;
}
.page-view {
  position: relative;
  top: 150px;
}
@media screen and (max-width: 992px) {
  .nav {
    display: relative;
  }
}

template {
  cursor: url("@/assets/Cardomancy-Cursor.png"),auto;
}

body {
  cursor: url("@/assets/Cardomancy-Cursor.png"),auto;
  /* BACKGROUND FOR CARDOMANCY */
  background: linear-gradient(-45deg, #ae9890, #a888a0, #e8b287, #4c2c2e);
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;
  text-align: center;
  background-attachment: fixed;
  
}

 @keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
} 
</style>

