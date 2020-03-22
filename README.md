# UserInformationManagement
User Information Management System

Import the maven project in IDE. (Eclipse/IntelliJ)
Run the class “UserInfoSystemApplication” as SpringBoot application.
By default your services should be up on your local machine at port 8080.


APIs :

METHOD 1 :
POST : /generateUserID
URL : localhost:8080/userInfoMgmt/generateUserID

Takes in Name, email, country, phone number in the request body.
Returns a generated USER_ID.
A user ID should be 10 characters/numbers.

Validations : validation should be done to ensure that:
1. Name is only characters.
2. Email has an @ and a . in the correct order.
3. Phone number only has numbers and dashes.
4. Duplicate emails should throw an error.

Errors returned : 
4 different errors based on the above validations.

REQUEST BODY : 
{
    "name": “John Doe",
    "email": "john.doe1@gmail.com",
    "country": "Canada",
    "phoneNumber": "999999992012"
}

RESPONSE : 
{
    "userID": "19ad783e-1",
    "errorList": null,
    "successMessage": "Successfully generated a new userID."
}














METHOD 2 :
POST : /updateUsageInfo
URL : localhost:8080/userInfoMgmt/updateUsageInfo

Use the userID generated from the method 1 above, and use in this service.

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
    "userID": "19ad783e-1",
    "usageType": "VOICE",
    "timeStamp": "2020/02/25"
}

RESPONSE : 
{
    "errorList": null,
    "successMessage": "The usage details were successfully updated."
}












METHOD 3:
GET : /fetchUsageInfo
URL : localhost:8080/userInfoMgmt/fetchUsageInfo

Use the userID generated from the method 1 above, and use in this service.

Retrieves ALL information about the user’s usage history based
on a time range.
Takes in User Id, Start Date, Type of Data (DATA, VOICE, SMS, ALL) in the request body.

Validations :
If the ID does not exist, an error should be thrown.
The day to fetch information from — should not be future date.

REQUEST :
{
    "userID": "19ad783e-1",
    "startDate": "2020/02/21",
    "usageType": "ALL"
}

RESPONSE : 
{
    "userID": "927efe2b-3",
    "usageDetailsList": [
        [
            "VOICE",
            "2020/02/25"
        ],
        [
            "VOICE",
            "2020/02/25"
        ],
        [
            "DATA",
            "2020/02/25"
        ],
        [
            "DATA",
            "2020/02/25"
        ],
        [
            "SMS",
            "2020/02/25"
        ]
    ],
    "errorString": null
}

REQUEST :
{
    "userID": "553bc6c6-4",
    "startDate": "2020/02/21",
    "usageType": "DATA"
}

RESPONSE :
{
    "userID": "553bc6c6-4",
    "usageDetailsList": [
        [
            "DATA",
            "2020/02/25"
        ]
    ],
    "errorString": null
}

REQUEST : 
{
    "userID": "553bc6c6-4",
    "startDate": "2020/02/21",
    "usageType": "VOICE"
}



RESPONSE :
{
    "userID": "553bc6c6-4",
    "usageDetailsList": [
        [
            "VOICE",
            "2020/02/25"
        ]
    ],
    "errorString": null
}
