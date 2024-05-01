<template>
<div id="ch" class="pt-5">
  <form class="pt-5">
    <div class="form-input-group pb-3">
      <label class="form-label pb-1" for="CN">Declare your new Collection below</label>
      <input class="form-control w-25 p-2 mx-auto text-center" type="text" name="collectionName" id="CN" placeholder="~my new favorite collection~" v-model="collection.name" required autofocus/>
    </div>
    <div class="form-input-group pt-3 pb-3">
      <label class="form-label pb-1"  for="tcg">Choose your TCG</label>
      <select name="tcg" v-model="collection.tcgId" class="form-control w-25 p-2 mx-auto text-center" required >
        <option value="0">Choose a game!</option>
        <option value="1" class="">Magic The Gathering</option>
      </select>
    </div>
    <button class="btn btn-dark" type="submit" @click.prevent="saveCollection">Create</button>
  </form>
  <!-- <img :src="catHat"/> -->
  </div>
</template>

<script>
import collectionService from "../services/CollectionService";
import mtgImage from "@/assets/Updated MTG Collections Image Transparent.png";
import danceHat from "@/assets/dancingHat.gif"
import catHat from "@/assets/catHat.gif"

export default {
  name: "start-collection",
  data() {
    return {
      collection: {
        name: "",
        tcgId: "",
      },
      mtgImage,
      danceHat,
      catHat
    };
  },
  methods: {
    saveCollection() {
      if (this.collection.tcgId === 0) {
        return;
      }
      collectionService.addCollection(this.collection).then((response) => {
        // fake method!!
        if (response.status === 201) {
          this.$router.push('/MyCollections/')
        }
      });
    },
  },

  computed:{
    showGameImage(tcgId){
      if(tcgId===1){
        return mtgImage;
      }else{
        return null;
      }
    }
  }
};
</script>

<style scoped>
.field {
  flex-direction: column;
}
label {
  margin-right: 0.5rem;
}

.form-label{
font-family: 'Forzan', sans-serif;
font-weight: bold;
font-size: 16pt;
}
button{
  font-family: 'Forzan', sans-serif;
}

</style>