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
  name: 'headlibrarian_view_script',
  data() {
    return {
      error: '',
      response: [],
      slide: 0,
      sliding: null
    }
  },

  methods: {

    openBooking(){
      this.$router.push('/EmployeePage/Booking')
    },
    openManagement(){
      this.$router.push('/EmployeePage/Management')
    },
    openEvent(){
      this.$router.push('/EmployeePage/Event')
    },
    openSchedule(){
      this.$router.push('/EmployeePage/Schedule')
    },
    openProfile(){
      this.$router.push('Employee/Profile')
    },
    openOrganize(){
      this.$router.push('HeadLibrarian/Organize')
    }
  }
}
