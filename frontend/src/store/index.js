import Vue from 'vue'
import Vuex from 'vuex'

import api from '../api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null,
    token: null,
    refresh_token: null,
    rounddata: {},
  },
  mutations: {

    // AUTH
    setToken(state, data) {
      state.token = data.access;
      state.refresh_token = data.refresh;
    },
    logout(state) {
      state.user = null;
      state.token = null;
      state.refresh_token = null;
    },

    // INFO
    updateUserInfo(state, data) {
      state.user = data;
    },
    updateRoundData(state, data) {
      state.rounddata = data;
    },
  },
  actions: {

    // AUTH
    login({commit}, data) {
      return api.login(data)
                .then(({data}) => commit('setToken', data))
    },
    logout({commit}) {
      return api.logout()
                .then(() => commit('logout'))
    },
    signup({commit}, data) {
      console.log(commit);
      return api.signup(data)
    },

    // GET INFO
    getRoundData({commit}) {
      return api.pickup()
                .then(({data}) => commit('updateRoundData', data))
    },
    getUserInfo({commit}) {
      return api.getuserinfo()
                .then(({data}) => commit('updateUserInfo', data))
    },

    // SET INFO
    updateUserInfo({commit}, data) {
      return api.updateuserinfo(data)
                .then(({data}) => commit('updateUserInfo', data))
    },
  },
  modules: {
  }
})
