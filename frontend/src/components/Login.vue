<template>
  <v-card width="500" class="mx-auto mt-5" shaped>
    <v-form ref='form' lazy-validation>
      <v-card-text>
        <v-text-field v-model="name" label="Никнейм" :rules="nameRules" required></v-text-field>
        <v-text-field v-model="password" type="password" label="Пароль" :rules="passRules" required></v-text-field>
        <div v-if="error_message">
          <span style="color:red;">{{ error_message }}</span>
        </div>
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
    error_message: '',
    nameRules: [
      name => !!name || "Введите никнейм",
    ],
    passRules: [
      pass => !!pass || "Введите пароль",
    ],
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
        .then(() => this.$store.dispatch('getUserInfo'))
        .then(() => {
          this.auth_in_process = false;
          this.$router.push({name: 'App'});
        })
        .catch((error) => {
          this.auth_in_process = false;
          if (error.status === 401) {
            this.error_message = "Неверные логин или пароль";
          }
        });
        
      }
    }
  },
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