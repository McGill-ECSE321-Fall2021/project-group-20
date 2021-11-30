<template>
  <div id="address_edit">
    <div class="bar">
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="/">Menu</b-navbar-brand>
      </b-navbar>
    </div>
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h3>Edit your Library's Address here!</h3>
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
          <input type="text" v-model="postalCode" id="postal" class="form-control" required>
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
            <option value="Other">Other</option>
          </select>
        </b-col>
        <b-col>
          <label for="country">Country:</label><br>
          <select name="country" id="country">
            <option selected value="Canada">Canada</option>
            <option value="Other">Other</option>
          </select>
        </b-col>
      </b-row>
    </b-container>
    <div class="buttons">
      <button class="btn btn-danger" @click="cancel">Cancel</button>&nbsp
      <button class="btn btn-success" @click="update(civic, street, city, postalCode, systemID)">Update Address</button>
    </div>
    <div class="error">
      <p>
        <span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>
  </div>
</template>

<script src="./system_address_edit_script.js">
export default {
  name: "System_address_edit"
}
</script>

<style scoped>

#address_edit {
  font-weight: bold;
  background-image: linear-gradient(to bottom right, #93A5CF, #E4EfE9);
  padding-bottom: 40vh;
}


.msg {
  padding-bottom: 5vh;
}

.input {
  padding-bottom: 5vh;
}

#province {
  width: 100%;
  height: 56%;
}

#country {
  width: 100%;
  height: 56%;
}

.buttons {
  padding-bottom: 5vh;
}

.btn-danger {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #ab0303;
}

.btn-success {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #03a634;
}
</style>
