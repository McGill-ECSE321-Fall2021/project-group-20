//Axios get to get library sytem, this.respons[0].calendar.calendarID
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
  name: 'Event_Update_script',
  data() {
    return {
      error: '',
      response: [],
      responseCalendar: [],
      calendarID: '',
    }
  },

  methods: {
    cancel() {
      this.$router.push('/')
    },
    eventTodelete: function (dayToDelete) {
      AXIOS.delete(backendUrl + '/event/date?date=' + dayToDelete).then(response => {

        this.response = response.data
        this.error = ''
        console.log(response)
        this.$router.push("/EmployeePage/Event")

      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    }

  }
}
