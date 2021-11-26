<template>
  <div id="employee_login">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="login_msg">
      <h1>Welcome to Group 20's Library System Application!</h1>
      <br>
      <h3>Restricted Access to Employees Only!</h3>
      <h3>If you came here by accident: please return to the regular login page</h3>
    </div>
    <div class="input">
      <form>
        <label for="username">
          <label for="loginType">Choose method of login:</label>
          <select id="loginType">
            <option selected value="name">Username/Email</option>
            <option value="id">Library Card ID</option>
          </select>
        </label>
        <input type="text" v-model="username" id="username" class="form-control" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" v-model="password" id="password" class="form-control" required>
      </form>
    </div>
    <div class="error">
      <p>
        <span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>
    <div class="login_button">
      <button class="btn btn-success" @click="login(username, password)">Login</button>
      <br><br>
      <button class="btn btn-danger" @click="back">Return</button>
    </div>
    <div class="easter_egg">
      <img src="../assets/Signature.png" style="width:35%; height:auto;">
    </div>
  </div>
</template>

<script src="./employee_login_script.js">
export default {
  name: "employee_login"
}
</script>

<style scoped>
#employee_login {
  background-color: rgba(66, 185, 131, 0.91);
}

.login_msg {
  position: relative;
  text-align: center;
  padding-bottom: 5vh;
  padding-top: 5vh;
}

.input {
  position: relative;
  align-content: center;
  padding-left: 30vw;
  padding-right: 30vw;
  padding-bottom: 5vh;
}

.login_button {
  position: relative;
  align-content: center;
  padding-bottom: 3vh;
}

.easter_egg {
  position: relative;
  align-content: center;
  padding-top: 5vh;
  padding-bottom: 15vh;
}
</style>
