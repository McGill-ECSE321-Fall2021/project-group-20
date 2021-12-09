<template>
  <div id="profile_view">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>

    <h2 class="main_title">
      <b>Your Shifts</b>
    </h2>

    <div>
      <img src="../assets/shift.jpg" style="width:80vh; height:auto;">
    </div>

    <div class="table">
      <table>
        <tr>
          <th>Day</th>
          <th>Start</th>
          <th>End</th>
        </tr>
        <tr v-for="hour in shifts">
          <td>{{hour.weekday}}</td>
          <td>{{hour.startTime}}</td>
          <td>{{hour.endTime}}</td>
        </tr>
      </table>
    </div>
    <div class="button">
      <button class="btn-success" @click="goBack">Return</button>
    </div>

    <span v-if="error" style="color:red"> Error: {{error}}</span>

  </div>
</template>


<script src="./personal_schedule_script.js">
export default {
  name: "personal_schedule",
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
  padding-bottom: 70vh;
}

tr {
  width: 40px;
}

.button {
  padding-top: 4vh;
}

.btn-success {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #03a634;
}
</style>
