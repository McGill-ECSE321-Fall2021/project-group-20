import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Customer_Login from '@/components/Customer_Login';
import Employee_Login from "@/components/Employee_Login";
import Create_Account from "@/components/Create_Account";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'customer_login',
      component: Customer_Login
    },
    {
      path: '/home',
      name: 'hello',
      component: Hello
    },
    {
      path: '/employee',
      name: 'employee_login',
      component: Employee_Login
    },
    {
      path: '/create',
      name: 'create_account',
      component: Create_Account
    }
  ],
  mode: "history"
})
