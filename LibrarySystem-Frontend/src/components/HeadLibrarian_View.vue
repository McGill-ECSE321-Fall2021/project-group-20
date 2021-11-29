<template>
  <div id="HeadLibrarian_View">
    <div>
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="#">Menu</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item @click="openLibrary">Library </b-nav-item>
            <b-nav-item @click="openManagement">Booking </b-nav-item>
            <b-nav-item @click="openBooking">Management</b-nav-item>
            <b-nav-item @click="openHour">Organize </b-nav-item>
            <b-nav-item @click="openHour">Hour </b-nav-item>
            <b-nav-item @click="openEvent">Event </b-nav-item>
            <b-nav-item @click="openSchedule">Schedule </b-nav-item>
            <b-nav-item @click="openOrganize">Organize </b-nav-item>
            <b-nav-item-dropdown>
              <template #button-content>Library Accounts Management</template>
              <b-dropdown-item @click="openCreate">Create local customer account</b-dropdown-item>
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

  </div>
</template>

<script src="./head_librarian_view_script.js">
export default {
  name: "head_librarian_view_script"
}
</script>

<style scoped>

</style>
