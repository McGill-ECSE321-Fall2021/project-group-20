import axios from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'employee_login_script',
  data() {
    return {
      error: '',
      response: []
    }
  },

  methods: {
    login: function (name, password, type) {
      let url = backendUrl + '/employee/login'
      let sts = 0;
      if (type === "name") {
        url += '?name=' + name + '&password=' + password;
        sts = 1;
      }
      else {
        url += '/' + name + '?password=' + password;
        sts = 2;
      }

      AXIOS.put(url).then(response => {
        this.response = response.data
        this.error = ''
        console.log(response)
        if (this.response.isLoggedIn) {
          document.cookie = "libraryCardID=" + this.response.libraryCardID;
          this.$router.push('home')
        }
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.status)
        this.error = msg.response.data;
      })
    },
    back: function () {
      this.$router.push('/')
    }
  }
}
