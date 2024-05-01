<template>
  <div>

      <search/>
      <button v-on:click.prevent="addCard(this.card.id)">Add To Collection</button>
  </div>
</template>

<script>
import search from "../components/SearchCards.vue"

export default {
 name:"add-card-view",
 components:{ search }
}
</script>

<style>

</style>