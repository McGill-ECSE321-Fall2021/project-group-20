import customerTest from "./CustomerTest.js";
import employeeTest from "./EmployeeTest.js";
import addressTest from "./AddressTest.js";
import authorTest from "./AuthorTest.js";
import titleTest from "./TitleTest.js";
import itemTest from "./ItemTest.js";
import calendarTest from "./CalendarTest.js";
import librarySystemTest from "./LibrarySystemTest.js";
import hourTest from "./HourTest.js";
import NewspaperTest from "./NewspaperTest.js";
import ArchiveTest from "./ArchiveTest.js";
import eventTest from "./EventTest.js";
import bookingTest from "./BookingTest.js";
import MusicAlbumTest from "./MusicAlbumTest.js";
import MovieTest from "./MovieTest.js";
import BookTest from "./BookTest.js";

console.log("");

customerTest().then( () => {
    employeeTest().then( () => {
        addressTest().then ( () => {
            authorTest().then( () => {
                titleTest().then ( () => {
                  itemTest().then ( () => {
                      calendarTest().then ( () => {
                          librarySystemTest().then ( () => {
                            hourTest().then( () => {
								NewspaperTest().then( () => {
									ArchiveTest().then( () => {
                                        eventTest().then( () => {
											bookingTest().then( () => {
												MusicAlbumTest().then( () => {
													MovieTest().then( () => {
														BookTest();
													})
												})
											})
										})
                                    })
								})
							})
						  })
					  })
				  })
				})
			})
		})
	})
})
