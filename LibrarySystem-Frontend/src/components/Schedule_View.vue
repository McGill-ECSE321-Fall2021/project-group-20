<template>
  <div id="profile_view">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>

    <h2 class="main_title">
      <b>View and Edit Employee Shifts</b>
      <p>(Press 'View' to update)</p>
    </h2>

    <b-tabs content-class="mt-3" align="center">

      <b-tab title="Monday" id="Monday" active>
          <table>
              <tr>
                <th>Day</th>
                <th>Name</th>
                <th>username</th>
                <th>Employee ID</th>
                <th>Start Time</th>
                <th>End Time</th>
              </tr>
              <tr v-for="monday in mondayShifts">
                <td>{{monday.weekday}}</td>
                <td>{{monday.employee.firstName}}  {{monday.employee.lastName}}</td>
                <td>{{monday.employee.username}}</td>
                <td>{{monday.employee.libraryCardID}}</td>
                <td>{{monday.startTime}}</td>
                <td>{{monday.endTime}}</td>
              </tr>
          </table>

        <div class="buttonFrame">
          <button class="btn" @click="getMondayShifts()">View</button>
          <div class="choose">
            <br><label for="mon_select"><b>Select Shift to Edit/Delete:</b></label>
            <select v-model="mon_select" name="mon_select" id="mon_select">
              <option v-for="monday in mondayShifts" :value="monday.hourId">{{monday.employee.username}}</option>
            </select>
            <br><label for="mon_start" name="mon_start"><b>Start Time:</b></label>
            <input type="text" v-model="mon_start" id="mon_start" class="form-control" required placeholder="hh:mm:ss">
            <label for="mon_end" name="mon_end"><b>End Time:</b></label>
            <input type="text" v-model="mon_end" id="mon_end" class="form-control" required placeholder="hh:mm:ss"><br>
          </div>
          <button class="btn" @click="editMon()">Edit</button>
          <button class="btn" @click="deleteMon()">Delete</button><br>
          <br><label for="mon_select_create"><b>Select Employee to add shift to:</b></label>
          <select v-model="mon_select_create" name="mon_select_create" id="mon_select_create">
            <option v-for="employee in mondayEmployees" :value="employee.libraryCardID">{{employee.username}}</option>
          </select><br>
          <button class="btn" @click="createMon">Create</button>
        </div>
      </b-tab>

      <b-tab title="Tuesday" id="Tuesday" active>
        <table>
          <tr>
            <th>Day</th>
            <th>Name</th>
            <th>username</th>
            <th>Employee ID</th>
            <th>Start Time</th>
            <th>End Time</th>
          </tr>
          <tr v-for="tuesday in tuesdayShifts">
            <td>{{tuesday.weekday}}</td>
            <td>{{tuesday.employee.firstName}}  {{tuesday.employee.lastName}}</td>
            <td>{{tuesday.employee.username}}</td>
            <td>{{tuesday.employee.libraryCardID}}</td>
            <td>{{tuesday.startTime}}</td>
            <td>{{tuesday.endTime}}</td>
          </tr>
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getTuesdayShifts()">View</button>
          <div class="choose">
            <br><label for="tue_select"><b>Select Shift to Edit/Delete:</b></label>
            <select v-model="tue_select" name="tue_select" id="tue_select">
              <option v-for="shift in tuesdayShifts" :value="shift.hourId">{{shift.employee.username}}</option>
            </select>
            <br><label for="tue_start" name="tue_start"><b>Start Time:</b></label>
            <input type="text" v-model="tue_start" id="tue_start" class="form-control" required placeholder="hh:mm:ss">
            <label for="tue_end" name="tue_end"><b>End Time:</b></label>
            <input type="text" v-model="tue_end" id="tue_end" class="form-control" required placeholder="hh:mm:ss"><br>
          </div>
          <button class="btn" @click="editTue()">Edit</button>
          <button class="btn" @click="deleteTue()">Delete</button><br>
          <br><label for="tue_select_create"><b>Select Employee to add shift to:</b></label>
          <select v-model="tue_select_create" name="tue_select_create" id="tue_select_create">
            <option v-for="employee in tuesdayEmployees" :value="employee.libraryCardID">{{employee.username}}</option>
          </select><br>
          <button class="btn" @click="createTue">Create</button>
        </div>

      </b-tab>

      <b-tab title="Wednesday" id="Wednesday" active>
        <table>
          <tr>
            <th>Day</th>
            <th>Name</th>
            <th>username</th>
            <th>Employee ID</th>
            <th>Start Time</th>
            <th>End Time</th>
          </tr>
          <tr v-for="wed in wednesdayShifts">
            <td>{{wed.weekday}}</td>
            <td>{{wed.employee.firstName}}  {{wed.employee.lastName}}</td>
            <td>{{wed.employee.username}}</td>
            <td>{{wed.employee.libraryCardID}}</td>
            <td>{{wed.startTime}}</td>
            <td>{{wed.endTime}}</td>
          </tr>
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getWednesdayShifts()">View</button>
          <div class="choose">
            <br><label for="wed_select"><b>Select Shift to Edit/Delete:</b></label>
            <select v-model="wed_select" name="wed_select" id="wed_select">
              <option v-for="shift in wednesdayShifts" :value="shift.hourId">{{shift.employee.username}}</option>
            </select>
            <br><label for="wed_start" name="wed_start"><b>Start Time:</b></label>
            <input type="text" v-model="wed_start" id="wed_start" class="form-control" required placeholder="hh:mm:ss">
            <label for="wed_end" name="wed_end"><b>End Time:</b></label>
            <input type="text" v-model="wed_end" id="wed_end" class="form-control" required placeholder="hh:mm:ss"><br>
          </div>
          <button class="btn" @click="editWed()">Edit</button>
          <button class="btn" @click="deleteWed()">Delete</button><br>
          <br><label for="wed_select_create"><b>Select Employee to add shift to:</b></label>
          <select v-model="wed_select_create" name="wed_select_create" id="wed_select_create">
            <option v-for="employee in wednesdayEmployees" :value="employee.libraryCardID">{{employee.username}}</option>
          </select><br>
          <button class="btn" @click="createWed">Create</button>
        </div>
      </b-tab>

      <b-tab title="Thursday" id="Thursday" active>
        <table>
          <tr>
            <th>Day</th>
            <th>Name</th>
            <th>username</th>
            <th>Employee ID</th>
            <th>Start Time</th>
            <th>End Time</th>
          </tr>
          <tr v-for="thu in thursdayShifts">
            <td>{{thu.weekday}}</td>
            <td>{{thu.employee.firstName}}  {{thu.employee.lastName}}</td>
            <td>{{thu.employee.username}}</td>
            <td>{{thu.employee.libraryCardID}}</td>
            <td>{{thu.startTime}}</td>
            <td>{{thu.endTime}}</td>
          </tr>
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getThursdayShifts">View</button>
          <div class="choose">
           <br><label for="thu_select"><b>Select Shift to Edit/Delete:</b></label>
            <select v-model="thu_select" name="thu_select" id="thu_select">
              <option v-for="shift in thursdayShifts" :value="shift.hourId">{{shift.employee.username}}</option>
            </select>
            <br><label for="thu_start" name="thu_start"><b>Start Time:</b></label>
            <input type="text" v-model="thu_start" id="thu_start" class="form-control" required placeholder="hh:mm:ss">
            <label for="thu_end" name="thu_end"><b>End Time:</b></label>
            <input type="text" v-model="thu_end" id="thu_end" class="form-control" required placeholder="hh:mm:ss"><br>
          </div>
          <button class="btn" @click="editThu()">Edit</button>
          <button class="btn" @click="deleteThu()">Delete</button><br>
          <br><label for="thu_select_create"><b>Select Employee to add shift to:</b></label>
          <select v-model="thu_select_create" name="thu_select_create" id="thu_select_create">
            <option v-for="employee in thursdayEmployees" :value="employee.libraryCardID">{{employee.username}}</option>
          </select><br>
          <button class="btn" @click="createThu">Create</button>
        </div>

      </b-tab>

      <b-tab title="Friday" id="Friday" active>
        <table>
          <tr>
            <th>Day</th>
            <th>Name</th>
            <th>username</th>
            <th>Employee ID</th>
            <th>Start Time</th>
            <th>End Time</th>
          </tr>
          <tr v-for="fri in fridayShifts">
            <td>{{fri.weekday}}</td>
            <td>{{fri.employee.firstName}}  {{fri.employee.lastName}}</td>
            <td>{{fri.employee.username}}</td>
            <td>{{fri.employee.libraryCardID}}</td>
            <td>{{fri.startTime}}</td>
            <td>{{fri.endTime}}</td>
          </tr>
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getFridayShifts()">View</button>
          <div class="choose">
            <br><label for="fri_select"><b>Select Shift to Edit/Delete:</b></label>
            <select v-model="fri_select" name="fri_select" id="fri_select">
              <option v-for="shift in fridayShifts" :value="shift.hourId">{{shift.employee.username}}</option>
            </select>
            <br><label for="fri_start" name="fri_start"><b>Start Time:</b></label>
            <input type="text" v-model="fri_start" id="fri_start" class="form-control" required placeholder="hh:mm:ss">
            <label for="fri_end" name="fri_end"><b>End Time:</b></label>
            <input type="text" v-model="fri_end" id="fri_end" class="form-control" required placeholder="hh:mm:ss"><br>
          </div>
          <button class="btn" @click="editFri()">Edit</button>
          <button class="btn" @click="deleteFri()">Delete</button><br>
         <br><label for="fri_select_create"><b>Select Employee to add shift to:</b></label>
          <select v-model="fri_select_create" name="fri_select_create" id="fri_select_create">
            <option v-for="employee in fridayEmployees" :value="employee.libraryCardID">{{employee.username}}</option>
          </select><br>
          <button class="btn" @click="createFri">Create</button>
        </div>

      </b-tab>

      <b-tab title="Saturday" id="Saturday" active>
        <table>
          <tr>
            <th>Day</th>
            <th>Name</th>
            <th>username</th>
            <th>Employee ID</th>
            <th>Start Time</th>
            <th>End Time</th>
          </tr>
          <tr v-for="sat in saturdayShifts">
            <td>{{sat.weekday}}</td>
            <td>{{sat.employee.firstName}}  {{sat.employee.lastName}}</td>
            <td>{{sat.employee.username}}</td>
            <td>{{sat.employee.libraryCardID}}</td>
            <td>{{sat.startTime}}</td>
            <td>{{sat.endTime}}</td>
          </tr>
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getSaturdayShifts()">View</button>
          <div class="choose">
            <br><label for="sat_select"><b>Select Shift to Edit/Delete:</b></label>
            <select v-model="sat_select" name="sat_select" id="sat_select">
              <option v-for="shift in saturdayShifts" :value="shift.hourId">{{shift.employee.username}}</option>
            </select>
            <br><label for="sat_start" name="sat_start"><b>Start Time:</b></label>
            <input type="text" v-model="sat_start" id="sat_start" class="form-control" required placeholder="hh:mm:ss">
            <label for="sat_end" name="sat_end"><b>End Time:</b></label>
            <input type="text" v-model="sat_end" id="sat_end" class="form-control" required placeholder="hh:mm:ss"><br>
          </div>
          <button class="btn" @click="editSat()">Edit</button>
          <button class="btn" @click="deleteSat()">Delete</button><br>
          <br><label for="sat_select_create"><b>Select Employee to add shift to:</b></label>
          <select v-model="sat_select_create" name="sat_select_create" id="sat_select_create">
            <option v-for="employee in saturdayEmployees" :value="employee.libraryCardID">{{employee.username}}</option>
          </select><br>
          <button class="btn" @click="createSat">Create</button>
        </div>

      </b-tab>

      <b-tab title="Sunday" id="Sunday" active>
        <table>
          <tr>
            <th>Day</th>
            <th>Name</th>
            <th>username</th>
            <th>Employee ID</th>
            <th>Start Time</th>
            <th>End Time</th>
          </tr>
          <tr v-for="sun in sundayShifts">
            <td>{{sun.weekday}}</td>
            <td>{{sun.employee.firstName}}  {{sun.employee.lastName}}</td>
            <td>{{sun.employee.username}}</td>
            <td>{{sun.employee.libraryCardID}}</td>
            <td>{{sun.startTime}}</td>
            <td>{{sun.endTime}}</td>
          </tr>
        </table>

        <div class="buttonFrame">
          <button class="btn" @click="getSundayShifts()">View</button>
          <div class="choose">
            <br><label for="sun_select"><b>Select Shift to Edit/Delete:</b></label>
            <select v-model="sun_select" name="sun_select" id="sun_select">
              <option v-for="shift in sundayShifts" :value="shift.hourId">{{shift.employee.username}}</option>
            </select>
            <br><label for="sun_start" name="sun_start"><b>Start Time:</b></label>
            <input type="text" v-model="sun_start" id="sun_start" class="form-control" required placeholder="hh:mm:ss">
            <label for="sun_end" name="sun_end"><b>End Time:</b></label>
            <input type="text" v-model="sun_end" id="sun_end" class="form-control" required placeholder="hh:mm:ss"><br>
          </div>
          <button class="btn" @click="editSun()">Edit</button>
          <button class="btn" @click="deleteSun()">Delete</button><br>
          <br><label for="sun_select_create"><b>Select Employee to add shift to (use time fields above!):</b></label>
          <select v-model="sun_select_create" name="sun_select_create" id="sun_select_create">
            <option v-for="employee in sundayEmployees" :value="employee.libraryCardID">{{employee.username}}</option>
          </select><br>
          <button class="btn" @click="createSun">Create</button>
        </div>

      </b-tab>
    </b-tabs>
  <span v-if="error" style="color:red"> Error: {{error}}</span>

  </div>
</template>


<script src="./schedule_view_script.js">
export default {
  name: "Schedule_view",
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

#mon_start, #mon_end, #tue_start, #tue_end, #wed_start, #wed_end,
#thu_start, #thu_end, #fri_start, #fri_end, #sat_start, #sat_end,
#sun_start, #sun_end {
  margin-right: auto;
  margin-left: auto;
  width: 10%;
  height: auto;
}

</style>
