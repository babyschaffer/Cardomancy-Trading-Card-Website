<template>
<div>
  <div class="d-flex flex-column me-2 justify-content-evenly" v-if="collectionList.length < 1">
      <h2 class="text-dark fs-1 text-center fw-bold title mt-5 p-5  mx-auto bg-white rounded-5 border border-1 border-white shadow"
      style="--bs-bg-opacity: 0.15" v-on:click="sendToCreate" >No Collections to View. Go Start One!</h2>
      <img v-bind:src='danceHat' v-if="!pause" v-on:click="toggleOff" class="w-25 mx-auto p-4 mt-5"/>
      </div>
      
  <div v-else>
         <h2 class="text-body-emphasis fs-8 text-center fw-bold title mt-5 p-3 mx-auto bg-white rounded-5 border border-1 border-warning shadow w-25"
      style="--bs-bg-opacity: 0.3" v-on:click="sendToCreate" >Start New Collection</h2>
      </div>
  <div class="d-flex flex-wrap me-2 justify-content-evenly" > 
     
    <div
      v-for="collection in collectionList"
      v-bind:key="collection.id"
    >
    <div  class="collectionPreview d-flex flex-column align-items-center m-4 rounded-4 text-light py-2 border border-5 border-dark">
      <h2>{{ collection.name }}</h2>
      <img
        v-on:click="displayCards(collection.id)"
        class="collection-image "
        v-bind:src="getCollectionImageURL(collection.tcgId)"
        width="200px"
        height="auto"
      />
      <p class="game fw-bolder fs-5">{{ getGameName(collection.tcgId) }}</p>
      <button class="btn" v-on:click="addToCollection(collection.id)" >Add To My Collection</button>
      <button class="btn"><router-link class="editLink"
          v-bind:to ="{name: 'edit-collection', params:{id:collection.id}}"
          >Edit Collection</router-link></button>
    </div>
    </div>
  </div>
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService.js";
import mtgImage from "@/assets/Updated MTG Collections Image Transparent.png";
import blackHat from "@/assets/Hat-Icon-Black.png";
import danceHat from "@/assets/dancingHat.gif"



export default {
  name: "collections",

  data() {
    return {
      collectionList: [],
      mtgImage,
      blackHat,
      danceHat,
      pause: false,
    };
  },

  created() {
    CollectionService.getMyCollections().then((response) => {
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

    addToCollection(collectionId){
      this.$router.push(`/collections/${collectionId}/add`)
    },

    deleteCollection(collectionId){
      confirm("Are you sure?");
      CollectionService.deleteCollection(collectionId);
      this.$router.push(`/myCollections`)
    },

    sendToCreate(){
      this.$router.push({name: 'collectionForm'})
    },
    toggleOff(){
      this.pause =!this.pause
    }
  },
};
</script>

<style scoped>
.collectionPreview {
  width: 250px;
  background-color: #4c2c2eec;  
}
button{
  color:rgb(202, 114, 114);
}
.editLink{
  color:rgb(202, 114, 114);
  text-decoration: none;
}
.editLink:hover{
  color: white;
}


</style>