<template>
  <div>
    <div id="login" class="text-center z-1 bg-white w-25 mx-auto rounded-5 border border-1 border-white shadow"
          style="--bs-bg-opacity: 0.6">
      <form @submit.prevent="login">
        <h1
          class="text-dark fs-1 text-center fw-bold title mb-5 p-3"
        >
          Please Sign In
        </h1>
        <div role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>
        <div role="alert" v-if="this.$route.query.registration">
          Thank you for registering, please sign in.
        </div>
        <div class="form-input-group"> 
          <label for="username">Username</label>
          <input
            type="text"
            class="form-control w-50 text-center mb-3 mx-auto "
            id="username"
            placeholder="type username here"
            v-model="user.username"
            required
            autofocus
          />
        </div>
        <div class="form-input-group"> 
          <label for="password">Password</label>
          <input
            type="password"
            class="form-control w-50 text-center mb-3 mx-auto "
            id="password"
            placeholder="type password here"
            v-model="user.password"
            required
          />
        </div>
        <div class="mb-2">
          <button class="btn btn-dark" type="submit">Sign in</button>
        </div>
        <p>
          <router-link :to="{ name: 'register' }"
            >Need an account? Sign up.</router-link
          >
        </p>
      </form>
    </div>
    <div class="z-n1 position-absolute top-150 start-50 translate-middle mt-5 pt-5">
      <img v-bind:src="colorWHat" />
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import blackHat from "@/assets/Hat-Icon-Black.png";
import whiteHat from "@/assets/Hat-Icon-White.png";
import colorWHat from "@/assets/Hat-Icon-BlackColor.png";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
      blackHat,
      whiteHat,
      colorWHat,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.go(this.$router.push('/'));
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },

     refresh(){
      this.$router.push('/')
    }
  },
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
  font-family: "Forzan", sans-serif;
  font-weight: 900;
  font-size: 16pt;
  color: rgb(0, 0, 0);
}
h1 {
  font-family: "Forzan", sans-serif;
  font-weight: 900;
}
a {
  color: rgb(0, 0, 0);
  font-weight: 500;
}
button {
  font-family: "Forzan", sans-serif;
}
</style>