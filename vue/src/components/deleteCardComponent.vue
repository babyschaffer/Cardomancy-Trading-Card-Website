<template>
  <div class="mb-4">
    <div id="card-stats-container" class="d-flex flex-row align-items-center">
      <div
        class="deleteCard-container mx-3 mt-3 d-flex flex-column align-items-center"
      >
        <img
          class="rounded-2 shadow-lg"
          v-bind:src="this.deleteCard.smallImgUrl"
          width="146px"
          height="204px"
          v-if="!showLargeImg"
          v-on:click="displayLargerImage"
        />
        <img
          class="large-addCard rounded-5 shadow-lg bg-transparent"
          v-bind:src="this.deleteCard.imageUrl"
          width="488px"
          height="680px"
          v-if="showLargeImg"
          v-on:click="displayLargerImage"
        />
        <p class="mt-1 mb-1 fw-semibold text-wrap text-center">
          {{ deleteCard.name }}
        </p>
        <!-- To DO: add boolean check for the checkbox -->
        <label for="deleteFromCollection" class="fs-6 text-start me-2">
          DELETE
        </label>
        <input
          type="checkbox"
          class="form-check-input"
          name="deleteFromCollection"
          v-model="localChecked"
          @change="emitChecked"
        />

       
      </div>
       <div
          id="cardInfo"
          v-if="displayInfo"
          class="bg-white rounded-5 border border-1 border-white fw-bold d-flex flex-column justify-content-center align-items-start mx-1 p-4 h-auto"
          style="--bs-bg-opacity: 0.15"
        >
          <p class="my-0 py-0">Card Name : {{ this.deleteCard.name }}</p>
          <p class="my-0 py-0">Colors : {{ this.deleteCard.colors }}</p>
          <p class="my-0 py-0">
            Color-Identities : {{ this.deleteCard.colorIdentity }}
          </p>
          <p class="my-0 py-0">
            Set Information : {{ this.deleteCard.setCode }} /
            {{ this.deleteCard.setName }}
          </p>
          <p class="my-0 py-0">
            Collector # : {{ this.deleteCard.collectorNumber }}
          </p>
          <p class="my-0 py-0">Play Formats:</p>
          <p
            v-bind:key="legality"
            v-for="(legality, index) in this.deleteCard.legalities"
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
          <p class="my-0 py-0">Layout : {{ this.deleteCard.layout }}</p>
          <p class="my-0 py-0">CMC : {{ this.deleteCard.cmc }}</p>
          <p class="my-0 py-0">
            EDHREC Rank : {{ this.deleteCard.edhrecRank }}
          </p>
          <p
            class="my-0 py-0"
            v-show="
              this.deleteCard.qty != 0 ||
              this.deleteCard.qty != null ||
              this.deleteCard.qty != ''
            "
          >
            Quantity : {{ this.deleteCard.qty }}
          </p>
          <p class="my-0 py-3">
            Want more stats or purchase information? Click
            <a :href="this.deleteCard.scryfallUrl">Here!</a>
          </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "delete-card-component",
  props: {
    deleteCard: Object,
    isChecked: Boolean, // Receive the checkbox state as a prop
  },

  methods: {
    emitChecked() {
      console.log(
        `Checkbox state changed for ${this.deleteCard.cardTitle}: ${this.localChecked}`
      );
      this.$emit("update:checked", this.localChecked);
    },
    displayLargerImage() {
      this.showLargeImg = !this.showLargeImg;
      this.displayInfo = !this.displayInfo;
    },
  },
  data() {
    return {
      showLargeImg: false,
      localChecked: this.isChecked, // Initialize localChecked with the prop value
      displayInfo: false,
    };
  },
};
</script>

<style scoped>
.large-card {
  z-index: 3;
}
p {
  color: #360a0c;
}
</style>