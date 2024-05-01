<template>
  <div class="d-flex justify-content-center">
    <div
      class="bg-secondary-subtle m-1 p-2 rounded-3 d-flex flex-column border border-dark w-75 shadow-lg"
    >
      <p class="ms-2 my-1 fs-4 ps-3 text-start">
        FROM:
        <span>{{
          oneMessage.messageSender.charAt(0).toUpperCase() +
          oneMessage.messageSender.slice(1)
        }}</span>
      </p>
      <hr />
      <p class="ms-2 my-1 fs-4 ps-3 text-start">
        TO:
        <span>{{
          oneMessage.messageReceiver.charAt(0).toUpperCase() +
          oneMessage.messageReceiver.slice(1)
        }}</span>
      </p>
      <hr />
      <p class="ms-2 my-1 fs-4 ps-3 text-start">
        DATE CREATED:
        <span class="ps-3">{{
          new Date(
            oneMessage.messageTimestamp.replace(" ", "T")
          ).toLocaleString()
        }}</span>
      </p>
      <hr />
      <p class="ms-2 my-1 fs-4 ps-3 text-start">
        MESSAGE: <span class="ps-3">{{ oneMessage.messageText }}</span>
      </p>
    </div>
  </div>
</template>

<script>
import MessageService from "../services/MessageService";

export default {
  name: "message",
  data() {
    return {
      oneMessage: {},
    };
  },
  created() {
    MessageService.getSingleMessageByID(this.$route.params.id)
      .then((response) => {
        this.oneMessage = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style>
</style>