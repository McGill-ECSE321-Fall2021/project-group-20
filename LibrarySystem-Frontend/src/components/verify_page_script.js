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
  name: 'verify_page_script',
  data() {
    return {
      error: '',
      response: [],
      accounts: []
    }
  },

  methods: {
    cancel: function () {
      this.$router.push('/')
    },
    verify: function() {
      AXIOS.put(backendUrl + '/customer/validate/' + document.getElementById('select').value).then(response => {
        this.response = response.data;
        if (this.response.isVerified) {
          location.reload()
        }
      }).catch(msg => {
        this.error = msg.response.data;
        console.log(this.error)
      })
    }
  },

  beforeMount() {
    AXIOS.get(backendUrl + '/customer/verified').then(response => {
      this.accounts = response.data;
    }).catch(msg => {
      this.error = msg.response.data;
      console.log(this.error)
    })
  }
}
