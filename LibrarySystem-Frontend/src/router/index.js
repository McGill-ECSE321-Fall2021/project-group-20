import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Customer_Login from "../components/Customer_Login";
import Employee_Login from "../components/Employee_Login";
import Create_Account from "../components/Create_Account";
import Employee_View from "../components/Employee_View";
import Booking_View from "../components/Booking_View";
import Management_View from "../components/Management_View";
import Event_View from "../components/Event_View";
import Schedule_View from "../components/Schedule_View";
import Profile_View from "../components/Profile_View";
import HeadLibrarian_View from "../components/HeadLibrarian_View";
import Organize_View from "../components/Organize_View";

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
    },

    {
      path: '/EmployeePage',
      name: 'employee_view',
      component: Employee_View
    },
    {
      path: '/EmployeePage/Booking',
      name: 'booking_view',
      component: Booking_View
    },
    {
      path: '/EmployeePage/Management',
      name: 'management_view',
      component: Management_View
    },
    {
      path: '/EmployeePage/Event',
      name: 'event_view',
      component: Event_View
    },
    {
      path: '/EmployeePage/Schedule',
      name: 'schedule_view',
      component: Schedule_View
    },
    {
      path: '/EmployeePage/Profile',
      name: 'profile_view',
      component: Profile_View
    },
    {
      path: '/HeadLibrarian',
      name: 'headlibrarian_view',
      component: HeadLibrarian_View
    },
    {
      path: 'HeadLibrarian/Organize',
      name: 'organize_view',
      component: Organize_View
    }

  ],

  mode: "history"
})
