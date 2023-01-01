This project is an example of a distributed REST API service with DDD approach.


![Actors Diagram](https://github.com/AdrianMartinezCode/scalarestapinotes/blob/master/resources/bounded_context_diagram.jpg?raw=true)

We have an actor Pool where every actor have their own segment of the database.

Every database manages their assigned entities preventing race conditions at write 
or read operations and scaling through the number of entities

The function of the oracle is to provide the reference of the actor by the correspondent
idEntity, and next, making the operation the correspondent actor.

The entities are assigned to their database by Round Robin method.

If we want to make another call to another bounded context, we can call directly to the
other wrapped context and the other bounded context have the same logic.

So, the dependencies actors hierarchy is:
![Actors Diagram](https://github.com/AdrianMartinezCode/scalarestapinotes/blob/master/resources/actor_dependencies_diagram.jpg?raw=true)

Evidently, this is the messages actors dependencies, at the software view we respect the SOLID principles.

With this approach we can prevent the race conditions on the read/write operations because every database
actor is the owner of their entities, and only this actor is who can modify these.

To extend this to a real databases we should make a database by actor and another for the oracle.

Will the oracle have bottlenecks? Probably yes if we choose a slow database, but if we use a fast read database
like cassandra we can avoid any bottlenecks that can appear.


-----------------------------------------------
OLD

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


