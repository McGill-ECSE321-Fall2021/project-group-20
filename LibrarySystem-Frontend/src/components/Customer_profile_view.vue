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
              <input type="text" v-model="username" id="username" class="form-control" required>
            </div>

            <div class="libraryCardIDLabel inputLabel">
              <label for="libraryCardID">Library Card ID</label>
              <input type="text" v-model="libraryCardID" id="libraryCardID" class="form-control" readonly>
            </div>

            <div class="demeritPoints inputLabel">
              <label for="demeritPoints">Demerit Points</label>
              <input type="number" v-model="demeritPts" id="demeritPoints" class="form-control" readonly>
            </div>

            <div class="Balance inputLabel">
              <label for="balance">Balance</label>
              <input type="number" v-model="balance" id="balance" class="form-control" readonly>
            </div>

            <div class="oldPasswordLabel inputLabel">
              <label for="oldPassword">New Password</label>
              <input type="password" v-model="confirm" id="oldPassword" class="form-control" required>
            </div>

            <div class="newPasswordLabel inputLabel">
              <label for="newPassword">Confirm Password</label>
              <input type="password" v-model="newPassword" id="newPassword" class="form-control" required>
            </div>

            <div class="emailLabel inputLabel">
              <label for="email">Email</label>
              <input type="email" v-model="email" id="email" class="form-control" required>
            </div>

          </div>

          <div class="buttonAccountFrame inputLabel">
            <button class="btn-cancel-acc" @click="cancel">Back
            </button>
            <button class="btn-success-acc" @click="customerUpdateOnlineInfo(username, confirm, newPassword, email)">Save Changes
            </button>
          </div>

        </b-tab>

        <b-tab title="Personal Info">
          <div class="inputFrame">

            <div class="firstNameLabel inputPersLabel">
              <label for="firstname">First Name</label>
              <input type="text" v-model="firstname" id="firstname" class="form-control" required>
            </div>

            <div class="lastNameLabel inputPersLabel">
              <label for="lastname">Last Name</label>
              <input type="text" v-model="lastname" id="lastname" class="form-control" required>
            </div>

            <div class="civicNumberLabel inputPersLabel">
              <label for="civicNumber">Civic Number</label>
              <input type="text" v-model="civicNumber" id="civicNumber" class="form-control" required>
            </div>

            <div class="streetLabel inputPersLabel">
              <label for="street">Street</label>
              <input type="text" v-model="street" id="street" class="form-control" required>
            </div>

            <div class="cityLabel inputPersLabel">
              <label for="city">City</label>
              <input type="text" v-model="city" id="city" class="form-control" required>
            </div>

            <div class="provinceLabel">
              <label for="province">Province:</label><br>
              <select v-model="province" name="province" id="province">
                <option value="AB">Alberta</option>
                <option value="BC">British Columbia</option>
                <option value="MB">Manitoba</option>
                <option value="NB">New Brunswick</option>
                <option value="NL">Newfoundland and Labrador</option>
                <option value="NT">Northwest Territories</option>
                <option value="NS">Nova Scotia</option>
                <option value="NU">Nunavut</option>
                <option value="ON">Ontario</option>
                <option value="PE">Prince Edward Island</option>
                <option value="QC">Quebec</option>
                <option value="SK">Saskatchewan</option>
                <option value="YK">Yukon</option>
                <option value="Other">Other</option>
              </select>
            </div>

            <div class="countryLabel">
              <label for="country">Country:</label><br>
              <select v-model="country" name="country" id="country">
                <option value="Canada">Canada</option>
                <option value="Other">Other</option>
              </select>
            </div>

            <div class="postalCodeLabel inputPersLabel">
              <label for="postalCode">Postal Code</label>
              <input type="text" v-model="postalCode" id="postalCode" class="form-control" required>
            </div>

          </div>

          <div class="buttonPersonalFrame inputPersLabel">
            <button class="btn-cancel-pers" @click="cancel">Back
            </button>
            <button class="btn-success-pers" @click="customerUpdateInfo(firstname, lastname, civicNumber, street, city, postalCode,
              province, country)">Save Changes
            </button>
          </div>

        </b-tab>
      </b-tabs>

    </div>
    <div class="delete">
      <button class="btn-danger" @click="deleteAcc">Delete Account</button>
    </div>
    <div class="error">
      <p>
        <span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>

  </div>
</template>

<script src="./customer_profile_view_script.js">
export default {
  name: "customer_profile_view"
}
</script>

<style scoped>

#profile_view {
  background-image: linear-gradient(to bottom right, #C6EA8D , #ED1E79);
  padding-bottom: 50vh;
}

.inputFrame {
  font-weight: bold;
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

.libraryCardIDLabel {
  grid-column: span 2;
}

#libraryCardID {
  width: 85%;
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

.provinceLabel, .countryLabel {
  width: 100%;
  height: 70%;
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
