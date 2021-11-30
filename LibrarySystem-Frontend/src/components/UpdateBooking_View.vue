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
          <p><b>Our library is located at:</b></p>
          <div class="centered">
            <p><br>{{civic}} {{street}}, {{city}}, {{province}}, {{postalCode}}, {{country}}</p>
          </div>
        </div>
      </div>
      <div clas="inline_right">
        <div class="split openingHours">
          <p><b>Our library is open on:</b></p><br>
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
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "UpdateBooking_View"
}
</script>

<style scoped>

</style>
