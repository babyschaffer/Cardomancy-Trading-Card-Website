<template>
<div class="d-flex flex-wrap me-2 justify-content-evenly">
  <h2
          class="bg-white p-3 rounded-5 border border-1 border-white shadow w-75"
          style="--bs-bg-opacity: 0.35"
        >
          Created Collections 
          <p class="fs-5 p-4">Here you can view all user created Collections!</p>
          <br />

          <p class="fs-6 fw-bolder"><label for="viewWishlists">Include Wishlists?</label>
    <input type="checkbox"  name="viewWishlists" @change="viewWishlists = !viewWishlists"></p>
        </h2>
  
  <div class="d-flex flex-wrap me-2 justify-content-evenly align-items-stretch">
    <div
      class="collectionPreview d-flex flex-column align-items-center justify-content-between m-5 rounded-4 text-light py-2 px-3 border border-3 border-dark"
      v-for="collection in getCorrectList()"
      v-bind:key="collection.id"
    >
   
      <p class="fs-2 fw-bolder px-2 ">{{ collection.collectionName }}</p>
      <img
        v-on:click="displayCards(collection.collectionId)"
        class="collection-image"
        v-bind:src="getCollectionImageURL(collection.tcgId)"
        width="200px"
        height="auto"
      />
      <p class="game fs-5" >{{ getGameName(collection.tcgId) }}</p>
      <div class="pb-1"><router-link :to="{name: 'Profile', params: {username: collection.username}}" class="username fs-4 text-uppercase fw-semibold p-2 m-0 text-white ">{{collection.username}}</router-link></div>
      <div
        id="collectionInfo"
        class=" bg-white rounded-5 border border-1 border-white fw-bold d-flex flex-column justify-content-center align-items-start mx-1 p-4 h-auto"
        style="--bs-bg-opacity: 0.15"
      >
        <p class="my-0 py-0">Total Cards: {{ collection.quantity }}</p>
        <!-- <p class="my-0 py-0">Colors(Red:  White: Blue: Green: Black: )</p> -->
      </div>
    </div>
  </div>
    
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService.js";
import mtgImage from "@/assets/Updated MTG Collections Image Transparent.png";

export default {
  name: "collections",

  data() {
    return {
      collectionList: [],
      mtgImage,
      Wishlist : {
        name : "Wishlist"
      },
      viewWishlists : false
    };
  },

  created() {
    CollectionService.getAllCollections().then((response) => {
      this.collectionList = response.data;
      this.isLoading = false;
    });
    

  },
  methods: {
    getGameName(tcgId) {
      if (tcgId === 1) {
        return "Magic: The Gathering";
      }
    },
    getCollectionImageURL(tcgId) {
      if (tcgId == 1) {
        return mtgImage;
      }
    },

    displayCards(collectionId) {
      this.$router.push(`/collections/${collectionId}`);
    },
    
    getCorrectList() {
      if(this.viewWishlists) {
        return this.collectionList
      } else {
        return this.filteredCollections
      }
    }
  },

  computed : {
    filteredCollections : function() {
      let filteredCollections = this.collectionList
      filteredCollections = filteredCollections.filter((collection) =>
          !collection.collectionName.toLowerCase().includes(this.Wishlist.name.toLowerCase())
        );

      return filteredCollections;
    }
  }
};
</script>

<style scoped>
.collectionPreview {
  width: 275px;
  background-color: #4c2c2eec;  
}

h2 {
  color: black;
}
</style>