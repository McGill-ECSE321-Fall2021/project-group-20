<template>
  <div id="profile_view">

    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>

    <h2 class="main_title">
      <b>View Employee Shifts</b>

    </h2>

    <div class="calendar">
      <b-calendar v-model="value" :date-disabled-fn="dateDisabled" locale="en" class="wrapper"></b-calendar>
    </div>

    <div class="shiftTable">
      <b-table striped outline hover :items="itemsTitles">

      </b-table>
    </div>
<!--    <hr>-->
<!--      <h2>Employee Shifts</h2>-->
<!--      <table>-->
<!--        <tr>-->
<!--          <th>Name</th>-->
<!--          <th>Employee ID</th>-->
<!--          <th>Start Time</th>-->
<!--          <th>End Time </th>-->
<!--        </tr>-->
<!--        <tr v-for="event in events">-->
<!--          <td>{{ event.name }}</td>-->
<!--          <td>{{ event.eventDate }}</td>-->
<!--          <td>{{ event.startTime }}</td>-->
<!--          <td>{{ event.endTime }}</td>-->
<!--        </tr>-->
<!--      </table>-->
<!--    <hr>-->




  </div>
</template>

<script>
export default {
  data() {
    return {
      value: '',
      fields: [
        {
          key: 'Name'
        },

        {
          key: 'Employee ID'
        },

        {
          key: 'Start Time'
        },

        {
          key: 'End Time'
        }
      ]
    }
  },
  methods: {
    dateDisabled(ymd, date) {
      // Disable weekends (Sunday = `0`, Saturday = `6`) and
      // disable days that fall on the 13th of the month
      const weekday = date.getDay()
      const day = date.getDate()
      // Return `true` if the date should be disabled
      return weekday === 0 || weekday === 6 || day === 13
    }
  }
}
</script>

<style scoped>

.calendar {
  width: 50%;
  min-width: 200vh;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 4vh;
}


#profile_view {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 50vh;
}

</style>
