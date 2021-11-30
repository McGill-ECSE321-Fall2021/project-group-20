<template>
  <div id="fee_page">
    <div class="header">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h2><b>Process Fee Payments</b></h2>
    </div>
    <div class="table">
      <h5><b>List of accounts with outstanding balances</b></h5>
      <table>
        <tr>
          <th>ID</th>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Username</th>
          <th>Email</th>
          <th>Fees</th>
        </tr>
        <tr v-for="account in accounts">
          <td>{{account.libraryCardID}}</td>
          <td>{{account.firstName}}</td>
          <td>{{account.lastName}}</td>
          <td>{{account.username}}</td>
          <td>{{account.email}}</td>
          <td>{{account.outstandingBalance}}</td>
        </tr>
      </table>
    </div>
    <div class="choose">
      <label for="select"><b>Select account to update balance:</b></label><br>
      <select v-model="select" name="select" id="select">
        <option v-for="account in accounts" :value="account.libraryCardID">{{account.username}}</option>
      </select>
    </div>
    <div class="amount">
      <label for="amount"><b>Amount to update:</b></label>
      <input type="text" v-model="amount" id="amount" class="form-control" required>
    </div>
    <div class="error_msg">
      <p>
        <span v-if="error" style="color: red">{{error}}</span>
      </p>
    </div>
    <div class="confirm_button">
      <button class="btn btn-danger" @click="cancel">Cancel</button>&nbsp
      <button class="btn btn-success" @click="update(amount)">Update Account Balance</button>
    </div>
  </div>
</template>

<script src="./fee_script.js">
export default {
  name: "fee_page"
}
</script>

<style scoped>

#fee_page {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 50vh;
}
.msg {
  padding-top: 2vh;
}

.table {
  padding-top: 2vh;
  padding-bottom: 3vh;
}

table {
  align-content: center;
  margin-left: auto;
  margin-right: auto;
}

.choose {
  padding-bottom: 3vh;
}

#select {
  width: 40%;
}

.amount {
  padding-bottom: 3vh;
  margin-left: 30%;
  margin-right: 30%;
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
  width: 250px;
  height: 55px;
  color: #FDEDEC;
  background-color: #03a634;
}


</style>
