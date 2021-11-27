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
  name: 'customer_profile_vue_script',
  data() {
    return {
      error: '',
      response: []
    }
  },

  methods: {
    /* Calls updateOnlineInfo */
    customerUpdateOnlineInfo: function (userName, newPassword, email) {
      let id = document.cookie.split('=');
      AXIOS.put(backendUrl + '/customer/updateOnline/' + id[1] + '?username=' + userName + '&newPassword=' + newPassword +
        '&email=' + email).then(response => {
        this.response = response.data
        this.error = ''
        console.log(response)
        if (this.response != '') {
          this.$router.push('/EmployeePage/Profile')
        }
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    /* Calls updateInfo */
    customerUpdateInfo: function (firstname, lastname, civic, street, city, postalCode, province, country) {
      let id = document.cookie.split('=');
      AXIOS.put(backendUrl + '/customer/update/' + id[1] + '?firstname=' + firstname + '&lastname=' + lastname + '&civic=' +
        civic + '&street=' + street + '&city=' + city + '&postalCode=' + postalCode + '&province=' + province +
        '&country=' + country).then(response => {
        this.response = response.data
        this.error = ''
        console.log(response)
        if (this.response != '') {
          this.$router.push('/EmployeePage/Profile')
        }
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    cancel: function () {
      //TODO
      this.$router.push('/')
    }
  },
  beforeMount() {
    if (document.cookie.indexOf('usertype=') !== -1) {
      let splits = document.cookie.split(';');
      let type = splits[1].split('=');
      if (type[1] === 'customer') {
        this.$router.push('home');
      }
    }
    else {
      this.$router.push('/');
    }
  }
}
