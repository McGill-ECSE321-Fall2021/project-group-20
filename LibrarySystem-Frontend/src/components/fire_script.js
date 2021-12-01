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
  name: 'fire_script',
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
    fireL: function () {
      if (document.getElementById('select').value === document.getElementById('confirmSel').value) {
        AXIOS.delete(backendUrl + '/employee/' + document.getElementById('select').value).then(response => {
          this.response = response.data;
          if (response.status === 200) {
            this.error = "Librarian has been fired";
            location.reload();
          }
        }).catch(msg => {
          this.error = msg.response.data;
          console.log(this.error)
        })
      }
      else {
        this.error = "Please confirm the correct Librarian to fire!"
      }
    }
  },

  beforeMount() {
    AXIOS.get(backendUrl + '/employees').then(response => {
      this.accounts = response.data;
    }).catch(msg => {
      this.error = msg.response.data;
      console.log(this.error)
    })
  }
}
