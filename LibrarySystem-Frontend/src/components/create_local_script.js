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
  name: 'create_local_script',
  data() {
    return {
      error: '',
      response: []
    }
  },

  methods: {
    create: function (firstname, lastname, civic, street, city, postalCode) {
      if (document.getElementById('firstname').value === '') {
        this.error = "Please enter a valid first name"
      }

      else if (document.getElementById('lastname').value === '') {
        this.error = "Please enter a valid last name"
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

      else {
        AXIOS.post(backendUrl + '/customer/createLocal?firstname=' + firstname + '&lastname=' + lastname +
          '&civic=' + civic + '&street=' + street + '&city=' + city + '&postalCode=' + postalCode + '&province=' +
          document.getElementById('province').value + '&country=' + document.getElementById('country').value).then(response => {
          this.response = response.data
          this.error = ''
          if (this.response.firstName === firstname && this.response.lastName === lastname && this.response.isVerified) {
            this.$router.push('/');
          }
        }).catch(msg => {
          console.log(msg.response.data);
          console.log(msg.response.status);
          this.error = msg.response.data;
        })}
    },

    cancel: function () {
      this.$router.push('/')
    }
  }
}
