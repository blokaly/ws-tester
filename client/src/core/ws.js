import ReconnectingWebSocket from 'reconnecting-websocket';
import MessageActions from '../actions/MessageActions';

const rws = new ReconnectingWebSocket('ws://localhost:4567/juno');

rws.addEventListener('open', () => {

});

rws.addEventListener('message', (message) => {
   MessageActions.insertMessage(message.data);
});

export const sendMessage = (message) => {
   rws.send(message);
};