import alt from '../core/alt';
import MessageActions from '../actions/MessageActions'

class MessageStore {
   constructor() {
      this.bindListeners({
         insertMessage: MessageActions.insertMessage
      });

      this.state = {
         messages: []
      };
   }

   insertMessage(message) {
      this.setState({messages: this.state.messages.concat(message)});
   }
}

export default alt.createStore(MessageStore, 'MessageStore');