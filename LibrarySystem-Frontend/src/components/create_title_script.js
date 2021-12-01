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
  name: 'create_title_script',
  data() {
    return {
      error: '',
      success: '',
      response: [],
    }
  },

  methods: {

    addTitle: function (titlename, pubdate, authorids) {

        AXIOS.post(backendUrl + '/title/create?name=' + titlename + '&pubDate=' + pubdate + '&authors=' + authorids).then(response => {
          this.response = response.data
          this.success = response.data.titleID
          console.log(response)
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        });
      },

    cancel: function () {
      this.$router.push('/EmployeePage/Management/')
    }

  }
}
