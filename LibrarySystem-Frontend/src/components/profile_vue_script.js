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
  name: 'profile_vue_script',
  data() {
    return {
      error: '',
      response: []
    }
  },

  methods: {
    /* Calls updateOnlineInfo */
    updateOnlineInfo: function (userName, newPassword, email) {
      AXIOS.put(backendUrl + '/employee/updateOnline/{id}?username=' + userName + '&newPassword=' + newPassword +
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
    updateInfo: function (firstname, lastname, civic, street, city, postalCode, province, country) {
      AXIOS.put(backendUrl + '/employee/update/{id}?firstname=' + firstname + '&lastname=' + lastname + '&civic=' +
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
      this.$router.push('/EmployeePage')
    }
  }
}
