import axios from "axios";

const customerTest = async () => {
    let remainingTests = 15;
    let resultData;
    let resultStatus;
    let id;
    let idO;

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
    Integration Test 1: Create a local customer Pass
     */
    try {
        let payload = "firstname=John&lastname=Doe&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada";
        let response = await axios.post("http://localhost:8080/customer/createLocal?" + payload)

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.firstName === "John" && resultData.lastName === "Doe" && resultStatus === 200) {
            remainingTests--;
            id = resultData.libraryCardID;
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
        let payload = "?civicNumber=1&street=University&city=Montreal&postalCode=&W2E3E3&province=QC&country=Canada";
        let response = await axios.post("http://localhost:8080/librarySystem/create" + payload);

        payload = "firstname=John&lastname=Doe&email=a@a.ca&username=johndoe&password=12345678&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada";
        response = await axios.post("http://localhost:8080/customer/create?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.firstName === "John" && resultData.lastName === "Doe" && resultData.username === "johndoe" && resultData.email === "a@a.ca" && resultStatus === 200) {
            remainingTests--;
            idO = resultData.libraryCardID;
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

        if (resultData.libraryCardID === idO && resultData.isLoggedIn && resultStatus === 200) {
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
        let response = await axios.put("http://localhost:8080/customer/login/" + idO + "?password=12345678");

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.libraryCardID === idO && resultData.isLoggedIn && resultStatus === 200) {
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
        let response = await axios.put("http://localhost:8080/customer/logout/" + idO);

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
        let response = await axios.put("http://localhost:8080/customer/balance/" + id + "?toModify=50")

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.libraryCardID === id && resultData.outstandingBalance === 50) {
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
        let response = await axios.put("http://localhost:8080/customer/validate/" + idO)

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.libraryCardID === idO && resultData.isVerified) {
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
        let response = await axios.delete("http://localhost:8080/customer/" + idO)

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed test 11: Delete customer");
            console.log("Error: " + resultData);
            console.log("");
        }
        else if (resultData.toString() === "Customer with ID " + idO + " has been successfully deleted") {
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
        let response = await axios.put("http://localhost:8080/customer/convert/" + id + "?username=jjjj&password=12345678&email=a.b@c.d")

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
    Integration Test 13: Update Customer Online Info Pass
     */
    try {
        let response = await axios.put("http://localhost:8080/customer/updateOnline/" + id + "?username=alex&password=12345678&email=a@z.y")

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.username === "alex" && resultData.email === "a@z.y") remainingTests--;
        else {
            console.log("Failed test 13: Update Customer Online Info Pass");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 13: Update Customer Online Info Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Integration Test 14: Update Customer Info Pass
     */
    try {
        let payload = "firstName=Alex&lastName=John&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada";
        let response = await axios.put("http://localhost:8080/customer/update/" + id + "?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.firstName === "Alex" && resultData.lastName === "John" && resultData.libraryCardID === id) remainingTests--;
        else {
            console.log("Failed test 14: Update Customer Info Pass");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed test 14: Update Customer Info Pass");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Customer Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};

export default customerTest;