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

function CustomerDto(libraryCardID, isOnlineAcc, isLoggedIn, firstName, lastName, isVerified, demeritPts, address, outstandingBalance) {
  this.libraryCardID = libraryCardID
  this.isOnlineAcc = isOnlineAcc
  this.isLoggedIn = isLoggedIn
  this.firstName = firstName
  this.lastName = lastName
  this.isVerified = isVerified
  this.demeritPts = demeritPts
  this.address = address
  this.outstandingBalance = outstandingBalance
}

export default {
  name: 'customer_login_script',
  data() {
    return {
      customer: '',
      error: '',
      response: []
    }
  },

  methods: {
    login: function(name, password) {
      AXIOS.put(backendUrl + '/customer/login?name=' + name + '&password=' + password).then(response => {
        this.response = response.data
        this.error = ''
        console.log(response)
        if (this.response != '') {
          this.$router.push('hello')
        }
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      });
    }
  }
}
