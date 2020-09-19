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
};
</script>
