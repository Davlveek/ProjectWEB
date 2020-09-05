<template>
  <v-card width="500" class="mx-auto mt-5">
    <v-card-title>
      <h2>Create new account</h2>
    </v-card-title>

    <v-card-text>
      <v-text-field v-model="firstname" label="First name" required></v-text-field>
      <v-text-field v-model="lastname" label="Last name" required></v-text-field>
      <v-text-field v-model="email" label="E-mail" required></v-text-field>
      <!-- :counter="10" :rules="nameRules"  -->
      <header>Choose your gender:</header>
      <v-radio-group v-model="gender_choice">
        <v-radio v-for="gender in genders" :key="gender" :label="gender"></v-radio>
      </v-radio-group>
    </v-card-text>

    <v-card-actions class="justify-center">
      <v-btn @click="registerUser" color="success">Create account</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "Signup",
  data: () => ({
    firstname: "",
    lastname: "",
    email: "",
    genders: ["male", "female", "other"],
    gender_choice: null,
  }),
  methods: {
    registerUser() {
      return;
    },
  },
};
</script>