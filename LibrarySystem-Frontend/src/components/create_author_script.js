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
  name: 'create_author_script',
  data() {
    return {
      error: '',
      success: '',
      response: [],
    }
  },

  methods: {

    addAuthor: function(firstname,lastname) {
      if (document.getElementById('firstname').value === '') {
        this.error = "Please enter a valid first name"
      }
      else if (document.getElementById('lastname').value === '') {
        this.error = "Please enter a valid last name"
      }
      else {
        AXIOS.post(backendUrl + '/author/create?firstname=' + firstname + '&lastname=' + lastname).then(response => {
          this.response = response.data
          this.success = response.data.authorID
          console.log(response)
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        });
      }
    },
    cancel: function () {
      this.$router.push('/EmployeePage/Management/')
    }
  }
}
