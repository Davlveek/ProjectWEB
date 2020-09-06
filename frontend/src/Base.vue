<template>
  <v-app
      style="    
        background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
        background-size: 400% 400%;
        animation: gradient 15s ease infinite;
      ">
    <v-app-bar app class="bar" 
              style="
                    background: background-color: #485461;
                    background-image: linear-gradient(315deg, #485461 0%, #28313b 74%);
      ">
      <v-toolbar-title>
        <router-link to="/" style="color: #FFF; text-decoration: none;">
          <span @click="cleanup">Huinder</span>
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn @click="showLogin">Войти</v-btn> 
      <v-btn @click="showSignup">Зарегистрироваться</v-btn> 
      <v-btn @click="openAppPage">Мобильное приложение</v-btn> 
    </v-app-bar>

    <router-view :comp="currentComp"/>
    <!--
    <v-footer app class="bar">
      <v-col class="text-left">Дахнович прими</v-col>
      <v-col class="text-center">Huinder</v-col>
      <v-col class="text-right">{{ new Date().getFullYear() }}</v-col>
    </v-footer>
    -->
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
@keyframes gradient {
        0% {
          background-position: 0% 50%;
        }
        50% {
          background-position: 100% 50%;
        }
        100% {
          background-position: 0% 50%;
        }
      }
</style>