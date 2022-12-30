This project is an example of a REST API service.

NOTE: pending to be refactored, because the exchanging messages between the different databases and different 
services can cause race conditions. One feasible implementation to handle this casuistic is assigning an actor 
for each bounded context. The bad news of this approach are that we will have bottlenecks, and may not be scalable
horizontally.


The architecture is based on akka actors:

HTTP Level has their own actors to handle the incoming HTTP requests,
then, I added two more levels of actors to handle the concurrency using
a DDD approach, separating the notes and users bounded contexts.

So, the second level of actors are the services, that they act as an internal
logic command handlers (in a hexagonal architecture) to avoid the dependencies,
needs to encapsulate the Service declaration from the different HttpControllers.
In fact, every service is an actor, handling one message at a time, avoiding
possible concurrent problems, like the third layer of actors.

The third layer of actors is the database layer, that we have one actor per
database, with this approach we can avoid different race conditions and simplify
the logic.

With this in mind we can draw the graph of dependencies between the actors:

![Actors Diagram](https://github.com/AdrianMartinezCode/scalarestapinotes/blob/master/resources/actors_diagram.jpg?raw=true)



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


