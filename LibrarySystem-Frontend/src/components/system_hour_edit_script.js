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
  name: 'system_hour_edit_script',

  data() {
    return {
      response : [],
      error: '',
      hours: [],
    }
  },

  methods: {
    cancel() {
      this.$router.push('/');
    },
    confirm: function (startTime, endTime) {
      AXIOS.put(backendUrl + '/hour/update?id=' + document.getElementById('select').value + '&startTime=' + startTime + '&endTime=' + endTime).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        this.error = msg.response.data
        console.log(this.error)
      })
    }
  },

  beforeMount() {
    AXIOS.get(backendUrl + '/hours/system').then(response => {
      this.hours = response.data;
    }).catch(msg => {
      this.error = msg.response.data
      console.log(this.error)
    })
  }
}
