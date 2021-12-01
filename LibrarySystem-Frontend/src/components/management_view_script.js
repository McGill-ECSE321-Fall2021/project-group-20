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
      success: [],
      authors: [],
      titles: [],
      selectedTitles: [],
      selectedAuthors: [],
    }
  },

  methods: {

    selectAuthor() {

      this.selectedAuthors = [];
      for (let author in this.authors) {
        this.selectedAuthors.push(this.authors[author].authorID)
      }
    },


    createArchive: function(titleName, titlePubDate) {
      let listOfAuthors = '';
      for (var i = 0; i < this.selectedAuthors.length; i++) {
        listOfAuthors += this.selectedAuthors[i] + ','
      }
      AXIOS.post(backendUrl + '/title/create?name=' + titleName + '&pubDate=' + titlePubDate + '&authors=' + listOfAuthors).then(titleResponse => {
        this.response = titleResponse.data
        this.error = ''
        AXIOS.post(backendUrl + '/items/create?status=' + document.getElementById("archive_item_status").value + '&titleId=' + this.response.titleID).then(itemResponse => {
        this.response = itemResponse.data
        this.error = ''
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createBook: function(titleName, titlePubDate, isbncode, numberpages) {
      let listOfAuthors = '';
      for (var i = 0; i < this.selectedAuthors.length; i++) {
        listOfAuthors += this.selectedAuthors[i] + ','
      }

      AXIOS.post(backendUrl + '/title/create?name=' + titleName + '&pubDate=' + titlePubDate + '&authors=' + listOfAuthors).then(titleResponse => {
        this.response = titleResponse.data
        this.error = ''
        AXIOS.post(backendUrl + '/Books/create?status=' + document.getElementById("book_item_status").value +
          '&titleId=' + this.response.titleID + '&isbn=' + isbncode + '&pages=' + numberpages).then(itemResponse => {
          this.response = itemResponse.data
          this.error = ''
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },


    createMovie: function(titleName, titlePubDate, lengthmovie) {
      let listOfAuthors = '';
      for (var i = 0; i < this.selectedAuthors.length; i++) {
        listOfAuthors += this.selectedAuthors[i] + ','
      }
      AXIOS.post(backendUrl + '/title/create?name=' + titleName + '&pubDate=' + titlePubDate + '&authors=' + listOfAuthors).then(titleResponse => {
        this.response = titleResponse.data
        this.error = ''
        AXIOS.post(backendUrl + '/Movies/create?status=' + document.getElementById("movie_item_status").value +
          '&titleId=' + this.response.titleID + '&length=' + lengthmovie).then(itemResponse => {
          this.response = itemResponse.data
          this.error = ''
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createMusicAlbum: function(titleName, titlePubDate, lengthmusic) {
      let listOfAuthors = '';
      for (var i = 0; i < this.selectedAuthors.length; i++) {
        listOfAuthors += this.selectedAuthors[i] + ','
      }
      AXIOS.post(backendUrl + '/title/create?name=' + titleName + '&pubDate=' + titlePubDate + '&authors=' + listOfAuthors).then(titleResponse => {
        this.response = titleResponse.data
        this.error = ''
        AXIOS.post(backendUrl + '/MusicAlbums/create?status=' + document.getElementById("music_item_status").value +
          '&titleId=' + this.response.titleID + '&length=' + lengthmusic).then(itemResponse => {
          this.response = itemResponse.data
          this.error = ''
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })

    },

    createNewsPaper: function(titleName, titlePubDate) {
      let listOfAuthors = '';
      for (var i = 0; i < this.selectedAuthors.length; i++) {
        listOfAuthors += this.selectedAuthors[i] + ','
      }
      AXIOS.post(backendUrl + '/title/create?name=' + titleName + '&pubDate=' + titlePubDate + '&authors=' + listOfAuthors).then(titleResponse => {
          this.response = titleResponse.data
          this.error = ''
        AXIOS.post(backendUrl + '/Newspapers/create?status=' + document.getElementById("music_item_status").value
        + '&titleId=' + this.response.titleID).then(itemResponse => {
          this.response = itemResponse.data
          this.error = ''
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },
    openUpdateItemPage() {
      this.$router.push('/EmployeePage/Management/Update')
    },

    openCreateAuthorPage() {
      this.$router.push('/EmployeePage/Management/Create_Author')
    },
  },

  beforeMount() {
      AXIOS.get(backendUrl + '/authors').then(response => {
        this.authors = response.data
        this.error = ''
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
  }


}
