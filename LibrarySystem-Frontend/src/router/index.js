import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Customer_Login from '@/components/Customer_Login';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Customer_Login',
      component: Customer_Login
    },
    {
      path: '/hello',
      name: 'Hello',
      component: Hello
    }
  ]
})
