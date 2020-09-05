<template>
  <div class="home">
    <v-main>
      <v-container fluid>
          <h1>
            тут заебатое название
            <br />
            <small>тут заебатая подпись</small>
          </h1>
          <component :is="comp"></component>
      </v-container>
    </v-main>
  </div>
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
h1 {
  text-align: center;
}

.home {
  height: 100%;
  /*background:linear-gradient(90deg, rgb(40, 34, 148) 0%, rgba(15,108,103,1) 53%, rgb(37, 88, 153) 100%);*/
}

.home.bar {
  background-color: rgb(66, 45, 75);
}
</style>