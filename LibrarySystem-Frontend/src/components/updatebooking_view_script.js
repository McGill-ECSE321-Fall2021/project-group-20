import axios from 'axios'
import JQuery from 'jquery'



let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl, backendUrl}
})
export default {
  name: 'updatebooking_view_script',
  data() {
    return {

      error: '',
      bookings: [],
      response: []

    }
  },

  beforeMount() {
    let split = document.cookie.split(';')
    let id = split[0].split('=');
    AXIOS.get(backendUrl + '/booking/user/?libraryId=' +  "109"  ).then(response => {
      this.bookings = response.data
    }).catch(e => {
      this.error = e
    });
  },



}
