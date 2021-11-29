import axios, {Axios} from 'axios'
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
  name: 'management_view_script',
  data() {
    return {
      error: '',
      responseAuthor: [],
      responseTitle: [],
      responseItem: [],
      selected: [],
      selectedAll: []
    }
  },

  methods: {

    createArchive: function (firstname, lastname, titlename, publishdate, archive_item_status) {
      if (document.getElementById('firstname').value === '') {
        this.error = "Please enter a valid first name"
      }

      else if (document.getElementById('lastname').value === '') {
        this.error = "Please enter a valid last name"
      }

      else if (document.getElementById('titlename').value === '') {
        this.error = "Please enter a valid title name"
      }

      else if (document.getElementById('publishdate').value === '') {
        this.error = "Please enter a valid publish date"
      }

      else {

        AXIOS.post(backendUrl + '/author/create?firstname=' + firstname + '&lastname=' + lastname).then(response => {
          this.responseAuthor = response.data
          AXIOS.post(backendUrl + '/title/create?name=' + titlename + '&publishdate=' + publishdate + '&authors=' + this.responseAuthor.authorID).then(response =>
            {
              this.responseTitle = response.data
              AXIOS.post(backendUrl+'/items/create?status=' + archive_item_status + '&titleId=' + this.responseTitle.titleID).then(response =>
                {
                  this.responseItem = response.data
                }
              ).catch(msg =>
                {
                  console.log(msg.response.data)
                  this.error = msg.response.data;
                }
              )
              //this.error = ''
              console.log(repsonse)
            }).catch(msg => {
              console.log(msg.response.data)
            if (this.error === '') this.error = msg.response.data;
            })
          //this.error = ''
          console.log(response)
        }).catch(msg => {
          console.log(msg.response.data)
          if (this.error === '') this.error = msg.response.data;
        })
      }
    },

    openUpdateItemPage() {
      this.$router.push('/EmployeePage/Management/Update')
    },

    beforeMount() {
      AXIOS.get(backendUrl + '/customer/verified').then(response => {
        this.accounts = response.data;
      }).catch(msg => {
        this.error = msg.response.data;
        console.log(this.error)
      })
    }
  }


}
