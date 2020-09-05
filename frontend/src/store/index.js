import Vue from 'vue'
import Vuex from 'vuex'

import api from '../api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: false,
    interests: [],
    token: false,
    rounddata: {},
  },
  mutations: {
    loginSuccess(state, user, token) {
      state.user = user;
      state.token = token;
    },
    loginFailed(state) {
      return state;
    },
    logout(state) {
      state.user = ''
      state.token = null;
    },
    updateInfo(state, interests) {
      state.interests = interests;
    },
    addInterest(state, interest) {
      state.interests.push(interest);
    },
    updateRoundData(state, data) {
      state.rounddata.participants = data.participants;
    },
    sudoku(state) {
      return state;
    }
  },
  actions: {
    login({commit}, {email, password}) {
      return api.login(email, password)
                .then(({data}) => commit('loginSuccess', data.user, data.token))
                .then(({data}) => commit('updateInfo', data.interests))
                .catch(() => commit('sudoku'))
    },
    logout({commit}) {
      return api.logout()
                .then(() => commit('logout'))
                .catch(() => commit('sudoku'))
    },
    signup({commit}, data) {
      return api.signup(data)
                .catch(() => commit('sudoku'))
    },
    updateInfo({commit}, data) {
      return api.updateInfo(data)
                .then(({data}) => commit('updateInfo', data.interests))
                .catch(() => commit('sudoku'))
    },
    pickup({commit}) {
      return api.pickup()
                .then(({data}) => commit('updateRoundData', data))
                .catch(() => commit('sudoku'))
    }
  },
  modules: {
  }
})
