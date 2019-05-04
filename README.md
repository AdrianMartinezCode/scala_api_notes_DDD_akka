This project is an example of akka http service. Is a REST service.

The endpoints planned to define are:

NOTE: {idu} = idUser and {idr} = idNote

POST /users/adduser
REQUEST {
    name: String
}

GET /users/{

DELETE /users/{idu}/deleteuser
REQUEST {}


PUT /users/{idu}
REQUEST {
    name: String
}


POST /users/{idu}/addnote
REQUEST {
    name: String,
    text: String
}

DELETE /users/{idu}/{idn}
REQUEST {}

PUT /users/{idu}/