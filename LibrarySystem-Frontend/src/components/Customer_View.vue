<template>
  <div id="Customer_View">
    <div>
      <b-navbar toggleable="lg" variant="faded" type="light" >

        <b-navbar-brand href="/">Menu</b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="/Library">Library </b-nav-item>
            <b-nav-item @click="openBooking" v-if="info.outstandingBalance === 0 && info.demeritPts < 3">Booking </b-nav-item>

          </b-navbar-nav>
          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown right>
              <template #button-content>
                <em>User</em>
              </template>
              <b-dropdown-item @click="openProfile">Profile</b-dropdown-item>
              <b-dropdown-item @click="signout">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
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
          <b-carousel-slide>
            <template #img>
              <img src="../assets/books.jpg" style="width:auto; height:30vh;">
            </template>
          </b-carousel-slide>
        </b-carousel>
      </div>

      <div class="bottomFrame">
        <!-- THIS IS THE BOOKING SECTION OF THE MAIN PAGE -->
        <div class="bookingFrame">
          <h2 class="main_title">
            <u><b>Your Bookings</b></u>
          </h2>

          <div class="table">
            <table>
              <tr>
                <th>Type</th>
                <th>Item</th>
                <th>Start</th>
                <th>End</th>
              </tr>
              <tr v-for="booking in bookings">
                <td>{{booking.type}}</td>
                <td>{{booking.item.title.name}}</td>
                <td>{{booking.startDate}}</td>
                <td>{{booking.endDate}}</td>
              </tr>
            </table>
          </div>
          <span v-if="error" style="color:red"> Error: {{error}}</span>
        </div>



        <!-- THIS IS THE PERSONAL SECTION OF THE MAIN PAGE -->
        <div class="personalFrame">
          <br>
          <h2><u><b>Your Information</b></u></h2><br>
          <div class="HI">
            <p><b>Card ID:</b> {{info.libraryCardID}}</p>
            <p><b>Current Balance:</b> {{info.outstandingBalance}}</p>
            <p><b>Demerit Points:</b> {{info.demeritPts}}</p>
          </div>
          <div class="LO_BALANCE" v-if="info.outstandingBalance !== 0">
            <h3>Your account cannot be used to book or reserve until the balance of <b>{{info.outstandingBalance}}</b> is paid off!</h3>
          </div>
          <div class="LO_PTS" v-if="info.demeritPts >= 3">
            <h3>Your account cannot be used to book or reserve until you meet with management!</h3>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script src="./customer_view_script.js">
export default {
  name: "customer_view"
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

.LO_BALANCE, .LO_PTS {
  color: #ff0008;
}
</style>
