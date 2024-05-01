<template>
  <div class="mt-3 p-3">
    <h1 class="text-danger-emphasis fw-bold mb-5 p-4 mx-auto bg-white rounded-5 border border-1 border-white shadow w-25"
      style="--bs-bg-opacity: 0.3">Meet the Developers!</h1>

    <about
      :imgSrc="require('@/assets/Shad.png')"
      :name="'Shadav Khan'"
      :aboutMe="Shad"
      :linkedin="'linkedin.com/in/shadavkhan'"
      :github="'https://github.com/AmaranthShad'"
      class="m-2 ms-lg-5 me-lg-3 my-lg-3"
      id="SK"
    />
    <about
      :imgSrc="require('@/assets/amanda_selfie.jpg')"
      :name="'Amanda Schaffer'"
      :aboutMe="Amanda"
      :linkedin="'linkedin.com/in/amanda-schaffer-'"
      :github="'https://github.com/babyschaffer'"
      class="m-2 ms-lg-5 me-lg-3 my-lg-3"
      id="AS"
    />
    <about
      :imgSrc="require('@/assets/ja-selfie.png')"
      :name="'Johnna Anderson'"
      :aboutMe="Johnna"
      :linkedin="'linkedin.com/in/johnnaandersondeveloper'"
      :github="'https://github.com/Jay-Burd'"
      class="m-2 ms-lg-5 me-lg-3 my-lg-3"
      id="JA"
    />
    <about
      :imgSrc="require('@/assets/Julianna.jpg')"
      :name="'Julianna Irons'"
      :aboutMe="Julie"
      :linkedin="'linkedin.com/in/juliannaclaicher'"
      :github="'github.com/JuliannaCynthia'"
      class="m-2 ms-lg-5 me-lg-3 my-lg-3"
      id="JI"
    />
    <about
      :imgSrc="require('@/assets/cg-selfie.jpg')"
      :name="'Chris Guerrero'"
      :aboutMe="Chris"
      :linkedin="'linkedin.com/in/christopherdguerrero'"
      :github="'github.com/Chris-D-G/'"
      class="m-2 ms-lg-5 me-lg-3 my-lg-3"
      id="CG"
    />
  </div>
</template>

<script>
import About from "../components/About.vue";

export default {
  components: { About },
  data() {
    return {
      Shad: "Lorem ipsum dolor- just kidding, nice to meet you!",
      Amanda:
        "I know I just woke up... but I need a nap.",
      Johnna:
        "Java doesn't have garbage collection, because if it did, it would delete itself.",
      Julie:
        "It's not working? Have you restarted the program? Are you sure?",
      Chris:`Full Stack Development Skill Acquired! How I feel about the following after 2 weeks...
        Front-End Development: It's alright, but I won't make it out alive if I have do it all day.
        Back-End Development: Won't break a sweat! Can I have more?`
    };
  },
};
</script>

<style>
@media screen and (min-width: 769px) {
  #SK,
  #JA,
  #CG {
    position: relative;
    left: 3vw;
  }
  #AS,
  #JI {
    position: relative;
    left: 40vw;
  }
  #SK,
  #AS,
  #JA,
  #JI,
  #CG {
    max-width: 55vw;
  }
}
</style>