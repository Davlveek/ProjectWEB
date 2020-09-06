<template>
  <v-main>
    <h1>Привет, {{ username }}</h1>
    <div v-if="round_ready">
      <Chat :chel2="next_chel"></Chat>
    </div>
    <div v-else-if="search_in_progress">
      <span class="loader"></span>
    </div>
    <div v-else>
      <p>Нажми на кнопку, чтобы начать раунд</p>
      <v-btn @click="pickup">Искать других дебилов</v-btn>
    </div>
  </v-main>
</template>

<script>
import Chat from '../components/Chat'

export default {
    name: 'App',
    components: {
        Chat
    },
    data: () => ({
        search_in_progress: false,
        round_ready: false,
    }),
    methods: {
        pickup() {
            this.search_in_progress = true;
            this.$store.dispatch('pickup')
                    .then(this.setupRound)
        },
        setupRound() {
            
        }
        /*saveInterest() {
            this.$store.commit('addInterest', this.new_interest);
            this.new_interest = '';
        },
        searchPeople() {
            //this.$store.dispatch('pickup');
            
            var participants = [
                {
                    'name': 'asd',
                    'age': 21
                }
            ]
            console.log(participants)
            this.$router.push('/chat/');
        }*/
    },
    computed: {
        username() {
            return this.$store.state.user;
        },
        rounddata() {
            return this.$store.state.rounddata;
        }
    }
}
</script>

<style scoped>

</style>