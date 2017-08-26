import React, {Component} from 'react';
import AltContainer from 'alt-container';
import MessageStore from '../store/MessageStore';
import MessageView from '../components/MessageView';
import MessageActions from '../actions/MessageActions';

class App extends Component {
   render() {
      return (
         <AltContainer store={MessageStore} actions={MessageActions}>
            <MessageView/>
         </AltContainer>
      );
   }
}

export default App;