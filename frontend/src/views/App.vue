<template>
    <v-main>
        <h1>Привет, {{ username }}</h1>
        <div v-if="!show_search">
            Добавьте несколько интересов, чтобы мы могли лучше сопоставить Вам пару
            <input type="text" @keypress.enter="saveInterest" v-model="new_interest">
            <v-btn @click="show_search = true">Далее</v-btn>
        </div>        

        <div v-if="show_search">
            <div>
                <v-btn @click="searchPeople">Начать поиск</v-btn>
            </div>
            Ваши интересы:
            <div v-for="(interest, index) in interests" :key="index">
                <input type="text" :value="interest">
            </div>
            Добавить:
            <input type="text" @keypress.enter="saveInterest" v-model="new_interest">
        </div>
    </v-main>
</template>

<script>
export default {
    name: 'App',
    data: () => ({
        new_interest: '',
        show_search: false,
    }),
    methods: {
        saveInterest() {
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
        }
    },
    computed: {
        username() {
            return this.$store.state.user;
        },
        interests() {
            return this.$store.state.interests;
        }
    }
}
</script>