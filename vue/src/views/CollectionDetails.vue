<template>
  <div class="ms-5 me-5">
    <h1
      class="text-dark fs-1 text-center fw-bold title mt-4 p-3 w-35 mx-auto bg-white rounded-5 border border-1 border-white shadow"
      style="--bs-bg-opacity: 0.15"
    >
      {{ this.collection.name }}
    </h1>
    <button
      id="deleteButton"
      class="btn btn-outline-dark text-dark fw-bold mb-3 mt-2 btn-space shadow-sm"
      v-on:click="deleteCollection(collection.name)"
      v-if="isLoggedIn && isOwner"
    >
      Delete Collection
    </button>

    
    <button
    id="addButton"
    class="btn btn-outline-dark text-dark fw-bold mb-3 mt-2 btn-space linkbtn shadow-sm" v-if="isLoggedIn && isOwner"
    ><router-link class="text-light fs-6 linkbtn" v-bind:to="{ name: 'searchCards' }"> Add Cards to Collection </router-link></button>

    

    <div
      class="d-flex flex-wrap m-4 bg-white rounded-5 border border-1 border-white shadow"
      style="--bs-bg-opacity: 0.15" v-if="isLoggedIn && isOwner"
    >
      <h2   class="btn m-4 flex-fill bg-light">Sort By :</h2>
      <button
        class="btn m-4 flex-fill"
        v-on:click.prevent="alphaDeck()"
        v-bind:class="buttonClass(alpha)"
        v-if="isLoggedIn && isOwner"
      >
        Alphabetical
      </button>
      <button
        class="btn m-4 flex-fill"
        v-on:click.prevent="colorDeck()"
        v-bind:class="buttonClass(color)"
        v-if="isLoggedIn && isOwner"
      >
        Color
      </button>
      <button
        class="btn m-4 flex-fill"
        v-on:click.prevent="identityDeck()"
        v-bind:class="buttonClass(colorIden)"
        v-if="isLoggedIn && isOwner"
      >
        Color Identity
      </button>
      <button
        class="btn m-4 flex-fill"
        v-on:click.prevent="setDeck()"
        v-bind:class="buttonClass(set)"
        v-if="isLoggedIn && isOwner"
      >
        Set
      </button>
      <button
        class="btn m-4 flex-fill"
        v-on:click.prevent="CMCDeck()"
        v-bind:class="buttonClass(CMC)"
        v-if="isLoggedIn && isOwner"
      >
        CMC
      </button>
      <button
        class="btn m-4 flex-fill"
        v-on:click.prevent="EDHRECDeck()"
        v-bind:class="buttonClass(EDHREC)"
        v-if="isLoggedIn && isOwner"
      >
        EDHREC Rank
      </button>
      <button
        class="btn btn-dark m-4 flex-fill"
        v-on:click.prevent="resetDeck()"
        v-if="isLoggedIn && isOwner"
      >
        Reset
      </button>
    </div>

    <div
      class="d-flex flex-wrap gap-2 justify-content-evenly"
      v-if="
        !this.alpha &&
        !this.color &&
        !this.colorIden &&
        !this.set &&
        !this.EDHREC &&
        !this.CMC
      "
    >
      <img :src="catHat" v-if="isLoading" />

      <div
        class="d-flex flex-wrap me-2 justify-content-between"
        v-if="isLoggedIn && isOwner"
      >
        <deleteCard
          v-for="(deleteCard, index) in cards"
          v-bind:key="index"
          v-bind:deleteCard="deleteCard"
          :isChecked="checkboxStates[index]"
          @update:checked="updateCheckboxState(index, $event)"
        />
      </div>
      <div class="d-flex flex-wrap gap-2 justify-content-evenly" v-else>
        <card v-for="card in cards" v-bind:key="card.id" v-bind:card="card" />
      </div>
    </div>

    <div class="d-flex flex-wrap me-2 justify-content-between" v-if="this.alpha && isLoggedIn && isOwner">
      <deleteCard v-for="(deleteCard, index) in alphcards" 
      v-bind:key="index" v-bind:deleteCard="deleteCard" :isChecked="checkboxStates[index]"
      @update:checked="updateCheckboxState(index, $event)"/>
      </div>
    

   
      <div class="d-flex flex-wrap me-2 justify-content-between" v-if="this.color && isLoggedIn && isOwner">
      <deleteCard v-for="(deleteCard, index) in colorcards" 
      v-bind:key="index" v-bind:deleteCard="deleteCard" :isChecked="checkboxStates[index]"
      @update:checked="updateCheckboxState(index, $event)"/>
      </div>
    

    
      <div class="d-flex flex-wrap me-2 justify-content-between" v-if="this.colorIden && isLoggedIn && isOwner">
      <deleteCard v-for="(deleteCard, index) in colorIdencards" 
      v-bind:key="index" v-bind:deleteCard="deleteCard" :isChecked="checkboxStates[index]"
      @update:checked="updateCheckboxState(index, $event)"/>
      </div>
    

    
      <div class="d-flex flex-wrap me-2 justify-content-between" v-if="isLoggedIn && isOwner && this.set">
      <deleteCard v-for="(deleteCard, index) in setcards" 
      v-bind:key="index" v-bind:deleteCard="deleteCard" :isChecked="checkboxStates[index]"
      @update:checked="updateCheckboxState(index, $event)"/>
      </div>
    
    
    <div class="d-flex flex-wrap me-2 justify-content-between" v-if="this.CMC && isLoggedIn && isOwner">
      <deleteCard v-for="(deleteCard, index) in CMCcards" 
      v-bind:key="index" v-bind:deleteCard="deleteCard" :isChecked="checkboxStates[index]"
      @update:checked="updateCheckboxState(index, $event)"/>
      </div>

   
      <div class="d-flex flex-wrap me-2 justify-content-between" v-if="this.EDHREC && isLoggedIn && isOwner">
      <deleteCard v-for="(deleteCard, index) in EDHRECcards" 
      v-bind:key="index" v-bind:deleteCard="deleteCard" :isChecked="checkboxStates[index]"
      @update:checked="updateCheckboxState(index, $event)"/>
      </div>

    <div
      v-if="this.checkedCards.length > 0"
      class="d-flex mx-auto mb-3 p-3 w-25 justify-content-evenly bg-white rounded-5 border border-1 border-white shadow"
      style="--bs-bg-opacity: 0.6"
    >
      QUEUED CARDS
    </div>
    <div
      v-for="card in this.checkedCards"
      v-bind:key="card.id"
      class="w-100 d-flex"
    >
      <ul
        class="d-flex mx-auto w-25 justify-content-evenly bg-white rounded-5 border border-1 border-white shadow"
      style="--bs-bg-opacity: 0.6"
      >
        <li>| {{ card.name }} |</li>
        <li>C# {{ card.collectorNumber }}</li>
      </ul>
    </div>

    <button
      class="btn btn-dark m-2"
      @click="addCheckedCards()"
      v-if="isLoggedIn && isOwner"
    >
      Add Checked Cards to Queue
    </button>
    <button
      class="btn btn-dark"
      @click="deleteCard()"
      v-if="isLoggedIn && isOwner"
    >
      Delete Queued Cards From Collection
    </button>
  </div>
