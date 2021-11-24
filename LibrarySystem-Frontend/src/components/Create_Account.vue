<template>
  <div id="create_account">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h1>Welcome to Group 20's Library System Application!</h1>
      <br>
      <h3>To create an account, please fill out the information required and submit</h3>
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
          <label for="province">Province:</label>
          <input type="text" v-model="province" id="province" class="form-control" required>
        </b-col>
        <b-col>
          <label for="country">Country:</label>
          <input type="text" v-model="country" id="country" class="form-control" required>
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
    <div class="buttons">
      <button class="btn btn-danger" @click="cancel">Cancel</button>&nbsp
      <button class="btn btn-success" @click="create(firstname, lastname, email, username, password, civic, street, city, postal, province, country)">Create Account</button>
    </div>
    <p>
      <span v-if="error" style="color: red">Error: {{error}}</span>
    </p>
  </div>
</template>

<script src="./create_account_script.js">
export default {
  name: "create_account"
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
</style>
