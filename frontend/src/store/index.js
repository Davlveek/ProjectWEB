import Vue from 'vue'
import Vuex from 'vuex'

import api from '../api'
import {updateHeader} from '../api'

Vue.use(Vuex)

const getDefaultState = () => {
  return {
    user: null,
    access_token: null,
    refresh_token: null,
    ws_connection: null,
    chatter: null,
    last_chatter: null,
  }
}

// initial state
const state = getDefaultState()

export default new Vuex.Store({
  state,
  mutations: {
    // AUTH
    setAccessToken(state, token) {
      localStorage.setItem('access_token', token);
      state.access_token = token;
      updateHeader(state.access_token);
    },
    setRefreshToken(state, token) {
      localStorage.setItem('refresh_token', token);
      state.refresh_token = token;
    },    

    // INFO
    updateUserInfo(state, data) {
      state.user = data;
    },

    // WebSocket
    createWSConnection(state, conn) {
      state.ws_connection = conn;
    },
    setWSonmessage(state, func) {
      state.ws_connection.onmessage = func;
    },
    setWSonopen(state, func) {
      state.ws_connection.onopen = func;
    },
    setWSonclose(state, func) {
      state.ws_connection.onclose = func;
    },

    // CHAT
    setChatter(state, data) {
      state.chatter = data;
    },
    setLastChatter(state, data) {
      state.last_chatter = data;
    },

    resetState (state) {
      localStorage.removeItem('access_token');
      localStorage.removeItem('refresh_token');

      updateHeader(null);

      if (state.ws_connection) {
        state.ws_connection.close();
      }

      Object.assign(state, getDefaultState());
    }
  },
  actions: {

    // AUTH
    login({commit}, data) {
      return api.login(data)
                  .then(function({data}) {
                    commit('setAccessToken', data.access);
                    commit('setRefreshToken', data.refresh);
                  })
    },
    refresh({commit}) {
      return api.refresh({refresh: state.refresh_token})
                  .then(({data}) => commit('setAccessToken', data.access))
    },
    signup({commit}, data) {
      console.log(commit);
      return api.signup(data)
    },

    // GET INFO
    getUserInfo({commit}) {
      return api.getuserinfo()
                .then(({data}) => commit('updateUserInfo', data))
                .catch(() => this.dispatch('refreshTokenAndRetry', 'getUserInfo'))
    },

    // SET INFO
    updateUserInfo({commit}, data) {
      return api.updateuserinfo(data)
                .then(({data}) => commit('updateUserInfo', data))
    },

    refreshTokenAndRetry(context, action, data=null) {
      this.dispatch('refresh')
            .then(() => data === null ? this.dispatch(action) : this.dispatch(action, data))
              
    },
  },
  modules: {
  }
})