</template>

<script>
import CollectionService from "../services/CollectionService.js";
import card from "../components/Card.vue";
import CardSort from "../services/cardSort.js";
import deleteCard from "../components/deleteCardComponent.vue";
import profileService from "../services/ProfileService.js";
import authService from "../services/AuthService.js";
import catHat from "@/assets/catHat.gif";

export default {
  name: "collection-details",
  components: { card, deleteCard },
  data() {
    return {
      cards: [],
      alphcards: [],
      colorcards: [],
      colorIdencards: [],
      setcards: [],
      CMCcards: [],
      EDHRECcards: [],
      collection: [],
      alpha: false,
      color: false,
      colorIden: false,
      set: false,
      CMC: false,
      EDHREC: false,
      isOwner: false,
      isLoggedIn: false,
      checkboxStates: [], // Array to store checkbox states
      checkedCards: [], // Array to store checked cards
      collectionOwnerUserId: 0,
      loggedInUsername: "",
      loggedInUserId: 0,
      collectionOwner: {},
      isLoading: true,
      catHat,
    };
  },

  methods: {
    buttonClass(isActive) {
      return isActive ? "btn-light" : "btn-dark";
    },

    checkLoginStatus() {
      let token = this.$store.state.token;

      if (token != "") {
        this.isLoggedIn = true;
      }
    },

    checkOwnerStatus() {
      if (this.loggedInUserId === this.collectionOwnerUserId) {
        this.isOwner = true;
        console.log(this.loggedInUserId)
        console.log(this.collectionOwnerUserId)
      }
    },

    alphaDeck() {
      this.alpha = true;
      this.color = false;
      this.colorIden = false;
      this.set = false;
      this.CMC = false;
      this.EDHREC = false;
    },
    colorDeck() {
      this.color = true;
      this.alpha = false;
      this.colorIden = false;
      this.set = false;
      this.CMC = false;
      this.EDHREC = false;
    },
    identityDeck() {
      this.alpha = false;
      this.color = false;
      this.colorIden = true;
      this.set = false;
      this.CMC = false;
      this.EDHREC = false;
    },
    setDeck() {
      this.alpha = false;
      this.color = false;
      this.colorIden = false;
      this.set = true;
      this.CMC = false;
      this.EDHREC = false;
    },
    CMCDeck() {
      this.alpha = false;
      this.color = false;
      this.colorIden = false;
      this.set = false;
      this.CMC = true;
      this.EDHREC = false;
    },
    EDHRECDeck() {
      this.alpha = false;
      this.color = false;
      this.colorIden = false;
      this.set = false;
      this.CMC = false;
      this.EDHREC = true;
    },

    resetDeck() {
      this.alpha = false;
      this.color = false;
      this.colorIden = false;
      this.set = false;
      this.CMC = false;
      this.EDHREC = false;
    },

    deleteCollection(name) {
      if (
        confirm(
          "*****PERMENANT***** \nComfirmation will delete the Collection named: \n\n~ " +
            name +
            " ~ \n\nAll Cards associated, and your access to the Collection, will be revoked.\nThere will be no reccords, and it cannot be reanimated.\n\n\nARE YOU 'DEAD' SERIOUS?"
        )
      ) {
        CollectionService.deleteCollection(this.$route.params.id).then(
          (response) => {
            if (response.status == 204) {
              this.$router.push(`/myCollections`);
            }
          }
        );
      }
    },

    updateCheckboxState(index, value) {
      // Update the checkbox state in the array
      console.log(
        `Checkbox state updated for card at index ${index}: ${value}`
      );
      this.checkboxStates[index] = value;
    },

    addCheckedCards() {
      // Add checked cards to the checkedCards array
      console.log("addCheckedCards method called");
      console.log("Checkbox states:", this.checkboxStates);
      this.checkedCards = this.cards.filter(
        (_, index) => this.checkboxStates[index]
      );
      console.log("Filtered checkedCards:", this.checkedCards);
    },

    deleteCard() {
      this.checkedCards.forEach((card) => {
        CollectionService.deleteCardFromCollection(card.id, this.collection.id);
      });
      this.$router.push(`/myCollections`);
    },
  },

  created() {
    this.checkLoginStatus();
    CollectionService.getAllCardsByCollection(this.$route.params.id).then(
      (response) => {
        this.cards = response.data;
        for (let i = 0; i < this.cards.length; i++) {
          const element = this.cards[i];
          this.alphcards.push(element);
          this.CMCcards.push(element);
          this.colorcards.push(element);
          this.colorIdencards.push(element);
          this.setcards.push(element);
          this.EDHRECcards.push(element);
        }
        this.alphcards = CardSort.sortByName(this.alphcards);
        this.CMCcards = CardSort.sortByCmc(this.CMCcards);
        this.colorcards = CardSort.sortByColor(this.colorcards);
        this.colorIdencards = CardSort.sortByColorId(this.colorIdencards);
        this.setcards = CardSort.sortBySetId(this.setcards);
        this.EDHRECcards = CardSort.sortByEdhrec(this.EDHRECcards);
        this.isLoading = false;
      }
    );

    CollectionService.getCollectionById(this.$route.params.id).then(
      (response) => {
        this.collection = response.data;
      }
    );
    

    // This method is responsible for finding and assigning the user.id for the collection owner.
    CollectionService.getUserForCollectionId(this.$route.params.id).then(
      (response) => {
        this.collectionOwner = response.data;
        this.collectionOwnerUserId = parseInt(this.collectionOwner.id);
        console.log(this.collectionOwnerUserId);
      }
      
    );

      profileService.getMyProfile().then((response) => {
      let profile = response.data;
      this.loggedInUsername = profile.username;
      authService.userValidation(this.loggedInUsername).then((response) => {
        this.loggedInUserId = response.data;
        console.log(this.loggedInUserId);
        this.checkOwnerStatus();
      });
    });




    
    

    // This method is responsible for finding and assigning the user.id for the logged in user.
    
  },

  
};
</script>

<style>
.title {
  z-index: 1;
  font-family: "Forzan", sans-serif;
}
ul {
  list-style-type: none;
}

.btn-space {
  margin-right: 5px;
  margin-left: 5px;
}

.linkbtn {
  text-decoration: none;
}
#deleteButton{
  background-color:lightcoral
}
#addButton{
  background-color:#e8b287
}
</style>