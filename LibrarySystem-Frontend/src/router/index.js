import Vue from 'vue'
import Router from 'vue-router'
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
import Event_Update from "../components/Event_Update";
import Event_Create from "../components/Event_Create";
import Hour_View from "../components/Hour_View";
import Customer_View from "../components/Customer_View";
import Setup from "../components/Setup";
import Library_View from "../components/Library_View";
import Update_Item from "../components/Update_Item";
import Create_Author from "../components/Create_Author";
import Create_Title from "../components/Create_Title";
import Create_Local from "../components/Create_Local";
import Verify_Page from "../components/Verify_Page";
import Fee_Page from "../components/Fee_Page";
import Hire from "../components/Hire";
import Fire from "../components/Fire";
import Library_edit from "../components/Library_edit";
import System_Address_Edit from "../components/System_Address_Edit";
import System_Hour_Edit from "../components/System_Hour_Edit";


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
      path: '/fees',
      name: 'fee_page',
      component: Fee_Page
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
      path: '/Library',
      name: 'library_view',
      component: Library_View
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
    },
    {
      path: '/HeadLibrarian/Hour',
      name: 'hour_view',
      component: Hour_View
    },
    {
      path: '/home',
      name: 'customer_view',
      component: Customer_View
    },
    {
      path: '/EmployeePage/Management/Update',
      name: 'update_item_view',
      component: Update_Item
    },
    {
      path: '/EmployeePage/Management/Create_Author',
      name: 'create_author_view',
      component: Create_Author
    },
    {
      path: '/EmployeePage/Management/Create_Title',
      name: 'create_title_view',
      component: Create_Title
    },
    {
      path: '/createLocalAccount',
      name: 'create_local',
      component: Create_Local
    },
    {
      path: '/verify',
      name: 'verify_page',
      component: Verify_Page
    },
    {
      path: '/HeadLibrarian/hire',
      name: 'hire',
      component: Hire
    },
    {
      path: '/HeadLibrarian/fire',
      name: 'fire',
      component: Fire
    },
    {
      path: '/Library/edit',
      name: 'library_edit',
      component: Library_edit
    },
    {
      path: '/Library/edit/address',
      name: 'system_address_edit',
      component: System_Address_Edit
    },
    {
      path: '/Library/edit/hours',
      name: 'system_hour_edit',
      component: System_Hour_Edit
    },
  ],
  mode: "history"
})
