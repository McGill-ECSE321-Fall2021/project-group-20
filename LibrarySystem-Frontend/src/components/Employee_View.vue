<template>

  <div id="Employee_View">
    <div>
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="#">Menu</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#">Library </b-nav-item>
            <b-nav-item href="http://127.0.0.1:8087/EmployeePage/Booking">Booking </b-nav-item>
            <b-nav-item href="http://127.0.0.1:8087/EmployeePage/Management">Management</b-nav-item>
            <b-nav-item href="http://127.0.0.1:8087/EmployeePage/Event">Event </b-nav-item>
            <b-nav-item href="http://127.0.0.1:8087/EmployeePage/Schedule">Schedule </b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">

            <b-nav-item-dropdown right>
              <!-- Using 'button-content' slot -->
              <template #button-content>
                <em>User</em>
              </template>
              <b-dropdown-item href="http://127.0.0.1:8087/EmployeePage/Profile">Profile</b-dropdown-item>
              <b-dropdown-item href="#">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <div class="header_img">
      <img src="https://www.commbox.io/wp-content/uploads/2019/10/32-1-1024x597.jpg" style="width:20vh; height:auto;">
    </div>
    <h1>Employees of the month</h1>
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
            <img src="https://scontent-lga3-1.xx.fbcdn.net/v/t39.30808-6/241768511_4467095386711745_3746825865012326078_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=EUb30M_58ycAX-5DDgT&_nc_ht=scontent-lga3-1.xx&oh=6568cef0b268d8c1ef5e3a85f798a65c&oe=61A5462E" style="width:auto; height:60vh;">

          </template>
        </b-carousel-slide>

        <b-carousel-slide>
          <template #img>
            <img src="https://pbs.twimg.com/profile_images/483697632003371008/YDdxZqsm_400x400.jpeg" style="width:auto; height:60vh;">

          </template>
        </b-carousel-slide>
      </b-carousel>
    </div>
  </div>

</template>

<script src="./employee_view_script.js">
export default {
  name: "employee_view_script"
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
</style>

