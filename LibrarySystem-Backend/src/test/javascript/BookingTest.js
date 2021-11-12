import axios from "axios";

const bookingTest = async () => {
    let remainingTests = 8;
    let resultData;
    let resultStatus;
    let id;
    let iid;
    let cid;
    let authorid;
    let titleid;

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
    Test 1: Create Booking
     */
    try {
        let payload = "?civicNumber=1&street=University&city=Montreal&postalCode=&W2E3E3&province=QC&country=Canada";
        let response = await axios.post("http://localhost:8080/librarySystem/create" + payload);

        payload = "firstname=John&lastname=Doe&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada";
        response = await axios.post("http://localhost:8080/customer/createLocal?" + payload)

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.firstName === "John" && resultData.lastName === "Doe" && resultStatus === 200) {
            cid = resultData.libraryCardID;
        } else {
            console.log("Failed Test 1: Customer createLocal for booking");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Doe");

        if (response.status === 200) authorid = response.data.authorID;

        else {
            console.log("Failed Test 1: Could not create author for booking");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/title/create?name=Book&pubDate=11/10/2020&authors=" + authorid);

        if (response.status === 200)  {
            titleid = response.data.titleID;
        }
        else {
            console.log("Failed Test 1: Create Title for booking");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/items/create?status=Available&titleId=" + titleid);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.status.toString().includes("Available") && resultData.title.titleID === titleid) {
            iid = resultData.itemBarcode;
        }
        else {
            console.log("Failed Test 1: Create Item");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/booking/create?startDate=12/12/2021&endDate=12/25/2021&type=Reservation&barcode=" + iid + "&LibraryId=" + cid);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.type.toString() === "Reservation" && resultData.item.itemBarcode === iid && resultData.user.libraryCardID === cid) {
            id = resultData.bookingID;
            remainingTests--;
        } else {
            console.log("Failed Test 1: Create Booking");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 1: Create Booking");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 2: Update start date
     */
    try {
        let response = await axios.put("http://localhost:8080/booking/updatebyID/startDate?bid=" + id + "&startDate=02/02/2023");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.startDate.toString() === "2023-02-02") remainingTests--;
        else {
            console.log("Failed Test 2: Update booking start date");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 2: Update booking start date");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 3: Update end date
     */
    try {
        let response = await axios.put("http://localhost:8080/booking/updatebyID/endDate?bid=" + id + "&endDate=03/03/2023");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.startDate.toString() === "2023-03-03") remainingTests--;
        else {
            console.log("Failed Test 3: Update booking end date");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 3: Update booking end date");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 4: Update Type
     */
    try {
        let response = await axios.put("http://localhost:8080/booking/updatebyID/type?bid=" + id + "&type=Borrow");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.type.toString() === "Borrow") remainingTests--;
        else {
            console.log("Failed Test 4: Update booking type");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 4: Update booking type");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 5: Update User
     */
    try {
        let payload = "firstname=John&lastname=Doey&civic=1&street=Univeristy&city=Montreal&postalCode=H2X1D3&province=QC&country=Canada";
        let response = await axios.post("http://localhost:8080/customer/createLocal?" + payload)

        resultData = response.data;
        resultStatus = response.status;

        if (resultData.firstName === "John" && resultData.lastName === "Doey" && resultStatus === 200) {
            cid = resultData.libraryCardID;
        } else {
            console.log("Failed Test 5: Customer createLocal for booking");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = await axios.put("http://localhost:8080/booking/updatebyID/user?bid=" + id + "&libId=" + cid);
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.user.libraryCardID === cid) remainingTests--;
        else {
            console.log("Failed Test 5: Update booking user");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 5: Update booking user");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 6: Delete Booking by ID
     */
    try {
        let response = await axios.delete("http://localhost:8080/booking/id?bid=" + id);
        resultData = response.data;
        resultStatus = response.status;
        if (resultStatus === 200 && resultData.toString().includes("Booking has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 6: Delete booking");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 6: Delete booking");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 7: Return Item by item
     */
    try {
        let response = await axios.post("http://localhost:8080/booking/create?startDate=12/12/2021&endDate=12/25/2021&type=Reservation&barcode=" + iid + "&LibraryId=" + cid);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.type.toString() === "Reservation" && resultData.item.itemBarcode === iid && resultData.user.libraryCardID === cid) {
            id = resultData.bookingID;
        } else {
            console.log("Failed Test 1: Create Booking");
            console.log("Error: " + resultData);
            console.log("");
        }

        response = await axios.put("http://localhost:8080/booking/return/item?barcode=" + iid);
        resultData = response.data;
        resultStatus = response.status;
        if (resultStatus === 200 && resultData.toString().includes("Item has been returned & booking removed")) remainingTests--;
        else {
            console.log("Failed Test 7: Return item");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 7: Return item");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Booking Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};
export default bookingTest;