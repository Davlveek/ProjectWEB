<template>
  <div class="home">
    <v-app-bar>
      <v-toolbar-title>
        <router-link to="/" style="color: black; text-decoration: none;">Huinder</router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn @click="openAppPage">Get app</v-btn>
    </v-app-bar>

    <v-main>
      <v-container fluid>
        <router-link to="/">ВЫЙДИ И ЗАЙДИ НОРМАЛЬНО</router-link>
      </v-container>
    </v-main>

    <v-footer app>
      <v-col class="text-left">Дахнович прими</v-col>
      <v-col class="text-center">Huinder</v-col>
      <v-col class="text-right">{{ new Date().getFullYear() }}</v-col>
    </v-footer>
  </div>
</template>

<script>
export default {
  name: "Error404",
  methods: {
    openAppPage() {
      /*
      let routeData = this.$router.resolve({name: 'adasd', query: {data: ""}});
      window.open(routeData.href, '_blank');
      */
    },
  },
  created() {
    console.log("async request here")
  }
};
</script>