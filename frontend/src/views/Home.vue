<template>
  <v-main>
    <v-container fill-height fluid>
      <v-col justify="center">
        <v-row justify="center">
          <h1>Rev-o-Dates</h1>
        </v-row>
        <v-row justify="center">
          <h2>Лабораторная работа №1</h2>
        </v-row>
        <v-row justify="center">
          <small>Мы не веберы, но что-то сделать надо</small>
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
};
</script>
