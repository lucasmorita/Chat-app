# Objective 
This is a project for learning, so I designed a webchat that uses WebSockets for real time messages. 

## Stack
For the frontend, I'm using:
- React
- Vite
- Tailwind with DaisyUI

For the backend:
- Ktor
- PostgreSQL
- Planning to use Cassandra for storing messages

## To-dos:
- [x] Add tests
- [x] Change Cookie session storage from In memory to a database
- [x] Add button for creating a room
- [x] Alter chat session table to use session id as primary key
- [ ] Fix refreshing page losing cookie user_session
- [ ] Cache fetch rooms
- [ ] Store messages in Cassandra
  - [ ] Setup replication between nodes
  - [ ] Add node in a different machine
- [ ] Support user preferences
- [ ] Change password
- [ ] Update password
- [ ] Add Observability
