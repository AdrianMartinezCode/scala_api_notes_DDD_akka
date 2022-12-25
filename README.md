This project is an example of akka http service. Is a REST service.

The endpoints planned to define are:

NOTE: {idu} = idUser and {idr} = idNote

### USERS

POST /user
````
REQUEST 
{
    name: String
}
RESPONSE {
    idUser: String
}
````

GET /user
````
RESPONSE {
    [
        {
            name: String,
            idUser: String,
            notes: [
                {
                    name: String,
                    idNote: String,
                    text: String,
                },
                ...
            ]
        },
        {
            ...
        }, ...
    ]
}
````

GET /user/{idu}
````
RESPONSE {
    idUser: String,
    name: String,
    notes: [
        {
            name: String,
            idNote: String,
            text: String,
        },
        ...
    ]
}
````

DELETE /user/{idu}
````
REQUEST {}
````



PUT /user/{idu}
````
REQUEST {
    name: String
}
````

### NOTES

POST /note
````
REQUEST {
    name: String,
    text: String,
    idUser: String
}
RESPONSE {
    idNote: String
}
````

GET /note
````
RESPONSE {
    [
        {
            idNote: String,
            name: String,
            text: String,
            idUser: String
        },
        {
            ...
        }, ...
    ]
}
````

GET /note/{idn}
````
RESPONSE {
    idNote: String,
    name: String,
    text: String,
    idUser: String
}
````

DELETE /note/{idn}
````
REQUEST {}
````

PUT /note/{idn}
````
REQUEST {
    name: String,
    text: String,
    idUser: String
}
````


