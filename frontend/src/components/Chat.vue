<template>
<v-container fluid >
  <v-card width=500 class="mx-auto" outlined height=450 style="overflow-y: scroll;">
    <v-card-title class="justify-center">
      Чат с {{ chatter }}
    </v-card-title>

    <v-container>
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
  props: [
    'chatter'
  ],
  data: () => ({
    new_message: '',    
    messages: [
      {
        text: 'text1',
        author: 'myself'
      },
      {
        text: 'text2',
        author: 'drugoy chel'
      },
      {
        text: 'soobdsasad',
        author: 'myself'
      }
    ]
  }),
  methods: {
    side(author) {
      return author === 'myself' ? 'end' : 'start';
    },
    sendMessage() {
      var new_message = {
        text: this.new_message,
        author: 'myself'
      };

      // сделать отправку другому участнику
      this.messages.push(new_message);
      this.new_message = '';
    },
  },
  computed: {
  }
}
</script>

<style scoped>
::-webkit-scrollbar {
    width: 0px;  /* Remove scrollbar space */
    background: transparent;  /* Optional: just make scrollbar invisible */
}
</style>