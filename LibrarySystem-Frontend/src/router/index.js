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
import Customer_profile_view from "../components/Customer_profile_view";
import Event_Create from "../components/Event_Create";
import Event_Update from "../components/Event_Update";
import Setup from "../components/Setup";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'customer_login',
      component: Customer_Login
    },
    {
      path: '/setup',
      name: 'setup',
      component: Setup
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
      path: '/Employee/Profile',
      name: 'profile_view',
      component: Profile_View
    },
    {
      path: '/HeadLibrarian',
      name: 'headlibrarian_view',
      component: HeadLibrarian_View
    },
    {
      path: '/HeadLibrarian/Organize',
      name: 'organize_view',
      component: Organize_View
    },
    {
      path: '/Customer/Profile',
      name: 'customer_profile_view',
      component: Customer_profile_view
    },

    {
      path: '/EmployeePage/Event/Update',
      name: 'event_update',
      component: Event_Update
    },
    {
      path: '/EmployeePage/Event/Create',
      name: 'event_create',
      component: Event_Create
    }
  ],
  mode: "history"
})
