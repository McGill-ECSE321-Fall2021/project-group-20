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
  name: 'personal_schedule_script',
  data() {
    return {
      error: '',
      response: [],
      shifts: []
    }
  },

  methods: {
    goBack() {
      this.$router.push('/');
    }
  },

  beforeMount() {
    let splits = document.cookie.split(';');
    let type = splits[0].split('=');
    AXIOS.get(backendUrl + '/hours/shifts/' + type[1]).then(response => {
      this.shifts = response.data;
    }).catch(msg => {
      this.error = msg.response.data
      console.log(this.error)
    })
  }
}
