<template>
  <v-main>
    <v-container fluid fill-height>
      <div class="justify-center mx-auto mt-5">
        <div v-if="round_ready">
          <h1 align="center">
            <Timer @timesup="setupNewChat" timelimit="30" />
          </h1>
          <Chat :chatter="next_chatter" />
        </div>
        <div v-else-if="search_in_progress">
          <span class="loader"></span>
        </div>
        <div v-else>
          <h1>Привет, {{ user.name }}</h1>
          <p>Нажмите на кнопку, чтобы начать раунд</p>
          <v-btn @click="pickup">Искать других дебилов</v-btn>
        </div>
      </div>
    </v-container>
  </v-main>
</template>

<script>
import Timer from "../components/Timer";
import Chat from "../components/Chat";

export default {
  name: "App",
  components: {
    Chat,
    Timer,
  },
  data: () => ({
    search_in_progress: false,
    round_ready: false,
    next_chatter: "vtoroy",
  }),
  methods: {
    pickup() {
      this.search_in_progress = true;
      //this.$store.dispatch('pickup')
      //        .then(this.setupRound)
      setTimeout(() => this.setupRound(), 1500);
    },
    setupRound() {
      this.search_in_progress = false;
      //this.next_chatter = this.rounddata.next
      this.round_ready = true;
    },
    setupNewChat() {
      // кидаем на нового чела
      //this.round_ready = false;
    },
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
    rounddata() {
      return this.$store.state.rounddata;
    },
    token() {
      return this.$store.state.token;
    }
  },
  created: function() {
    if (!this.token) {
      this.$router.push({name:'Home', props: {comp: 'null'}})
    }
  },
  updated: function() {
    if (!this.token) {
      this.$router.push({name:'Home', props: {comp: 'null'}})
    }
  }
};
</script>

<style scoped>
</style>