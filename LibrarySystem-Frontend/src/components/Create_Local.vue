<template>
  <div id="create_account">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h1>Management Page to create a customer's offline account</h1>
      <br>
      <h3>Please fill out the information required and submit</h3>
    </div>
    <b-container class="input">
      <b-row>
        <b-col>
          <label for="firstname">Firstname:</label>
          <input type="text" v-model="firstname" id="firstname" class="form-control" required>
        </b-col>
        <b-col>
          <label for="lastname">Lastname:</label>
          <input type="text" v-model="lastname" id="lastname" class="form-control" required>
        </b-col>
      </b-row><br>
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
      </b-row><br>
    </b-container>
    <div class="error">
      <p>
        <span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>
    <div class="buttons">
      <button class="btn btn-danger" @click="cancel">Cancel</button>&nbsp
      <button class="btn btn-success" @click="create(firstname, lastname, civic, street, city, postal)">Create Account</button>
    </div>
  </div>
</template>

<script src="./create_local_script.js">
export default {
  name: "create_local"
}
</script>

<style scoped>
#create_account {
  background-image: linear-gradient(to bottom right, #C6EA8D , #ED1E79);
  padding-bottom: 50vh;
}

.input {
  padding-bottom: 3vh;
  font-weight: bold;
}

#province {
  width: 100%;
  height: 56%;
}

#country {
  width: 100%;
  height: 56%;
}

.btn-danger {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #ab0303;
}

.btn-success{
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #14820c;
}

.msg {
  padding-top: 2vh;
  padding-bottom: 5vh;
}

.input {
  padding-bottom: 5vh;
  text-align: left;
}

.buttons {
  padding-bottom: 20vh;
}

.error {
  padding-bottom: 5vh;
}
</style>
