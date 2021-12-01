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
  name: 'fee_script',
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
    update: function (amount) {
      AXIOS.put(backendUrl + '/customer/balance/' + document.getElementById('select').value + '?toModify=-' + amount).then(response => {
        this.response = response.data;
        console.log(this.response)
        location.reload()
      }).catch (msg => {
        this.error = msg.response.data;
        console.log(this.error)
      })
    }
  },

  beforeMount() {
    AXIOS.get(backendUrl + '/customer/withBalance').then(response => {
      this.accounts = response.data;
    }).catch(msg => {
      this.error = msg.response.data;
      console.log(this.error)
    })
  }
}
