<template>
  <div id="customer_login">
    <div class="login_msg">
      <h1>Welcome to Group 20's Library System Application!</h1>
      <br>
      <h3>To continue, please log in below or create an account</h3>
    </div>
    <div class="input">
      <form>
        <label for="username">Username/Email/ID:</label>
        <input type="text" v-model="username" id="username" class="form-control" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" v-model="password" id="password" class="form-control" required>
      </form>
    </div>
    <div class="login_button">
      <button class="btn btn-primary" @click="login(username, password)">Login</button>
    </div>
    <p>
      <span v-if="error" style="color: red">Error: {{error}}</span>
    </p>
  </div>
</template>

<script src="./customer_login_script.js">
export default {
  name: "customer_login"
}
</script>

<style scoped>
.login_msg {
  position: relative;
  text-align: center;
  padding-bottom: 5vh;
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
  padding-bottom: 5vh;
}
</style>
