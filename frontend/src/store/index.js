import Vue from 'vue'
import Vuex from 'vuex'

import api from '../api'

Vue.use(Vuex)

const getDefaultState = () => {
  return {
    user: null,
    access_token: null,
    refresh_token: null,
    ws_connection: null,
    chatter: null,
    last_chatter: null,
    signedup_once: false,
  }
}

// initial state
const state = getDefaultState()

export default new Vuex.Store({
  state,
  mutations: {
    // AUTH
    setAccessToken(state, token) {
      state.access_token = token;
    },
    setRefreshToken(state, token) {
      state.refresh_token = token;
    },
    updateStateTokens(state) {
      state.access_token = localStorage.getItem('access_token');
      state.refresh_token = localStorage.getItem('refresh_token');
    },

    // INFO
    updateUserInfo(state, data) {
      state.user = data;
    },
    signedupOnce(state) {
      state.signedup_once = true;
    },
    setFirstName(state, data) {
      state.user.first_name = data;
    },
    setLastName(state, data) {
      state.user.last_name = data;
    },
    setAge(state, data) {
      state.user.age = data;
    },
    setGender(state, data) {
      state.user.gender = data;
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
                    localStorage.setItem('access_token', data.access);
                    localStorage.setItem('refresh_token', data.refresh);
                    commit('updateStateTokens');
                  })
    },
    refresh({commit}) {
      return api.refresh({refresh: state.refresh_token})
                  .then(({data}) => commit('setAccessToken', data.access))
    },
    signup({commit}, data) {
      return api.signup(data)
                  .then(() => commit('signedupOnce'))
    },

    // GET INFO
    getUserInfo({commit}) {
      return api.getuserinfo()
                .then(({data}) => commit('updateUserInfo', data))
                .catch(() => {
                  this.commit('updateStateTokens');
                  
                  if (state.access_token !== null) {
                    return this.dispatch('getUserInfo');
                  }
                })
                
    },

    // SET INFO
    updateUserInfo({commit}) {
      return api.updateuserinfo(state.user)
                .catch(() => {
                  commit('updateStateTokens');
                  
                  if (state.access_token !== null) {
                    return this.dispatch('updateUserInfo');
                  }
                })
    },
  },
  modules: {
  }
})
