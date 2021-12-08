<template>
  <div id="customer_login">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20%; height:auto;">
    </div>
    <div class="login_msg">
      <h2>Welcome to Group 20's LibraS!</h2>
    </div>
    <div class="input">
      <form>
        <label for="username">
          <label for="loginType">Choose login method:</label>
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
      <button class="btn btn-primary" @click="create">Create Account</button>
      <button class="btn btn-danger" @click="employee">Employee Login</button>
    </div>
    <div class="easter_egg">
      <img src="../assets/Signature.png" style="width:35%; height:auto;">
    </div>
  </div>
</template>

<script src="./customer_login_script.js">
export default {
  name: "customer_login"
}
</script>

<style scoped>
#customer_login {
  background-color: rgb(255, 216, 167);
  position: fixed;
  overflow: hidden;
}

.login_msg {
  position: relative;
  text-align: center;
  padding-bottom: 2vh;
  padding-top: 5vh;
}

.input {
  position: relative;
  align-content: center;
  padding-left: 30vw;
  padding-right: 30vw;
  padding-bottom: 3vh;
}

.login_button {
  position: relative;
  align-content: center;
  padding-top: 2vh;
  padding-bottom: 3vh;
}

.easter_egg {
  position: relative;
  align-content: center;
  padding-top: 7vh;
  padding-bottom: 15vh;
}
</style>
