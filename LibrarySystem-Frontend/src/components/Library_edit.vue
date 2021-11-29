<template>
  <div id="library_view">
    <div class="bar">
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="/">Menu</b-navbar-brand>
      </b-navbar>
    </div>
    <div class="head">
      <br>
      <br>
      <h1>Library Information</h1>
    </div>
    <div class="block">
      <div class="inline_left">
        <div class="address">
          <p><b>Your library is located at:</b></p>
          <div class="centered">
            <p><br>{{civic}} {{street}}, {{city}}, {{province}}, {{postalCode}}, {{country}}</p>
          </div>
        </div>
        <div class="address_edit">
          <button class="btn btn-success" @click="openAddressEdit">Edit Address</button>
        </div>
      </div>
      <div class="inline_right">
        <div class="split openingHours">
          <p><b>Your library is open on:</b></p><br>
          <table>
            <tr>
              <th>Day</th>
              <th>Open</th>
              <th>Close</th>
            </tr>
            <tr v-for="hour in hours">
              <td>{{hour.weekday}}</td>
              <td>{{hour.startTime}}</td>
              <td>{{hour.endTime}}</td>
            </tr>
          </table>
        </div>
        <div class="hour_edit">
          <button class="btn btn-success" @click="openHoursEdit">Edit Opening Hours</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./library_edit_script.js">
export default {
  name: "library_View"
}
</script>

<style scoped>
.block {
  width: 100%;
  display: block;
  padding-top: 17vh;
}

.inline_left {
  width: 50%;
  float: left;
  display: inline-block;
}

.inline_right {
  width: 50%;
  float: right;
  display: inline-block;
}

.address {
  left: 0;
  text-align: center;
}

.openingHours {
  right: 0;
}

.hour_edit {
  padding-top: 8vh;
}

.address_edit {
  padding-top: 5vh;
}

table {
  margin-left: auto;
  margin-right: auto;
}
</style>
