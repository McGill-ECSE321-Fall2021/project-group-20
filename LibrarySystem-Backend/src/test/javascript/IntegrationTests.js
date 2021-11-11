import customerTest from "./CustomerTest.js";
import employeeTest from "./EmployeeTest.js";
import addressTest from "./AddressTest.js";
import authorTest from "./AuthorTest.js";
import titleTest from "./TitleTest.js";
import itemTest from "./ItemTest.js";

console.log("");

customerTest().then( () => {
    employeeTest().then( () => {
        addressTest().then ( () => {
            authorTest().then( () => {
                titleTest().then ( () => {
                  itemTest();
                })
            })
        })
    })
})