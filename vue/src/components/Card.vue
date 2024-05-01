<template>
  <div class="mb-4">
    <div id="card-stats-container" class="d-flex flex-row align-items-center">
      <div
        class="card-container mx-3 mt-3 d-flex flex-column align-items-center"
      >
        <img
          class="rounded-2 shadow-lg"
          v-bind:src="this.card.smallImgUrl"
          width="146px"
          height="204px"
          v-if="!showLargeImg"
          v-on:click="displayLargerImage"
        />
        <img
          class="large-card rounded-5 shadow-lg bg-transparent"
          v-bind:src="this.card.imageUrl"
          width="488px"
          height="680px"
          v-if="showLargeImg"
          v-on:click="displayLargerImage"
        />
        <p class="mt-1 mb-1 fw-semibold text-wrap text-center">
          {{ card.name }}
        </p>
        <label
          for="addToCollection"
          class="fs-6 text-start me-2"
          v-show="isLoggedIn && routeCheck"
        >
          ADD
        </label>
        <input
          type="checkbox"
          class="form-check-input"
          name="addToCollection"
          v-model="localChecked"
          @change="emitChecked"
          v-show="isLoggedIn && routeCheck"
        />
      </div>
      <div
        id="cardInfo"
        v-if="displayInfo"
        class="bg-white rounded-5 border border-1 border-white fw-bold d-flex flex-column justify-content-center align-items-start mx-1 p-4 h-auto"
        style="--bs-bg-opacity: 0.15"
      >
        <p class="my-0 py-0">Card Name : {{ this.card.name }}</p>
        <p class="my-0 py-0">Colors : {{ this.card.colors }}</p>
        <p class="my-0 py-0">
          Color-Identities : {{ this.card.colorIdentity }}
        </p>
        <p class="my-0 py-0">
          Set Information : {{ this.card.setCode }} / {{ this.card.setName }}
        </p>
        <p class="my-0 py-0">Collector # : {{ this.card.collectorNumber }}</p>
        <p class="my-0 py-0">Play Formats:</p>
        <p
          v-bind:key="legality"
          v-for="(legality, index) in this.card.legalities"
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
        <p class="my-0 py-0">Layout : {{ this.card.layout }}</p>
        <p class="my-0 py-0">CMC : {{ this.card.cmc }}</p>
        <p class="my-0 py-0">EDHREC Rank : {{ this.card.edhrecRank }}</p>
        <p
          class="my-0 py-0"
          v-show="
            this.card.qty != 0 || this.card.qty != null || this.card.qty != ''
          "
        >
          Quantity : {{ this.card.qty }}
        </p>
        <p class="my-0 py-3">
          Want more stats or purchase information? Click
          <a :href="this.card.scryfallUrl">Here!</a>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "card-component",

  props: {
    card: Object,
    isChecked: Boolean, // Receive the checkbox state as a prop
  },

  methods: {
    emitChecked() {
      console.log(
        `Checkbox state changed for ${this.card.cardTitle}: ${this.localChecked}`
      );
      this.$emit("update:checked", this.localChecked);
    },
    
    displayLargerImage() {
      this.showLargeImg = !this.showLargeImg;
      this.displayInfo = !this.displayInfo;
    },
    checkLoginStatus() {
      let token = this.$store.state.token;

      if (token != "") {
        this.isLoggedIn = true;
      }
    },

    checkroute(){
      if(this.$route.params.id >-1){
          this.routeCheck=false
      }
    }
  },
  data() {
    return {
      showLargeImg: false,
      displayInfo: false,
      isLoggedIn: false,
      localChecked: this.isChecked, // Initialize localChecked with the prop value
      routeCheck:true
   };
  },
  created() {
    this.checkLoginStatus();
    this.checkroute();
  },
};
</script>

<style scoped>
.large-card {
  z-index: 1;
}
#card-info {
  background-color: black;
}
</style>
