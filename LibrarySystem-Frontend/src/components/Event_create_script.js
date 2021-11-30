//Axios get to get library sytem, this.respons[0].calendar.calendarID
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
  name: 'Event_create_script',
  data() {
    return {
      error: '',
      response: [],
      responseCalendar:[],
      calendarID:'',
    }
  },


  methods: {
    createeventbyEmployee: function (eventname,eventdate,eventday,startTime,endTime,employeeUsername){
      if (document.getElementById('eventname').value === '') {
        this.error = "Please enter a valid event name"
      }
      else if (document.getElementById('eventdate').value === '') {
        this.error = "Please enter a valid date"
      }
      else if (document.getElementById('eventday').value === '') {
        this.error = "Please enter a valid weekday"
      }
      else if (document.getElementById('starTime').value === '') {
        this.error = "Please enter a valid start time"
      }
      else if (document.getElementById('endTime').value === '') {
        this.error = "Please enter a valid end time"
      }
      else if (document.getElementById('employeeUsername').value === '') {
        this.error = "Please enter a valid employee Username"
      }

      else{

        AXIOS.get(backendUrl + "/librarySystem").then(reSPOONSE => {

          this.responseCalendar=reSPOONSE.data[0]

          this.calendarID =reSPOONSE.data[0].calendar.calendarID
          this.error=''
          //http://localhost:8080/event/create?name=test&date=01/10/2022&weekday=Tuesday&startTime=12:00:00&endTime=14:00:00&employeeUserName=johnboy&calendarID=
         // backendUrl + "/event/create?name=" + eventname + "&date=" + eventdate + "&weekday=" + eventday + "&startTime=" + startTime + "&endTime=" + endTime
          //+ "&employeeUserName=" + employeeUsername + "&calendarID=" + this.calendarID
          AXIOS.post(backendUrl + "/event/create?name=" + eventname + "&date=" + eventdate + "&weekday=" + eventday + "&startTime=" + startTime + "&endTime=" + endTime
          + "&employeeUserName=" + employeeUsername + "&calendarID=" +  this.calendarID).then(response => {

            this.response = response.data
            this.error = ''
            console.log(response)
            this.$router.push("/EmployeePage/Event")

          }).catch(msg => {
            console.log(msg.response.data)
            console.log(msg.response.status)
            this.error = msg.response.data;
          })
        }).catch(msg => {
          console.log(msg.responseCalendar.data)
          console.log(msg.responseCalendar.status)
          this.error = msg.responseCalendar.data;
        })



      }
    }


  }





}
