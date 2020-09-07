<template>
  <v-app style="background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
                background-size: 400% 400%;
                animation: gradient 15s ease infinite;">

    <v-app-bar app class="bar" style="background: background-color: #485461;
                                      background-image: linear-gradient(315deg, #485461 0%, #28313b 74%);">
      <v-toolbar-title>
        <router-link to="/" style="color: #FFF; text-decoration: none;">
          <span @click="cleanup">Huinder</span>
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn v-if="!is_authenticated" @click="showLogin">Войти</v-btn> 
      <v-btn v-if="!is_authenticated" @click="showSignup">Зарегистрироваться</v-btn> 
      <v-btn @click="openAppPage">Мобильное приложение</v-btn> 
      <v-btn v-if="is_authenticated" @click="logout">Выйти</v-btn>
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
import {updateHeader} from './api'
export default {
  name: "Base",
  data: () => ({
    currentComp: null,
  }),
  computed: {
    access_token() {
      return this.$store.state.token;
    },
    is_authenticated() {
      return (this.access_token === null) ? false : true;
    },    
    user() {
      return this.$store.state.user;
    }
  },
  methods: {
    get_tokens_from_storage() {
      var token = localStorage.getItem('access_token');
      if (token) {
        var refresh = localStorage.getItem('refresh_token');
        this.$store.commit('setToken', {access: token, refresh: refresh});
      }
    },
    checkAuth() {
      if (this.access_token === null) {
        this.get_tokens_from_storage();
      }

      if (this.user === null && this.is_authenticated) {
        updateHeader(this.access_token)
        this.$store.dispatch('getUserInfo')
                .then(() => this.$router.push('/app'))
      }
    },
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
    logout() {
      localStorage.removeItem('access_token')
      localStorage.removeItem('refresh_token')
      this.$store.dispatch('logout')
      updateHeader(null)
    },
    cleanup() {
      this.currentComp = null;
    },
  },
  created: function() { this.checkAuth()},
  updated: function() { this.checkAuth()},
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