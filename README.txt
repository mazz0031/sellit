Functionality working in web UI:
    - Show active/completed listings
    - Show listing detail (click on hyperlink listing name)
    - Login (can use username "Mark" and password "password1" in debug mode)
    - Create account
    - Create listing

    - Client side validation example in Create New Account

Not yet done:
    - Create bid
    - Provide buyer/seller feedback


No Angular tests written yet; only functional tests of REST controllers


Notes:
    - Create Listing "Start Date" does not work;
        I messed with this for an entire day but could not get the HTML/Angular date input
        into a format in javascript that grails would successfully parse, I tried every possible
        permutation of formatting etc. that I could find but always produced an 'unparsable' error.
        So all Listings start at the current date & time they are submitted
        (this is commended in the code for the Listing javascript controller and grails controller)



Requirements:
    - Chrome browser