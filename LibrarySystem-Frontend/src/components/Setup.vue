<template>
<div id="setup">
  <div class="header_img">
    <img src="../assets/LS.png" style="width:20vh; height:auto;">
  </div>
  <div class="msg">
    <h3>Welcome to Group 20's Library System Application's initial setup wizard!</h3>
    <br>
    <br>
    <p><b>To setup your Library System, please fill out the following address information<br>and a new
    Head Librarian account will also be created for you with the following information:</b></p>
    <br>
    <p><b>Username:</b> headlibrarian     |     <b>Password:</b> headlibrarian</p>
    <br>
    <p><u>Please change the password ASAP after your first login!</u></p>
  </div>
  <b-container class="input">
    <b-row>
      <b-col>
        <label for="civic">Civic Number:</label>
        <input type="text" v-model="civic" id="civic" class="form-control" required>
      </b-col>
      <b-col>
        <label for="street">Street:</label>
        <input type="text" v-model="street" id="street" class="form-control" required>
      </b-col>
      <b-col>
        <label for="city">City:</label>
        <input type="text" v-model="city" id="city" class="form-control" required>
      </b-col>
    </b-row><br>
    <b-row>
      <b-col>
        <label for="postal">Postal Code:</label>
        <input type="text" v-model="postal" id="postal" class="form-control" required>
      </b-col>
      <b-col>
        <label for="province">Province:</label><br>
        <select name="province" id="province">
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
          <option selected value="QC">Quebec</option>
          <option value="SK">Saskatchewan</option>
          <option value="YK">Yukon</option>
        </select>
      </b-col>
      <b-col>
        <label for="country">Country:</label><br>
        <select name="country" id="country">
          <option selected value="Canada">Canada</option>
        </select>
      </b-col>
    </b-row><br>
    <b-row>
      <b-col>
        <label for="firstname">Firstname:</label>
        <input type="text" v-model="firstname" id="firstname" class="form-control" required>
      </b-col>
      <b-col>
        <label for="lastname">Lastname:</label>
        <input type="text" v-model="lastname" id="lastname" class="form-control" required>
      </b-col>
      <b-col>
        <label for="email">Email:</label>
        <input type="text" v-model="email" id="email" class="form-control" required>
      </b-col>
    </b-row>
  </b-container>
  <div class="error">
    <p>
      <span v-if="error" style="color: red">{{error}}</span>
    </p>
  </div>
  <div class="button">
    <button class="btn btn-success" @click="create(firstname, lastname, email, civic, street, city, postal)">Initialize System</button>
  </div>
</div>
</template>

<script src="./setup_script.js">
export default {
  name: "setup"
}
</script>

<style scoped>
.msg {
  padding-bottom: 1vh;
}

#province {
  width: 100%;
  height: 56%;
}

#country {
  width: 100%;
  height: 56%;
}

.input {
  padding-bottom: 5vh;
}

.error {
  padding-bottom: 3vh;
}
</style>
