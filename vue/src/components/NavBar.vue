<template>
<div id="nav">
  <nav
  class="navbar navbar-expand-xl border-bottom border-2 border-black p-0">
    <div class="container-fluid p-0 m-0">
      <a class="navbar-brand p-0 m-0">
        <router-link :to="{ name: 'home' }">
          <img :src="image" alt="..." height="130px" width="auto"/>
        </router-link>
      </a>
      <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav align-items-center justify-content-between w-100">
          <li class="nav-item ">              
            <router-link
            class="nav-link active text-light fs-3 ms-4 me-3"
            :to="{ name: 'home' }">
              Home
            </router-link>              
          </li>
          <li class="nav-item" v-show="isLoggedIn">
            <a
            class="nav-link text-light fs-3 me-3"
            @click="myProfile()">
              My Profile
            </a>
          </li>
          <li class="nav-item" v-if="isLoggedIn">
            <router-link
              class="nav-link text-light fs-3 me-3"
              v-bind:to="{name: 'messages'}">
              My Messages
            </router-link>
          </li>
          <li class="nav-item dropdown">
            <a
            class="nav-link dropdown-toggle text-light fs-3 z-1 me-3"
            href="#"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false">
              Manage Collections
            </a>
            <ul class="dropdown-menu shadow">
              <li>
                <router-link :to="{ name: 'myCollections' }" class="dropdown-item text-dark text-decoration-none">
                  View My Collections
                </router-link>
              </li>
              <li>
                <router-link :to="{ name: 'collectionForm' }" class="dropdown-item text-dark text-decoration-none">
                  Start New Collection
                </router-link>
              </li>
              <li>
                <hr class="dropdown-divider" />
              </li>
              <li>
                <router-link :to="{ name: 'allCollections' }" class="dropdown-item text-dark text-decoration-none">
                  View All Collections
                </router-link>
              </li>
            </ul>
          </li>
          <li class="nav-item text-">
            <a>
              <router-link
              class="nav-link text-light fs-3"
              :to="{ name: 'searchCards' }">
                Search
              </router-link>
            </a>
          </li>
          <li class="nav-item text-">
            <a>
              <router-link
              class="nav-link text-light fs-3"
              :to="{ name: 'FAQ' }">
                FAQ's
              </router-link>
            </a>
          </li>
          <li class="nav-item text-">
            <a>
              <router-link
              class="nav-link text-light fs-3"
              :to="{ name: 'About' }">
                About
              </router-link>
            </a>
          </li>

          <li class="nav-item" v-if="!isLoggedIn">
            <a v-on:click="refresh" >
              <router-link
              class="nav-link text-light fs-3"
              :to="{ name: 'login' }">
                Login
              </router-link>
            </a>
          </li>
          <li class="nav-item" v-if="isLoggedIn" >
            <a v-on:click="refresh">
              <router-link
              class="nav-link text-light fs-3"
              :to="{ name: 'logout' }">
                Logout
              </router-link>
            </a>
          </li>
          <li class="nav-item" v-if="!isLoggedIn" >
            <a>
              <router-link
              class="nav-link text-light fs-3"
              :to="{ name: 'register' }">
                Register
              </router-link>
            </a>
          </li>
        </ul>
        <img
        v-if="!isLoggedIn" 
        class="nav-img d-none d-xl-inline"
        :src="registerBubble"
        alt="register-now"
        height="130"
        width="auto" />
      </div>
    </div>
  </nav>
</div>
</template>

<script>
import image from "@/assets/Cardomancy-Logo.png";
import registerBubble from "@/assets/RegisterSuggestionBubble.png";

export default {
  data() {
    return {
      image,
      registerBubble,
      isLoggedIn: false
    };
  },
  created(){
    this.checkLoginStatus()
  },
  methods :{
    checkLoginStatus(){
      let token = this.$store.state.token;

      this.isLoggedIn = token != '';
      return this.isLoggedIn;
    },
    refresh(){
      this.$router.go(this.$router.currentRoute)
      this.checkLoginStatus;
    },
    myProfile(){
      this.$router.go(this.$router.push('/profile/'+this.$store.state.user.username));
    }
  }
};
</script>
<style >
.navbar {
  background-color: #4c2c2e;
}
.nav-img {
  position: relative;
  top: -20px;
  right: 30px; 
}

div > ul > li > a:hover {
  background-color: #e8b287;
  border-radius: 5px;
}
div > ul > li > ul > li  a:hover {
  background-color: #ae9890;
}
.navbar-toggler {
  background-color: #e8b287;
  border-color: #4c2c2e;
}
.navbar-toggler-icon {
  color: #4c2c2e;
}
</style>
