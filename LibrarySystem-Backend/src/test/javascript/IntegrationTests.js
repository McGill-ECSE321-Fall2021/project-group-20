import customerTest from "./CustomerTest.js";
import employeeTest from "./EmployeeTest.js";

console.log("");

customerTest().then( () => {
    employeeTest();
})