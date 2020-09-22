<template>
  <v-main>
    <v-container fluid fill-height>
      <div class="justify-center mx-auto mt-5">
        
        <div v-if="round_ready">
          <h1 align="center">
            <Timer @timesup="reset_queue" :timelimit="timelimit" />
          </h1>
          <Chat />
        </div>

        <div v-else-if="searching">
          <h1>Ищем партнера...</h1>
          <span class="loader"></span>
        </div>

        <div v-else>
          <h1 v-if="user">Привет, 
            <span v-if="user.first_name && user.last_name">{{ user.first_name }} {{ user.last_name }}</span>
            <span v-else-if="user.first_name">{{ user.first_name }}</span>
            <span v-else>{{ user.name }}</span>
          </h1>
          <p>Нажмите на кнопку, чтобы начать раунд</p>
          <v-btn @click="search">Искать партнера</v-btn>
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
    searching: false,
    round_ready: false,
    timelimit: 15,
  }),
  methods: {
    search() {
      this.searching = true;
      
      this.$store.commit('createWSConnection', new WebSocket('ws://localhost:8000/api/app/chat/?token=' + this.access_token));
        
      this.$store.commit('setWSonopen', this.onopen);
      this.$store.commit('setWSonclose', this.onclose);
      this.$store.commit('setWSonmessage', this.onmessage);   
    },
    research() {
      this.$store.commit('createWSConnection', new WebSocket('ws://localhost:8000/api/app/chat/?token=' + this.access_token));
    },
    onmessage(event) {
      // в этом компоненте ставим обработчик, который устанавливает подключение с другим клиентом
      // в Chat меняем на обработчик передачи сообщений
      const data = JSON.parse(event.data);

      console.log(data)

      switch(data.type) {
        case 'request.connection':
          // если в данный момент не обрабатывается запросов на подключение
          // то принимаем запрос и ждем подтверждения
          if (this.chatter === null) {
            if ((this.last_chatter === null) || (this.last_chatter.name !== data.user.name)) {
              this.$store.commit('setChatter', data.user);
              this.connection.send(JSON.stringify({
                  type: 'accept.connection',
                  user: data.user,
                  channel: data.channel,
                })
              );
            }
          }

          // если получаем ответный запрос от узла, с которым устанавливаем подключение
          // то считаем подключение установленным
          else if (this.chatter.name === data.user.name){
            this.connection.send(JSON.stringify({
                type: 'connection.established',
                user: data.user,
                channel: data.channel,
              })
            );
          }

          // если получаем запрос от узла, отличного от того, с которым уже устанавливается подключение
          // то отклоняем запрос
          else {
            this.connection.send(JSON.stringify({
                type: 'deny.connection',
                user: data.user,
                channel: data.channel,
              })
            );
          }
          break;

        // если получаем deny.connection, то сбрасываем текущего chatter
        // и ждем подключений
        case 'deny.connection':
          if (this.chatter.name === data.user.name) {
            this.$store.commit('setChatter', null);
          }
          break;
        
        // если получаем connection.established, то считаем подключение установленным
        // и используем this.connection для чата
        case 'connection.established':
          this.searching = false;
          this.round_ready = true;
          break;
      }
    },
    onopen() {
      this.connection.send(JSON.stringify({
          type: 'init.connection'
        })
      );
    },
    onclose(event) {
      if (event.code === 1006) {
        this.$store.dispatch('refresh')
                    .then(() => this.research())
      }
      
    },
    reset_queue() {
      this.round_ready = false;
      this.searching = true;

      this.$store.commit('setLastChatter', this.chatter);
      
      this.connection.send(JSON.stringify({
          type: 'reset.connection',
          chatter: this.chatter,
        })
      );

      this.$store.commit('setChatter', null);
      this.$store.commit('setWSonmessage', this.onmessage);
    }
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
    access_token() {
      return this.$store.state.access_token;
    },
    connection() {
      return this.$store.state.ws_connection;
    },
    chatter() {
      return this.$store.state.chatter;
    },
    last_chatter() {
      return this.$store.state.last_chatter;
    },
  },
  beforeMount: function() {
    if (!this.access_token) {
      this.$router.push({name: 'Home'})
    }
  },
};
</script>

<style scoped>
</style>