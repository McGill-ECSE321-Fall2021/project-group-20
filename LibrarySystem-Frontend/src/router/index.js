import Vue from 'vue'
import Router from 'vue-router'

import Customer_Login from "../components/Customer_Login.vue";
import Employee_Login from "../components/Employee_Login.vue";
import Create_Account from "../components/Create_Account.vue";
import Employee_View from "../components/Employee_View.vue";
import Booking_View from "../components/Booking_View.vue";
import Management_View from "../components/Management_View.vue";
import Event_View from "../components/Event_View.vue";
import Schedule_View from "../components/Schedule_View.vue";
import Profile_View from "../components/Profile_View.vue";
import HeadLibrarian_View from "../components/HeadLibrarian_View.vue";
import Customer_profile_view from "../components/Customer_profile_view.vue";
import Event_Update from "../components/Event_Update.vue";
import Event_Create from "../components/Event_Create.vue";
import Customer_View from "../components/Customer_View.vue";
import Setup from "../components/Setup.vue";
import Library_View from "../components/Library_View.vue";
import Update_Item from "../components/Update_Item.vue";
import Create_Author from "../components/Create_Author.vue";
import Create_Title from "../components/Create_Title.vue";
import Create_Local from "../components/Create_Local.vue";
import Verify_Page from "../components/Verify_Page.vue";
import Fee_Page from "../components/Fee_Page.vue";
import Hire from "../components/Hire.vue";
import Fire from "../components/Fire.vue";
import Library_edit from "../components/Library_edit.vue";
import System_Address_Edit from "../components/System_Address_Edit.vue";
import System_Hour_Edit from "../components/System_Hour_Edit.vue";
import UpdateBooking_View from "../components/UpdateBooking_View.vue";
import Personal_Schedule from "../components/Personal_Schedule.vue";
import EmployeeBooking_View from "../components/EmployeeBooking_View.vue";
import HeadLibrarianBooking_View from "../components/HeadLibrarianBooking_View.vue";
import Convert_Local_Account from "../components/Convert_Local_Account.vue";

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
      component: EmployeeBooking_View
    },
    {
      path: '/home/Booking',
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
      path: '/schedules',
      name: 'schedule_view',
      component: Schedule_View
    },
    {
      path: '/schedule',
      name: 'personal_schedule',
      component: Personal_Schedule
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
      path: '/home/profile',
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
    {
      path: '/home/Booking/UpdateBooking',
      name:'UpdateBooking_View',
      component:UpdateBooking_View
    },
    {
      path: '/account/convert',
      name: 'convert_local_account',
      component: Convert_Local_Account
    },
    {
      path: '/HeadLibrarian/Booking',
      name: 'booking_view',
      component: HeadLibrarianBooking_View
    }
  ]
})
