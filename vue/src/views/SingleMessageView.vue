<template>
  <div>
    <full-message />
    <div>
      <button
        v-on:click.prevent="goToMyMessagse"
        class="btn btn-success w-25 mx-auto my-2">
          ALL MESSAGES
      </button>
    </div>
    
  </div>
</template>

<script>
import FullMessage from "../components/FullMessage.vue"

export default {
    components: { FullMessage },
    methods: {
      goToMyMessagse() {
      this.$router.push(`/messages`);
    },
    }

}
</script>

<style>

</style>