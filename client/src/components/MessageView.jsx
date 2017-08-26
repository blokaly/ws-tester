import React, {Component} from 'react';
import {Container, List, Button, Form, TextArea, Message} from 'semantic-ui-react'
import {sendMessage} from '../core/ws';

class MessageView extends Component {
   constructor(props) {
      super(props);
      this.send = this.send.bind(this);
   }

   send() {
      sendMessage(this._input.ref.value);
      this._input.ref.value = null;
   }

   render() {
      return (
         <Container text className="App-container">
            <Form>
               <TextArea autoHeight placeholder='Message...' rows={3} ref={input => this._input = input}/>
            </Form>
            <p/>
            <Button primary onClick={this.send}>Send</Button>

            <List>
            {this.props.messages.map((message, i) => {
               return (
                  <List.Item key={i}>
                     <Message>{message}</Message>
                  </List.Item>
               );
            })}
            </List>
         </Container>
      );
   }
}

export default MessageView;