# UserInformationManagement
User Information Management System

APIs :

METHOD 1 :
POST : /generateUserID
URL : localhost:8080/userInfoMgmt/generateUserID

Takes in Name, email, country, phone number in the request body.
Returns a generated USER_ID.
A user ID should be 10 characters/numbers.

Validations : validation should be done to ensure that:
§ Name is only characters;
§ Email has an @ and a . in the correct order.;
§ Phone number only has numbers and dashes.
o Duplicate emails should throw an error to the terminal

Errors returned : 
4 different errors based on the above validations.


REQUEST BODY : 
{
    "name": "JohnDoe",
    "email": "john.doe1@gmail.com",
    "country": "Canada",
    "phoneNumber": "999999992012"
}

RESPONSE : 
The generated userID is : c2403fc3-d




METHOD 2 :
POST : /updateUsageInfo
URL : localhost:8080/userInfoMgmt/updateUsageInfo

Takes in User Id, Type of usage: DATA, VOICE SMS, Timestamp
(Date in the YYYY/MM/DD format) in the request body.

Validations :
If the ID does not exist, an error should be thrown.
If a non-existent parameter is entered, an error should be thrown.
Date validation so that it is a valid date in the past.

Errors returned : 
3 different errors based on the above validations.


REQUEST :
{
    "userID": "a8c1415c-3",
    "usageType": "SMS",
    "timeStamp": "2020/02/25"
}

RESPONSE : 
The usage details were updated successfully !!



METHOD 3:
GET : /fetchUsageInfo
URL : localhost:8080/userInfoMgmt/fetchUsageInfo

Retrieves ALL information about the user’s usage history based
on a time range.
Takes in User Id, Start Date, Type of Data (DATA, VOICE, SMS, ALL) in the request body.

Validations :
If the ID does not exist, an error should be thrown.
The day to fetch information from — should not be future date.

REQUEST :
{
    "userID": "a8c1415c-3",
    "startDate": "2020/02/21",
    "usageType": "DATA"
}

RESPONSE : 
The usageInfo details for the userID a8c1415c-3are : [[DATA, 2020/02/21], [DATA, 2020/02/24]]

