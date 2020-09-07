<template>
  <v-card width="500" class="mx-auto mt-5" shaped>
    <v-form ref='form' lazy-validation>
      <v-card-text>
        <v-text-field v-model="name" label="Никнейм" :rules="nameRules" required></v-text-field>
        <v-text-field v-model="password" type="password" label="Пароль" :rules="passRules" required></v-text-field>
      </v-card-text>
      
      <v-card-actions class="justify-center">
        <div v-if="!auth_in_process">
          <v-btn @click="login">Войти</v-btn>
        </div>
        <div v-else>
          <span class="loader"></span>
        </div>
      </v-card-actions>
    </v-form>
  </v-card>
</template>

<script>
export default {
  name: "Login",
  data: () => ({
    name: "",
    password: "",
    auth_in_process: false,
    nameRules: [
      name => !!name || "Введите никнейм",
    ],
    passRules: [
      pass => !!pass || "Введите пароль",
    ]
  }),
  methods: {
    login() {
      if (this.$refs.form.validate()) {
        this.auth_in_process = true;
        
        var data = {
          name: this.name,
          password: this.password
        };

        this.$store.dispatch('login', data)
                    .then(this.postlogin);
        
      }
    },
    postlogin() {
        localStorage.setItem('access_token', this.access_token);
        localStorage.setItem('refresh_token', this.refresh_token);
        this.auth_in_process = false;
        this.$router.push('app');
    }
  },
  computed: {
    access_token() {
      return this.$store.state.token;
    },
    refresh_token() {
      return this.$store.state.refresh_token;
    },
  }
};
</script>

<style>
.loader {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  display: block;
  margin:15px auto;
  position: relative;
  color: #000;
  box-sizing: border-box;
  animation: animloader 1s linear infinite alternate;
}

@keyframes animloader {
  0% {
    box-shadow: -38px -6px, -14px 6px,  14px -6px;
  }
  33% {
    box-shadow: -38px 6px, -14px -6px,  14px 6px;
  }
  66% {
    box-shadow: -38px -6px, -14px 6px, 14px -6px;
  }
  100% {
    box-shadow: -38px 6px, -14px -6px, 14px 6px;
  }
}
</style>