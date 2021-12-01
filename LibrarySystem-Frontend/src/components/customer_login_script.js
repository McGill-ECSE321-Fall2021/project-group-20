import axios from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.build.host + ':' + config.build.port
var backendUrl = 'http://' + config.build.backendHost + ':' + config.build.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'customer_login_script',
  data() {
    return {
      error: '',
      response: []
    }
  },

  methods: {
    login: function (name, password) {
      if (document.getElementById('username').value === '') {
        this.error = "Please enter a valid username";
      }
      else if (document.getElementById('password').value === '') {
        this.error = "Please enter a valid password";
      }

      else {
        let url = backendUrl + '/customer/login'
        let sts = 0;
        if (document.getElementById('loginType').value === "name") {
          url += '?name=' + name + '&password=' + password;
          sts = 1;
        } else {
          url += '/' + name + '?password=' + password;
          sts = 2;
        }

        AXIOS.put(url).then(response => {
          this.response = response.data
          this.error = ''
          console.log(response)
          if (this.response.isLoggedIn) {
            document.cookie = "libraryCardID=" + this.response.libraryCardID + "; path=/";
            document.cookie = "usertype=customer ; path=/";
            this.$router.push('home')
          }
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.status)
          this.error = msg.response.data;
        })
      }
    },
    employee: function () {
      this.$router.push('employee')
    },
    create: function () {
      this.$router.push('create')
    },
  },
  beforeMount(){
      AXIOS.get(backendUrl + '/librarySystem').then(response => {
        this.response = response.data
        this.error = ''
        console.log(this.response)
        if (this.response === 'No Library Systems found') {
          this.$router.push('setup')
        }
      }).catch(msg => {
        this.error = msg.response.data;
        console.log(this.error)
        if (this.error === 'No Library Systems found') {
          this.$router.push('setup')
        }
      })
  }
}
