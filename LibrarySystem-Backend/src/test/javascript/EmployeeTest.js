import axios from "axios";

const employeeTest = async () => {
    let resultData;
    let resultStatus;
    let remainingTests = 11;
    let id;

    /*
    Clean database for testing (Test 0)
     */
    try {
        let response = await axios.delete("http://localhost:8080/librarySystem/clear?confirmbool=true");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Database has been wiped")) remainingTests--;
        else {
            console.log("Failed DB Clear");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed DB Clear");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 1: Create Employee Pass
     */
    try {
        let payload = "firstName=John&lastName=Doe&civic=1&street=University&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada" +
            "&email=a@c.de&username=johnboy&password=12345678&role=Librarian";
        let response = await axios.post("http://localhost:8080/employee/create?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.firstName === "John" && resultData.lastName === "Doe" && resultData.username === "johnboy" && resultData.email === "a@c.de") {
            remainingTests--;
            id = resultData.libraryCardID;
        }
        else {
            console.log("Failed Test 1: Create Employee Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 1: Create Employee Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 2: Change Employee Demerit Pts Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/employee/demerit/" + id + "?toChange=2");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.demeritPts === 2) remainingTests--;
        else {
            console.log("Failed Test 2: Change Employee Demerit Pts Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 2: Change Employee Demerit Pts Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 3: Change Employee Balance Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/employee/balance/" + id + "?toChange=50");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.outstandingBalance === 50) remainingTests--;
        else {
            console.log("Failed Test 3: Change Employee Balance Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 3: CChange Employee Balance Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 4: Login Name Pass
    */
    try {
        let response = await axios.put("http://localhost:8080/employee/login?name=a@c.de&password=12345678");

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.libraryCardID === 3 && resultData.isLoggedIn && resultStatus === 200) {
            remainingTests--;
        } else {
            console.log("Failed Test 4: Employee Login Name Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 4: Employee Login Name Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 5: Logout Name Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/employee/logout?name=johnboy");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed Test 5: Employee Logout Name Pass");
            console.log("Error: " + resultData);
            console.log("");
        } else if (resultData.toString() === "Logged out") {
            remainingTests--;
        }
    } catch (errorMsg) {
        console.log("Failed Test 5: Employee Logout Name Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 6: Login ID Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/employee/login/" + id + "?password=12345678");

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.libraryCardID === 3 && resultData.isLoggedIn && resultStatus === 200) {
            remainingTests--;
        } else {
            console.log("Failed Test 6: Employee Login ID Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 6: Employee Login ID Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 7: Logout ID Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/employee/logout/" + id);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed Test 7: Employee Logout ID Pass");
            console.log("Error: " + resultData);
            console.log("");
        } else if (resultData.toString() === "Logged out") {
            remainingTests--;
        }
    } catch (errorMsg) {
        console.log("Failed Test 7: Employee Logout ID Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 8: Update Online Info Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/employee/updateOnline/" + id + "?username=alex&password=12345678&email=a@z.y");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.username === "alex" && resultData.email === "a@z.y" && resultData.libraryCardID === id) remainingTests--;
        else {
            console.log("Failed test 8: Update Employee Online Info Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 8: Update Employee Online Info Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 9: Update Info Pass
     */
    try {
        let payload = "?firstName=Alex&lastName=John&civic=1&street=University&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada"
        let response = await axios.put("http://localhost:8080/employee/update/" + id + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.firstName === "Alex" && resultData.lastName === "John" && resultData.libraryCardID === id) remainingTests--;
        else {
            console.log("Failed test 9: Update Employee Info Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 9: Update Employee Info Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 10: Delete Pass
     */
    try {
        let response = await axios.delete("http://localhost:8080/employee/" + id);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString() === ("Employee with id " + id + " has been deleted")) remainingTests--;
        else {
            console.log("Failed test 10: Delete Employee Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 10: Delete Employee Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Employee Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");

};

export default employeeTest;