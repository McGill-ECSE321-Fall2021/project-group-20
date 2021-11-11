import axios from "axios";

const hourTest = async () => {
    let remainingTests = 8;
    let resultData;
    let resultStatus;
    let eid;
    let cid;
    let weekdy;
    let event;

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
    Test 1: Create Hour
     */
    try {
        let payload = "firstName=John&lastName=Doe&civic=1&street=University&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada" +
            "&email=a@c.de&username=johnboy&password=12345678&role=Librarian";
        let response = await axios.post("http://localhost:8080/employee/create?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.firstName === "John" && resultData.lastName === "Doe" && resultData.username === "johnboy" && resultData.email === "a@c.de") {
            eid = resultData.libraryCardID;
        }
        else {
            console.log("Failed Test 1: Create Employee for Create Hour");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/calendar/create");

        if (response.status === 200 && response.data !== null) {
            cid = response.data.calendarID;
        }
        else {
            console.log("Failed test 1: Create calendar for Create Hour");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/hour/create?weekday=Monday&startTime=12:00:00&endTime=16:00:00&EmployeeId=" + eid + "&calendarId=" + cid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.weekday === "Monday" && resultData.employee.libraryCardID === eid && resultData.calendar.calendarID === cid) {
            weekdy = "Monday";
            remainingTests--;
        }
        else {
            console.log("Failed Test 1: Create Hour");
            console.log("Error: " + resultData);
            console.log();
        }

    } catch (errorMsg) {
        console.log("Failed Test 1: Create Hour");
        console.log("Error: " + errorMsg.response.data);
        console.log();
    }

    /*
    Test 2: Change Hour Employee
     */
    try {
        let payload = "firstName=John&lastName=Doey&civic=1&street=University&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada" +
            "&email=a@c.com&username=johnnny&password=12345678&role=Librarian";
        let response = await axios.post("http://localhost:8080/employee/create?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.firstName === "John" && resultData.lastName === "Doey" && resultData.username === "johnnny" && resultData.email === "a@c.com") {
            eid = resultData.libraryCardID;
        }
        else {
            console.log("Failed Test 2: Create Employee for Change Event");
            console.log("Error: " + resultData);
            console.log("");
        }
        response = await axios.put("http://localhost:8080/hour/update/employee?weekday=Monday&newEmployeeID=" + eid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.employee.libraryCardID === eid) remainingTests--;
        else {
            console.log("Failed Test 2: Update Hour Employee");
            console.log("Error: " + resultData);
            console.log();
        }

    } catch (errorMsg) {
        console.log("Failed Test 2: Update Hour Employee");
        console.log("Error: " + errorMsg.response.data);
        console.log();
    }

    /*
    Test 3: Update Hour End Time
     */
    try {
        let response = await axios.put("http://localhost:8080/hour/update/endTime?weekday=Monday&endTime=18:00:00");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.weekday === "Monday" && resultData.endTime.toString() === "18:00:00") remainingTests--;
        else {
            console.log("Failed Test 3: Update Hour end time");
            console.log("Error: " + resultData);
            console.log();
        }
    } catch (errorMsg) {
        console.log("Failed Test 3: Update Hour end time");
        console.log("Error: " + errorMsg.response.data);
        console.log();
    }

    /*
    Test 4: Update Hour Start Time
     */
    try {
        let response = await axios.put("http://localhost:8080/hour/update/startTime?weekday=Monday&startTime=14:00:00");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.weekday === "Monday" && resultData.startTime.toString() === "14:00:00") remainingTests--;
        else {
            console.log("Failed Test 4: Update Hour start time");
            console.log("Error: " + resultData);
            console.log();
        }
    } catch (errorMsg) {
        console.log("Failed Test 4: Update Hour start time");
        console.log("Error: " + errorMsg.response.data);
        console.log();
    }

    /*
    Test 5: Delete Hour by weekday
     */
    try {
        let response = await axios.delete("http://localhost:8080/hour/weekday?weekday=Monday");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Hour has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 5: Delete Hour by weekday");
            console.log("Error: " + resultData);
            console.log();
        }
    } catch (errorMsg) {
        console.log("Failed Test 5: Delete Hour by weekday");
        console.log("Error: " + errorMsg.response.data);
        console.log();
    }

    /*
    Test 6: Delete Hour by Employee
     */
    try {
        let response = await axios.post("http://localhost:8080/hour/create?weekday=Monday&startTime=12:00:00&endTime=16:00:00&EmployeeId=" + eid + "&calendarId=" + cid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.weekday === "Monday" && resultData.employee.libraryCardID === eid && resultData.calendar.calendarID === cid) weekdy = "Monday";
        else {
            console.log("Failed Test 1: Create Hour");
            console.log("Error: " + resultData);
            console.log();
        }

        response = await axios.delete("http://localhost:8080/hour/" + eid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Hour has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 6: Delete Hour by employee");
            console.log("Error: " + resultData);
            console.log();
        }
    } catch (errorMsg) {
        console.log("Failed Test 6: Delete Hour by employee");
        console.log("Error: " + errorMsg.response.data);
        console.log();
    }

    /*
    Test 7: Delete Hour By Event
     */
    try {
        let response = await axios.post("http://localhost:8080/event/create?name=test&date=01/10/2022&weekday=Tuesday&startTime=12:00:00&endTime=14:00:00&employeeUserName=johnnny&calendarID=" + cid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200) event = resultData.eventID;
        else {
            console.log("Failed Test 7: Create Event for Delete Hour by Event");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = response = await axios.delete("http://localhost:8080/hour/event/" + event);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Hour has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 7: Delete Hour by Event");
            console.log("Error: " + resultData);
            console.log();
        }
    } catch (errorMsg) {
        console.log("Failed Test 7: Delete Hour by Event");
        console.log("Error: " + errorMsg.response.data);
        console.log();
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Hour Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};

export default hourTest;