<template>
<v-container fluid >
  <v-card width=500 class="mx-auto" outlined height=450 style="overflow-y: scroll;">
    <v-card-title class="justify-center">
      Чат с {{ chatter.name }}
    </v-card-title>

    <v-container v-if="messages">
      <v-row v-for="(message, index) in messages" :key="index" :justify="side(message.author)">
        <v-card elevation="12" class='ma-2' max-width="400px" outlined><v-card-text class='ma-1 pa-1'>{{ message.text }}</v-card-text></v-card>
      </v-row>
    </v-container>
  </v-card>

  <v-card width=500 class="mx-auto" outlined>
    <v-card-actions>
      <v-text-field v-model="new_message" @keyup.enter="sendMessage"></v-text-field>
    </v-card-actions>
  </v-card>
  
</v-container>
</template>

<script>
export default {
  name: 'Chat',
  data: () => ({
    new_message: '',    
    messages: [],
  }),
  methods: {
    side(author) {
      return author.name ===  this.user.name ? 'end' : 'start';
    },
    sendMessage() {
      const new_message = {
        text: this.new_message,
        author: this.user,
      };

      this.connection.send(JSON.stringify({
        type: 'message',
        message: new_message,
        chatter: this.chatter,
      }));

      this.new_message = '';
    },
    onmessage(event) {
      const data = JSON.parse(event.data);
      console.log(data)
      this.messages.push(data.message);
      console.log(this.messages)
    }
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
    connection() {
      return this.$store.state.ws_connection;
    },
    chatter() {
      return this.$store.state.chatter;
    }
  },
  beforeMount() {
    console.log('chat set wsonmessage');
    this.$store.commit('setWSonmessage', this.onmessage);
  },
}
</script>

<style scoped>
::-webkit-scrollbar {
    width: 0px;  /* Remove scrollbar space */
    background: transparent;  /* Optional: just make scrollbar invisible */
}
</style>