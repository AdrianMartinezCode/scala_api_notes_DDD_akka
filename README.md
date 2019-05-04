This project is an example of akka http service. Is a REST service.

The endpoints planned to define are:

NOTE: {idu} = idUser and {idr} = idNote

### USERS

![#0000ff](https://placehold.it/15/3349ff/000000?text=+) POST /users/adduser
````
REQUEST 
{
    name: String
}
RESPONSE {
    idUser: long
}
````

![#00ff00](https://placehold.it/15/00ff00/000000?text=+) GET /users/list
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

![#00ff00](https://placehold.it/15/00ff00/000000?text=+) GET /users/{idu}
````
RESPONSE {
    name: long
}
````

![#ff0000](https://placehold.it/15/ff0000/000000?text=+) DELETE /users/{idu}/deleteuser
````
REQUEST {}
````



![#ffff00](https://placehold.it/15/ffff00/000000?text=+) PUT /users/{idu}
````
REQUEST {
    name: String
}
````

### NOTES

![#0000ff](https://placehold.it/15/3349ff/000000?text=+) POST /users/{idu}/addnote
````
REQUEST {
    name: String,
    text: String
}
RESPONSE {
    idNote: long
}
````

![#00ff00](https://placehold.it/15/00ff00/000000?text=+) GET /users/{idu}/allnotes
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

![#00ff00](https://placehold.it/15/00ff00/000000?text=+) GET /users/{idu}/{idn}
````
RESPONSE {
    idNote: long,
    name: String,
    text: String
}
````

![#ff0000](https://placehold.it/15/ff0000/000000?text=+) DELETE /users/{idu}/{idn}
````
REQUEST {}
````

![#ffff00](https://placehold.it/15/ffff00/000000?text=+) PUT /users/{idu}/{idn}
````
REQUEST {
    name: String,
    text: String
}
````


