import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home.vue'
import App from '../views/App.vue'
import Error404 from '../views/404.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    props: true
  },
  {
    path: '/app',
    name: 'App',
    component: App
  },
  {
    path: '/*',
    name: '404',
    component: Error404
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
