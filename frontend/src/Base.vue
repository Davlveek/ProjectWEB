<template>
  <v-app style="background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
                background-size: 400% 400%;
                animation: gradient 15s ease infinite;">

    <v-app-bar app class="bar" style="background: background-color: #485461;
                                      background-image: linear-gradient(315deg, #485461 0%, #28313b 74%);">
      <v-app-bar-nav-icon v-if="is_authenticated" @click="settings = true"></v-app-bar-nav-icon>
      <v-toolbar-title>
        <router-link to="/" style="color: #FFF; text-decoration: none;">
          <span @click="resetForm">Huinder</span>
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn v-if="!is_authenticated" @click="showLogin">Войти</v-btn> 
      <v-btn v-if="!is_authenticated" @click="showSignup">Зарегистрироваться</v-btn> 
      <v-btn @click="openAppPage">Мобильное приложение</v-btn> 
      <v-btn v-if="is_authenticated" @click="logout">Выйти</v-btn>
    </v-app-bar>

    <v-navigation-drawer
      v-model="settings"
      absolute
      temporary
      style="background: background-color: #485461;
              background-image: linear-gradient(315deg, #485461 0%, #28313b 74%);"
    >
      <v-list nav>
        <v-list-item-group v-if="is_authenticated"
            active-class="deep-purple--text text--accent-4">
          <v-list-item>
            <v-list-item-subtitle style="color: #FFF;">First name</v-list-item-subtitle>    
            <v-list-item-title>
              <v-text-field v-model="first_name"></v-text-field>
            </v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-subtitle style="color: #FFF;">Last name</v-list-item-subtitle>    
            <v-list-item-title>
              <v-text-field v-model="last_name"></v-text-field>
            </v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-subtitle style="color: #FFF;">Age</v-list-item-subtitle>    
            <v-list-item-title>
              <v-text-field v-model="age"></v-text-field>
            </v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-subtitle style="color: #FFF;">Gender</v-list-item-subtitle>    
            <v-list-item-title>
              <v-text-field v-model="gender"></v-text-field>
            </v-list-item-title>
          </v-list-item>
        </v-list-item-group>

      </v-list>
    </v-navigation-drawer>

    <router-view :comp="activeForm"/>
    
    <v-footer v-show="footer" app class="bar">
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
    activeForm: null,
    settings: false,
    footer: false,
  }),
  computed: {
    access_token() {
      return this.$store.state.access_token;
    }, 
    user() {
      return this.$store.state.user;
    },

    is_authenticated() {
      return (this.access_token === null) ? false : true;
    },   

    first_name() {
      return this.user ? this.user.first_name : null;
    },
    last_name() {
      return this.user ? this.user.last_name : null;
    },
    age() {
      return this.user ? this.user.age : null;
    },
    gender() {
      return this.user ? this.user.gender : null;
    },
  },
  methods: {
    // проверяем авторизацию перед созданием страницы    
    checkAuth() {
      if (this.access_token === null) {
        var access = localStorage.getItem('access_token');
        var refresh = localStorage.getItem('refresh_token');
        if (access && refresh) {          
          this.$store.commit('setAccessToken', access);
          this.$store.commit('setRefreshToken', refresh);
        }
      }

      if (this.user === null && this.is_authenticated) {        
        this.$store.dispatch('getUserInfo')
                    .then(() => this.$router.currentRoute.name !== 'App' ? this.$router.push({name: 'App'}) : void(0))
      }
    },

    // хэндлеры для кнопок в баре
    showLogin() { this.activeForm = Login; },
    showSignup() { this.activeForm = Signup; },
    openAppPage() {  
      /* let routeData = this.$router.resolve({name: 'adasd', query: {data: ""}});
      window.open(routeData.href, '_blank'); */
    },
    
    // хэндлеры для выхода из аккаунта
    logout() {
      this.$store.commit('resetState');
      this.resetForm()
      
      if (this.$router.currentRoute.name !== 'Home') {
        this.$router.push({name: 'Home'})
      }
    },
    resetForm() {
      this.activeForm = null;
    },
  },
  beforeMount() {
    this.checkAuth()
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