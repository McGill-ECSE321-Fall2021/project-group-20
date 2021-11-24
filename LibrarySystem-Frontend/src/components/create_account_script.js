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
    create: function (firstname, lastname, email, username, password, civic, street, city, postalCode, province, country) {
      AXIOS.post(backendUrl + '/customer/create?firstname=' + firstname + '&lastname=' + lastname +
        '&email=' + email + '&username=' + username + '&password=' + password + '&civic=' + civic +
        '&street=' + street + '&city=' + city + '&postalCode=' + postalCode + '&province=' +
        province + '&country=' + country).then(response => {
          this.response = response.data
        this.error = ''
        console.log(response)
        if (this.response != '') {
          this.$router.push('home')
        }
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },
    cancel: function () {
      this.$router.push('/')
    }
  }
}
