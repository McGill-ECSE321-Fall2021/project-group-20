<template>

  <div id="Employee_View">
    <div>
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="#">Menu</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item @click="openLibrary">Library </b-nav-item>  <!-- Will delete -->
            <b-nav-item @click="openBooking">Booking </b-nav-item>
            <b-nav-item @click="openManagement">Inventory</b-nav-item>
            <b-nav-item @click="openEvent">Event </b-nav-item>      <!-- Put On main page -->
            <b-nav-item @click="openSchedule">Schedule </b-nav-item> <!-- Shifts of the day  -->
            <b-nav-item-dropdown>
              <template #button-content>Library Accounts Management</template>
              <b-dropdown-item @click="openCreate">Create local customer account</b-dropdown-item>
              <b-dropdown-item @click="openConvert">Convert local customer account</b-dropdown-item>
              <b-dropdown-item @click="openVerify">Verify customer account</b-dropdown-item>
              <b-dropdown-item @click="openFees">Process customer fees</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">

            <b-nav-item-dropdown right>
              <!-- Using 'button-content' slot -->
              <template #button-content>
                <em>Librarian</em>
              </template>
              <b-dropdown-item @click="openProfile">Profile</b-dropdown-item>
              <b-dropdown-item @click="signout">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <div class="header_img">
      <img src="https://www.commbox.io/wp-content/uploads/2019/10/32-1-1024x597.jpg" style="width:20vh; height:auto;">
    </div>
    <h1>Employees of the Month</h1>
    <div>
      <b-carousel
        id="carousel-1"
        v-model="slide"
        :interval="4500"
        controls
        indicators
        background="#ababab"
        img-width="1024"
        img-height="200"
        style="text-shadow: 1px 1px 2px #333;"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <!-- Text slides with image -->

        <!-- Slides with img slot -->
        <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
        <b-carousel-slide>
          <template #img>
            <img src="../assets/books.jpg" style="width:auto; height:30vh;">
          </template>
        </b-carousel-slide>
      </b-carousel>
    </div>

    <div class="bottomFrame">

      <!-- THIS IS THE LIBRARY INFORMATION SECTION OF THE MAIN PAGE -->
<!--      <div class="libraryInfoFrame" id="library_view">-->
<!--        <div class="head">-->
<!--          <br>-->
<!--          <h2><b>Library Information</b></h2>-->
<!--        </div>-->
<!--        <div class="block">-->
<!--          <div class="inline_left">-->
<!--            <div class="address">-->
<!--              <p><b>The library is located at:</b></p>-->
<!--              <div class="centered">-->
<!--                <p>{{civic}} {{street}}, {{city}}, {{province}}, {{postalCode}}, {{country}}</p>-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div class="inline_right">-->
<!--            <div class="split openingHours">-->
<!--              <p><b>The library is open on:</b></p>-->
<!--              <table>-->
<!--                <tr>-->
<!--                  <th>Day</th>-->
<!--                  <th>Open</th>-->
<!--                  <th>Close</th>-->
<!--                </tr>-->
<!--                <tr v-for="hour in hours">-->
<!--                  <td>{{hour.weekday}}</td>-->
<!--                  <td>{{hour.startTime}}</td>-->
<!--                  <td>{{hour.endTime}}</td>-->
<!--                </tr>-->
<!--              </table>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->

      <!-- THIS IS THE SHIFT SECTION OF THE MAIN PAGE -->
      <div class="shiftFrame">
        <h2 class="main_title">
          <b>Your Shifts</b>
        </h2>

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
        <span v-if="shiftError" style="color:red"> Error: {{shiftError}}</span>
      </div>



      <!-- THIS IS THE EVENT SECTION OF THE MAIN PAGE -->
      <div class="eventFrame">
        <br>
        <h2><b>Upcoming Events</b></h2>
        <div class="HI">

          <div class="table">
            <table>
              <tr>
                <th>Name</th>
                <th>Date</th>
                <th>Weekday</th>
                <th>Start</th>
                <th>End</th>

              </tr>
              <tr v-for="event in events">
                <td>{{event.name}}</td>
                <td>{{event.eventDate}}</td>
                <td>{{event.eventhour.weekday}}</td>
                <td>{{event.eventhour.startTime}}</td>
                <td>{{event.eventhour.endTime}}</td>

              </tr>
            </table>

          </div>
          <span v-if="eventError" style="color:red">Error: {{eventError}} </span>
        </div>
      </div>

    </div>
    <div class="error">
      <p>
        <span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>
  </div>

</template>

<script src="./employee_view_script.js">
import Library_View from "./Library_View";
export default {
  name: "employee_view_script",
  components: {Library_View},
  Monday: 'Monday'
}
</script>

<style scoped>


.msg {
  padding-top: 2vh;
  padding-bottom: 5vh;
}

.input {
  padding-bottom: 5vh;
  text-align: left;

}

.buttons {
  padding-bottom: 3vh;
}
{box-sizing: border-box;}
body {font-family: Verdana, sans-serif;}
.mySlides {display: none;}
img {vertical-align: middle;}

/* Slideshow container */
.slideshow-container {
  max-width: 1000px;
  position: relative;
  margin: auto;
}



/* Caption text */
.text {
  color: #f2f2f2;
  font-size: 15px;
  padding: 8px 12px;
  position: absolute;
  bottom: 8px;
  width: 100%;
  text-align: center;
}

/* Number text (1/3 etc) */
.numbertext {
  color: #f2f2f2;
  font-size: 12px;
  padding: 8px 12px;
  position: absolute;
  top: 0;
}

/* The dots/bullets/indicators */
.dot {
  height: 15px;
  width: 15px;
  margin: 0 2px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
  transition: background-color 0.6s ease;
}

.active {
  background-color: #717171;
}

/* Fading animation */
.fade {
  -webkit-animation-name: fade;
  -webkit-animation-duration: 1.5s;
  animation-name: fade;
  animation-duration: 1.5s;
}

@-webkit-keyframes fade {
  from {opacity: .4}
  to {opacity: 1}
}

@keyframes fade {
  from {opacity: .4}
  to {opacity: 1}
}

/* On smaller screens, decrease text size */
@media only screen and (max-width: 300px) {
  .text {font-size: 11px}
}

table {
  border-collapse: separate;
  margin-left: auto;
  margin-right: auto;
}

tr {
  width: 40px;
}

.main_title {
  padding-top: 2vh;
}

.bottomFrame {
  display: grid;
  grid-template-rows: repeat(2, 1fr);
  border-collapse: separate;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
}

</style>

