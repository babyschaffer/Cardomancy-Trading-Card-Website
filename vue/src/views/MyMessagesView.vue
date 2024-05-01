<template>
  <div>
    <h2 class="text-danger-emphasis fs-2 text-center fw-bold title mt-4 p-3 w-50 mx-auto bg-white rounded-5 border border-1 border-white shadow"
      style="--bs-bg-opacity: 0.3">{{this.$store.state.user.username}}'s Messages</h2>
    <message-table class="shadow-lg"/>
    <!-- off canvas field -->
    <div id="buttonContainer" class="mt-5 ">
      <button id="messageButton" class="btn btn-primary fs-4 shadow-lg" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">Create a new message</button>
      <div class="offcanvas offcanvas-bottom" tabindex="-1" id="offcanvasBottom" aria-labelledby="offcanvasBottomLabel">
        <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasBottomLabel">New Message</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">        
          <new-message-form />
          
        </div>
      </div>
    </div>
    
    

  </div>
</template>

<script>
import messageTable from '../components/MessageTable.vue'
import newMessageForm from '../components/NewMessageForm.vue'



export default {
    components:{
        messageTable,
        newMessageForm
    },
}
</script>

<style>
#newMessageScreen{
  z-index: 3;
}
#messageButton {
  background-color:#4c2c2e
}

</style>