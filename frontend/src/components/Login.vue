<template>
  <v-card width="500" class="mx-auto mt-5">
    <v-card-text>
      <v-text-field label="Email" type="text" v-model="email"></v-text-field>
      <v-text-field label="Password" type="password" v-model="password"></v-text-field>
    </v-card-text>
    
    <v-card-actions class="justify-center">
      <div v-if="!auth_in_process">
        <v-btn color="success" @click="authenticateUser">log in</v-btn>
      </div>

      <div v-else>
        <span class="loader"></span>
      </div>
    </v-card-actions>

    <v-card-text>
      <p class="text-xs-right">Don't have an account? Sign up here</p>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "Login",
  data: () => ({
    email: "",
    password: "",
    auth_in_process: false,
  }),
  methods: {
    authenticateUser() {
      this.auth_in_process = true
      
      //this.$store.dispatch('login', {email, password})
      //    .then(() => {this.$router.push('app')}))
      
      setTimeout(() => this.$router.push('app'), 1500)
      console.log(this.email, this.password)
    },
  }
};
</script>

<style scoped>
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