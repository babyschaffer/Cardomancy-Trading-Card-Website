<template>
  <div
    class="d-flex justify-content-center mx-2 mx-lg-5 mt-5 border border-dark rounded-4 "
    id="messageTable"
  >
    <div class="d-flex flex-column flex-lg-row justify-content-between flex-fill px-2 px-lg-5">
      <div id="messageId" class="d-flex flex-row justify-content-between flex-lg-column">
        <p class="fs-4">Message ID</p>
        <router-link
          class="fs-5 mb-3"
          v-for="(message, index) in messageList"
          v-bind:message="message"
          v-bind:key="index"
          
          v-bind:to="{name:'messagesByID', params:{id: message.messageID}}"
        >
          <div v-on:click="toggleStatus(message.messageID)" >{{ message.messageID }}</div>
        </router-link>
      </div>
      <div id="sender" class="d-flex flex-row justify-content-between flex-lg-column">
        <p class="fs-4">Sender</p>
        <p
          class="fs-5"
          v-for="(message, index) in messageList"
          v-bind:key="index"
        >
          {{ message.messageSender.charAt(0).toUpperCase() + message.messageSender.slice(1) }}
        </p>
      </div>
      <div id="receipient" class="d-flex flex-row justify-content-between flex-lg-column">
        <p class="fs-4">Receipient</p>
        <p
          class="fs-5"
          v-for="(message, index) in messageList"
          v-bind:key="index"
        >
          {{ message.messageReceiver.charAt(0).toUpperCase() + message.messageReceiver.slice(1) }}
        </p>
      </div>
      <div id="dateTime" class="d-flex flex-row justify-content-between flex-lg-column">
        <p class="fs-4 text-start">Send Date & Time </p>
        <p
          class="fs-5 text-wrap"
          v-for="(message, index) in messageList"
          v-bind:key="index"
        >
          {{ new Date(message.messageTimestamp.replace(" ", "T")
          ).toLocaleString() }}
        </p>
      </div>
      <div id="readStatus" class="d-flex flex-row justify-content-between flex-lg-column">
        <p class="fs-4">Status</p>
        <p
          class="fs-5"
          v-for="(message, index) in messageList"
          v-bind:key="index"
        >
          {{ message.read? "Read":"Unread" }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import MessageService from "../services/MessageService.js";

export default {
  name: "message-table",
  data() {
    return {
      messageList: [],
    };
  },
  created() {
    MessageService.getMessagesForUser().then((response) => {
      this.messageList = response.data;
    });
  },
  methods:{
    toggleStatus(messageId){
      MessageService.updateReadStatus(messageId).then(
        response => {
         this.message.read = response
        }
      )
  }
  }
};
</script>

<style scoped>
#messageTable {
  background-color: #ffffffcc;
}
</style>