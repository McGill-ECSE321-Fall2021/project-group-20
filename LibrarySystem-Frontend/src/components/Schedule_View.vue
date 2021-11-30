<template>
  <div id="profile_view">

    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>

    <h2 class="main_title">
      <b>View Employee Shifts</b>
    </h2>

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

    <b-tabs content-class="mt-3" align="center">

      <b-tab title="Monday" id="Monday" active>
          <table>
            <div class="Fields">
              <tr>
                <th>Name</th>
                <th>Employee ID</th>
                <th>Start Time</th>
                <th>End Time</th>
              </tr>
            </div>

            <tr>
<!--              <td>{{mondayShifts.firstName}}</td>-->
<!--&lt;!&ndash;              <td>{{mondayShifts.employee.firstName}}</td>&ndash;&gt;-->
<!--&lt;!&ndash;              <td>{{mondayShifts.employee.libraryCardID}}</td>&ndash;&gt;-->
<!--              <td>{{mondayShifts.startTime}}</td>-->
<!--              <td>{{mondayShifts.endTime}}</td>-->
            </tr>

          </table>

        <div class="buttonFrame">
          <button class="btn" @click="getMondayShifts()">View</button>
        </div>

      </b-tab>

      <b-tab title="Tuesday" id="Tuesday" active>

        <table>

          <div class="Fields">
            <tr>
              <th>Name</th>
              <th>Employee ID</th>
              <th>Start Time</th>
              <th>End Time</th>
            </tr>
          </div>

<!--          <tr>-->
<!--            <td>{{tuesdayShifts.employee.firstName}}</td>-->
<!--            <td>{{tuesdayShifts.employee.libraryCardID}}</td>-->
<!--            <td>{{tuesdayShifts.startTime}}</td>-->
<!--            <td>{{tuesdayShifts.endTime}}</td>-->
<!--          </tr>-->

        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getTuesdayShifts()">View</button>
        </div>

      </b-tab>

      <b-tab title="Wednesday" id="Wednesday" active>

        <table>

          <div class="Fields">
            <tr>
              <th>Name</th>
              <th>Employee ID</th>
              <th>Start Time</th>
              <th>End Time</th>
            </tr>
          </div>

<!--          <tr>-->
<!--            <td>{{wednesdayShifts.employee.firstName}}</td>-->
<!--            <td>{{wednesdayShifts.employee.libraryCardID}}</td>-->
<!--            <td>{{wednesdayShifts.startTime}}</td>-->
<!--            <td>{{wednesdayShifts.endTime}}</td>-->
<!--          </tr>-->
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getWednesdayShifts()">View</button>
        </div>

      </b-tab>

      <b-tab title="Thursday" id="Thursday" active>

        <table>

          <div class="Fields">
            <tr>
              <th>Name</th>
              <th>Employee ID</th>
              <th>Start Time</th>
              <th>End Time</th>
            </tr>
          </div>

<!--          <tr>-->
<!--            <td>{{thursdayShifts.employee.firstName}}</td>-->
<!--            <td>{{thursdayShifts.employee.libraryCardID}}</td>-->
<!--            <td>{{thursdayShifts.startTime}}</td>-->
<!--            <td>{{thursdayShifts.endTime}}</td>-->
<!--          </tr>-->
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getThursdayShifts()">View</button>
        </div>

      </b-tab>

      <b-tab title="Friday" id="Friday" active>

        <table>

          <div class="Fields">
            <tr>
              <th>Name</th>
              <th>Employee ID</th>
              <th>Start Time</th>
              <th>End Time</th>
            </tr>
          </div>

<!--          <tr>-->
<!--            <td>{{fridayShifts.employee.firstName}}</td>-->
<!--            <td>{{fridayShifts.employee.libraryCardID}}</td>-->
<!--            <td>{{fridayShifts.startTime}}</td>-->
<!--            <td>{{fridayShifts.endTime}}</td>-->
<!--          </tr>-->
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getFridayShifts()">View</button>
        </div>

      </b-tab>
    </b-tabs>
  <span v-if="error" style="color:red"> Error: {{error}}</span>

  </div>
</template>


<script src="./schedule_view_script.js">
export default {
  name: "Schedule_view",
  Monday: 'Monday'
}

</script>

<style scoped>

table {
  border-collapse: separate;
  border-spacing: 100px 0;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 4vh;
}

#profile_view {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 50vh;
}

.btn {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #45B39D ;
}

.buttonFrame {
  padding-top: 10%;
}

tr {
  width: 40px;
}

</style>
