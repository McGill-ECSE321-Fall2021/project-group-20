<template>
  <div id="convert_account">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h1>Management Page to convert a customer's offline account</h1>
      <br>
      <p><b>Default password is 'password', advice customer to change on first login!</b></p>
    </div>
    <div class="table">
      <table>
        <tr>
          <th>ID</th>
          <th>First Name</th>
          <th>Last Name</th>
        </tr>
        <tr v-for="account in customers">
          <td>{{account.libraryCardID}}</td>
          <td>{{account.firstName}}</td>
          <td>{{account.lastName}}</td>
        </tr>
      </table>
    </div>
    <div class="dropdown">
      <label for="select">Select account to convert:</label><br>
      <select v-model="select" name="select" id="select">
        <option v-for="account in customers" :value="account.libraryCardID">{{account.firstName}}  {{account.lastName}}</option>
      </select>
    </div>
    <div class="inputs">
      <input type="text" v-model="username" id="username" class="form-control" required placeholder="Username"><br>
      <input type="text" v-model="email" id="email" class="form-control" required placeholder="Email">
    </div>
    <div class="error">
      <p>
        <br><span v-if="error" style="color: red">{{error}}</span>
      </p>
    </div>
    <div class="buttons">
      <button class="btn btn-danger" @click="cancel">Cancel</button>&nbsp
      <button class="btn btn-success" @click="convert(username, email)">Convert Account</button>
    </div>
  </div>
</template>

<script src="./convert_local_script.js">
export default {
  name: "convert_local_account"
}
</script>

<style scoped>
#convert_account {
  background-image: linear-gradient(to bottom right, #C6EA8D , #ED1E79);
  padding-bottom: 50vh;
}

table {
  margin-left: auto;
  margin-right: auto;
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

.dropdown {
  padding-bottom: 1vh;
}

.buttons {
  padding-bottom: 20vh;
}

.error {
  padding-bottom: 5vh;
}

.inputs {
  margin-left: auto;
  margin-right: auto;
  width: 60%;
  height: auto;
}
</style>
