<template>
  <div id="HeadLibrarian_View">
    <div>
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="/">Menu</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item @click="openLibrary">Library </b-nav-item>
            <b-nav-item @click="openBooking">Booking </b-nav-item>
            <b-nav-item @click="openManagement">Inventory</b-nav-item>
            <!--<b-nav-item @click="openHour">Organize </b-nav-item>
            <b-nav-item @click="openHour">Hour </b-nav-item>-->
            <b-nav-item @click="openEvent">Event </b-nav-item>
            <b-nav-item @click="openSchedule">Schedule </b-nav-item>
            <!--<b-nav-item @click="openOrganize">Organize </b-nav-item>-->
            <b-nav-item-dropdown>
              <template #button-content>Library Accounts Management</template>
              <b-dropdown-item @click="openCreate">Create local customer account</b-dropdown-item>
              <b-dropdown-item @click="openConvert">Convert local customer account</b-dropdown-item>
              <b-dropdown-item @click="openVerify">Verify customer account</b-dropdown-item>
              <b-dropdown-item @click="openFees">Process customer fees</b-dropdown-item>
              <b-dropdown-item @click="openHire">Hire Librarian</b-dropdown-item>
              <b-dropdown-item @click="openFire">Fire Librarian</b-dropdown-item>
            </b-nav-item-dropdown>


          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">

            <b-nav-item-dropdown right>
              <!-- Using 'button-content' slot -->
              <template #button-content>
                <em>Head Librarian</em>
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

      <!-- THIS IS THE LIBRARY INFORMATION SECTION ON THE MAIN PAGE -->
      <div class="libraryInfoFrame" id="library_view">
        <div class="head">
          <br>
          <h2><b><u>Library Information</u></b></h2>
        </div>
        <div class="block">
          <div class="inline_left">
            <div class="address">
              <p><b>The library is located at:</b></p>
              <div class="centered">
                <p>{{civic}} {{street}}, {{city}}, {{province}}, {{postalCode}}, {{country}}</p>
              </div>
            </div>
          </div>
          <div class="inline_right">
            <div class="split openingHours">
              <p><b>The library is open on:</b></p>
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

      <div class="allTodayShiftsFrame">
        <h2 class="main_title">
          <u><b>Employee Shifts</b></u>
        </h2>

        <div class="table">
          <table>
            <tr>
              <th>Day</th>
              <th>Employee Username</th>
              <th>Start</th>
              <th>End</th>
            </tr>
            <tr v-for="hour in shifts">
                <td>{{hour.weekday}}</td>
                <td>{{hour.employee.username}}</td>
                <td>{{hour.startTime}}</td>
                <td>{{hour.endTime}}</td>
            </tr>
          </table>
        </div>
        <span v-if="shiftError" style="color:red"> Error: {{shiftError}}</span>
      </div>

      <!-- THIS IS THE EVENT SECTION IN THE MAIN PAGE -->
      <div class="eventFrame">
        <br>
        <h2><u><b>Upcoming Events</b></u></h2>
        <div class="HI">

          <table style="margin-left:auto; margin-right:auto">
            <tr>
              <th>Event Name</th>
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
          <span v-if="eventError" style="color:red">Error: {{eventError}} </span>
        </div>
      </div>




    </div>



  </div>
</template>

<script src="./head_librarian_view_script.js">
export default {
  name: "head_librarian_view_script"
}



</script>

<style scoped>

table {
  border-collapse: separate;
  border-spacing: 40px 0;
  position: relative;
  margin-left: auto;
  margin-right: auto;
}

tr {
  width: 40px;
}

.bottomFrame {
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  display: grid;
  grid-auto-columns: minmax(0, 1fr);
  grid-auto-flow: column;
}

.main_title {
  padding-top: 2vh;
}

</style>
