<template>
  <v-main>
    <v-container fill-height fluid>
      <v-col justify="center">
        <v-row justify="center">
          <h1>Лабораторная работа №1</h1>
        </v-row>
        <v-row justify="center">
          <h3>Мы не веберы, но что-то сделать надо</h3>
        </v-row>
        <component :is="comp"></component>
      </v-col>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "Home",
  props: ['comp'],
  data: () => ({
  })
};
</script>

<style>
.home {
  height: 100%;
  /*background:linear-gradient(90deg, rgb(40, 34, 148) 0%, rgba(15,108,103,1) 53%, rgb(37, 88, 153) 100%);*/
}

.home.bar {
  background-color: rgb(66, 45, 75);
}
</style>