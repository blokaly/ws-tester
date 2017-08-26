## ws-tester

A Web Socket Tester using [Java Spark Framework](http://sparkjava.com) and [Create React App](https://github.com/facebookincubator/create-react-app)

To manually test some reactions driven by web socket messages, 
ws clients can point to this test server (ws://localhost:4567/juno) then users can
send mock test messages, such as json data, to the ws clients by typing in the test
messages on the react-app web page.


### Quick Start

Clone this repo and then run:

`> cd server`

`> ./gradlew run`

Then open [http://localhost:4567](http://localhost:4567)

### Manual Build

You can run client and server separately, see README.md files under client/server folders.

Also you can run `> yarn build` under client folder, then copy the client/build folder to
server/src/main/resources/public, then only run the java spark server.
