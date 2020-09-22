<template>
  <v-card width="500" class="mx-auto mt-5" shaped>
    <v-form ref='form' lazy-validation>      

      <v-card-title class="justify-center">
        <h2>Создать аккаунт</h2>
      </v-card-title>

      <v-card-text>
        <v-text-field v-model="name" label="Никнейм*" :rules="nameRules" required></v-text-field>

        <v-text-field type="password" v-model="password" label="Пароль*" :rules="passRules" required></v-text-field>
        <v-text-field type="password" label="Подтвердите пароль*" :rules="confirmPassRules" required></v-text-field>

        <v-text-field v-model="age" label="Возраст*" :rules="ageRules" required></v-text-field>

        <v-radio-group v-model="gender_choice" required :rules="genderRules" row>
          <v-radio v-for="gender in genders" :key="gender.short" :label="gender.value" :value="gender.short"></v-radio>
        </v-radio-group>

        <div v-if="error_message">
          <span style="color:red;">{{ error_message }}</span>
        </div>
      </v-card-text>

      <v-card-actions class="justify-center">
        <div v-if="signup_in_process">
          <span class="loader"></span>
        </div>
        <div v-else-if="signedup_once">
          <v-btn color="success" icon disabled>
            Аккаунт создан
          </v-btn>
        </div>
        <div v-else>
          <v-btn @click="signup">Создать</v-btn>
        </div>
      </v-card-actions>

    </v-form>
  </v-card>
</template>


<script>
export default {
  name: "Signup",
  data: () => ({
    signup_in_process: false,
    error_message: '',

    name: '',
    password: '',
    age: '',
    genders: [{value: "мужчина", short: "m"}, {value: "женщина", short: "f"}, {value: "другое", short: "o"}],
    gender_choice: null,
    
    nameRules: [
      name => name.length >= 3 || 'Выберите имя длиной не менее 3 символов',
    ],
    ageRules: [
      age => !isNaN(age) || 'Неверно задан возраст',
      age => age >= 16 || 'Вы должны быть не младше 16 лет',
      age => age <= 100 || '????',
    ],
    passRules: [
      pass => pass.length >= 8 || 'Задайте пароль длиной не менее 8 символов',
      pass => pass.length <= 64 || 'Задайте пароль длиной не более 64 символов',
    ],
    genderRules: [
      gender => gender !== null || 'Выберите пол'
    ] 
  }),
  computed: {
    confirmPassRules() {
      return [pass => pass === this.password || "Пароли не совпадают"]
    },
    signedup_once() {
      return this.$store.state.signedup_once;
    }
  },
  methods: {
    signup() {
      this.signup_in_process = true;

      if (this.$refs.form.validate()) {

        var data = {
          name: this.name,
          password: this.password,
          age: this.age,
          gender: this.gender_choice,
          first_name: null,
          last_name: null,
        }
        
        this.$store.dispatch('signup', data)
        .then(() => {
          this.signup_in_process = false;
        })
        .catch((error) => {
          this.signup_in_process = false;
          if (error.status === 409) {
            this.error_message = "Пользователь с таким именем уже существует, попробуйте другое";
          }
          else {
            this.error_message = "Ошибка, попробуйте снова";
          }
        });
      }
      else {
        this.signup_in_process = false;
      }
    },
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