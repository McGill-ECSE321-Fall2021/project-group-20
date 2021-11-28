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
  name: 'create_title_script',
  data() {
    return {
      error: '',
      responseAuthor: [],
      responseTitle: [],
      responseItem: []
    }
  },

  methods: {

    addAuthor: function () {
      if (document.getElementById('firstname').value === '') {
        this.error = "Please enter a valid first name"
      }

      else if (document.getElementById('lastname').value === '') {
        this.error = "Please enter a valid last name"
      }
      else {

      }

    },

    cancel: function () {
      this.$router.push('/')
    }

  }
}
