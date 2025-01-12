COSC2657
Lollipop team

For LoginActivity(testing):
Email: test@example.com
Password: test_password



Application: RelationshipApp

Github repo: https://github.com/thanhthienbui/Lollipop-team-Android-As3.git

Team members:

Le Tuan Khai - s3978908

Ngo Tra Tam Khoa - s3978419
  
Huynh Bao Khang - s3911723
  
Bui Hong Thanh Thien - s3878323

Team: Lollipop

Activities:


LoginActivity

EventTrackingActivity

MapActivity

GiftDetailActivity

GiftPageActivity

Other java classes:

GiftPageAdapter.java

Location


Database folder:



DatabaseHelper.java

NotesDbHelper.java





Functionality:


MainActivity- this is the main screen where users can get access to the app, it will show the 1st anniversary on screen and display "You and Claire have been married for 365 days"


LoginActivity is to get users an account to access the mainActivity


GiftDetailActivity - this is the UI where users can see the lines of love sentences when each gift is being selected.


GiftPageActivity - this is where gift images are in the center and can be navigated through to see different types of gifts.


EventTrackingActivity - this is the timepicker activity where users can set a time in hour:minute am/pm format


MapActivity - this activity will show a map with pins of locations including hotels, restaurants, etc. 



Roles of each members


Bui Hong Thanh Thien - MapActivity and the databases that are related



Le Tuan Khai - EventTrackingActivity, LoginActivity(the function provided for Thien's loginActivity UI)



Ngo Tra Tam Khoa - MainActivity(HomePage)



Huynh Bao Khang - GiftPageActivity, GiftDetailActivity



Contribution score in %:




Bui Hong Thanh Thien - 25%

Le Tuan Khai - 25%

Ngo Tra Tam Khoa - 25%

Huynh Bao Khang - 25%



Technology use:



SQLLite


OnCompleteListener


OnSuccessListener


OnFailureListener


AppCompatActivity


RecyclerView


RadioGroup


RadioButton



TimePicker


RecyclerView


Calendar


Animation


SimpleDateFormat


gms.maps.GoogleMap


PackageManager


Bundle


SearchView


TextView


Checkbox


Cursor


AlertDialog


Notification


Medium Phone API 34

OPEN ISSUES and BUGS: 

LoginActivity: When the username is entered, it appeared to said that "username does not exist" when Toast. And we decided to use SQLLite instead of Firebase to sync the data. Unfortunately, the data from the username textView wasnt syncing up with the program. So it is an error that would not let us access the MainActivity.

SignUpActivity: the problem is related above as well. Since the program can't take the username and password, creating one through SignUpActivity would be disastrous and would create more problematic errors that would not make the program successful to run.













