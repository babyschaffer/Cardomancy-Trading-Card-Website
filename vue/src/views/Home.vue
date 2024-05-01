<template>
  <div class="home">
    <body>
      <div
        class="d-flex justify-content-center align-items-center mt-3 mb-3 fw-semibold"
      >
        <img :src="stars" v-if="!pause" v-on:click="toggleOff" />
        <h2
          class="bg-white p-5 rounded-5 border border-1 border-white shadow"
          style="--bs-bg-opacity: 0.35"
        >
          Welcome to Cardomancy <br /><br />
          Display Your Cards, Showcase Your Magic!
        </h2>
        <img :src="stars" v-if="!pause" v-on:click="toggleOff"  />
      </div>
      <div class="bg-white w-75 mx-auto mb-4 p-5 rounded-5 border border-1 border-white shadow d-flex justify-content-evenly"
          style="--bs-bg-opacity: 0.35"
        >
        <h4>Coming Soon:</h4>
        <h5>NEW! Friends!</h5>
        <h5>NEW! Trading Options!</h5>
        <h5>NEW! Pokémon!</h5>
        <h5>NEW! Messaging!</h5>
        <h5>NEW! Favourites!</h5>
      </div>
      <!-- bootstrap carousel -->
      <div
        id="MTG-carousel"
        class="carousel slide carousel-fade"
        data-bs-theme="dark"
      >
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img
              src="../assets/wayne-low-OvN4OkhkTLo-unsplash.jpg"
              class="d-block w-50 m-auto border border-dark border-1 rounded-5"
              alt="Man holding a Magic the Gathering Card in his hand"
            />
          </div>
          <div class="carousel-item">
            <img
              src="../assets/ryan-quintal-Vp8RnXSnJ6I-unsplash.jpg"
              class="d-block w-50 m-auto border border-dark border-1 rounded-5"
              alt="Five Magic the Gathering cards displaying each land type"
            />
          </div>
          <div class="carousel-item">
            <img
              src="../assets/depositphotos_469022238-stock-photo-bangkok-thailand-may-2021-magic.jpg"
              class="d-block w-50 m-auto border border-dark border-1 rounded-5"
              alt="Many Magic the Gatheringc cards in a pile "
            />
          </div>
        </div>
        <button
          class="carousel-control-prev"
          type="button"
          data-bs-target="#MTG-carousel"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          data-bs-target="#MTG-carousel"
          data-bs-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
      <!-- end carousel -->
    </body>
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService";
import stars from "@/assets/stars.gif";
export default {
  name: "home",
  loggedin: false,
  isLoading: true,

  // window: (onload = function () {
  //   this.checkLoginStatus();
  // }),

  data() {
    return {
      topCollections: [],
      isLoading: true,
      stars,
      pause:false
    };
  },

  created() {
    CollectionService.getFavCollections().then((response) => {
      this.topCollections = response.data;
      this.isLoading = false;
    });
  },
  methods: {
    toggleOff(){
      this.pause = !this.pause;
    }
  }
  // To DO: figure out what to do with this method
  // method: {
  //   checkLoginStatus() {
  //     let token = this.$state.state.token;
  //     if (token != "") {
  //       this.loggedin = true;
  //     } else {
  //       this.loggedin = false;
  //     }
  //   },
  // },
};
</script>


<style scoped>
</style>