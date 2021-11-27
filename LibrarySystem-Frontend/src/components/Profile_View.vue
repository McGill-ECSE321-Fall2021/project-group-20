<template>
  <div id="profile_view">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>

    <div class="mainFrame">
      <b-tabs content-class="mt-3" align="center">
        <b-tab title="Account Info" active>
          <div class="inputFrame">

            <div class="usernameLabel inputLabel">
              <label for="username">Username</label>
              <input value="response.username" type="text" v-model="userName" id="username" class="form-control" required>
            </div>

            <div class="oldPasswordLabel inputLabel">
              <label for="oldPassword">Old Password</label>
              <input type="password" v-model="oldPassword" id="oldPassword" class="form-control" required>
            </div>

            <div class="newPasswordLabel inputLabel">
              <label for="newPassword">New Password</label>
              <input type="password" v-model="newPassword" id="newPassword" class="form-control" required>
            </div>

            <div class="emailLabel inputLabel">
              <label for="email">Email</label>
              <input value="response.email" type="email" v-model="email" id="email" class="form-control" required>
            </div>

          </div>

          <div class="buttonAccountFrame inputLabel">
            <button class="btn-cancel-acc" @click="cancel">Back
            </button>
            <button class="btn-success-acc" @click="updateOnlineInfo(userName, newPassword, email)">Save Changes
            </button>
          </div>

        </b-tab>

        <b-tab title="Personal Info">
          <div class="inputFrame">

            <div class="firstNameLabel inputPersLabel">
              <label for="firstname">First Name</label>
              <input value="response.firstName" type="text" v-model="firstName" id="firstname" class="form-control" required>
            </div>

            <div class="lastNameLabel inputPersLabel">
              <label for="lastname">Last Name</label>
              <input value="response.lastName" type="text" v-model="lastName" id="lastname" class="form-control" required>
            </div>

            <div class="civicNumberLabel inputPersLabel">
              <label for="civic">Civic Number</label>
              <input value="response.address.civicNumber" type="text" v-model="civic" id="civic" class="form-control" required>
            </div>

            <div class="streetLabel inputPersLabel">
              <label for="street">Street</label>
              <input value="response.address.street" type="text" v-model="street" id="street" class="form-control" required>
            </div>

            <div class="cityLabel inputPersLabel">
              <label for="city">City</label>
              <input value="response.address.city" type="text" v-model="city" id="city" class="form-control" required>
            </div>

            <div class="provinceLabel inputPersLabel">
              <label for="province">Province</label>
              <input value="response.address.province" type="text" v-model="province" id="province" class="form-control" required>
            </div>

            <div class="countryLabel inputPersLabel">
              <label for="country">Country</label>
              <input value="response.address.country" type="text" v-model="country" id="country" class="form-control" required>
            </div>

            <div class="postalCodeLabel inputPersLabel">
              <label for="postalCode">Postal Code</label>
              <input value="response.address.postalCode" type="text" v-model="postalCode" id="postalCode" class="form-control" required>
            </div>

          </div>

          <div class="buttonPersonalFrame inputPersLabel">
            <button class="btn-cancel-pers" @click="cancel">Back
            </button>
            <button class="btn-success-pers" @click="updateInfo(firstname, lastname, civic, street, city, postalCode,
              province, country)">Save Changes
            </button>
          </div>

        </b-tab>
      </b-tabs>

    </div>

  </div>
</template>

<script src="./profile_vue_script.js">
export default {
  name: "profile_view"
}
</script>

<style scoped>


.inputFrame {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(4, 1fr);
  width: 25%;
  min-width: 500px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 2vh;
}

.usernameLabel {
  grid-column: span 2;
}


.form-control {
  display: inline-block;
  width: 70%;
}

#username {
  width: 85%
}

#email {
  width: 85%;
}

.emailLabel {
  grid-column: span 2;
}

label {
  display: block;
}

.inputLabel {
  padding-top: 1vh;
  padding-bottom: 1vh;
}

.btn-success-acc {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #45B39D ;
}

.btn-cancel-acc {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #45B39D ;
  background-color: #F5B7B1;
}

.inputPersLabel {
  padding-top: 2vh;
  padding-bottom: 2vh;
}

.btn-success-pers {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #45B39D ;
}

.btn-cancel-pers {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #45B39D ;
  background-color: #F5B7B1;
}

a:link {
  color: #333333;
}
a:hover {
 text-decoration: None;
}

</style>
