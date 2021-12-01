import axios, {Axios} from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.build.host + ':' + config.build.port
var backendUrl = 'http://' + config.build.backendHost + ':' + config.build.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
let marepo;


export default {
  name: 'update_item_view',
  methods: {
    get(input){
      AXIOS.get(backendUrl+'/items/title/?titlename='+input).then(response => {
        this.response = response.data
        this.items = response.data
        marepo=response.data
        this.error = ''
        console.log(response)
        console.log(response.data[0].itemBarcode)

      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })

    },
    update(id,status){
      AXIOS.put(backendUrl+'/items/upstatus/?itemBarcode='+id+'&status='+status).then(response => {
        this.error= ''

      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.uperror = msg.response.data;
      })
    },
    delet(id){
      AXIOS.delete(backendUrl+'/items/delitem/?itemBarcode='+id).then(response => {
        this.error= ''
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.uperror = msg.response.data;
      })
    },
    back(){
      this.$router.push('/')
      this.$router.push('EmployeePage/Management')

    }

  },
  data() {

    return {
      error: '',
      response: [],
      items:[],
      uperror: '',
      heads:{

      },
      slide: 0,
      sliding: null
    }
  },

  mounted(){
    this.get();
  }

}
