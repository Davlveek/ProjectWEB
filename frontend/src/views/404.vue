<template>
  <v-main>
    <v-container fluid class="justify-center" fill-height>
        <v-btn @click="home" width=500 height="300"><h1>Вас здесь быть не должно</h1></v-btn>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "Error404",
  methods: {
    home() {
      this.$router.push({name: 'Home'});
    }
  },
};
</script>