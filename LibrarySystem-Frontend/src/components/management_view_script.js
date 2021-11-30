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
      response: [],
      success: []
    }
  },

  methods: {

    createArchive: function(titleid) {

      AXIOS.post(backendUrl + '/Archives/create?status=' + document.getElementById('archive_item_status').value + '&titleId=' + titleid).then(response => {
        this.response = response.data
        this.success = 'success'
        console.log(response)
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      });
    },

    createBook: function(titleid,isbncode, numberpages) {

      AXIOS.post(backendUrl + '/Books/create?status=' + document.getElementById('book_item_status').value + '&titleId=' + titleid
        + '&isbn=' + isbncode + '&pages=' + numberpages).then(response => {
        this.response = response.data
        this.success = 'success'
        console.log(response)
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      });
    },

    createMovie: function(titleid, lengthmovie) {

      AXIOS.post(backendUrl + '/Movies/create?status=' + document.getElementById('movie_item_status').value + '&titleId=' + titleid
        + '&length=' + lengthmovie).then(response => {
        this.response = response.data
        this.success = 'success'
        console.log(response)
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      });
    },

    createMusicAlbum: function(titleid, lengthmusic) {

      AXIOS.post(backendUrl + '/MusicAlbums/create?status=' + document.getElementById('music_item_status').value + '&titleId=' + titleid
        + '&length=' + lengthmusic).then(response => {
        this.response = response.data
        this.success = 'success'
        console.log(response)
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      });
    },




    createNewsPaper: function(titleid) {

      AXIOS.post(backendUrl + '/Newspapers/create?status=' + document.getElementById('newspaper_item_status').value + '&titleId=' + titleid).then(response => {
        this.response = response.data
        this.success = 'success'
        console.log(response)
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      });
    },


    // createArchive: function (archive_item_status) {
    //
    //     // AXIOS.post(backendUrl + '/author/create?firstname=' + firstname + '&lastname=' + lastname).then(response => {
    //     //   this.responseAuthor = response.data
    //     //   AXIOS.post(backendUrl + '/title/create?name=' + titlename + '&publishdate=' + publishdate + '&authors=' + this.responseAuthor.authorID).then(response =>
    //     //     {
    //     //       this.responseTitle = response.data
    //     //       AXIOS.post(backendUrl+'/items/create?status=' + archive_item_status + '&titleId=' + this.responseTitle.titleID).then(response =>
    //     //         {
    //     //           this.responseItem = response.data
    //     //         }
    //     //       ).catch(msg =>
    //     //         {
    //     //           console.log(msg.response.data)
    //     //           this.error = msg.response.data;
    //     //         }
    //     //       )
    //     //       //this.error = ''
    //     //       console.log(repsonse)
    //     //     }).catch(msg => {
    //     //       console.log(msg.response.data)
    //     //     if (this.error === '') this.error = msg.response.data;
    //     //     })
    //     //   //this.error = ''
    //     //   console.log(response)
    //     // }).catch(msg => {
    //     //   console.log(msg.response.data)
    //     //   if (this.error === '') this.error = msg.response.data;
    //     // })
    //
    //
    //   AXIOS.post(backendUrl+'/items/create?status=' + archive_item_status + '&titleId=' + this.responseTitle.titleID).then(response =>
    //     {
    //       this.responseItem = response.data
    //     }
    //   ).catch(msg =>
    //     {
    //       console.log(msg.response.data)
    //       this.error = msg.response.data;
    //     }
    //   )
    //
    //
    //
    // },
    //
    openUpdateItemPage() {
      this.$router.push('/EmployeePage/Management/Update')
    },

    openCreateAuthorPage() {
      this.$router.push('/EmployeePage/Management/Create_Author')
    },

    openCreateTitlePage() {
      this.$router.push('/EmployeePage/Management/Create_Title')
    },
    //
    // beforeMount() {
    //   AXIOS.get(backendUrl + '/customer/verified').then(response => {
    //     this.accounts = response.data;
    //   }).catch(msg => {
    //     this.error = msg.response.data;
    //     console.log(this.error)
    //   })
    // }
  }


}
