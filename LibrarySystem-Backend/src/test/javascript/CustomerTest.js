import axios from "axios";

const customerTest = async () => {
    let remainingTests = 12;
    let resultData;
    let resultStatus;

    /*
    Integration Test 1: Create a local customer Pass
     */
    try {
        let payload = "firstname=John&lastname=Doe&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=CA";
        let response = await axios.post("http://localhost:8080/customer/createLocal?" + payload)

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.firstName === "John" && resultData.lastName === "Doe" && resultData.libraryCardID === 1 && resultStatus === 200) {
            remainingTests--;
        } else {
            console.log("Failed Test 1: Customer createLocal");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed Test 1: Customer createLocal");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 2: Create a local customer Illegal Input
     */
    try {
        let payload = "firstname=&lastname=Doe&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=CA";
        let response = await axios.post("http://localhost:8080/customer/createLocal?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus !== 400) {
            console.log("Failed Test 2: Customer createLocal Illegal Input");
            console.log(resultData);
            console.log("");
        }

    } catch (errorMsg) {
        if (errorMsg.response.status === 400 && errorMsg.response.data.toString() === "Please enter a valid name") {
            remainingTests--;
        } else {
            console.log("Failed Test 2: Customer createLocal Illegal input");
            console.log("Error: " + errorMsg.response.data);
            console.log("");
        }
    }

    /*
    Integration Test 3: Create an online customer Pass
     */
    try {
        let payload = "firstname=John&lastname=Doe&email=a@a.ca&username=johndoe&password=12345678&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada";
        let response = await axios.post("http://localhost:8080/customer/create?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.firstName === "John" && resultData.lastName === "Doe" && resultData.username === "johndoe" && resultData.email === "a@a.ca" && resultData.libraryCardID === 2 && resultStatus === 200) {
            remainingTests--;
        } else {
            console.log("Failed Test 3: Customer create online");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed Test 3: Customer create online");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 4: Create an online customer Fail: email in use
     */
    try {
        let payload = "firstname=John&lastname=Doe&email=a@a.ca&username=johndoe&password=12345678&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada";
        let response = await axios.post("http://localhost:8080/customer/create?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus !== 400) {
            console.log("Failed Test 4: Create an online customer Fail: email in use");
            console.log(resultData);
            console.log("");
        }

    } catch (errorMsg) {
        if (errorMsg.response.status === 400 && errorMsg.response.data.toString() === "Email in use, please enter a new email") {
            remainingTests--;
        } else {
            console.log("Failed Test 4: Create an online customer Fail: email in use");
            console.log("Error: " + errorMsg.response.data);
            console.log("");
        }
    }

    /*
    Integration Test 5: Login Name Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/customer/login?name=a@a.ca&password=12345678");

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.libraryCardID === 2 && resultData.isLoggedIn && resultStatus === 200) {
            remainingTests--;
        } else {
            console.log("Failed Test 5: Login Name Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 5: Login Name Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 6: Logout Name Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/customer/logout?name=johndoe");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed Test 6: Logout Name Pass");
            console.log("Error: " + resultData);
            console.log("");
        } else if (resultData.toString() === "Logged out") {
            remainingTests--;
        }
    } catch (errorMsg) {
        console.log("Failed Test 6: Logout Name Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 7: Login ID Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/customer/login/2?password=12345678");

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.libraryCardID === 2 && resultData.isLoggedIn && resultStatus === 200) {
            remainingTests--;
        } else {
            console.log("Failed Test 7: Login ID Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 7: Login ID Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 8: Logout ID Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/customer/logout/2");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed Test 8: Logout ID Pass");
            console.log("Error: " + resultData);
            console.log("");
        } else if (resultData.toString() === "Logged out") {
            remainingTests--;
        }
    } catch (errorMsg) {
        console.log("Failed Test 8: Logout ID Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 9: Modify Balance Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/customer/balance/1?toModify=50")

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.libraryCardID === 1 && resultData.outstandingBalance === 50) {
            remainingTests--;
        }
        else {
            console.log("Failed test 9: Modify balance pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 9: Modify balance pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 10: Validate Customer Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/customer/validate/2")

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.libraryCardID === 2 && resultData.isVerified) {
            remainingTests--;
        }
        else {
            console.log("Failed test 10: Validate Customer pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 10: Validate Customer pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 11: Delete Customer
     */
    try {
        let response = await axios.delete("http://localhost:8080/customer/2")

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed test 11: Delete customer");
            console.log("Error: " + resultData);
            console.log("");
        }
        else if (resultData.toString() === "Customer with ID 2 has been successfully deleted") {
            remainingTests--;
        }
    } catch (errorMsg) {
        console.log("Failed test 11: Delete customer");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 12: Convert Local Customer Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/customer/convert/1?username=jjjj&password=12345678&email=a.b@c.d")

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.username === "jjjj" && resultData.email === "a.b@c.d") {
            remainingTests--;
        }
        else {
            console.log("Failed test 12: Convert Local Customer Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 12: Convert Local Customer Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all 12 Customer Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};

export default customerTest;