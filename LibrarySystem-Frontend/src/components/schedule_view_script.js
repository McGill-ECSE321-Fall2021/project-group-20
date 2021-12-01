import axios from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.build.host
var backendUrl = 'http://' + config.build.backendHost

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl, backendUrl}
})


export default {
  name: 'schedule_view_script',
  data() {
    return {
      error: '',
      response: [],
      mondayEmployees: [],
      tuesdayEmployees: [],
      wednesdayEmployees: [],
      thursdayEmployees: [],
      fridayEmployees: [],
      saturdayEmployees: [],
      sundayEmployees: [],
      shifts: [],
      mondayShifts: [],
      tuesdayShifts: [],
      wednesdayShifts: [],
      thursdayShifts: [],
      fridayShifts: [],
      saturdayShifts: [],
      sundayShifts: [],
      library: []
    }
  },

  methods: {
    getMondayShifts: function () {
      AXIOS.get(backendUrl + '/hours/shifts/day?day=Monday').then(response => {
        this.mondayShifts = response.data;
        console.log(this.mondayShifts);
      }).catch(msg => {
        console.log(msg.response)
        console.log(msg.response.status)
        this.error = msg.response.data;
      });
    },

    getTuesdayShifts: function () {
      AXIOS.get(backendUrl + '/hours/shifts/day?day=Tuesday').then(response => {
        this.tuesdayShifts = response.data;
        console.log(this.tuesdayShifts);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    getWednesdayShifts: function () {
      AXIOS.get(backendUrl + '/hours/shifts/day?day=Wednesday').then(response => {
        this.wednesdayShifts = response.data;
        console.log(this.wednesdayShifts);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    getThursdayShifts: function () {
      AXIOS.get(backendUrl + '/hours/shifts/day?day=Thursday').then(response => {
        this.thursdayShifts = response.data;
        console.log(this.thursdayShifts);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    getFridayShifts: function () {
      AXIOS.get(backendUrl + '/hours/shifts/day?day=Friday').then(response => {
        this.fridayShifts = response.data;
        console.log(this.fridayShifts);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    getSaturdayShifts: function () {
      AXIOS.get(backendUrl + '/hours/shifts/day?day=Saturday').then(response => {
        this.saturdayShifts = response.data;
        console.log(this.saturdayShifts);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    getSundayShifts: function () {
      AXIOS.get(backendUrl + '/hours/shifts/day?day=Sunday').then(response => {
        this.sundayShifts = response.data;
        console.log(this.sundayShifts);
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    deleteMon: function () {
      AXIOS.delete(backendUrl + '/hour/weekday/id?id=' + document.getElementById('mon_select').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    deleteTue: function () {
      AXIOS.delete(backendUrl + '/hour/weekday/id?id=' + document.getElementById('tue_select').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    deleteWed: function () {
      AXIOS.delete(backendUrl + '/hour/weekday/id?id=' + document.getElementById('wed_select').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    deleteThu: function () {
      AXIOS.delete(backendUrl + '/hour/weekday/id?id=' + document.getElementById('thu_select').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    deleteFri: function () {
      AXIOS.delete(backendUrl + '/hour/weekday/id?id=' + document.getElementById('fri_select').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    deleteSat: function () {
      AXIOS.delete(backendUrl + '/hour/weekday/id?id=' + document.getElementById('sat_select').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    deleteSun: function () {
      AXIOS.delete(backendUrl + '/hour/weekday/id?id=' + document.getElementById('sun_select').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    editMon: function () {
      AXIOS.put(backendUrl + '/hour/update?id=' + document.getElementById('mon_select').value + '&startTime=' +
      document.getElementById('mon_start').value + '&endTime=' + document.getElementById('mon_end').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    editTue: function () {
      AXIOS.put(backendUrl + '/hour/update?id=' + document.getElementById('tue_select').value + '&startTime=' +
        document.getElementById('tue_start').value + '&endTime=' + document.getElementById('tue_end').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    editWed: function () {
      AXIOS.put(backendUrl + '/hour/update?id=' + document.getElementById('wed_select').value + '&startTime=' +
        document.getElementById('wed_start').value + '&endTime=' + document.getElementById('wed_end').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    editThu: function () {
      AXIOS.put(backendUrl + '/hour/update?id=' + document.getElementById('thu_select').value + '&startTime=' +
        document.getElementById('thu_start').value + '&endTime=' + document.getElementById('thu_end').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    editFri: function () {
      AXIOS.put(backendUrl + '/hour/update?id=' + document.getElementById('fri_select').value + '&startTime=' +
        document.getElementById('fri_start').value + '&endTime=' + document.getElementById('fri_end').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    editSat: function () {
      AXIOS.put(backendUrl + '/hour/update?id=' + document.getElementById('sat_select').value + '&startTime=' +
        document.getElementById('sat_start').value + '&endTime=' + document.getElementById('sat_end').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    editSun: function () {
      AXIOS.put(backendUrl + '/hour/update?id=' + document.getElementById('sun_select').value + '&startTime=' +
        document.getElementById('sun_start').value + '&endTime=' + document.getElementById('sun_end').value).then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createMon: function () {
      AXIOS.post(backendUrl + '/hour/create?weekday=Monday&startTime=' + document.getElementById('mon_start').value + '&endTime=' +
      document.getElementById('mon_end').value + '&EmployeeId=' + document.getElementById('mon_select_create').value + '&calendarId=' +
      this.library[0].calendar.calendarID + '&type=Shift').then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createTue: function () {
      AXIOS.post(backendUrl + '/hour/create?weekday=Tuesday&startTime=' + document.getElementById('tue_start').value + '&endTime=' +
        document.getElementById('tue_end').value + '&EmployeeId=' + document.getElementById('tue_select_create').value + '&calendarId=' +
        this.library[0].calendar.calendarID + '&type=Shift').then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createWed: function () {
      AXIOS.post(backendUrl + '/hour/create?weekday=Wednesday&startTime=' + document.getElementById('wed_start').value + '&endTime=' +
        document.getElementById('wed_end').value + '&EmployeeId=' + document.getElementById('wed_select_create').value + '&calendarId=' +
        this.library[0].calendar.calendarID + '&type=Shift').then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createThu: function () {
      AXIOS.post(backendUrl + '/hour/create?weekday=Thursday&startTime=' + document.getElementById('thu_start').value + '&endTime=' +
        document.getElementById('thu_end').value + '&EmployeeId=' + document.getElementById('thu_select_create').value + '&calendarId=' +
        this.library[0].calendar.calendarID + '&type=Shift').then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createFri: function () {
      AXIOS.post(backendUrl + '/hour/create?weekday=Friday&startTime=' + document.getElementById('fri_start').value + '&endTime=' +
        document.getElementById('fri_end').value + '&EmployeeId=' + document.getElementById('fri_select_create').value + '&calendarId=' +
        this.library[0].calendar.calendarID + '&type=Shift').then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createSat: function () {
      AXIOS.post(backendUrl + '/hour/create?weekday=Saturday&startTime=' + document.getElementById('sat_start').value + '&endTime=' +
        document.getElementById('sat_end').value + '&EmployeeId=' + document.getElementById('sat_select_create').value + '&calendarId=' +
        this.library[0].calendar.calendarID + '&type=Shift').then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    createSun: function () {
      AXIOS.post(backendUrl + '/hour/create?weekday=Sunday&startTime=' + document.getElementById('sun_start').value + '&endTime=' +
        document.getElementById('sun_end').value + '&EmployeeId=' + document.getElementById('sun_select_create').value + '&calendarId=' +
        this.library[0].calendar.calendarID + '&type=Shift').then(response => {
        this.response = response.data;
        location.reload();
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },
  },

  beforeMount() {
    AXIOS.get(backendUrl + '/librarySystem').then(response => {
      this.library = response.data;
    }).catch(msg => {
      console.log(msg.response.data)
      console.log(msg.response.status)
      this.error = msg.response.data;
    })

    AXIOS.get(backendUrl + '/hours/shifts/notWorking?day=Monday').then(response => {
      this.mondayEmployees = response.data;
    }).catch(msg => {
      console.log(msg.response.data)
      console.log(msg.response.status)
      this.error = msg.response.data;
    })

    AXIOS.get(backendUrl + '/hours/shifts/notWorking?day=Tuesday').then(response => {
      this.tuesdayEmployees = response.data;
    }).catch(msg => {
      console.log(msg.response.data)
      console.log(msg.response.status)
      this.error = msg.response.data;
    })

    AXIOS.get(backendUrl + '/hours/shifts/notWorking?day=Wednesday').then(response => {
      this.wednesdayEmployees = response.data;
    }).catch(msg => {
      console.log(msg.response.data)
      console.log(msg.response.status)
      this.error = msg.response.data;
    })

    AXIOS.get(backendUrl + '/hours/shifts/notWorking?day=Thursday').then(response => {
      this.thursdayEmployees = response.data;
    }).catch(msg => {
      console.log(msg.response.data)
      console.log(msg.response.status)
      this.error = msg.response.data;
    })

    AXIOS.get(backendUrl + '/hours/shifts/notWorking?day=Friday').then(response => {
      this.fridayEmployees = response.data;
    }).catch(msg => {
      console.log(msg.response.data)
      console.log(msg.response.status)
      this.error = msg.response.data;
    })

    AXIOS.get(backendUrl + '/hours/shifts/notWorking?day=Saturday').then(response => {
      this.saturdayEmployees = response.data;
    }).catch(msg => {
      console.log(msg.response.data)
      console.log(msg.response.status)
      this.error = msg.response.data;
    })

    AXIOS.get(backendUrl + '/hours/shifts/notWorking?day=Sunday').then(response => {
      this.sundayEmployees = response.data;
    }).catch(msg => {
      console.log(msg.response.data)
      console.log(msg.response.status)
      this.error = msg.response.data;
    })
  }
}
