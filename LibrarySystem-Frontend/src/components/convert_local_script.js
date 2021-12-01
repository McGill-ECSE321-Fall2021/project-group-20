import axios from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.build.host
var backendUrl = 'http://' + config.build.backendHost

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'convert_local_script',
  data() {
    return {
      error: '',
      response: [],
      customers: []
    }
  },

  methods: {
    cancel() {
      this.$router.push('/')
    },
    convert: function (username, email) {
      AXIOS.put(backendUrl + '/customer/convert/' + document.getElementById('select').value + '?username=' + username + '&password=password&email=' + email).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        this.error = msg.response.data
        console.log(this.error)
      })
    }
    },

    beforeMount() {
      AXIOS.get(backendUrl + '/customers/local').then(response => {
        this.customers = response.data;
        console.log(this.customers)
      }).catch(msg => {
        this.error = msg.response.data
        console.log(this.error)
      })
    }
}
