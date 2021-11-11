import axios from "axios";

const eventTest = async () => {
    let remainingTests = 8;
    let resultData;
    let resultStatus;
    let eventID;
    let eid;
    let cid;

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
    Test 1: Create Event
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
            console.log("Failed Test 1: Create Employee for Create Event");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/calendar/create");

        if (response.status === 200 && response.data !== null) {
            cid = response.data.calendarID;
        }
        else {
            console.log("Failed test 1: Create calendar for Create Event");
            console.log("Error: " + response.data);
            console.log("");
        }
        response = await axios.post("http://localhost:8080/event/create?name=test&date=01/10/2022&weekday=Tuesday&startTime=12:00:00&endTime=14:00:00&employeeUserName=johnboy&calendarID=" + cid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200) {
            eventID = resultData.eventID;
            remainingTests--
        }
        else {
            console.log("Failed Test 1: Create Event");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 1: Create event");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 2: Update event date
     */
    try {
        let response = await axios.put("http://localhost:8080/event/update/date?id=" + eventID + "&date=12/12/2021");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.eventID === eventID && resultData.eventDate.toString() === "2021-12-12") remainingTests--;
        else {
            console.log("Failed Test 2: Update Event date");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 2: Update Event date");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 3: Update event hour
     */
    try {
        let response = await axios.put("http://localhost:8080/event/update/hour?upweekday=Monday&id=" + eventID + "&upstartTime=12:00:00&upendTime=13:00:00" +
            "&employeeUserName=johnboy&calendarID=" + cid);
        resultData = response.data;
        resultStatus = response.status;
        if (resultStatus === 200 && resultData.eventID === eventID) remainingTests--;
        else {
            console.log("Failed Test 3: Update Event hour");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 3: Update Event hour");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 4: Update event name
     */
    try {
        let response = await axios.put("http://localhost:8080/event/update/name?id=" + eventID + "&updatedName=update");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.eventID === eventID && resultData.name === "update") remainingTests--;
        else {
            console.log("Failed Test 4: Update Event name");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 4: Update Event name");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 5: Delete event by date
     */
    try {
        let response = await axios.delete("http://localhost:8080/event/date?date=12/12/2021")
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Event has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 5: delete Event by date");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 5: delete Event by date");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 6: Delete event by ID
     */
    try {
        let response = await axios.post("http://localhost:8080/event/create?name=test2&date=01/10/2022&weekday=Wednesday&startTime=12:00:00&endTime=14:00:00&employeeUserName=johnboy&calendarID=" + cid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200) {
            eventID = resultData.eventID;
        }
        else {
            console.log("Failed Test 6: Create Event for Delete by ID");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = await axios.delete("http://localhost:8080/event/" + eventID);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Event has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 6: Delete by ID");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 6: Delete by ID");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 7: Delete event by hour
     */
    try {
        let response = await axios.post("http://localhost:8080/event/create?name=test2&date=01/10/2022&weekday=Friday&startTime=12:00:00&endTime=14:00:00&employeeUserName=johnboy&calendarID=" + cid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200) {
            eventID = resultData.eventID;
        }
        else {
            console.log("Failed Test 6: Create Event for Delete by ID");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = await axios.delete("http://localhost:8080/event/hour?weekday=Friday");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Event has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 7: Delete by hour");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 7: Delete by hour");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Event Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};
export default eventTest;