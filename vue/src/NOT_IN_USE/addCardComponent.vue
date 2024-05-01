<template>
<div class="mb-4">
  <div id="card-stats-container" class="d-flex flex-row align-items-center">
  <div class="addCard-container mx-3 mt-3 d-flex flex-column align-items-center">
    <img
      class="rounded-2 shadow-lg"
      v-bind:src="this.addCard.smallImgUrl"
      width="146px"
      height="204px"
      v-if="!showLargeImg"
      v-on:click="displayLargerImage"
    />
    <img
      class="large-addCard rounded-5 shadow-lg bg-transparent"
      v-bind:src="this.addCard.imageUrl"
      width="488px"
      height="680px"
      v-if="showLargeImg"
      v-on:click="displayLargerImage"
    />
    <p class="mt-1 mb-1 fw-semibold">{{ addCard.name }}</p>
    <label for="addToCollection"  class="fs-6 text-start me-2" v-show="isLoggedIn"> ADD </label>
    <input type="checkbox" class="form-check-input" name="addToCollection" v-model="localChecked" @change="emitChecked" v-show="isLoggedIn">
    <div
        id="cardInfo"
        v-if="displayInfo"
        class=" bg-white rounded-5 border border-1 border-white fw-bold d-flex flex-column justify-content-center align-items-start mx-1 p-4 h-auto"
        style="--bs-bg-opacity: 0.15"
      >
        <p class="my-0 py-0">Card Name : {{ this.addCard.name }}</p>
        <p class="my-0 py-0">Colors : {{ this.addCard.colors }}</p>
        <p class="my-0 py-0">
          Color-Identities : {{ this.addCard.colorIdentity }}
        </p>
        <p class="my-0 py-0">
          Set Information : {{ this.addCard.setCode }} / {{ this.addCard.setName }}
        </p>
        <p class="my-0 py-0">Collector # : {{ this.addCard.collectorNumber }}</p>
        <p class="my-0 py-0">Play Formats:</p>
        <p
          v-bind:key="legality"
          v-for="(legality, index) in this.addCard.legalities"
          v-bind:legality="legality"
          class="mb-1 ms-5 px-2 fw-semibold text-white"
          v-bind:class="
            String(legality) == 'legal'
              ? 'bg-success rounded-pill'
              : 'bg-danger rounded-pill'
          "
        >
          {{ index }}:
          {{ String(legality) == "not_legal" ? "not legal" : "legal" }}
        </p>
        <p class="my-0 py-0">Layout : {{ this.addCard.layout }}</p>
        <p class="my-0 py-0">CMC : {{ this.addCard.cmc }}</p>
        <p class="my-0 py-0">EDHREC Rank : {{ this.addCard.edhrecRank }}</p>
        <p class="my-0 py-0" v-show="this.addCard.qty!=0 || this.addCard.qty != null || this.addCard.qty !=''"> Quantity : {{ this.addCard.qty }}</p>
        <p class="my-0 py-3">
          Want more stats or purchase information? Click
          <a :href="this.addCard.scryfallUrl">Here!</a>
        </p>
      </div>
      </div>

    
    
</div>

  <!-- <button class="btn btn-dark p-1 btn-sm btn-outline-light ">Delete</button> -->
  </div>
</template>

<script>
export default {
  name: "add-card-component",
  props: {
    addCard: Object,
    isChecked: Boolean // Receive the checkbox state as a prop
  },

  methods: {
    emitChecked() {
      console.log(`Checkbox state changed for ${this.addCard.cardTitle}: ${this.localChecked}`);
      this.$emit("update:checked", this.localChecked);
    },
    displayLargerImage() {
      this.showLargeImg = !this.showLargeImg;
      this.displayInfo=!this.displayInfo;
    },
    checkLoginStatus(){
      let token = this.$store.state.token;

      if(token != ""){
        this.isLoggedIn = true;       
      }
    },
  },
  created(){
    this.checkLoginStatus()
  },

  data() {
    return {
      showLargeImg: false,
      localChecked: this.isChecked, // Initialize localChecked with the prop value
      displayInfo:false,
      isLoggedIn: false
    
    };
  },
};
</script>

<style scoped>

</style>