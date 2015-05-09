Functionality that IS working in web UI:
    - Show active/completed listings
    - Show listing detail (click on hyperlink listing name)
    - Login (can use username "Mark" and password "password1" in debug mode -or- "Admin" and "password1" in debug or test)
    - Create account (only when not logged in)
    - Create listing (only when logged in)
    - Create bid (only when logged in and listing is not ended)
    - Provide buyer/seller feedback (only when logged in as buyer or high bidder and listing is ended)
    Client side validation:
    - one example in Create New Account page/controller


What is NOT working/implemented:
    - UI/Angular functional tests:
        Somewhere along the way I lost some work, and I just ran out of time messing with karma etc. so I never got
        back to fixing and finishing this
    - "unhappy path" handling:
        graceful/friendly handling of non-OK HTTP responses is not fully implemented
    - Style
        buttons and layouts, fonts, etc. are all functional but 'not pretty'
    - Karma.  Local installation execution failed with “Error java.io.IOException: error while starting karma: java.io.IOException:
        Cannot run program "node_modules/karma/bin/karma": CreateProcess error=2, The system cannot find the file specified”
        I tried changing / to \\ in the paths wherever I could find to do so, I googled with no usable results, tried installing Karma globally,
        all to no avail.  So the karma JavaScriptUnitTestKarmaSuite is deleted, but the one test is still there to look at code if you're so inclined
        (I did create the one example test but I never did get it to run).


Notes:
    - Create Listing "Start Date" does not work;
        I messed with this for an entire day but could not get the HTML/Angular date input
        into a format in javascript that grails would successfully parse, I tried every possible
        permutation of formatting etc. that I could find but always produced an 'unparsable' error.
        So all Listings start at the current date & time they are submitted
        (this is commended in the code for the Listing javascript controller and grails controller)



Requirements:
    - Chrome browser
    - Karma installed globally (?)