<template>
  <div>
      <div>
      <form class="pt-5">
    <div class="form-input-group pb-3">
      <label class="form-label pb-1" for="CN">Enter New Collection Name Below</label>
      <input class="form-control w-25 p-2 mx-auto text-center" type="text" name="collectionName" id="CN" placeholder="~the coolest name ever~" v-model="collection.name" required autofocus/>
    </div>
    
    <button v-if="isLoggedIn && isOwner" class="btn btn-dark" type="submit" @click.prevent="updateCollection">Save Changes</button>
  </form>
  </div>
  </div>
</template>

<script>

import collectionService from "../services/CollectionService";
import profileService from "../services/ProfileService.js";
import authService from "../services/AuthService.js";

export default {
    name : "edit-collection",
  

  data() {
    return {
      collection: {
        name: "",
        tcgId: "",
      },
      isLoggedIn: false,
      isOwner: false,
      collectionOwnerUserId: 0,
      loggedInUsername: "",
      loggedInUserId: 0,
      collectionOwner: {},
    };
  },

  methods: {
      updateCollection() {
          collectionService.updateCollection(this.collection.id, this.collection).then(
              (response) => {
                  if (response.status === 200) {
          this.$router.push('/MyCollections/')
        }
              }
          )
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
      }
    },
  },

  created() {
      collectionService.getCollectionById(this.$route.params.id).then(
          (response) => {
              this.collection = response.data
          }
      );
      this.checkLoginStatus();

    this.checkOwnerStatus();

    collectionService.getUserForCollectionId(this.$route.params.id).then(
      (response) => {
        this.collectionOwner = response.data;
        this.collectionOwnerUserId = parseInt(this.collectionOwner.id);
        console.log(this.collectionOwnerUserId);
      }
    );

    // This method is responsible for finding and assigning the user.id for the logged in user.
    profileService.getMyProfile().then((response) => {
      let profile = response.data;
      this.loggedInUsername = profile.username;
      authService.userValidation(this.loggedInUsername).then((response) => {
        this.loggedInUserId = response.data;
        console.log(this.loggedInUserId);
      });
    });
  }

}
</script>

<style>

</style>