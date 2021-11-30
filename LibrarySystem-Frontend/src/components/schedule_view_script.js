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
  name: 'schedule_view_script',
  data() {
    return {
      error: '',
      response: [],
      shifts: [],
      mondayShifts: [],
      tuesdayShifts: [],
      wednesdayShifts: [],
      thursdayShifts: [],
      fridayShifts: []
    }
  },

  methods: {
    getMondayShifts: function () {
      AXIOS.get(backendUrl + '/hour/weekday?weekday=' + 'Monday').then(response => {
        this.mondayShifts = response.data;
        console.log(response.length);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      });
    },

    getTuesdayShifts: function () {
      AXIOS.get(backendUrl + '/hour/weekday?weekday=' + 'Tuesday').then(response => {
        this.tuesdayShifts = response.data;
        console.log(response.length);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    getWednesdayShifts: function () {
      AXIOS.get(backendUrl + '/hour/weekday?weekday=' + 'Wednesday').then(response => {
        this.wednesdayShifts = response.data;
        console.log(response.length);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    getThursdayShifts: function () {
      AXIOS.get(backendUrl + '/hour/weekday?weekday=' + 'Thursday').then(response => {
        this.thursdayShifts = response.data;
        console.log(response.length);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    getFridayShifts: function () {
      AXIOS.get(backendUrl + '/hour/weekday?weekday=' + 'Friday').then(response => {
        this.fridayShifts = response.data;
        console.log(response.length);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    }
  }
}
