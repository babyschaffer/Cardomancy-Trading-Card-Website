<template>
  <!-- profile -->
  <div class="d-flex flex-column flex-lg-row justify-content-center">
    <div class="w-lg-50 rounded-5 mx-1 mx-lg-5 mt-lg-5" id="outer-shell">
      <div
        id="pic-username"
        class="d-flex flex-column flex-lg-row align-items-center rounded-5 m-lg-5"
      >
        <img
          v-bind:src="profile.profilePic"
          alt="Profile Image"
          class="rounded-circle border border-2 border-dark m-4"
          width="300px"
          height="auto"
        />
        <h1 class="fw-bold text-center mb-3 mb-lg-0 ms-lg-3 ps-lg-3">
          {{ profile.username.toUpperCase() }}
        </h1>
      </div>
      <div id="addFriend"
      class="d-flex flex-column"
      v-show="isFriended !== 'cannotFriend'">
        <button v-show="isFriended === true"
        v-on:click.prevent="unFriend()"
        class="btn btn-danger w-25 mx-auto p-2">
          Remove Friend
        </button>
        <button v-show="isFriended === false"
        v-on:click.prevent="addFriend()"
        class="btn btn-success w-25 mx-auto p-2">
          Add Friend
        </button>
        <button
        v-on:click.prevent="goToTradeForm"
        v-show="isFriended !== 'cannotFriend'"
        class="btn btn-success w-25 mx-auto my-2">
          Request Trade
          </button> 
      </div>
      <div
        id="aboutme"
        class="d-flex flex-column align-items-start ms-lg-5 me-lg-5 text-wrap"
      >
        <p class=" text-danger-emphasis fs-2 fw-semibold mx-auto mx-lg-0 my-0">About Me:</p>
        <p class="fs-4 px-5 px-lg-0 mx-lg-0 text-center text-lg-start">
          {{ profile.aboutMe }}
        </p>
      </div>
      <div id="friendslist" class="">
        <p class="fw-semibold fs-2 text-lg-start ms-lg-5 text-danger-emphasis ">
          Username is friends with:
        </p>
        <ul class="d-flex flex-column align-items-center d-lg-block ms-lg-3"
        v-show="profile.friends > 0">
          <li
            class="fs-4 text-capitalize ms-lg-5 text-lg-start"
            v-for="friend in profile.friends"
            v-bind:key="friend"
            @click="pushToProfile(friend)"
          >
            {{ friend }}
          </li>
        </ul>
      </div>
    </div>
    <!-- MyCollections -->
    <aside
      id="myCollections"
      class="w-sm-100 w-lg-50 align-self-lg-stretch mx-1 mx-lg-5 mt-2 mt-lg-5 rounded-5"
    >
      <p class=" text-danger-emphasis fs-2 fw-semibold pt-lg-5 px-lg-5">My Collections</p>
      <ul class="ps-lg-5 d-flex flex-column align-items-center d-lg-block">
        <li v-for="collection in collectionList"
          v-bind:collection="collection"
          v-bind:key="collection.id"
          class="p-2 text-start"
          >
          <router-link v-bind:to ="{name: 'CollectionCards', params:{id:collection.id}}"
          class="fs-4 text-capitalize text-lg-start text-dark"
          >{{ collection.name }}</router-link></li>                    
      </ul>
    </aside>    
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService.js";
import ProfileService from "../services/ProfileService.js";
import FriendService from "../services/FriendService.js";



export default {  
  created() {
    ProfileService.getAProfile(this.$route.params.username).then((response) => {
      if (response.status == 200) {
        this.profile = response.data;
        this.profile.profilePic = this.getPfp();
      }
    }),
    CollectionService.getUserCollections(this.$route.params.username).then((response) => {
      if (response.status == 200) {
        this.collectionList = response.data;
      }
    });
    this.updateIsFriended();
  },
  data() {
    return {
      profile: {
        username: "",
        profilePic: "",
        aboutMe: "",
        friends: [],
      },
      collectionList: [],
      isFriended: 'cannotFriend', //either 'cannotFriend', 'true' or 'false'
      
    };
  },
  methods: {
    getPfp() {
      switch(this.profile.profilePic) {
        case 'Logo-Default-Icon':
          return require('@/assets/Profile-Icons/Logo-Default-Icon.png');
        case 'MTG-Symbols':
          return require('@/assets/Profile-Icons/MTG-Symbols.png');
        case 'Plains-Default-Icon':
          return require('@/assets/Profile-Icons/Plains-Default-Icon.png');
        case 'Blue-Default-Icon':
          return require('@/assets/Profile-Icons/Blue-Default-Icon.png');
        case 'Swamp-Default-Icon':
          return require('@/assets/Profile-Icons/Swamp-Default-Icon.png');
        case 'Red-Default-Icon':
          return require('@/assets/Profile-Icons/Red-Default-Icon.png');
        case 'Forest-Default-Icon':
          return require('@/assets/Profile-Icons/Forest-Default-Icon.png');
      }
    },
    addFriend() {
      FriendService.addFriend(this.$route.params.username).then(()=>
        this.updateIsFriended());
        this.$router.go(this.$router.push(`/profile/${this.$route.params.username}`))
    },
    unFriend() {
      FriendService.unFriend(this.$route.params.username).then(()=>
        this.updateIsFriended());
         this.$router.go(this.$router.push(`/profile/${this.$route.params.username}`))
    },
    goToTradeForm() {
      let username = this.$route.params.username;
      this.$router.push(`/user/${username}/request-trade`);
    },

    updateIsFriended() {
    const loggedIn = this.$store.state.token != "";
    const isntSameUser = this.$store.state.user.username !== this.$route.params.username;
    console.log("isnt same user: " + isntSameUser);
      if(loggedIn && isntSameUser){
        FriendService.isFriended(this.$route.params.username).then(response => {
          if (response.status == 200) {
            this.isFriended = response.data;
          }
        });
      }
    },

    pushToProfile(username) {
      this.$router.go(this.$router.push(username));
    }
  }
};
</script>

<style scoped>
#outer-shell,
#myCollections {
  background-color: #ffffffcc;
}
li {
  list-style: square;
}
</style>
