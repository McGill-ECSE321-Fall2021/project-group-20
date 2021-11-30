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
  name: 'create_account_script',
  data() {
    return {
      error: '',
      response: []
    }
  },

  methods: {
    create: function (firstname, lastname, email, username, password, password_conf, civic, street, city, postalCode) {
      if (document.getElementById('firstname').value === '') {
        this.error = "Please enter a valid first name"
      }

      else if (document.getElementById('lastname').value === '') {
        this.error = "Please enter a valid last name"
      }

      else if (document.getElementById('email').value === '') {
        this.error = "Please enter a valid email"
      }

      else if (document.getElementById('civic').value === '') {
        this.error = "Please enter a valid civic number"
      }

      else if (document.getElementById('street').value === '') {
        this.error = "Please enter a valid street"
      }

      else if (document.getElementById('city').value === '') {
        this.error = "Please enter a valid city"
      }

      else if (document.getElementById('postal').value === '') {
        this.error = "Please enter a valid postal code"
      }

      else if (document.getElementById('username').value === '') {
        this.error = "Please enter a valid username"
      }

      else if (document.getElementById('password').value === '') {
        this.error = "Please enter a valid password"
      }

      else if (password === password_conf) {
        AXIOS.post(backendUrl + '/customer/create?firstname=' + firstname + '&lastname=' + lastname +
          '&email=' + email + '&username=' + username + '&password=' + password + '&civic=' + civic +
          '&street=' + street + '&city=' + city + '&postalCode=' + postalCode + '&province=' +
          document.getElementById('province').value + '&country=' + document.getElementById('country').value).then(response => {
            this.response = response.data
          this.error = ''
          console.log(response)
          if (this.response.username === username && this.response.email === email && this.response.isLoggedIn) {
            document.cookie = "libraryCardID=" + this.response.libraryCardID + "; path=/";
            document.cookie = "usertype=customer; path=/";
            this.$router.push('home')
          }
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })}

      else {
        this.error = "Inputted passwords do not match!";
      }
    },

    cancel: function () {
      this.$router.push('/')
    }
  }
}
