This project is an example of akka http service. Is a REST service.

The endpoints planned to define are:

NOTE: {idu} = idUser and {idr} = idNote

### USERS

POST /users/adduser
````
REQUEST 
{
    name: String
}
RESPONSE {
    idUser: long
}
````

GET /users/list
````
RESPONSE {
    [
        {
            name: String,
            idUser: long
        },
        {
            ...
        }, ...
    ]
}
````

GET /users/{idu}
````
RESPONSE {
    name: long
}
````

DELETE /users/{idu}/deleteuser
````
REQUEST {}
````



PUT /users/{idu}
````
REQUEST {
    name: String
}
````

### NOTES

POST /users/{idu}/addnote
````
REQUEST {
    name: String,
    text: String
}
RESPONSE {
    idNote: long
}
````

GET /users/{idu}/allnotes
````
RESPONSE {
    [
        {
            idNote: long,
            name: String,
            text: String
        },
        {
            ...
        }, ...
    ]
}
````

GET /users/{idu}/{idn}
````
RESPONSE {
    idNote: long,
    name: String,
    text: String
}
````

DELETE /users/{idu}/{idn}
````
REQUEST {}
````

PUT /users/{idu}/{idn}
````
REQUEST {
    name: String,
    text: String
}
````


