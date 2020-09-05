<template>
  <v-app>
    <v-app-bar app class="bar">
      <v-toolbar-title>
        <router-link to="/" style="color: black; text-decoration: none;">
          <span @click="cleanup">Huinder</span>
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn @click="openAppPage">Get app</v-btn>
      <v-btn @click="showLogin">Log in</v-btn>
      <v-btn @click="showSignup">Sign up</v-btn>
    </v-app-bar>

    <router-view :comp="currentComp"/>

    <v-footer app class="bar">
      <v-col class="text-left">Дахнович прими</v-col>
      <v-col class="text-center">Huinder</v-col>
      <v-col class="text-right">{{ new Date().getFullYear() }}</v-col>
    </v-footer>
  </v-app>
</template>

<script>
import Login from './components/Login'
import Signup from './components/Signup'

export default {
  name: "Base",
  data: () => ({
    currentComp: null,
  }),
  methods: {
    showLogin() {
      this.currentComp = Login;
    },
    showSignup() {
      this.currentComp = Signup;
    },
    openAppPage() {
      /*
      let routeData = this.$router.resolve({name: 'adasd', query: {data: ""}});
      window.open(routeData.href, '_blank');
      */
    },
    cleanup() {
      this.currentComp = null;
    },
  },
}
</script>


<style>
html, body
{
    height: 100%;
}
</style>