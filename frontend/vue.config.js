module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  devServer: {
    host: '0.0.0.0',
    disableHostCheck: true,
    proxy: {
      '^/api/': {
        target: 'http://127.0.0.1:8000/api/',
      }
    }
  },

}
