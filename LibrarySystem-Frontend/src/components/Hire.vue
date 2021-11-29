<template>
  <div id="hire">
    <div class="header">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h2>Hire a new Librarian!</h2>
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
        <b-col>
          <label for="email">Email:</label>
          <input type="text" v-model="email" id="email" class="form-control" required>
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
      <b-row>
        <b-col>
          <label for="username">Username:</label>
          <input type="text" v-model="username" id="username" class="form-control" required>
        </b-col>
        <b-col>
          <label for="password">Password:</label>
          <input type="password" v-model="password" id="password" class="form-control" required>
        </b-col>
        <b-col>
          <label for="password_conf">Confirm Password:</label>
          <input type="password" v-model="password_conf" id="password_conf" class="form-control" required>
        </b-col>
      </b-row>
    </b-container>
    <div class="error">
      <p>
        <span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>
    <div class="buttons">
      <button class="btn btn-danger" @click="cancel">Cancel</button>&nbsp
      <button class="btn btn-success" @click="create(firstname, lastname, email, username, password, password_conf, civic, street, city, postal)">Complete Hiring Process</button>
    </div>
  </div>
</template>

<script src="./hire_script.js">
export default {
  name: "hire"
}
</script>

<style scoped>
.msg {
  padding-bottom: 3vh;
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
  padding-bottom: 3vh;
}

.buttons {
  padding-top: 3vh;
}
</style>
